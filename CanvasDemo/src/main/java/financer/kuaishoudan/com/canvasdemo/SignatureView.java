package financer.kuaishoudan.com.canvasdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Create by Xiangshifu
 * on 2019-09-16 10:20
 */
public class SignatureView  extends View {
    private Paint mPaint;
    private Context mContext;
    private Canvas mCanvas;
    private Path mPath;
    public SignatureView(Context context) {
        super(context,null);
        initView(context);
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        initView(context);
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.mContext = context;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    public void reset(){
        if(mCanvas != null){
            mCanvas.restore();
             mPath.reset();
             postInvalidate();
        }
    }

    public Bitmap viewToBitmap(){
         Bitmap  screenShot = Bitmap.createBitmap(this.getWidth(),this.getHeight(),Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(screenShot);
        this.draw(canvas);
        return  screenShot;
     }

     @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;

        canvas.drawPath(mPath,mPaint);
       }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
             mPath.moveTo(event.getX(),event.getY());
            postInvalidate();
                 return  true;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
             mPath.lineTo(event.getX(),event.getY());
            postInvalidate();
            return  true;
        }
        return super.onTouchEvent(event);
    }
}
