package com.android.control.camera;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Create by Xiangshifu
 * on 2018/11/23 10:54 AM
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context,Camera camera) {
        super(context);
        this.mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);

       mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //sucfaceview创建
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
         //surfaceview发生变化,必须先停止预览再开启新的预览

        if(mHolder.getSurface() == null){
            return;
        }

        try {
            mCamera.stopPreview();
        }catch ( Exception e){
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
      //surfaceview销毁
    }
}
