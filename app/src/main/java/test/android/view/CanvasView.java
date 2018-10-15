package test.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Create by Xiangshifu
 * on 2018/7/23 上午11:31
 */
public class CanvasView extends View {
    Paint paint ;
    Paint textPaint;
    Paint linePaint;
    Context mContext;
    int viewWidth;
    int viewHeight;

    public CanvasView(Context context) {
        super(context);
        initView(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.mContext = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(25);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(30.0f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
    }

    float radius = 200.0f;
    float startAng = - 90;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(viewWidth/2.0f,viewHeight/2.0f);
        paint.setColor(Color.BLACK);
         textPaint.setColor(Color.BLACK);
        canvas.drawCircle(0,0,10,paint);
        canvas.drawText("原点",0,0,textPaint);

//        canvas.drawCircle(0,0,radius,linePaint);



//        double rectange = 45 ;
//        //绘制-30度时的线
//        float x = (float) (Math.cos((startAng +  rectange )* Math.PI / 180.0f )*radius);
//        float y = (float) (Math.sin((startAng + rectange) * Math.PI / 180.0f)*radius);
//
//        canvas.drawLine(0,0,x,y,linePaint);

        RectF rectF = new RectF(-radius,-radius,radius,radius);
        linePaint.setStrokeWidth(3.0f);
        canvas.drawRect(rectF,linePaint);
        linePaint.setStrokeWidth(30.0f);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF,startAng,180,false,linePaint);
     }
}
