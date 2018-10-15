package com.android.mpandroidchart.learning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

/**
 * @ses {https://github.com/PhilJay/MPAndroidChart/wiki/Interaction-with-the-Chart}
 */
public class InteractionWithTheChartActivity extends AppCompatActivity  implements OnChartGestureListener{
    private LineChart lineChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interation_with_the_chart);

        lineChart = findViewById(R.id.lineChart);
        //setTouchEnabled允许或者禁用与图表的交互
        lineChart.setTouchEnabled(true);
        //setDragEnabled启用或禁用图表的拖动
        lineChart.setDragEnabled(false);
        //setScaleEnabled启用或禁用两个轴上图表的缩放比例
        lineChart.setScaleEnabled(true);
        //setScaleXEnabled在x轴上启用/禁用缩放
        lineChart.setScaleXEnabled(true);
        //setPinchZoom如果设置为true，则启用捏缩放。如果禁用，x轴和y轴可以分开放大。
        lineChart.setPinchZoom(true);
        //setDoubleTapToZoomEnabled是否通过双击来缩放图表
        lineChart.setDoubleTapToZoomEnabled(true);

        lineChart.setData(DateUtils.getLineData());

        lineChart.setOnChartGestureListener(this);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }
}
