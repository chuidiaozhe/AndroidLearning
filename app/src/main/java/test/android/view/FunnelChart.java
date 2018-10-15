package test.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import test.android.com.androidtest2.R;

/**
 * Create by Xiangshifu
 * on 2018/7/15 下午2:39
 */
public class FunnelChart  extends View{
    private Paint mPaint;
    private Paint textPaint;
    private Paint linePaint;

    private Path path;


    private int textColor = Color.BLACK;
    private int lineColor = Color.BLACK;
    private float textSize = 20;
    private int maxLine = 800;

    private List<CharData> dataList = new ArrayList<>();

    private Context mContext;

    private int viewHeight;
    private boolean isLineColorSync = true;
    private float lineLength = 40.0f;
    private float lineWidht = 3.0f;
    private float textPading = 5.0f;

    private ChartLableFormat chartLableFormat;

    public FunnelChart(Context context) {
        super(context);
        initView(context,null);
    }

    public void setChartLableFormat(ChartLableFormat format){
        this.chartLableFormat = format;
    }

    public FunnelChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public FunnelChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context,AttributeSet attrs){
           mContext = context;
           if(attrs != null){
               TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.FunnelChart);
               textColor = typedArray.getColor(R.styleable.FunnelChart_textColor,Color.BLACK);
               lineColor = typedArray.getColor(R.styleable.FunnelChart_lineColor,Color.BLACK);
               textSize = typedArray.getDimension(R.styleable.FunnelChart_textSize,20);
               maxLine = typedArray.getDimensionPixelOffset(R.styleable.FunnelChart_maxLine,400);
               isLineColorSync = typedArray.getBoolean(R.styleable.FunnelChart_isLineColorSync,true);
               lineLength = typedArray.getDimension(R.styleable.FunnelChart_lineLenght,40.0f);
               textPading = typedArray.getDimension(R.styleable.FunnelChart_textPading,5.0f);
               lineWidht = typedArray.getDimension(R.styleable.FunnelChart_lineWidht,3.0f);
               typedArray.recycle();
           }

            path = new Path();
           getLinePaint();
           getmPaint();
           getTextPaint();
    }
    int height = 100;
    private PathMeasure pathMeasure;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate( getWidth()/2,0);

        if(dataList != null && dataList.size() > 0){
            height = getHeight()/dataList.size();
            for (int i = 0; i < dataList.size() ; i++) {
                 getmPaint().setColor(dataList.get(i).getColor());
                path.reset();
                float
                        w1=(float) ((maxLine*dataList.get(i).getData())/(dataList.get(0).getData()*2.0));
                path.moveTo(0-w1,i*height);
                path.lineTo(w1,i*height);
                Path temPath = new Path();
                temPath.moveTo(w1,i*height);
                if((i + 1) >= dataList.size()){
                    path.lineTo(0,dataList.size()*height);
                    temPath.lineTo(0,dataList.size()*height);
                }else{
                    float w2 =  (float) ((maxLine*dataList.get(i+1).getData())/(dataList.get(0).getData()*2.0));
                    path.lineTo(w2,(i+1) * height);
                    float w3 =  0 - w2;
                    path.lineTo(w3,(i+1) * height);
                    temPath.lineTo(w2,(i+1) * height);
                }
                if(pathMeasure == null){
                    pathMeasure = new PathMeasure();
                }
               pathMeasure.setPath(temPath,false);
                if(isLineColorSync){
                    getLinePaint().setColor(dataList.get(i).getColor());
                }
                float[] pos = new float[2];
                pathMeasure.getPosTan(pathMeasure.getLength()*0.5f,pos,null);
                canvas.drawLine(pos[0] - 2,pos[1],pos[0]+lineLength,pos[1],getLinePaint());
                Paint.FontMetrics fontMetrics = getTextPaint().getFontMetrics();
                if(this.chartLableFormat != null){
                    canvas.drawText(chartLableFormat.formatLable(dataList.get(i)),pos[0]+lineLength+textPading,pos[1] + Math.abs(fontMetrics.ascent)/2.0f,getTextPaint());
                }else{
                    canvas.drawText(dataList.get(i).getBottomLable(),pos[0]+lineLength+textPading,pos[1] + Math.abs(fontMetrics.ascent)/2.0f,getTextPaint());
                }


                canvas.drawPath(path,getmPaint());
            }

        }
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight  = h;
    }

    /**
     * 设置数据源，删除里面值为0的数据，并且按值的从大到小拍讯
     * @param list
     */
    @SuppressWarnings("unchecked")
    public void setDataList(List<CharData> list){
        List<CharData> charDataList = new ArrayList<>();
        charDataList.addAll(list);
        if(charDataList != null && charDataList.size() > 0){
            for (CharData data: charDataList) {
                if(data.getData() == 0.00f){
                    list.remove(data);
                }
            }
        }

        dataList.addAll(list);
         Collections.sort(dataList);
        postInvalidate();
    }

    /**
     * 获得绘制主体的画笔
     * @return
     */
    private Paint getmPaint(){
        if(mPaint == null){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.FILL);
        }
        return mPaint;
    }

    /**
     * 获得绘制线条画笔
     * @return
     */
    private Paint getLinePaint(){
        if(linePaint == null){
            linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setColor(lineColor);
            linePaint.setStrokeWidth(lineWidht);
        }
        return linePaint;
    }

    /**
     * 获得绘制文字的画笔
     * @return
     */
    private Paint getTextPaint(){
        if(textPaint == null){
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setTextSize(textSize);
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setColor(textColor);
        }
        return textPaint;
    }

}
