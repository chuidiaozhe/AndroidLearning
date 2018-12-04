package com.android.control.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.CamcorderProfile;
 import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;

import com.andorid.camera.demo.R;

import java.io.File;
 import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2018/11/23 11:26 AM
 * 相机预览
 */
public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "testtest";

    private boolean isRecording = false;


    private Camera mCamera;
    private CameraPreview mPreview;
    private MediaRecorder mediaRecorder;

    Button btnVideo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mCamera = CustomCamera.getCameraInstance();
        mPreview = new CameraPreview(this,mCamera);

        boolean isRedio = ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED ? true : false;
        if(!isRedio){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},100);
        }


        FrameLayout frameLayout = findViewById(R.id.fl_preview);
        frameLayout.addView(mPreview);

        findViewById(R.id.btn_capture).setOnClickListener((v)->{
            //获取相机照片
            takePicture();
        });

        btnVideo = findViewById(R.id.btn_video);
        findViewById(R.id.btn_video).setOnClickListener((v)->{
             //录制视屏
            if(isRecording){
                mediaRecorder.stop();
                releaseMediaRecorder();
                mCamera.lock();
                btnVideo.setText("Video");
                isRecording =false;
            }else{
               if(prepareVideoRecorder()){
                   mediaRecorder.start();
                   btnVideo.setText("stop");
                   isRecording = true;
               }else{
                   releaseMediaRecorder();
               }
            }
        });

//        setCameraParams();
        setMeteringAndFocusAreas();
    }

    /**
     * 设置聚焦区域
     */
    private void setMeteringAndFocusAreas(){
        if(mCamera != null){
            Camera.Parameters parameters = mCamera.getParameters();
            if(parameters.getMaxNumMeteringAreas() > 0){
                List<Camera.Area> meteringAreas = new ArrayList<>();
                Rect rect1 = new Rect(-100,-100,100,100);
                meteringAreas.add(new Camera.Area(rect1,600));
                Rect rect2 = new Rect(800,-1000,1000,-800);
                meteringAreas.add(new Camera.Area(rect2,400));
                parameters.setMeteringAreas(meteringAreas);

            }
            mCamera.setParameters(parameters);
        }

    }

    /**
     * 设置相机的一些参数
     */
    private void setCameraParams(){
        if(mCamera != null){
          Camera.Parameters parameters = mCamera.getParameters();
            List<String> focusMods = parameters.getSupportedFocusModes();
            //检测是否具有某些功能
            if(focusMods.contains(Camera.Parameters.FOCUS_MODE_AUTO)){
                  parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }
            mCamera.setParameters(parameters);
        }
    }

    /**
     * 捕获相机图片
     */
    private void takePicture(){
        if(mCamera != null){
            mCamera.takePicture(new Camera.ShutterCallback() {
                @Override
                public void onShutter() {

                }
            }, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {

                }
            }, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    try {
                        File pictureFile = createImageFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);
                        fileOutputStream.write(data);
                        fileOutputStream.close();
                        mCamera.release();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 创建一个存储图片的文件
     * @return
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss" ).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg",storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }
    /**
     * 创建一个存储图片的文件
     * @return
     */
    private File creatVideoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss" ).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".mp4",storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }


    private boolean prepareVideoRecorder(){
        mediaRecorder = new MediaRecorder();
        if(mCamera == null){
            mCamera = Camera.open();
        }

        //Step1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);

        //Step2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        //Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        //Step 4: Set output file
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mediaRecorder.setOutputFile(creatVideoFile());
            }else{
                mediaRecorder.setOutputFile(creatVideoFile().getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());

        //Step6:Step 6: Prepare configured MediaRecorder
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;

    }

    private void releaseMediaRecorder(){
        if(mediaRecorder != null){
            // clear recorder configuration
            mediaRecorder.reset();
            // release the recorder object
            mediaRecorder.release();
            mediaRecorder = null;
            // lock camera for later use
            mCamera.lock();
        }
    }

    private void releaseCamera(){
        if(mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCamera != null){
            mCamera.stopPreview();
        }
    }
}
