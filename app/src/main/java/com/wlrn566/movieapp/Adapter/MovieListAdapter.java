package com.wlrn566.movieapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.Vo.MovieVO;

import java.util.HashMap;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private final String TAG = getClass().getName();
    private HashMap<String, MovieVO> map;
    private Context mcontext;

    // 뷰 펼치고 닫기 상태 저장
    private SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();

    // 데이터 받기
    public MovieListAdapter(Context context, HashMap<String, MovieVO> map) {
        this.mcontext = context;
        this.map = map;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView list_cardView;
        TextView rank_tv, movieNm_tv, openDt_tv, audiAcc_tv;

        ConstraintLayout info3_cl;
        TextView pudDate_tv, actor_tv, userRating_tv;
        ImageView image;
        Button detailPage_btn;

        // UI 선언
        public ViewHolder(View v) {
            super(v);
            rank_tv = v.findViewById(R.id.rank_tv);
            movieNm_tv = v.findViewById(R.id.movieNm_tv);
            openDt_tv = v.findViewById(R.id.openDt_tv);
            audiAcc_tv = v.findViewById(R.id.audiAcc_tv);
            info3_cl = v.findViewById(R.id.info3_cl);
            info3_cl.setVisibility(View.GONE);
            pudDate_tv = v.findViewById(R.id.pudDate_tv);
            actor_tv = v.findViewById(R.id.actor_tv);
            image = v.findViewById(R.id.image);
            userRating_tv = v.findViewById(R.id.userRating_tv);

            // 클릭 이벤트 부여
            detailPage_btn = v.findViewById(R.id.detailPage_btn);
            detailPage_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onClickListener != null) {
                            MovieVO mvo = map.get(String.valueOf(position + 1));
                            onClickListener.onClickListener(view, mvo);
//                            Log.d(TAG,"onclick mvo = "+mvo);
                        }
                    }
                }
            });

            list_cardView = v.findViewById(R.id.list_cardView);
            list_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (sparseBooleanArray.get(position)) {
                            // 펼쳐져 있다면 해당 포지션 삭제
                            sparseBooleanArray.delete(position);
                        } else {
                            // 닫혀 있다면 해당 포지션 true 로 저장
                            sparseBooleanArray.put(position, true);
                        }
                        // View 펼치고 닫기
                        changeView(info3_cl, position);
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
        MovieVO mvo = map.get(String.valueOf(position + 1));
        if (mvo != null) {
            if (mvo.getRank().equals(String.valueOf(position + 1))) {
                holder.rank_tv.setText(mvo.getRank());
                holder.movieNm_tv.setText(mvo.getMovieNm());
                holder.openDt_tv.setText("개봉일 : " + ((mvo.getOpenDt() != null && !mvo.getOpenDt().equals(" ")) ? mvo.getOpenDt() : "정보 없음"));
                holder.audiAcc_tv.setText("누적 관객수 : " + mvo.getAudiAcc());

                holder.pudDate_tv.setText("제작년도 : " + ((mvo.getPubDate() != null) ? mvo.getPubDate() : "정보 없음"));
                holder.actor_tv.setText((mvo.getActor() != null) ? mvo.getActor() : "");
                holder.userRating_tv.setText((mvo.getUserRating() != null) ? mvo.getUserRating() : "0.0");
                if (mvo.getImage() != null) {
                    Glide.with(mcontext).load(mvo.getImage()).override(100, 100).into(holder.image);
                } else {
                    holder.image.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    // 카드뷰 펼치기
    public interface onClickListener {
        void onClickListener(View v, MovieVO mvo);
    }

    // 클릭리스너 객체 전달 변수
    private onClickListener onClickListener;

    // 클릭리스너 메소드 정의
    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void changeView(View info3_cl, int position) {
        if (sparseBooleanArray.get(position)) {
            info3_cl.setVisibility(View.VISIBLE);
        } else {
            info3_cl.setVisibility(View.GONE);
        }
        Log.d(TAG, sparseBooleanArray.toString());
    }
}
