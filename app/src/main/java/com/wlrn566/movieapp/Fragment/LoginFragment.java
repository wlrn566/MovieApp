package com.wlrn566.movieapp.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

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
import com.google.gson.Gson;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.Service.RetrofitClient;
import com.wlrn566.movieapp.Vo.UserVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getName();
    private View rootView;
    private EditText id_et, pw_et;
    private Button login_btn, join_btn;

    private ProgressDialog progressDialog;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        return rootView;
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

        Call<UserVO> getUser = RetrofitClient.getApiService().getUser(id, pw);
        getUser.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, retrofit2.Response<UserVO> response) {
                Log.d(TAG, "response = " + response.body());
                try {
                    if (response.body().getResult().equals("success")) {
                        UserVO userVO = (UserVO) new Gson().fromJson(response.body().getUser(), UserVO.class);
                        Log.d(TAG, response.body().getUser().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {
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
}