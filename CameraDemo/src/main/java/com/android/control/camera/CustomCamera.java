package com.android.control.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by Xiangshifu
 * on 2018/11/23 10:43 AM
 */
public class CustomCamera {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /**
     * 检查是否有相机
     * @param context
     * @return
     */
    private boolean checkCameraHardare(Context context){
      if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
          // this device has a camera
           return  true;
      }else{
          //no camera on this device
           return  false;
      }
    }

    /**
     * 获得设备上有几个摄像头
     * @return
     */
    public static  int getNumberOfCameras(){
        return  Camera.getNumberOfCameras();
    }

    public static Camera getCameraInstance(){
        Camera camera = null;
        try {
            camera = Camera.open();
        }catch (Exception e){

        }

        return camera; // returns null if camera is unavailable
    }

    public static Uri getOutputMediaileUri(int type){
        return  Uri.fromFile(getOutputMediaFile(type));
    }

    private static File  getOutputMediaFile(int type){
        File mediaStoragdir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"CameraApp");
        if( !mediaStoragdir.exists()){
            if(!mediaStoragdir.mkdirs()){
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHHmmss").format(new Date());
        File mediaFile = null;
        if(type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStoragdir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg" );
        }else if(type == MEDIA_TYPE_VIDEO){
            mediaFile = new File(mediaStoragdir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        }else{
            return  null;
        }
        return  null;
    }


}
