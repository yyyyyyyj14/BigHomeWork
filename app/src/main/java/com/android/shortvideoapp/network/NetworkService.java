package com.android.shortvideoapp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkService {
    //用于请求获取api的视频地址
    @GET("api/invoke/video/invoke/video")
    Call<List<DataModel>> getData();//返回请求到的数据
}
