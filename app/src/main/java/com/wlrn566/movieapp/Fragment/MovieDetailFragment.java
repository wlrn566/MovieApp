package com.wlrn566.movieapp.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wlrn566.movieapp.BuildConfig;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.vo.MovieDetailVO;
import com.wlrn566.movieapp.vo.MovieVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieDetailFragment extends Fragment {
    private final String TAG = getClass().getName();
    private View rootView;
    private String movieNm;

    private TextView title_tv;

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieNm = getArguments().getString("movieNm");
            Log.d(TAG, "movieNm = " + movieNm);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // 영화 상세 내용 출력 (네이버 영화검색 api)
        loadDetail();

        return rootView;
    }

    private void loadDetail() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String url = "https://openapi.naver.com/v1/search/movie.json?query=" + movieNm;
        final String NAVER_Client_ID = BuildConfig.NAVER_Client_ID;
        final String NAVER_Client_Secret = BuildConfig.NAVER_Client_Secret;

        Log.d(TAG, "url = " + url);

        // api 호출
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "response = " + response);
                        ArrayList<MovieDetailVO> mdVOList = new ArrayList<>();
                        try {
                            // 박스오피스 순위리스트 추출
                            JSONArray items = response.getJSONArray("items");
                            JSONObject item = items.getJSONObject(0);
//                            Log.d(TAG, "item = " + item);

                            String title = item.getString("title").substring(3, item.getString("title").length() - 4);  // 한글명
                            String image = item.getString("image");  // 포스터
                            String subtitle = item.getString("subtitle");  // 영문
                            String pubDate = item.getString("pubDate");  // 제작연도
                            String director = item.getString("director").substring(0, item.getString("director").length() - 1); // 감독
                            String actor = item.getString("actor").substring(0, item.getString("actor").length() - 1); // 배우
                            String userRating = item.getString("userRating"); // 평점

                            Log.d(TAG, "title = " + title + " image = " + image + " subtitle = " + subtitle + " pudDate = " + pubDate
                                    + " director = " + director + " actor = " + actor + " userRating = " + userRating);

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