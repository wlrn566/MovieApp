package com.wlrn566.movieapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.vo.MovieVO;

import java.util.ArrayList;
import java.util.Objects;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private ArrayList<MovieVO> mdata;
    private Context mcontext;

    // 데이터 받기
    public MovieListAdapter(Context context, ArrayList<MovieVO> data) {
        this.mcontext = context;
        this.mdata = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank_tv;
        TextView movieNm_tv;
        TextView openDt_tv;
        TextView audiAcc_tv;

        // UI 선언
        public ViewHolder(View v) {
            super(v);
            rank_tv = v.findViewById(R.id.rank_tv);
            movieNm_tv = v.findViewById(R.id.movieNm_tv);
            openDt_tv = v.findViewById(R.id.openDt_tv);
            audiAcc_tv = v.findViewById(R.id.audiAcc_tv);
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
}
