package test.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.android.com.androidtest2.R;

/**
 * Create by Xiangshifu
 * on 2018/8/6 下午1:12
 */
public class DountChartView2 extends View {

    private final String TAG = "DountChartView2";

    private Paint mPaint;
    private Paint mCenterPaint;
    private Paint textPaint;
    private Paint bgPaint;
    private Paint linePaint;
    private Paint drawTopInfoPaint;
    private Paint drawCentLinePaint;
    private Paint drawBottomInfoPaint;

    private float outRadius = 60.0f;
    private float startAngle = -90f;
    private float lineLenght = 40;
    private float lineHLength = 40;
    private float lineWidht = 3.0f;
    private float lableTextSize = 20.0f;
    private float textPading = 5.0f;
    private int lableTextColor = Color.BLACK;
    private int lineColor = Color.BLACK;
    private boolean isSyncLineColor = true;
    private boolean isSyncTextColor = true;
    private boolean isDrawLableText = true;
    private int bgColor = Color.GRAY;
    private int centerColor = Color.WHITE;


    private float topInfoTextSize = 20.0f;
    private int topInfoTextColor = Color.BLACK;
    private float bottomInfoTextSize = 20.0f;
    private int bottomInfoTextColor = Color.BLACK;
    private int centerLineColor = Color.BLACK;
    private float centerLineLength = 0.0f;
    private float centerLineWidth = 3.0f;
    private float topInfoPaddingBottom = 10.0f;
    private float bottomInfoPaddingTop = 10.0f;

    private List<CharData> dataList = new ArrayList<>();
    private String topInfo = "";
    private String bottomInfo = "";

    private ChartLableFormat chartLableFormat;

    public DountChartView2(Context context) {
        super(context);
        initView(context,null);
    }

    public DountChartView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public DountChartView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs){
        if(attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DountChartView);
            outRadius = typedArray.getDimension(R.styleable.DountChartView_outRadius,60.0f);
            startAngle = typedArray.getFloat(R.styleable.DountChartView_startAngle,-90.0f);
            lineLenght = typedArray.getDimension(R.styleable.DountChartView_lineLength,40.0f);
            lineHLength = typedArray.getDimension(R.styleable.DountChartView_lineHLength,40.0f);
            lineWidht = typedArray.getDimension(R.styleable.DountChartView_linewidth,3.0f);
            lableTextSize = typedArray.getDimension(R.styleable.DountChartView_lableTextSize,20.0f);
            lableTextColor  = typedArray.getColor(R.styleable.DountChartView_lableTextColor, Color.BLACK);
            lineColor = typedArray.getColor(R.styleable.DountChartView_lineColors, Color.BLACK);
            isSyncLineColor = typedArray.getBoolean(R.styleable.DountChartView_isSyncLineColor,true);
            isSyncTextColor = typedArray.getBoolean(R.styleable.DountChartView_isSyncTextColor,true);
            topInfoTextColor = typedArray.getColor(R.styleable.DountChartView_topInfoTextColor, Color.BLACK);
            topInfoTextSize = typedArray.getDimension(R.styleable.DountChartView_topInfoTextSize,20.0f);
            topInfoPaddingBottom = typedArray.getDimension(R.styleable.DountChartView_topInfoPadingBottom,10.0f);
            bottomInfoTextSize = typedArray.getDimension(R.styleable.DountChartView_bottomInfoTextSize,20.0f);
            bottomInfoTextColor = typedArray.getColor(R.styleable.DountChartView_bottomInfoTextColor, Color.BLACK);
            bottomInfoPaddingTop = typedArray.getDimension(R.styleable.DountChartView_bottomInfoPaddingTop,10.0f);
            centerLineColor = typedArray.getColor(R.styleable.DountChartView_centerLineColor, Color.BLACK);
            centerLineLength  = typedArray.getDimension(R.styleable.DountChartView_centLineLength,0.0f);
            centerLineWidth = typedArray.getDimension(R.styleable.DountChartView_centLineWidth,3.0f);
            bgColor = typedArray.getColor(R.styleable.DountChartView_bgColor, Color.GRAY);
            textPading = typedArray.getDimension(R.styleable.DountChartView_textHPading,0.0f);
            isDrawLableText = typedArray.getBoolean(R.styleable.DountChartView_isDrawLableText,false);
            topInfo =  typedArray.getString(R.styleable.DountChartView_default_topinfo);
            bottomInfo = typedArray.getString(R.styleable.DountChartView_default_bottominfo);
            typedArray.recycle();
        }


        //初始化画笔
        getmPaint();
        getTextPaint();
        getBgPaint();
        getLinePaint();
        getCenterPaint();
        getDrawBottomInfoPaint();
        getDrawCentLinePaint();
        getDrawTopInfoPaint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidht = getWidth();
        int viewHeight = getHeight();
        int maxSize = viewWidht > viewHeight ? viewHeight:viewWidht;
        canvas.translate(viewWidht/2.0f,maxSize/2.0f);
        float radius = (maxSize - outRadius - lineHLength *2 - lineLenght*2) / 2.0f ;
        RectF rectF = new RectF(-radius,-radius,radius,radius);
        float tempStartAng = startAngle;
        getmPaint().setStrokeWidth(outRadius);
        if(dataList != null && dataList.size() > 0){
            Paint.FontMetrics lableFontMetrics = getTextPaint().getFontMetrics();
            float tempDrawTextXStart = 0.0f;
            float tempDrawTextYStart = 0.0f;
            float temptDrawTextXEnd = 0.0f;

          float textHeight =  lableFontMetrics.descent -  lableFontMetrics.ascent;
             for (int i = 0; i <  dataList.size(); i++) {
                CharData charData  = dataList.get(i);
                float swipAng = 360.0f * charData.getPercent();
                getmPaint().setColor(charData.getColor());
                canvas.drawArc(rectF,tempStartAng,swipAng,false,getmPaint());
                if(isDrawLableText && charData.getPercent() > 0.04f){
                   float startX = (float) ((radius + outRadius/2.0f)*Math.cos((tempStartAng + swipAng/2.0f)*Math.PI / 180.f));
                   float startY =  (float) ((radius + outRadius/2.0f)*Math.sin((tempStartAng + swipAng/2.0f)*Math.PI / 180.f));

                    float endX = (float) ((radius + outRadius/2.0f + lineLenght)*Math.cos((tempStartAng + swipAng/2.0f)*Math.PI / 180.f));
                    float endY =  (float) ((radius + outRadius/2.0f + lineLenght)*Math.sin((tempStartAng + swipAng/2.0f)*Math.PI / 180.f));

                    if(isSyncLineColor){
                        getLinePaint().setColor(charData.getColor());
                    }
                    canvas.drawLine(startX,startY,endX,endY,getLinePaint());
                    float textX;
                    float textY;
                    String text = charData.getBottomLable();
                    if(chartLableFormat != null){
                        text = chartLableFormat.formatLable(charData);
                    }
                    if(isSyncTextColor){
                        getTextPaint().setColor(charData.getColor());
                    }
                    if(endX >  0){
                        getTextPaint().setTextAlign(Paint.Align.LEFT);
                        textX = endX + lineHLength;
                        textY = endY;

                        canvas.drawLine(endX,endY,textX,textY,getLinePaint());
                        textX = textX + textPading;
                    }else{
                        getTextPaint().setTextAlign(Paint.Align.RIGHT);
                        textX = endX - lineHLength;
                        textY = endY;

                        canvas.drawLine(endX,endY,textX,textY,getLinePaint());
                        textX = textX - textPading;
                    }
                    textY = textY + Math.abs(lableFontMetrics.ascent)/2.0f;
                    canvas.drawText(text,textX,textY,getTextPaint());
                }
                tempStartAng = tempStartAng + swipAng;

            }
        }else{
            canvas.drawCircle(0,0,radius ,getBgPaint());
            canvas.drawArc(rectF,startAngle,360.0f,true,getCenterPaint());
        }

        //绘制中间部分
        if(centerLineLength > 0.0f){
            canvas.drawLine(0-centerLineLength/2.0f,0,centerLineLength/2.0f,0,getDrawCentLinePaint());
        }
        if(!TextUtils.isEmpty(topInfo)){
            Paint.FontMetrics topFontMetrics =  getDrawTopInfoPaint().getFontMetrics();
            canvas.drawText(topInfo,0,0 -topInfoPaddingBottom-Math.abs(topFontMetrics.descent) - centerLineWidth/2.0f,getDrawTopInfoPaint());
        }
        if(!TextUtils.isEmpty(bottomInfo)){
            Paint.FontMetrics bottomFontMetrics =  getDrawBottomInfoPaint().getFontMetrics();
            canvas.drawText(bottomInfo,0,0+bottomInfoPaddingTop + Math.abs(bottomFontMetrics.ascent) + centerLineWidth/2.0f,getDrawBottomInfoPaint());
        }
        onDrawInfo(canvas);


     }

    public void setChartLableFormat(ChartLableFormat format){
        this.chartLableFormat = format;
    }


    public String getTopInfo() {
        return topInfo;
    }

    public void setBottomInfo(String s){
        this.bottomInfo = s;
    }

    public void setTopInfo(String topInfo) {
        this.topInfo = topInfo;
    }

    public void drawTopInfo(String topinfo){
        this.topInfo = topinfo;
        if(!TextUtils.isEmpty(topInfo)){
            postInvalidate();
        }
    }

    public void drawBottomInfo(String bottominfo){
        this.bottomInfo = bottominfo;
        if(!TextUtils.isEmpty(bottomInfo)){
            postInvalidate();
        }
    }


    /**
     * 开放给用户绘制其它信息
     * @param canvas
     */
    public void onDrawInfo(Canvas canvas){

    }

    public Paint getDrawTopInfoPaint() {
        if(drawTopInfoPaint == null){
            drawTopInfoPaint = new Paint();
            drawTopInfoPaint.setAntiAlias(true);
            drawTopInfoPaint.setTextSize(topInfoTextSize);
            drawTopInfoPaint.setColor(topInfoTextColor);
            drawTopInfoPaint.setTextAlign(Paint.Align.CENTER);
        }
        return drawTopInfoPaint;
    }



    public Paint getDrawCentLinePaint() {
        if(drawCentLinePaint == null){
            drawCentLinePaint = new Paint();
            drawCentLinePaint.setAntiAlias(true);
            drawCentLinePaint.setColor(centerLineColor);
            drawCentLinePaint.setStrokeWidth(centerLineWidth);
            drawCentLinePaint.setStyle(Paint.Style.STROKE);
        }
        return drawCentLinePaint;
    }

    public Paint getDrawBottomInfoPaint() {
        if(drawBottomInfoPaint == null){
            drawBottomInfoPaint = new Paint();
            drawBottomInfoPaint.setAntiAlias(true);
            drawBottomInfoPaint.setTextAlign(Paint.Align.CENTER);
            drawBottomInfoPaint.setColor(bottomInfoTextColor);
            drawBottomInfoPaint.setTextSize(bottomInfoTextSize);
        }
        return drawBottomInfoPaint;
    }

    /**
     * 获得绘制线条的画笔
     * @return
     */
    public Paint getLinePaint(){
        if(linePaint == null){
            linePaint = new Paint();
            linePaint.setAntiAlias(true);
            linePaint.setColor(lineColor);
            linePaint.setStrokeWidth(lineWidht);
            linePaint.setStyle(Paint.Style.STROKE);
        }
        return  linePaint;
    }

    /**
     * 获得绘制主体的画笔
     * @return
     */
    public Paint getmPaint(){
        if(mPaint == null){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(outRadius);
        }
        return mPaint;
    }

    /**
     * 获得绘制文字的画笔
     * @return
     */
    public Paint getTextPaint(){
        if(textPaint == null){
            textPaint = new Paint();
            textPaint.setTextSize(lableTextSize);
            textPaint.setColor(lableTextColor);
            textPaint.setAntiAlias(true);
        }
        return  textPaint;
    }


    /**
     * 获得未绘制区域的画笔
     * @return
     */
    public Paint getBgPaint(){
        if(bgPaint == null){
            bgPaint = new Paint();
            bgPaint.setAntiAlias(true);
            bgPaint.setColor(bgColor);
            bgPaint.setStyle(Paint.Style.FILL);
        }
        return bgPaint;
    }

    /**
     * 获得中心区域的画笔
     * @return
     */
    public Paint getCenterPaint(){
        if(mCenterPaint == null){
            mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCenterPaint.setColor(centerColor);
            mCenterPaint.setStyle(Paint.Style.FILL);
        }
        return mCenterPaint;
    }

    /**
     * 设置数据源，删除里面值为0的数据
     * @param list
     */
    public void setDataList(List<CharData> list){
        dataList.clear();
        if(list != null) {
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
        }
        postInvalidate();
    }

    public List<CharData> getDataList(){
        return dataList;
    }
}
