package test.android.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

 import java.util.ArrayList;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2018/7/20 下午2:37
 */
public class CutLineLayout extends View {
    private Paint textPaint;
    private Paint graphPaint;

    private Context mContext;
    private List<CharData> dataList = new ArrayList<>();

    private float textSize = 40;
    private int textColor = Color.BLACK;
    private float textPaind = 10;

    private float circleSize = 20.0f;
    private float gapSize = 30.0f;


    public CutLineLayout(Context context) {
        super(context);
    }

    public CutLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CutLineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context,AttributeSet attrs){
      mContext = context;


      getGraphPaint();
      getTextPaint();
    }

    public void setDataList(List<CharData> list){
        this.dataList.clear();
        this.dataList.addAll(list);
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setMeasuredDimension(100,100);

    }


    int lineCount = 0;
    float textHeightView= 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetrics fontMetrics  = getTextPaint().getFontMetrics();
        float textHeight = Math.abs(fontMetrics.top) + Math.abs(fontMetrics.bottom);
        float textHeightHalf = textHeight / 2.0f;
        float startX = textHeightHalf;
        float circleRadius = circleSize/2.0f;
        for (int i = 0; i <  dataList.size(); i++) {
            CharData charData = dataList.get(i);
            getGraphPaint().setColor(charData.getColor());
             canvas.drawCircle(startX, textHeightHalf,circleRadius,getGraphPaint());
             canvas.drawText(charData.getBottomLable(),startX + textPaind + circleRadius,Math.abs(fontMetrics.top),getTextPaint());
             startX = startX + circleRadius*2 + textPaind + getTextPaint().measureText(charData.getBottomLable()) + gapSize;
        }
        lineCount = 2;
        textHeightView = textHeight;
     }

    private Paint getTextPaint(){
        if(textPaint == null){
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setTextSize(textSize);
            textPaint.setColor(textColor);
            textPaint.setTextAlign(Paint.Align.LEFT);
        }
        return textPaint;
    }

    private Paint getGraphPaint(){
        if(graphPaint == null){
            graphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            graphPaint.setStyle(Paint.Style.FILL);
         }
        return graphPaint;
    }
}
