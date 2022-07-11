package com.wlrn566.movieapp.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.Activity.MainActivity;
import com.wlrn566.movieapp.Adapter.ReviewAdapter;
import com.wlrn566.movieapp.Service.AppDB;
import com.wlrn566.movieapp.Vo.MovieVO;
import com.wlrn566.movieapp.Vo.ReviewVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MovieDetailFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getName();
    private View rootView;
    private MovieVO mvo;

    private TextView movieNm_tv, openDt_tv, actor_tv, audiAcc_tv, pudDate_tv, plot_tv;
    private ImageView image;
    private LinearLayout review_ll;
    private EditText review_et;
    private Button insert_btn;

    private RecyclerView review_rv;

    private AppDB appDB = new AppDB();

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
        plot_tv = rootView.findViewById(R.id.plot_tv);
        image = rootView.findViewById(R.id.image);
        review_ll = rootView.findViewById(R.id.review_ll);
        review_et = rootView.findViewById(R.id.review_et);
        insert_btn = rootView.findViewById(R.id.insert_btn);
        review_rv = rootView.findViewById(R.id.review_rv);

        insert_btn.setOnClickListener(this);

        setToolBar();
        setPage();

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

        ((MainActivity) getActivity()).changeActionBarTitle("상세정보");
    }

    private void setPage() {
        movieNm_tv.setText(mvo.getMovieNm());
        openDt_tv.setText("개봉일 : " + mvo.getOpenDt());
        actor_tv.setText(mvo.getActor());
        audiAcc_tv.setText("누적 관객수 : " + mvo.getAudiAcc());
        pudDate_tv.setText("제작년도 : " + mvo.getPubDate());
        plot_tv.setText(mvo.getPlot());
        Glide.with(getActivity()).load(mvo.getPoster()).into(image);
        loadReview(); // 관람평 가져오기
    }

    private void loadReview() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String url = "http://192.168.0.9/load_review.php";

        String movieNm = movieNm_tv.getText().toString();

        try {
            movieNm = URLEncoder.encode(movieNm, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String encode_movieNm = movieNm;

        String GET_url = url + "?movieNm=" + encode_movieNm;
        Log.d(TAG, "url = " + GET_url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, GET_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response = " + response);
                try {
                    String result_str = response.getString("result");
                    if (result_str.equals("success")) {
                        JSONArray review = response.getJSONArray("review");
                        Log.d(TAG, "review = " + review);
                        if (review.length() > 0) {
                            // Gson 을 이용하여 관람평을 VO 로 만들어 리스트에 넣기
                            ArrayList<ReviewVO> rvoList = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0; i < review.length(); i++) {
                                ReviewVO rvo = gson.fromJson(String.valueOf(review.get(i)), ReviewVO.class);
                                rvoList.add(rvo);
                            }
                            setRecycler(rvoList);
                        } else { // 등록된 관람평이 없으면
                            review_rv.setVisibility(View.GONE);
                        }
                    } else {  // DB 연동 실패 시
                        review_rv.setVisibility(View.GONE);
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
        });
        request.setShouldCache(false);
        queue.add(request);
    }

    private void insertReview() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String url = "http://192.168.0.9/insert_review.php";

        String id = "wlrn566";
        String movieNm = movieNm_tv.getText().toString();
        String content = review_et.getText().toString();

        try {
            id = URLEncoder.encode(id, "utf-8");
            movieNm = URLEncoder.encode(movieNm, "utf-8");
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String encode_id = id;
        final String encode_content = content;
        final String encode_movieNm = movieNm;

        String GET_url = url + "?id=" + encode_id + "&movieNm=" + encode_movieNm + "&content=" + encode_content;
        Log.d(TAG, "url = " + GET_url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, GET_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response = " + response);
                review_et.setText(null);
                review_et.clearFocus();
                loadReview();  // 관람평 작성 시 다시 불러오기
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        });
        request.setShouldCache(false);
        queue.add(request);
    }

    // 리사이클러뷰 세팅
    private void setRecycler(ArrayList<ReviewVO> rvoList) {
        Log.d(TAG, "setRecycler");
        review_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        review_rv.setLayoutManager(layoutManager);
        ReviewAdapter reviewAdapter = new ReviewAdapter(getActivity(), rvoList);
        review_rv.setAdapter(reviewAdapter);
        review_rv.setVisibility(View.VISIBLE);
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
        // 로그인 확인하기
        String id = appDB.getString(getActivity(), "user", "id");
        switch (view.getId()) {
            case R.id.insert_btn:
                if (id != null) {
                    Log.d(TAG, "id = " + id);
                    // 관람평이 적혀져있을 때만 실행되게끔
                    if (!review_et.getText().toString().isEmpty() && !review_et.getText().toString().equals("") && review_et.getText().toString() != null) {
                        insertReview();
                    } else {
                        Log.d(TAG, "review empty");
                    }
                } else {
                    Toast.makeText(getActivity(), "로그인이 필요한 기능입니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}