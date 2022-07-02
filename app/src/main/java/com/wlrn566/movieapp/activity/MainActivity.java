package com.wlrn566.movieapp.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.adapter.MovieListAdapter;
import com.wlrn566.movieapp.vo.MovieVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();

    RecyclerView rv;

    // 1. 영화진흥위원회 api 사용(volley) -> 현재 상영 영화데이터 불러오기 -> 리사이클러뷰로 출력
    // 2. 리스트 클릭 시 상세페이지
    // 3. 커뮤니티페이지 -> 회원가입 및 로그인 (mariaDB + php)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);

        // 영화 api 불러오기
        loadMovies();
    }

    private void loadMovies() {
        RequestQueue queue = Volley.newRequestQueue(this);

        final String boxOffice_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
        final String key = getString(R.string.kobis_key);
        final String targetDt = getYesterday();

        String url = boxOffice_url + "?key=" + key + "&targetDt=" + targetDt;

        Log.d(TAG, "url = " + url);

        // api 호출 및 json 으로 받기
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, "response = " + response);
                        ArrayList<MovieVO> mvoList = new ArrayList<>();
                        try {
                            // 박스오피스 순위리스트 추출
                            JSONObject boxOfficeResult = response.getJSONObject("boxOfficeResult");
                            JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");
                            // 행, 순위, 영화제목, 개봉일, 누적관객수, 영화코드 추출  -> VO로 묶어버리기
                            for (int i = 0; i < dailyBoxOfficeList.length(); i++) {
                                String rnum = dailyBoxOfficeList.getJSONObject(i).getString("rnum");
                                String rank = dailyBoxOfficeList.getJSONObject(i).getString("rank");
                                String movieCd = dailyBoxOfficeList.getJSONObject(i).getString("movieCd");
                                String movieNm = dailyBoxOfficeList.getJSONObject(i).getString("movieNm");
                                String openDt = dailyBoxOfficeList.getJSONObject(i).getString("openDt");
                                String audiAcc = dailyBoxOfficeList.getJSONObject(i).getString("audiAcc");
                                Log.d(TAG, rank + "등 movieNm = " + movieNm + " openDt = " + openDt + " audiAcc = " + audiAcc + " movieCd = " + movieCd);

                                MovieVO mvo = new MovieVO(rnum, rank, movieCd, movieNm, openDt, audiAcc);
                                mvoList.add(mvo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // 리사이클러뷰 구현
                        setRecycler(mvoList);
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

    // 리사이클러뷰 세팅
    private void setRecycler(ArrayList<MovieVO> mvoList) {
        Log.d(TAG, "setRecycler");
        // 리사이클러뷰의 데이터가 변했을 때 사이즈를 동일하게 갱신하게 함
        rv.setHasFixedSize(true);
        // 리사이클러뷰 레이아웃
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        // 어댑터 할당
        MovieListAdapter movieListAdapter = new MovieListAdapter(this, mvoList);
        rv.setAdapter(movieListAdapter);
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
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}