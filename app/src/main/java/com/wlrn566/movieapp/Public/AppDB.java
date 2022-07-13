package com.wlrn566.movieapp.Public;

import android.content.Context;
import android.content.SharedPreferences;

public class AppDB {

    // SharedPreferences 선언
    private SharedPreferences preferences = null;

    public void putString(Context context, String db, String id, String value) {
        // 파일이름(DB), 모드
        // 모드 : MODE_PRIVATE 또는 0 (현재 앱에서만 사용가능)
        preferences = context.getSharedPreferences(db, 0);

        // Editor 를 쓰겠다. -> SharedPreferences 는 읽기만 가능하기에 Editor 로 값을 변경하거나 해야함
        SharedPreferences.Editor editor = preferences.edit();

        // 데이터 넣기(키, 값)
        editor.putString(id, value);

        // commit 과 apply 를 해야 저장이 된다.
        // commit : 반환값을 t/f 리턴 (동기식) / apply : 반환값 X (비동기)
        editor.commit();
    }


    public void putBoolean(Context context, String db, String id, Boolean value) {
        preferences = context.getSharedPreferences(db, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(id, value);
        editor.commit();
    }

    public String getString(Context context, String db, String id) {
        preferences = context.getSharedPreferences(db, 0);
        // 데이터 꺼내기 : preferences.getString(키값, default 값);
        return preferences.getString(id, null);
    }

    public Boolean getBoolean(Context context, String db, String id) {
        preferences = context.getSharedPreferences(db, 0);
        return preferences.getBoolean(id, false);
    }


}
