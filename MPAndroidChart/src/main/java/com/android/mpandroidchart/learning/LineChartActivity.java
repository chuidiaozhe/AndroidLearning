package com.android.mpandroidchart.learning;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2019-08-15 11:24
 *
 *  折线图
 * @link {https://blog.csdn.net/qq_26787115/article/details/53185657}
 *
 */
public class LineChartActivity  extends AppCompatActivity {
    final String[] quarters = new String[] { "Q1", "Q2", "Q3", "Q4", "Q5", "Q6", "Q7" };

    private LineChart mLineChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        mLineChart = findViewById(R.id.mLineChar);

//        List<Entry> valsComp1 = new ArrayList<Entry>();
//        List<Entry> valsComp2 = new ArrayList<Entry>();
//
//        Entry c1e1 = new Entry(0f, 100000f); // 0 == quarter 1
//        valsComp1.add(c1e1);
//        Entry c1e2 = new Entry(1f, 1000f); // 1 == quarter 2 ...
//        valsComp1.add(c1e2);
//        valsComp1.add(new Entry(2f,2000f));
//        valsComp1.add(new Entry(3f,32000f));
//        valsComp1.add(new Entry(4f,234000f));
//        valsComp1.add(new Entry(5f,90000f));
//        // and so on ...
////        Entry c2e1 = new Entry(0f, 130000f); // 0 == quarter 1
////        valsComp2.add(c2e1);
////        Entry c2e2 = new Entry(1f, 115000f); // 1 == quarter 2 ...
////        valsComp2.add(c2e2);
//
//
//        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
//        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
////        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
////        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
//
//
//        XAxis xAxis = mLineChart.getXAxis();
//        xAxis.setGranularity(1);
//
//        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
////        dataSets.add(setComp1);
////        dataSets.add(setComp2);
//        LineData data = new LineData( );
//        data.addDataSet(new LineDataSet(valsComp1,"测试"));
//        mLineChart.setData(data);
//        mLineChart.invalidate(); // refresh
////        LineData lineData = new LineData();
////
////        Description description = new Description();
////        description.setText("MDescription");
////
////        description.setTextColor(Color.RED);
////        mLineChart.setDescription(description);
////        mLineChart.setData(lineData);


        List<Entry> entryList = new ArrayList<>();
        entryList.add(new Entry(0,1000));
        entryList.add(new Entry(2,900));
        entryList.add(new Entry(3,500));
        entryList.add(new Entry(4,100));
        entryList.add(new Entry(5,900));
        entryList.add(new Entry(5,10));


        //设置x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        //设置左y轴
        YAxis leftYAxis = mLineChart.getAxisLeft();
        leftYAxis.setInverted(false);
        leftYAxis.setTextColor(Color.GREEN);
        leftYAxis.setTextSize(11);
        leftYAxis.setDrawZeroLine(false);
        leftYAxis.setEnabled(false);

        //设置右y轴
        YAxis rightYAxis = mLineChart.getAxisRight();
        rightYAxis.setDrawZeroLine(false);
        rightYAxis.setDrawLabels(false);
        rightYAxis.setDrawLabels(false);
        rightYAxis.setEnabled(false);

        mLineChart.setTouchEnabled(false);//设置是否可以触摸
        mLineChart.setDragEnabled(false);//设置是否可以拖拽


        //图表描述
        Description description = mLineChart.getDescription();
        description.setEnabled(false);
        description.setText("测试数据");
        description.setTextSize(12);
        description.setTextColor(Color.LTGRAY);
        description.setPosition(300,300);

        //设置图例
        Legend legend = mLineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.GRAY);//设置颜色
        legend.setTextSize(12);//设置大小
        legend.setTypeface(Typeface.DEFAULT);
        legend.setWordWrapEnabled(true); //设置当图例文字过长时是否可以折行
        legend.setMaxSizePercent(0.99f); //设置在视图中的最大相对大小，默认值：0.95f(95%)
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER); //设置图例当位置
        legend.setForm(Legend.LegendForm.CIRCLE);//设置图例的颜色块
        legend.setFormSize(10);//设置图例颜色块的大小，单位是dp
        legend.setXEntrySpace(100);//设置水平轴上图例条目之间的间距
        legend.setYEntrySpace(10);//设置垂直方向上图例条目之间的间距
        legend.setFormToTextSpace(10);//设置文字和图例块的间距

        List<LegendEntry> legendEntryList = new ArrayList<>();
        LegendEntry legendEntry1 = new LegendEntry();
        legendEntry1.formColor = Color.GRAY;
        legendEntry1.label = "图例1";
        LegendEntry legendEntry2= new LegendEntry();
        legendEntry2.formColor = Color.BLUE;
        legendEntry2.label = "图例2";
        legendEntryList.add(legendEntry1);
        legendEntryList.add(legendEntry2);
         legend.setCustom(legendEntryList);
         legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

         //设置动画
//         mLineChart.animateX(1300);
//         mLineChart.animateY(1300);
          mLineChart.animateXY(1500,1500, Easing.EasingOption.EaseInCubic, Easing.EasingOption.EaseInCubic);


        LineDataSet lineDataSet = new LineDataSet(entryList,"第一条数据第一条数据第一条数据第一条数据第一条数据第一条数据第一条数据第一条数据第一条数据");
        //设置是否填满
        lineDataSet.setFillColor(Color.GRAY);
        lineDataSet.setDrawFilled(true);

        //设置数据小圆点
        lineDataSet.setCircleColor(Color.RED);
        lineDataSet.setCircleRadius(3);
        lineDataSet.setCircleColorHole(Color.GREEN);
        lineDataSet.setValueTextColor(Color.BLACK);

        //设置折线颜色
        lineDataSet.setLineWidth(1);
        lineDataSet.setColor(Color.LTGRAY);
        lineDataSet.setValueTextSize(10);

        LineData lineData = new LineData(lineDataSet);
        //是否绘制顶部的值
        lineData.setDrawValues(false);

        lineData.addDataSet(lineDataSet);
        mLineChart.setData(lineData);

     }
}
