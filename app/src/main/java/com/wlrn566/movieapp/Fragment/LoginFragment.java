package com.wlrn566.movieapp.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
                login();
                break;
            case R.id.join_btn:
                progressDialog = new ProgressDialog(getActivity());
                // volley 가 느려서 가입까지 기다릴 수 있도록 다이얼로그 띄우기
                progressDialog.setMessage("잠시만 기다려주세요.");
                progressDialog.setCancelable(false);
                progressDialog.show();
                join();
                break;
            default:
                break;
        }
    }
    private void login() {

    }

    private void join() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String id = id_et.getText().toString();
        String pw = pw_et.getText().toString();

        final String url = "http://192.168.0.9/join.php";

        try {
            id = URLEncoder.encode(id, "utf-8");
            pw = URLEncoder.encode(pw, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String encode_id = id;
        final String encode_pw = pw;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response = " + response);
                progressDialog.dismiss();
                try {
                    if (response.getString("result").equals("success")) {
                        Toast.makeText(getActivity(), "가입이 완료 되었습니다. \n다시 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                        id_et.setText(null);
                        pw_et.setText(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", encode_id);
                param.put("pw", encode_pw);
                return param;
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }
}