package com.andorid.camera.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

/**
 * Create by Xiangshifu
 * on 2018/11/21 3:34 PM
 * 录制视频
 */
public class RecordVideoActivity extends AppCompatActivity {
    static final int REQUEST_VIDEO = 101;

    VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);

        videoView = findViewById(R.id.video);

        findViewById(R.id.btn_video).setOnClickListener((v)->{
            Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if(videoIntent.resolveActivity(getPackageManager()) != null){
                  startActivityForResult(videoIntent,REQUEST_VIDEO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_VIDEO && resultCode == RESULT_OK){
            Uri uri = data.getData();
            videoView.setVideoURI(uri);
            videoView.start();
        }
    }
}
