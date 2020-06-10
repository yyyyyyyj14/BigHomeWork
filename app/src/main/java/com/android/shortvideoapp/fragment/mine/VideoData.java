package com.android.shortvideoapp.fragment.mine;

import android.net.Uri;

public class VideoData {
    private String path;
    private Uri uri;
    private String name;

    public String getPath() {
        return path;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setName(String name) {
        this.name = name;
    }
}
