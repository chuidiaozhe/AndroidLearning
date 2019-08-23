package com.android.mpandroidchart.learning;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
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
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

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

        List<Entry> entryList = new ArrayList<>();
         entryList.add(new Entry(1,1000));
        entryList.add(new Entry(2,900));
        entryList.add(new Entry(3,500));
        entryList.add(new Entry(4,100));
        entryList.add(new Entry(5,0));
        entryList.add(new Entry(6,900));


        //设置x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(13);
        xAxis.setTextColor(Color.RED);
         xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);//是否绘制竖的分割线
        xAxis.setDrawLabels(true);
        xAxis.setAxisLineWidth(1);
        xAxis.setGridLineWidth(0.5f);
         xAxis.enableGridDashedLine(10f,10f,10);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(9);
        xAxis.setSpaceMin(40);
        String[] arr = new String[]{"","1月","2月","3月","4月","5月","6月","7月","8月","","10月","11月","12月"};
        xAxis.setLabelCount(9);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return arr[(int) value];
            }
        });


        //设置左y轴
        YAxis leftYAxis = mLineChart.getAxisLeft();
        leftYAxis.setInverted(false);
        leftYAxis.setTextColor(Color.GREEN);
        leftYAxis.setTextSize(11);
        leftYAxis.setDrawZeroLine(false);
         leftYAxis.setDrawLabels(false);
         leftYAxis.setDrawTopYLabelEntry(false);
         leftYAxis.setDrawAxisLine(false);
        leftYAxis.setDrawGridLines(true);
        leftYAxis.enableGridDashedLine(10f,10f,10);
        leftYAxis.setSpaceMax(10f);
        leftYAxis.setAxisMinimum(0f);

        //设置右y轴
        YAxis rightYAxis = mLineChart.getAxisRight();
        rightYAxis.setDrawZeroLine(true);
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
        legend.setEnabled(false);
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
        lineDataSet.setDrawFilled(false);

        //设置数据小圆点
        lineDataSet.setCircleColor(Color.parseColor("#58CEFF"));
        lineDataSet.setCircleRadius(4);
        lineDataSet.setCircleColorHole(Color.parseColor("#58CEFF"));
        lineDataSet.setValueTextColor(Color.BLACK);

        //设置折线颜色
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.parseColor("#58CEFF"));
        lineDataSet.setValueTextSize(10);


        LineData lineData = new LineData(lineDataSet);
         //是否绘制顶部的值
        lineData.setDrawValues(true);

        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int )value +"ddd";
            }
        });

        lineData.addDataSet(lineDataSet);
         mLineChart.setData(lineData);

     }
}
