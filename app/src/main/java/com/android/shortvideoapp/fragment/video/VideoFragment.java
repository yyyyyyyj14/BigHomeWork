package com.android.shortvideoapp.fragment.video;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.shortvideoapp.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.shortvideoapp.network.DataModel;
import com.android.shortvideoapp.network.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoFragment extends Fragment {
    private View view;
    private ViewPager2 videoPager;
    private ProVideoAdapter videoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取fragment的layout
        view = inflater.inflate(R.layout.fragment_video, container, false);
        //从api获取数据
        getData();
        videoPager = view.findViewById(R.id.video_pager);
        videoAdapter = new ProVideoAdapter(getContext());
        videoPager.setAdapter(videoAdapter);

        //设置自动播放
        videoPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                videoAdapter.playVideo(position);
            }
        });
        return view;
    }

    public void pauseVideo()
    {
        videoAdapter.pauseVideo(videoPager.getCurrentItem());
    }


    @Override
    public void onResume() {
        super.onResume();
        ProVideoView.goOnPlayOnResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        ProVideoView.releaseAllVideos();
    }

    private void getData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetworkService netService=retrofit.create(NetworkService.class);
        netService.getData().enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if(response.body() != null) {
                    List<DataModel> dataList = response.body();
                    Log.d("retrofit", dataList.toString());
                    if(dataList.size() != 0) {
                        videoAdapter.setData(dataList);
                        videoAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Log.d("retrofit", "failed get data");
            }
        });

    }

}
