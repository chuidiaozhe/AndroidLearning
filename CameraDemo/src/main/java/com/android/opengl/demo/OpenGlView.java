package com.android.opengl.demo;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Create by Xiangshifu
 * on 2018/11/27 3:40 PM
 */
public class OpenGlView extends GLSurfaceView implements  GLSurfaceView.Renderer {
    public OpenGlView(Context context) {
        super(context);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
