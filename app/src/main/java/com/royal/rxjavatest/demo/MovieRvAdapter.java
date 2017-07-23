package com.royal.rxjavatest.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.royal.rxjavatest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 25505 on 2017/7/23.
 */

public class MovieRvAdapter extends RecyclerView.Adapter {
    private List<ResponseInfo.SubjectsBean> mMovieList;
    private Context mContext;

    //接收数据源
    public MovieRvAdapter(Context context, List<ResponseInfo.SubjectsBean> movielist) {
        mMovieList = movielist;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(mContext).inflate(R.layout.movie_item, null);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder movieHolder = (ViewHolder) holder;
        movieHolder.setData(mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mMovieList == null) {
            return 0;
        }
        return mMovieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_movie)
        ImageView mIvMovie;
        @BindView(R.id.tv_movie)
        TextView mTvMovie;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        void setData(ResponseInfo.SubjectsBean subjectsBean){
            mTvMovie.setText(subjectsBean.getTitle());
            Glide.with(mContext).load(subjectsBean.getImages().getMedium()).override(400,800).into(mIvMovie);
        }

    }
}
