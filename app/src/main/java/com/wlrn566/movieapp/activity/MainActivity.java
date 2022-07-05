package com.wlrn566.movieapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.Fragment.MainBoxOfficeFragment;


public class MainActivity extends AppCompatActivity {
    // 프래그먼트가 존재할 액티비티페이지
    private final String TAG = getClass().getName();
    private long backPressedTime = 0;

    // 영화 순위 정보와 영화 상세 정보를 알 수 있고, 영화에 따라 사용자들의 의견을 달 수 있는 기능.
    // 프래그먼트를 이용
    // 1. 일일 박스오피스 : 영화진흥위원회 api 사용(volley) -> 리사이클러뷰로 출력
    // 2. 리스트 클릭 시 좀 더 세부내용 펼치기 : 네이버 검색 api 사용(임시로 사용) -> 박스오피스의 영화제목 담아서 호출
    // 2. 리스트 클릭 시 좀 더 세부내용 펼치기 : KMDb api 호출 (키 승인시)
    // 3. 화살표 클릭 시 커뮤니티페이지 -> 회원가입 및 로그인 (mariaDB + php) 필요

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 메인 프레그먼트로 이동(MainBoxOfficeFragment)
        MainBoxOfficeFragment mainBoxOfficeFragment = new MainBoxOfficeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity, mainBoxOfficeFragment)
                .addToBackStack(null)
                .commit();
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

    @Override
    public void onBackPressed() {
        // 백스택이 있으면 스택 프래그먼트 호출
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            Log.d(TAG, "BackStackCount = " + getSupportFragmentManager().getBackStackEntryCount());
            getSupportFragmentManager().popBackStack();
        } else {
            // 뒤로가기 키를 누른 후 2초가 지났으면 Toast 출력
            if (System.currentTimeMillis() > backPressedTime + 2000) {
                backPressedTime = System.currentTimeMillis();
                Toast toast = Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            // 뒤로가기 키를 누른 후 2초가 안지났으면 종료
            if (System.currentTimeMillis() <= backPressedTime + 2000) {
                finish();
            }
        }
    }
}