package com.android.mpandroidchart.learning;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;

public class YAxisActivity extends AppCompatActivity {
    private LineChart lineChart;
    private BarChart barChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        lineChart = findViewById(R.id.lineChart);
        barChart = findViewById(R.id.barChart);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextSize(15);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawLabels(false);//无轴标签
        leftAxis.setDrawAxisLine(false);//没有轴线
        leftAxis.setDrawGridLines(true);//没有网格线
        leftAxis.setDrawZeroLine(true);//绘制零线

        leftAxis.setTypeface(Typeface.DEFAULT); //设置字体
        leftAxis.setTextSize(14); //设置字体大小
        leftAxis.setTextColor(Color.BLACK);//设置字体颜色
        leftAxis.setAxisMinimum(0f);//设置轴从哪开始
        leftAxis.setAxisMaximum(200f);//设置轴的最大值
        leftAxis.setGranularity(1f);//设置间隔
        leftAxis.setLabelCount(10,true);//设置强制几个标签

        lineChart.getAxisRight().setEnabled(false);//没有右轴


        YAxis rightAxis = lineChart.getAxisRight();
        YAxis yLeftAxis = lineChart.getAxis(YAxis.AxisDependency.RIGHT);


        lineChart.setData(DateUtils.getLineData());




    }
}
