package test.android.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import test.android.com.androidtest2.R;

/**
 * Create by Xiangshifu
 * on 2018/7/10 上午9:55
 * @see {https://blog.csdn.net/u013831257/article/details/51565591}
 */
public class CustomCircleSpeedView   extends View implements  View.OnClickListener{
    int width;
    int height;

    Path path;
    Paint paint;
    PathMeasure pathMeasure;
    private Bitmap bitmap;
    Matrix matrix;

    float precent = 0.0f;

    public CustomCircleSpeedView(Context context) {
        super(context);
        initView(context);
    }

    public CustomCircleSpeedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomCircleSpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setOnClickListener(this);
         BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        bitmap =  BitmapFactory.decodeResource(getResources(),R.mipmap.arrow,options);
        matrix = new Matrix();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width/2,height/2);
        canvas.rotate(-90);
        path.addCircle(0,0,200, Path.Direction.CW);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawPath(path,paint);
        pathMeasure = new PathMeasure(path,true);
        float[] pos = new float[2];
        float[] tan = new float[2];
         pathMeasure.getPosTan(pathMeasure.getLength()*precent,pos,tan);
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        Log.e("testtest","=======degress======== "+degrees);

          //通过手动去改变Matrix
//        matrix.reset();
//        matrix.postRotate(degrees,bitmap.getWidth()/2,bitmap.getHeight()/2);
//        matrix.postTranslate(pos[0]-bitmap.getWidth()/2,pos[1]-bitmap.getHeight()/2);

          //通过PathMeasure来获得矩阵
          matrix.reset();
          pathMeasure.getMatrix(pathMeasure.getLength()*precent,matrix,PathMeasure.POSITION_MATRIX_FLAG|PathMeasure.TANGENT_MATRIX_FLAG);
          matrix.preTranslate(-bitmap.getWidth()/2,-bitmap.getHeight()/2);
         canvas.drawBitmap(bitmap,matrix,paint);

        paint.setColor(Color.RED);
        canvas.drawPoint(pos[0],pos[1],paint);
        paint.setColor(Color.GREEN);
        canvas.drawPoint(tan[0],tan[1],paint);

//        canvas.save();
//        canvas.translate(pos[0],pos[1]);
//        canvas.rotate(degrees);
//        canvas.drawLine(0,0,100,0,paint);
//        canvas.restore();

    }
    ValueAnimator valueAnimator;
    @Override
    public void onClick(View view) {

        if(valueAnimator == null){
            valueAnimator  = ValueAnimator.ofFloat(0,1);
            valueAnimator.setDuration(2000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    precent = (float) valueAnimator.getAnimatedValue();
                    postInvalidate();
                }
            });
            valueAnimator.start();
        }else{
            if(!valueAnimator.isPaused()){
                valueAnimator.pause();
            }else{
                 valueAnimator.resume();
            }
        }

    }
}
