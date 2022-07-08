package com.wlrn566.movieapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wlrn566.movieapp.R;
import com.wlrn566.movieapp.Vo.ReviewVO;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHoler> {
    private final String TAG = getClass().getName();
    private Context mcontext;
    private ArrayList<ReviewVO> mdata;

    public ReviewAdapter(Context mcontext, ArrayList<ReviewVO> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.adapter_review, parent, false);
        ReviewAdapter.ViewHoler vh = new ReviewAdapter.ViewHoler(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHoler holder, int position) {
        ReviewVO rvo = mdata.get(position);
        holder.date.setText(rvo.getCrt_dt().substring(0,10));
        holder.id.setText(rvo.getId());
        holder.content.setText(rvo.getContent());
    }


    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView date, id, content;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);
            content = itemView.findViewById(R.id.content);
        }
    }
}
