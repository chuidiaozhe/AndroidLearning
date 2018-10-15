package test.android.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LouDouView extends View {
    private List<Integer> dataList;
    private int wWdith;
    private int wHeight;
    private Context mContext;
    private Paint paint;
    private Path path;
    private int[] colors = new int[]{Color.GREEN,Color.RED,Color.BLUE,Color.GRAY,Color.YELLOW,Color.GRAY,Color.LTGRAY};

    public LouDouView(Context context) {
        super(context);
        initView(context);
    }

    public LouDouView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LouDouView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.mContext = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Resources resources = mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        wWdith = displayMetrics.widthPixels;
        wHeight = displayMetrics.heightPixels;
    }

    public void setDataList(List<Integer> list){
        dataList = list;
        dataList.remove(0);
        Collections.sort(dataList);
        Collections.reverse(dataList);
        postInvalidate();
    }


    int height = 100;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        wWdith =  wWdith - 40;
        wHeight = wHeight - 40;
        canvas.translate( wWdith/2,20);


//        paint.setColor(colors[0]);
////        path.moveTo((float) (-1/2.0*wWdith),0);
//        path.lineTo((float) 520,0);
//        path.lineTo((float)  462.8,height);
//        path.lineTo((float) -462.8,height);
//        path.close();
//        canvas.drawPath(path,paint);

        if(dataList != null && dataList.size() > 0){

                 for (int i = 0; i < dataList.size() ; i++) {
                     paint.reset();
                    paint.setColor(colors[i]);
                    path.reset();
                     float
                            w1=(float) ((wWdith*dataList.get(i))/(dataList.get(0)*2.0));
                    path.moveTo(0-w1,i*height);
                    path.lineTo(w1,i*height);
                    if((i + 1) >= dataList.size()){
                        path.lineTo(0,dataList.size()*height);
                     }else{
                        float w2 =  (float) ((wWdith*dataList.get(i+1))/(dataList.get(0)*2.0));
                        path.lineTo(w2,(i+1) * height);
                        float w3 =  0 - w2;
                        path.lineTo(w3,(i+1) * height);
                    }
                       canvas.drawPath(path,paint);
                 }

        }
    }
}
