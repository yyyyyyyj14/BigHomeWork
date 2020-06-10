package com.android.shortvideoapp.network;

import com.google.gson.annotations.SerializedName;

public class DataModel {

    @SerializedName("_id")
    public String id;
    @SerializedName("feedurl")
    public String feedurl;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("description")
    public String description;
    @SerializedName("likecount")
    public int likecount;
    @SerializedName("avatar")
    public String avatar;

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", feedurl='" + feedurl + '\'' +
                ", nickname='" + nickname + '\'' +
                ", description='" + description + '\'' +
                ", likecount=" + likecount +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
