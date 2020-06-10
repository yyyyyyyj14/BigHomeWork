package com.android.shortvideoapp.fragment.mine;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.shortvideoapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MineFragment extends Fragment {
    private RecyclerView mainRecyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private MineVideoAdapter videoAdapter;
    private List<VideoData> fileList;//保存本地视频信息
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取fragment的layout
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        fileList = new ArrayList<>();

        mainRecyclerView = view.findViewById(R.id.mine_recyclerview);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mainRecyclerView.setLayoutManager(layoutManager);
        videoAdapter = new MineVideoAdapter(getContext());
        mainRecyclerView.setAdapter(videoAdapter);

        //获取数据
        getData();
        return view;
    }

    private void getData()
    {
        if ((Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))) {
            // 选择自己的文件夹
            String path = getContext().getExternalFilesDir(null).getPath() + "/myShortVideo/";
            File file = new File(path);
            File[] subFile = file.listFiles();
            if(subFile!=null) {
                for (int i = 0; i < subFile.length; i++) {
                    if (!subFile[i].isDirectory()) {
                        VideoData videoData = new VideoData();
                        //保存绝对路径
                        videoData.setPath(subFile[i].getAbsolutePath());
                        //保存名字
                        videoData.setName(subFile[i].getName());
                        //保存Uri
                        videoData.setUri(Uri.fromFile(subFile[i]));

                        fileList.add(videoData);
                    }
                }
                videoAdapter.setData(fileList);
            }
            else
            {
                Toast.makeText(getContext(),"这里空空如也, 点击 + 拍摄第一个视频吧",Toast.LENGTH_SHORT).show();
            }
        }

    }

}
