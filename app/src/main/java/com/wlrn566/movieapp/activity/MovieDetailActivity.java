package com.wlrn566.movieapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wlrn566.movieapp.R;

public class MovieDetailActivity extends AppCompatActivity {
    // 영화 상세페이지
    private final String TAG = getClass().getName();
    private String movieNm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        movieNm = intent.getStringExtra("movieNm");
        Log.d(TAG, "movieNm = " + movieNm);
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