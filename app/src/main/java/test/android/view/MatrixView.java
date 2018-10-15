package test.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import test.android.com.androidtest2.R;

/**
 * Create by Xiangshifu
 * on 2018/7/11 上午10:36
 * @see {https://blog.csdn.net/z82367825/article/details/54709041?utm_source=itdadao&utm_medium=referral}
 */
public class MatrixView extends View {
    private Bitmap bitmap;
    private Matrix matrix;
    private Paint paint;
    TextView tv;
    public MatrixView(Context context) {
        super(context);
        initView(context);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(0,10);
        //前乘效果,先平移然后再缩放
//        matrix.setScale(0.5f,0.5f);
//        matrix.preTranslate(150,0);
        //后乘效果,先缩放，然后平移150像素
//        matrix.setScale(0.5f,0.5f);
//        matrix.postTranslate(150,0);
        //直接设置，后面的设置会覆盖前面的值
//         matrix.setScale(0.5f,0.5f);
//         matrix.setTranslate(150,0);
         matrix.setTranslate(150,150);
         matrix.postScale(0.5f,0.5f);
        canvas.drawLine(0,bitmap.getHeight()/2 + 10,150,bitmap.getHeight()/2 + 10,paint);
//       Matrix matrix2 = new Matrix();
//       matrix2.setScale(0.5f,0.5f);
//       matrix2.preTranslate(150,150);


        canvas.drawBitmap(bitmap,matrix,paint);
//        canvas.translate(0,200);
//        canvas.drawBitmap(bitmap,matrix2,paint);

        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(100);
        paint.setShader(new LinearGradient(400,400,800,800, Color.RED,Color.GREEN, Shader.TileMode.CLAMP));



//        canvas.drawLine(200,600,600,600,paint);
        RectF rectF = new RectF(400,400,800,800);
        canvas.drawArc(rectF,-90,315,false,paint);
        paint.setStrokeWidth(5);
        canvas.drawPoint(800,800,paint);

    }
}
