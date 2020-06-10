package com.android.shortvideoapp.fragment.video;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.android.shortvideoapp.utils.DoubleClickHelper;

public class MyClickListener implements View.OnTouchListener {
    private static int timeout = 400;//双击间四百毫秒延时
    private int clickCount = 0;//记录连续点击次数
    private Handler handler;
    private MyClickCallBack myClickCallBack;

    public interface MyClickCallBack {
        void oneClick();//点击一次的回调

        void doubleClick();//双击及连续多次点击的回调
    }


    public MyClickListener(MyClickCallBack myClickCallBack) {
        this.myClickCallBack = myClickCallBack;
        handler = new Handler();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (DoubleClickHelper.isOnDoubleClick()) {
                        myClickCallBack.doubleClick();

                    } else {
                        myClickCallBack.oneClick();

                    }

                    handler.removeCallbacksAndMessages(null);
                }
            }, timeout);//延时timeout后执行run方法中的代码
        }
        return false;//让点击事件继续传播，方便再给View添加其他事件监听
    }
}