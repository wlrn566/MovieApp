package com.wlrn566.movieapp.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.wlrn566.movieapp.BuildConfig;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.vo.MovieVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MovieDetailFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getName();
    private View rootView;
    private MovieVO mvo;

    private TextView movieNm_tv, openDt_tv, actor_tv, audiAcc_tv, pudDate_tv;
    private ImageView image;
    private LinearLayout review_ll;
    private EditText review_et;
    private Button insert_btn;

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
            mvo = (MovieVO) getArguments().getSerializable("mvo");
            Log.d(TAG, "mvo = " + mvo);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        movieNm_tv = rootView.findViewById(R.id.movieNm_tv);
        openDt_tv = rootView.findViewById(R.id.openDt_tv);
        actor_tv = rootView.findViewById(R.id.actor_tv);
        audiAcc_tv = rootView.findViewById(R.id.audiAcc_tv);
        pudDate_tv = rootView.findViewById(R.id.pudDate_tv);
        image = rootView.findViewById(R.id.image);
        review_ll = rootView.findViewById(R.id.review_ll);
        review_et = rootView.findViewById(R.id.review_et);
        insert_btn = rootView.findViewById(R.id.insert_btn);
        insert_btn.setOnClickListener(this);

        setPage();

        return rootView;
    }

    private void setPage() {
        movieNm_tv.setText(mvo.getMovieNm());
        openDt_tv.setText("개봉일 : " + mvo.getOpenDt());
        actor_tv.setText(mvo.getActor());
        audiAcc_tv.setText("누적 관객수 : " + mvo.getAudiAcc());
        pudDate_tv.setText("제작년도 : " + mvo.getPubDate());
        Glide.with(getActivity()).load(mvo.getImage()).into(image);
    }

    private void insertReview(String id, String content) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String url = "http://192.168.0.9/insert_review.php";

        Log.d("loadImage", "url = " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "response = " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.d(TAG, "error = " + e.toString());
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("content", content);
                Log.d(TAG,"params = "+params);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert_btn:
                String content = review_et.getText().toString();
                String id = "wlrn566";
                insertReview(id, content);
                break;
            default:
                break;
        }

    }
}