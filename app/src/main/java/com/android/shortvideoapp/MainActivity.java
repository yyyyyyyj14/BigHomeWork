package com.android.shortvideoapp;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shortvideoapp.fragment.mine.MineFragment;
import com.android.shortvideoapp.fragment.video.VideoFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends FragmentActivity {
    private static final int REQ_VIDEO_CAPTURE = 1;
    private Context context;
    private TextView tvHome;
    private TextView tvGz;
    private ImageView ivAdd;
    private TextView tvMes;
    private TextView tvMine;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    VideoFragment videoFragment;
    MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //点击首页按钮
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.ll_bottom).setBackgroundColor(0);
                //选中后文字变大
                tvHome.setTextSize(18);
                tvHome.setTextColor(Color.WHITE);
                tvMine.setTextSize(16);
                tvMine.setTextColor(Color.rgb(153,155,154));
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.pro_fragment, videoFragment);
                fragmentTransaction.commit();
            }
        });

        //点击add按钮
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoFragment.pauseVideo();//点击拍照按钮后暂停视频播放
                startRecord();
            }
        });

        //点击我的按钮
        tvMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.ll_bottom).setBackgroundColor(Color.BLACK);
                //选中后文字变大
                tvMine.setTextSize(18);
                tvMine.setTextColor(Color.WHITE);
                tvHome.setTextSize(16);
                tvHome.setTextColor(Color.rgb(153,155,154));

                videoFragment.pauseVideo();//点击按钮后暂停视频播放
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.pro_fragment, mineFragment);
                fragmentTransaction.commit();
          }
        });

    }
    private void initView()
    {
        context = this;
        ivAdd = findViewById(R.id.iv_add);
        tvMine = findViewById(R.id.tv_mine);
        tvHome = findViewById(R.id.tv_home);
        tvGz = findViewById(R.id.tv_gz);
        tvMes = findViewById(R.id.tv_mes);

        tvMine.setTextColor(Color.rgb(153,155,154));
        tvGz.setTextColor(Color.rgb(153,155,154));
        tvMes.setTextColor(Color.rgb(153,155,154));

        fragmentManager = getSupportFragmentManager();
        videoFragment = new VideoFragment();
        mineFragment = new MineFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.pro_fragment, videoFragment);
        fragmentTransaction.commit();
    }

    //启动相机API录像
    private void startRecord()
    {
        Uri fileUri;
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            fileUri = null;//这是正确的写法

            try {
                fileUri = FileProvider.getUriForFile(this,
                        getApplicationContext().getPackageName() + ".provider",createMediaFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
            startActivityForResult(intent,REQ_VIDEO_CAPTURE);
        }
    }
    
    private File createMediaFile() throws IOException {
        if ((Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))) {
            // 选择自己的文件夹
            String path = getExternalFilesDir(null).getPath() + "/myShortVideo/";

            File mediaStorageDir = new File(path);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                    return null;
                }
            }
            Log.d("++++++++++++TAG", String.valueOf(mediaStorageDir));
            // 文件根据当前的时间命名
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Date date = new Date(System.currentTimeMillis());
            String imageFileName = "V_"+simpleDateFormat.format(date);
            String suffix = ".mp4";
            File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
            return mediaFile;
        }

        return null;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Video saved to:\n" +
                    data.getData(), Toast.LENGTH_LONG).show();
        }
    }

}
