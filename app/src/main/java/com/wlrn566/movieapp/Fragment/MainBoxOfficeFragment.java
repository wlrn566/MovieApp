package com.wlrn566.movieapp.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wlrn566.movieapp.BuildConfig;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.adapter.MovieListAdapter;
import com.wlrn566.movieapp.vo.MovieDetailVO;
import com.wlrn566.movieapp.vo.MovieVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainBoxOfficeFragment extends Fragment {
    private final String TAG = getClass().getName();
    private View rootView;

    private HashMap<String, MovieVO> map = new HashMap<>();

    RecyclerView rv;

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_main_box_office, container, false);

        rv = rootView.findViewById(R.id.rv);

        // 영화 순위 출력 (영화진흥위원회 api)
        loadBoxOffice();

        return rootView;
    }

    private void loadBoxOffice() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String boxOffice_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
        final String key = BuildConfig.KOBIS_KEY;
        final String targetDt = getYesterday();

        String url = boxOffice_url + "?key=" + key + "&targetDt=" + targetDt;

        Log.d("loadBoxOffice", "url = " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("loadBoxOffice", "response = " + response);
                        try {
                            // 박스오피스 순위리스트 추출
                            JSONObject boxOfficeResult = response.getJSONObject("boxOfficeResult");
                            JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");
                            // 행, 순위, 영화제목, 개봉일, 누적관객수, 영화코드 추출  -> VO로 묶어버리기
                            for (int i = 0; i < dailyBoxOfficeList.length(); i++) {
                                String rank = dailyBoxOfficeList.getJSONObject(i).getString("rank");
                                String movieCd = dailyBoxOfficeList.getJSONObject(i).getString("movieCd");
                                String movieNm = dailyBoxOfficeList.getJSONObject(i).getString("movieNm");
                                String openDt = dailyBoxOfficeList.getJSONObject(i).getString("openDt");
                                String audiAcc = dailyBoxOfficeList.getJSONObject(i).getString("audiAcc");
//                                Log.d("loadBoxOffice", rank + "등 movieNm = " + movieNm + " openDt = " + openDt + " audiAcc = " + audiAcc + " movieCd = " + movieCd);

                                // 제작년도 찾기 (네이버 api 에서 영화를 정확히 검색하기 위함)
                                findPrdtYear(rank, movieCd, movieNm, openDt, audiAcc);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.d(TAG, "error = " + e.toString());
                    }
                });

        request.setShouldCache(false);
        queue.add(request);
    }

    private void findPrdtYear(String rank, String movieCd, String movieNm, String openDt, String audiAcc) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String searchMovieInfo_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
        final String key = BuildConfig.KOBIS_KEY;
        String url = searchMovieInfo_url + "?key=" + key + "&movieCd=" + movieCd;

        Log.d("findPrdtYear", "url = " + url);

        // api 호출
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("findPrdtYear", "response = " + response);
                        try {
                            // 영화 상세정보에서 제작년도만 추출
                            String prdtYear = response.getJSONObject("movieInfoResult").getJSONObject("movieInfo").getString("prdtYear");
//                            Log.d("findPrdtYear", "movieNm = " + movieNm + " prdtYear = " + prdtYear);

                            // 네이버 api 를 호출 -> 포스터, 감독, 배우, 평점 추출
                            serchNAVER(rank, movieNm, openDt, audiAcc, prdtYear);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.d(TAG, "error = " + e.toString());
                    }
                }) {
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    private void serchNAVER(String rank, String movieNm, String openDt, String audiAcc, String prdtYear) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String yearfrom = String.valueOf(Integer.parseInt(prdtYear) - 1);  // 제작년도가 영화진흥위원회 정보랑 네이버검색과 다른 데이터가 있기에 오차(-1)설정 해줌
        final String url = "https://openapi.naver.com/v1/search/movie.json?query=" + movieNm + "&yearfrom=" + yearfrom + "&yearto=" + prdtYear;
        final String NAVER_Client_ID = BuildConfig.NAVER_Client_ID;
        final String NAVER_Client_Secret = BuildConfig.NAVER_Client_Secret;

        Log.d("loadImage", "url = " + url);

        // api 호출
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("loadImage", "response = " + response);
                        try {
                            JSONArray items = response.getJSONArray("items");
                            JSONObject item = items.getJSONObject(0);

                            String image = item.getString("image");  // 포스터
                            String pubDate = item.getString("pubDate");  // 제작연도
                            String director = item.getString("director").substring(0, item.getString("director").length() - 1); // 감독
                            String actor = (!item.getString("actor").equals("") ? item.getString("actor").substring(0, item.getString("actor").length() - 1) : ""); // 배우
                            String userRating = item.getString("userRating"); // 평점
//                            Log.d("loadImage", "item = " + item);

                            // 배우 데이터 사이에 | 표시 바꿔주기
                            String actor_replace = actor.replace("|", " ");
                            MovieVO mvo = new MovieVO(rank, movieNm, openDt, audiAcc, pubDate, image, director, actor_replace, userRating);
                            // 순위대로 map에 담아서 어댑터에 넘기기
                            map.put(rank, mvo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // 리사이클러뷰 구현
                        setRecycler(map);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.d(TAG, "error = " + e.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-Naver-Client-Id", NAVER_Client_ID);
                params.put("X-Naver-Client-Secret", NAVER_Client_Secret);
                return params;
            }
        };

        request.setShouldCache(false);
        queue.add(request);
    }

    // 리사이클러뷰 세팅
    private void setRecycler(HashMap<String, MovieVO> map) {
        Log.d(TAG, "setRecycler");
        // 리사이클러뷰의 데이터가 변했을 때 사이즈를 동일하게 갱신하게 함
        rv.setHasFixedSize(true);
        // 리사이클러뷰 레이아웃
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        // 어댑터 할당
        MovieListAdapter movieListAdapter = new MovieListAdapter(getActivity(), map);
        rv.setAdapter(movieListAdapter);
        // 어댑터 클릭리스너 할당 -> 영화 제목을 넘겨서 네이버 검색 api 호출
        movieListAdapter.setOnClickListener(new MovieListAdapter.onClickListener() {
            @Override
            public void onClickListener(View v, String movieNm) {
                Log.d(TAG, "click movie = " + movieNm);
                // Bundle 로 영화이름 넘겨주기
                Bundle bundle = new Bundle();
                bundle.putString("movieNm", movieNm);
                MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                movieDetailFragment.setArguments(bundle);  // bundle set
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_activity, movieDetailFragment)
                        .addToBackStack(null)  // 현재 프래그먼트를 스택에 추가하기
                        .commit();
            }
        });

    }

    // 어제 날짜 구하기
    private String getYesterday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        // Calendar 함수의 add 이용
        // Calendar.DATE 로 오늘을 구하고 하루를 빼줌
        calendar.add(Calendar.DATE, -1);
        String result = simpleDateFormat.format(calendar.getTime());
        return result;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }
}