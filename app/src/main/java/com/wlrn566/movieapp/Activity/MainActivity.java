package com.wlrn566.movieapp.Activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wlrn566.movieapp.Fragment.LoginFragment;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.Fragment.MainBoxOfficeFragment;
import com.wlrn566.movieapp.Service.AppDB;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // 프래그먼트가 존재할 액티비티페이지
    private final String TAG = getClass().getName();
    private long backPressedTime = 0;

    private String id;

    private AppDB appDB = new AppDB();

    // 액션바 뷰
    private TextView title, login, logout;

    // 영화 순위 정보와 영화 상세 정보를 알 수 있고, 영화에 따라 사용자들의 의견을 달 수 있는 기능.
    // 프래그먼트를 이용
    // 1. 일일 박스오피스 : 영화진흥위원회 api 사용(volley) -> 리사이클러뷰로 출력
    // 2-1. 리스트 클릭 시 좀 더 세부내용 펼치기 : 네이버 검색 api 사용(임시로 사용) -> 박스오피스의 영화제목 담아서 호출
    // 2-2. 리스트 클릭 시 좀 더 세부내용 펼치기 : KMDb api 호출 (키 승인시)
    // 3. 화살표 클릭 시 커뮤니티페이지 -> 회원가입(volley) 및 로그인(retrofit)  (mariaDB + php) 필요 + SharedPreferences
    // 4. 툴바 구성 : 페이지마다 다르게 / 로그인 기능 넣기


    // 로컬호스트라서 컴퓨터가 켜져있어야하고, IP는 각 장소마다 달라짐 -> 코드에 적혀있는 장소의 ip만 DB 연결가능
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = appDB.getString(this, "user", "id");
        Log.d(TAG, "id = " + id);

        // 툴바 생성
        setToolbar();

        // 메인 프레그먼트로 이동(MainBoxOfficeFragment)
        MainBoxOfficeFragment mainBoxOfficeFragment = new MainBoxOfficeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity, mainBoxOfficeFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);  // 액티비티에서 툴바 공간
        setSupportActionBar(toolbar);  // 툴바를 액션바로 사용  --- 아래부터는 액션바
        View actionbarView = getLayoutInflater().inflate(R.layout.actionbar_custom, null);  // 커스텀 액션바 레이아웃
        ActionBar actionBar = getSupportActionBar();  // 액션바 get
        actionBar.setDisplayHomeAsUpEnabled(false);  // 뒤로가기 생성
        actionBar.setDisplayShowTitleEnabled(false);  // 제목 제거

        // 커스텀 액션바 사이즈
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionbarView, layoutParams); // 커스텀 액션바 set
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);  // 커스텀 디스플레이 옵션 설정

        // 커스텀 액션바 UI 선언
        title = actionbarView.findViewById(R.id.title);
        login = actionbarView.findViewById(R.id.login);
        logout = actionbarView.findViewById(R.id.logout);
        if (id != null) {
            changeActionBarView(true);
        } else {
            changeActionBarView(false);
        }
        login.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    public void changeActionBarView(boolean b) {
        // 로그인 상황에 따라 메뉴 텍스트 변경
        if (b) {
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
        } else {
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
        }
    }

    public void changeActionBarTitle(String str) {
        // 페이지마다 툴바 제목 변경
        title.setText(str);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                LoginFragment loginFragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_activity, loginFragment)
                        .commit();
                break;
            case R.id.logout:
                appDB.putString(this, "user", "id", null);
                changeActionBarView(false);
                break;
            default:
                break;
        }
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

    // 키보드가 올라와있을 때 다른 곳 터치 시 키보드 내림
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}