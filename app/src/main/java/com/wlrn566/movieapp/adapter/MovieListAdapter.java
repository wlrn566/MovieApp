package com.wlrn566.movieapp.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wlrn566.movieapp.BuildConfig;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.vo.MovieDetailVO;
import com.wlrn566.movieapp.vo.MovieVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private final String TAG = getClass().getName();
    private ArrayList<MovieVO> mdata;
    private Context mcontext;

    // 리스트를 열고 닫기 위해
    // Item 의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item 의 position
    private int prePosition = -1;

    // 데이터 받기
    public MovieListAdapter(Context context, ArrayList<MovieVO> data) {
        this.mcontext = context;
        this.mdata = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView list_cardView;
        TextView rank_tv, movieNm_tv, openDt_tv, audiAcc_tv;

        ConstraintLayout detail_cl;
        TextView title_tv, pudDate_tv, actor_tv;
        ImageView image;

        // UI 선언
        public ViewHolder(View v) {
            super(v);
            rank_tv = v.findViewById(R.id.rank_tv);
            movieNm_tv = v.findViewById(R.id.movieNm_tv);
            openDt_tv = v.findViewById(R.id.openDt_tv);
            audiAcc_tv = v.findViewById(R.id.audiAcc_tv);
            detail_cl = v.findViewById(R.id.detail_cl);
            detail_cl.setVisibility(View.GONE);
            title_tv = v.findViewById(R.id.title_tv);
            pudDate_tv = v.findViewById(R.id.pudDate_tv);
            actor_tv = v.findViewById(R.id.actor_tv);
            image = v.findViewById(R.id.image);

            // 클릭 이벤트 부여
            list_cardView = v.findViewById(R.id.list_cardView);
            list_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // 클릭리스너 호출
                        if (onClickListener != null) {
                            String movieNm = mdata.get(position).getMovieNm();
                            onClickListener.onClickListener(view, movieNm);
                            detail_cl.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.adpater_movielist, parent, false);
        MovieListAdapter.ViewHolder vh = new MovieListAdapter.ViewHolder(view);
        return vh;
    }

    // 각 뷰홀더의 UI에 데이터 삽입
    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.ViewHolder holder, int position) {
        MovieVO mvo = mdata.get(position);
        holder.rank_tv.setText(mvo.getRank());
        holder.movieNm_tv.setText(mvo.getMovieNm());
        holder.openDt_tv.setText(mvo.getOpenDt());
        holder.audiAcc_tv.setText(mvo.getAudiAcc());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    // 클릭 시 상세 페이지 진입을 위해 클릭리스너 인터페이스
    public interface onClickListener {
        void onClickListener(View v, String movieNm);
    }

    // 클릭리스너 객체 전달 변수
    private onClickListener onClickListener;

    // 클릭리스너 메소드 정의
    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
