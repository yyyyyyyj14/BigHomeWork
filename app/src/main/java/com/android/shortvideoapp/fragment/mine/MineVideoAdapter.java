package com.android.shortvideoapp.fragment.mine;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.android.shortvideoapp.R;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JzvdStd;

public class MineVideoAdapter extends RecyclerView.Adapter<MineVideoAdapter.VideoViewHolder> {

    public static final String TAG = "VideoViewAdapter";
    private Context context;
    private List<VideoData> videoDataSet;

    public MineVideoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<VideoData> dataList)
    {
        videoDataSet = new ArrayList<>();
        videoDataSet = dataList;
    }
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoViewHolder holder = new VideoViewHolder(LayoutInflater.from(
                context).inflate(R.layout.adapter_video_mine, parent, false));
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder [" + holder.jzvdStd.hashCode() + "] position=" + position);
        //加载视频
        holder.jzvdStd.setUp(videoDataSet.get(position).getPath(),videoDataSet.get(position).getName());
        //获取第一秒的帧数作为封面
        Glide.with(holder.jzvdStd.getContext()).setDefaultRequestOptions(
                new RequestOptions().frame(1000000).centerCrop()).load(videoDataSet.get(position).getUri()).into(holder.jzvdStd.posterImageView);
    }


    @Override
    public int getItemCount() {
        return videoDataSet == null ? 0 : videoDataSet.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        JzvdStd jzvdStd;

        public VideoViewHolder(View itemView) {
            super(itemView);
            jzvdStd = itemView.findViewById(R.id.mine_video_view);
        }
    }

}