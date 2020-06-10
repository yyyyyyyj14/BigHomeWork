package com.android.shortvideoapp.fragment.video;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.android.shortvideoapp.R;
import com.android.shortvideoapp.network.DataModel;
import com.android.shortvideoapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

public class ProVideoAdapter extends RecyclerView.Adapter<ProVideoAdapter.ProViewHolder> {


    public static final String TAG = "ProVideoViewAdapter";
    private Context context;
    private List<DataModel> videoDataSet;
    private List<ProViewHolder> videoViewSet;
    private ProVideoView currentVideo;

    public ProVideoAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<DataModel> dataList) {
        videoViewSet = new ArrayList<>();
        videoDataSet = dataList;
    }

    @NonNull
    @Override
    public ProViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProViewHolder holder = new ProViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.adapter_video_pro,parent,false));
        return holder;
    }

    public void playVideo(int position) {
        videoViewSet.get(position).videoView.startVideo();
    }

    public void pauseVideo(int position) { videoViewSet.get(position).videoView.goOnPlayOnPause();}

    @Override
    public void onBindViewHolder(@NonNull ProViewHolder holder, int position) {
        videoViewSet.add(holder);
        //加载视频
        holder.videoView.setUp(
                videoDataSet.get(position).feedurl,
                videoDataSet.get(position).description, Jzvd.SCREEN_NORMAL);

        //获取第一秒的帧数作为封面
        Glide.with(holder.videoView.getContext())
                .setDefaultRequestOptions(new RequestOptions().frame(1000000).centerCrop())
                .load(videoDataSet.get(position).feedurl)
                .into(holder.videoView.posterImageView);
        //加载ID
        holder.videoId.setText(videoDataSet.get(position).nickname);
        //加载描述
        holder.videoDes.setText(videoDataSet.get(position).description);
        //加载圆形头像
        Glide.with(holder.videoView.getContext())
                .load(videoDataSet.get(position).avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.avatar);
        //加载点赞数
        holder.likeCount.setText(Utils.convertInt(videoDataSet.get(position).likecount));



        holder.likeCount.setOnClickListener(new View.OnClickListener() {//点赞监听
            @Override
            public void onClick(View v) {
                if(holder.like)
                {
                    holder.like = false;
                    Drawable nav_up = context.getResources().getDrawable(R.drawable.like_selected);
                    nav_up.setBounds(0, 0, nav_up.getIntrinsicWidth(), nav_up.getIntrinsicHeight());
                    holder.likeCount.setCompoundDrawables(null, nav_up, null,null);
                }
                else
                {
                    holder.like = true;
                    Drawable nav_up = context.getResources().getDrawable(R.drawable.like_normal);
                    nav_up.setBounds(0, 0, nav_up.getIntrinsicWidth(), nav_up.getIntrinsicHeight());
                    holder.likeCount.setCompoundDrawables(null, nav_up, null, null);
                    Toast.makeText(context,"Like!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return videoDataSet == null ? 0 : videoDataSet.size();
    }

    class ProViewHolder extends RecyclerView.ViewHolder {
        boolean like;
        ProVideoView videoView;
        TextView videoId;
        TextView videoDes;
        ImageView avatar;
        TextView likeCount;
        public ProViewHolder(@NonNull View itemView) {
            super(itemView);
            like = false;
            videoView = itemView.findViewById(R.id.pro_video_view);
            videoId = itemView.findViewById(R.id.video_id);
            videoDes = itemView.findViewById(R.id.video_description);
            avatar = itemView.findViewById(R.id.avatar_img);
            likeCount = itemView.findViewById(R.id.like_count);
        }

    }
}
