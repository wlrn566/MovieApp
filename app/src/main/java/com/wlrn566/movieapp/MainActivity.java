package com.wlrn566.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();
    private final String key = getString(R.string.kobis_key);
    private String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";

    private TextView text;

    // 1. 영화진흥위원회 api 사용(volley) -> 현재 상영 영화데이터 불러오기 -> 리사이클러뷰로 출력
    // 2. 리스트 클릭 시 상세페이지
    // 3. 커뮤니티페이지 -> 회원가입 및 로그인 (mariaDB + php)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);

        // 영화 api 불러오기


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