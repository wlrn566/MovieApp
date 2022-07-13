package com.wlrn566.movieapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.Public.AppDB;
import com.wlrn566.movieapp.Public.RetrofitClient;
import com.wlrn566.movieapp.Vo.MovieVO;
import com.wlrn566.movieapp.Vo.ResultVO;
import com.wlrn566.movieapp.Vo.UserVO;
import com.wlrn566.movieapp.Activity.MainActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getName();
    private MovieVO mvo;

    private View rootView;
    private EditText id_et, pw_et;
    private Button login_btn, join_btn;

    private ProgressDialog progressDialog;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mvo = (MovieVO) getArguments().getSerializable("mvo");
            Log.d(TAG, "mvo = " + mvo);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        id_et = rootView.findViewById(R.id.id_et);
        pw_et = rootView.findViewById(R.id.pw_et);
        login_btn = rootView.findViewById(R.id.login_btn);
        join_btn = rootView.findViewById(R.id.join_btn);

        login_btn.setOnClickListener(this);
        join_btn.setOnClickListener(this);

        setToolBar();
        return rootView;
    }

    private void setToolBar() {
        // 툴바 생성
        // 액티비티에서 툴바를 찾아줘야한다.
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);  // 툴바 획득
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();  // 툴바를 액션바로 사용하기
        actionBar.setDisplayHomeAsUpEnabled(false);  // 뒤로가기 생성
        actionBar.setDisplayShowTitleEnabled(false);  // 제목 제거

        ((MainActivity) getActivity()).changeActionBarTitle("로그인 하기");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if (!id_et.getText().toString().equals("") && !id_et.getText().toString().isEmpty() && id_et.getText().toString() != null) {
                    login();
                } else {
                    Toast.makeText(getActivity(), "ID와 PW를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.join_btn:
                if (!id_et.getText().toString().equals("") && !id_et.getText().toString().isEmpty() && id_et.getText().toString() != null) {
                    // 가입까지 기다릴 수 있도록 다이얼로그 띄우기
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("잠시만 기다려주세요.");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    join();
                } else {
                    Toast.makeText(getActivity(), "ID와 PW를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void login() {
        // retrofit 을 이용하여 DB 연동 시행
        String id = id_et.getText().toString();
        String pw = pw_et.getText().toString();

        Call<ResultVO> getUser = RetrofitClient.getApiService().getUser(id, pw);
        getUser.enqueue(new Callback<ResultVO>() {
            @Override
            public void onResponse(Call<ResultVO> call, retrofit2.Response<ResultVO> response) {
                Log.d(TAG, "response = " + response.body().getResult());
                try {
                    if (response.body().getResult().equals("success")) {
                        UserVO userVO = response.body().getUser();
//                        Log.d(TAG, userVO.toString());

                        // 받은 데이터를 저장
                        setUserData(userVO);
                    } else {
                        Toast.makeText(getActivity(), "정보를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResultVO> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    private void join() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String id = id_et.getText().toString();
        String pw = pw_et.getText().toString();

        final String url = "http://192.168.0.9/join.php";

//        try {
//            id = URLEncoder.encode(id, "utf-8");
//            pw = URLEncoder.encode(pw, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        final String encode_id = id;
//        final String encode_pw = pw;
//        String url_GET = url + "?id=" + encode_id + "&pw=" + encode_pw;

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        if (response.contains("success")) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "가입되었습니다.\n로그인을 다시 해주세요.", Toast.LENGTH_SHORT).show();
                        } else if (response.contains("duplicate")) {
                            Toast.makeText(getActivity(), "ID가 중복됩니다..", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "가입이 되지 않았습니다..", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("pw", pw);

                return params;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    private void setUserData(UserVO userVO) {
        AppDB appDB = new AppDB();
        appDB.putString(getActivity(), "user", "id", userVO.getId());
        Log.d(TAG, "id = " + appDB.getString(getActivity(), "user", "id"));

        // 현재 메인 액티비티를 날리고 로그인 상태로 새로 시작해줌
        goMain();
    }

    private void goMain() {
        getActivity().getSupportFragmentManager().popBackStackImmediate(null, getChildFragmentManager().POP_BACK_STACK_INCLUSIVE);
        getActivity().finish();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}