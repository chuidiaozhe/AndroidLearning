package com.andorid.camera.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.control.camera.CameraActivity;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_take_photo).setOnClickListener(this);
        findViewById(R.id.btn_video).setOnClickListener(this);
        findViewById(R.id.btn_control_camera).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent ;
        switch (v.getId()){
            case R.id.btn_take_photo:
                intent = new Intent(MainActivity.this,TakingPhotoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_video:
                intent = new Intent(MainActivity.this,RecordVideoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_control_camera:
                intent = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
                break;
        }
    }
}
