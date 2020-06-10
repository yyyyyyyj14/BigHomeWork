package com.android.shortvideoapp.fragment.video;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JzvdStd;

public class ProVideoView extends JzvdStd {

    public ProVideoView(Context context) {
        super(context);
    }
    //xml文件调用两个参数的构造函数、不能少！
    public ProVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        bottomContainer.setVisibility(GONE);
        topContainer.setVisibility(GONE);
        bottomProgressBar.setVisibility(GONE);
        loadingProgressBar.setVisibility(GONE);
        bottomProgressBar.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
    }


    @Override
    public void onClickUiToggle() {//点击时的UI变化
        super.onClickUiToggle();
        startButton.performClick();
        bottomContainer.setVisibility(GONE);
        topContainer.setVisibility(GONE);
    }
}
