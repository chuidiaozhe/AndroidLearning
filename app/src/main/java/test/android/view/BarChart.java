package test.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
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
 * on 2018/7/12 下午2:53
 */
public class BarChart  extends View{

    /**
     * 绘制外边线的画笔
     */
    private Paint sidePaint;

    /**
     * 用来绘制水平线的画笔
     */
    private Paint  hLinePaint;

    /**
     * 用来绘制矩形的画笔
     */
    private Paint rectPaint;

    /**
     * 用来绘制底部标签的画笔
     */
    private Paint bottomLablePaint;

    /**
     * 用来绘制条目顶部标签的画笔
     */
    private Paint lablePaint;


    /**
     * 显示内容占控件水平方向的比例
     */
    private float contentPercent = 0.6f;

    /**
     * 设置间隙与条目所占的比例
     */
    private float spaceBarPercent = 1.2f;

    private float defaultsize = 12.0f;
    private int defaultColor = Color.BLACK;

    private float viewWidth;
    private float viewHeight;
    private float bottomLablePaddintTop = defaultsize;
    private float lablePaddingBottom = defaultsize;


    private float bottomLableTextSize = defaultsize;
     private float sideLineSize = 3;
    private float hLineSize = 3;
    private float lableTextSize = defaultsize;

    private int  bottomLableTextColor = defaultColor;
    private int sideLineColor = defaultColor;
    private int hLineColor = defaultColor;
    private int lableTextColor = defaultColor;


    private boolean isDrawBottomLable = true;
    private  boolean isDrawBottomLine = true;
    private boolean isDrawDrawHLine = true;
    private boolean isDrawLable = true;
    private boolean isSysncLableColor = true;

    private int viewsHeight = 500;


    private List<CharData> dataList = new ArrayList<>();
    /**
     * 水平方向横线的数量,默认为5条
     */
    private int hLineNum = 5;
    private float maxValue = 100.0f;
    public BarChart(Context context) {
        super(context);
        initView(context,null);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context,AttributeSet attrs){
        if(attrs != null){
            TypedArray typedArray  = context.obtainStyledAttributes(attrs,R.styleable.BarChart);
             isDrawBottomLable = typedArray.getBoolean(R.styleable.BarChart_drawBottomLables,true);
             bottomLableTextSize = typedArray.getDimension(R.styleable.BarChart_bottomLablesTextSize,defaultsize);
             bottomLableTextColor = typedArray.getColor(R.styleable.BarChart_bottomLablesTextColor,defaultColor);
             bottomLablePaddintTop = typedArray.getDimension(R.styleable.BarChart_bottomLablesPaddingTop,defaultsize);
             sideLineColor = typedArray.getColor(R.styleable.BarChart_sideLineColor,defaultColor);
             sideLineSize = typedArray.getDimension(R.styleable.BarChart_sideLineSize,defaultsize);
             isDrawBottomLine = typedArray.getBoolean(R.styleable.BarChart_drawSideLine,false);
             isDrawDrawHLine = typedArray.getBoolean(R.styleable.BarChart_drawHLine,false);
             hLineColor = typedArray.getColor(R.styleable.BarChart_hLineColor,defaultColor);
             hLineSize = typedArray.getDimension(R.styleable.BarChart_hLineSize,defaultsize);
             isDrawLable = typedArray.getBoolean(R.styleable.BarChart_drawLables,true);
             lableTextColor = typedArray.getColor(R.styleable.BarChart_lablesTextColor,defaultColor);
             lableTextSize = typedArray.getDimension(R.styleable.BarChart_lablesTextSize,defaultsize);
             lablePaddingBottom = typedArray.getDimension(R.styleable.BarChart_lablePaddingBottom,defaultsize);
             spaceBarPercent = typedArray.getFloat(R.styleable.BarChart_spaceBarPercent,1.0f);
             contentPercent  = typedArray.getFloat(R.styleable.BarChart_contentPercent,1.0f);
             hLineNum = typedArray.getInt(R.styleable.BarChart_hLineNum,5);
             isSysncLableColor = typedArray.getBoolean(R.styleable.BarChart_isSysncLableColor,true);
             viewHeight = typedArray.getDimensionPixelOffset(R.styleable.BarChart_viewheight,500);
            typedArray.recycle();
        }

        initPaint();
    }

    private void initPaint( ){
        getSidePaint();
        gethLinePaint();
        getRectPaint();
        getBottomLablePaint();
        getLablePaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            //绘制水平线
          if(isDrawDrawHLine){
              drawHLine(canvas);
          }
         //绘制底部边线
         if(isDrawBottomLine){
             drawBottomLabe(canvas);
         }
         //绘制矩形和文字

        if(dataList != null && dataList.size() > 0){
              float share = dataList.size()*(spaceBarPercent + 1) - spaceBarPercent;
              float barSpace  ;
              if(share !=  0.0f){
                  barSpace = viewWidth * contentPercent/share;
                  float leftSpace = (viewWidth - viewWidth*contentPercent) * 0.5f;
                  for (int i = 0; i < dataList.size(); i++) {
                      CharData charData = dataList.get(i);
                      //绘制文字
                      if(isDrawBottomLable){
                          float  x =  (float) (leftSpace + barSpace/2.0 + barSpace*(1 + spaceBarPercent)*i);
                          float y = viewHeight - getTextHeight(getBottomLablePaint())/2 + bottomLablePaddintTop;
                           canvas.drawText(charData.getBottomLable(),x,y,getBottomLablePaint());
                      }
                      //绘制矩形条目
                      if(maxValue != 0.0f ){
                          float precent = (getViewContentPaddingBottom() - getViewContentPaddingTop()) / maxValue;
                          getRectPaint().setColor(charData.getColor());
                          float left =  leftSpace +barSpace*(1 + spaceBarPercent)*i ;
                          float top = getViewContentPaddingBottom() - precent*charData.getData();
                          float right = left+barSpace ;
                          float bottom  =  getViewContentPaddingBottom();
                          canvas.drawRect(left,top,right,bottom,getRectPaint());

                      }
                      //绘制条目标签
                      if(isDrawLable && !TextUtils.isEmpty(charData.getLabe())){
                          if(isSysncLableColor){
                              getLablePaint().setColor(charData.getColor());
                          }else{
                              getLablePaint().setColor(lableTextColor);
                          }
                          float precent = (getViewContentPaddingBottom() - getViewContentPaddingTop() ) / maxValue;
                          float  x =  (float) (leftSpace + barSpace/2.0 + barSpace*(1 + spaceBarPercent)*i);
                          float y = getViewContentPaddingBottom() - precent*charData.getData() - lablePaddingBottom ;

                          canvas.drawText(charData.getLabe(),x,y,getLablePaint());
                        }

                  }
              }

        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        viewWidth  = MeasureSpec.getSize(widthMeasureSpec);
//        viewHeight =viewsHeight;
//        int widht = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        setMeasuredDimension(widht,viewsHeight);
     }

    private float getViewContentPaddingBottom() {
        float height = viewHeight;
        if(isDrawBottomLable){
            height = height - getTextHeight(getBottomLablePaint()) - bottomLablePaddintTop;
        }
        return height;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    private float getViewContentPaddingTop(){
        float height = viewHeight;
        if(isDrawLable){
           height =  getTextHeight(getLablePaint()) + lablePaddingBottom;
        }
        return height;
    }

    /**
     * 获得文字占据的高度
     * @param paint 画笔
      * @return 返回文字占的位置
     */
    private float getTextHeight(@NonNull Paint paint ){
     Paint.FontMetrics fontMetrics =  paint.getFontMetrics();
    return  Math.abs(fontMetrics.bottom)+Math.abs(fontMetrics.top);
    }

    /**
     *绘制水平方向的横线
     */
    private void drawHLine(Canvas canvas){
        float height = getViewContentPaddingBottom() - getViewContentPaddingTop();
        float hHeight = height;
        if(hLineNum != 0){
            hHeight = height / (hLineNum*1.0f);
        }
        float startX = 0 ;
        float endX = viewWidth;
         for (int i = 0; i < hLineNum  + 1 ; i++) {
             if(i == 0 && isDrawBottomLine){
             }else{
                 canvas.drawLine(   startX,getViewContentPaddingBottom() - hHeight*i   , endX,getViewContentPaddingBottom() - hHeight*i  ,gethLinePaint());
             }
        }
    }

    /**
     * 绘制底部的标签栏
     * @param canvas
     */
    private void drawBottomLabe(Canvas canvas){
        float height = getViewContentPaddingBottom() ;
        float startX = 0;
        float endx = viewWidth;

        canvas.drawLine(startX,height,endx,height,getSidePaint());
    }

    public List<CharData> getDataList() {
        return dataList;
    }

    public void setDataList(List<CharData> list) {
         this.dataList.clear();
         dataList.addAll(list);
         maxValue = getMaxValue(dataList);
         postInvalidate();
    }

    private float getMaxValue(List<CharData> list){
        float max = 0.0f;
        if( list != null){
            for (CharData data : list) {
                if(data.getData() >=  max){
                    max = data.getData();
                }
            }
        }
        return max;
    }


    public Paint getSidePaint(){
        if(sidePaint == null){
            sidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            sidePaint.setStyle(Paint.Style.STROKE);
            sidePaint.setColor(sideLineColor);
            sidePaint.setStrokeWidth(sideLineSize);
        }
        return sidePaint;
    }

    public Paint gethLinePaint(){
        if(hLinePaint == null){
            hLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            hLinePaint.setStyle(Paint.Style.STROKE);
            hLinePaint.setStrokeWidth(hLineSize);
            hLinePaint.setColor(hLineColor);
         }
        return hLinePaint;
    }

    public Paint getRectPaint() {
        if(rectPaint == null){
            rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            rectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            rectPaint.setColor(Color.RED);
            rectPaint.setStrokeCap(Paint.Cap.SQUARE);
        }
        return rectPaint;
    }


    public Paint getBottomLablePaint() {
        if(bottomLablePaint == null){
            bottomLablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
             bottomLablePaint.setTextAlign(Paint.Align.CENTER);
            bottomLablePaint.setColor(bottomLableTextColor);
            bottomLablePaint.setTextSize(bottomLableTextSize);
         }
        return bottomLablePaint;
    }


    public Paint getLablePaint() {
        if(lablePaint == null){
            lablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
              lablePaint.setTextAlign(Paint.Align.CENTER);
             lablePaint.setTextSize(lableTextSize);
             lablePaint.setColor(lableTextColor);
         }
        return lablePaint;
    }

}
