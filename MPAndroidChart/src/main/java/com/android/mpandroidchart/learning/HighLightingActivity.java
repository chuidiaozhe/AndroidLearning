package com.android.mpandroidchart.learning;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.Date;

/**
 * @see {https://github.com/PhilJay/MPAndroidChart/wiki/Highlighting}
 * */
public class HighLightingActivity extends AppCompatActivity {
    private LineChart lineChart;
    private BarChart barChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        lineChart = findViewById(R.id.lineChart);
        barChart = findViewById(R.id.barChart);

        barChart.setTouchEnabled(false);

        LineDataSet lineDataSet = new LineDataSet(DateUtils.getEntryList2(),"语文");
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setHighLightColor(Color.YELLOW);
        lineDataSet.setHighlightLineWidth(3f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setLabelRotationAngle(90);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value+"ddd";
            }
        });

        lineChart.setHighlightPerDragEnabled(true);
        lineChart.setHighlightPerTapEnabled(true);
        lineChart.setData(new LineData(lineDataSet));
        lineChart.setDrawBorders(false);


        lineChart.highlightValue(10,40);
        lineChart.invalidate();


        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });



//        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getAxisLeft().setDrawAxisLine(false);
         barChart.getAxisRight().setDrawAxisLine(false);

        BarData barData = new BarData(DateUtils.getBarDataSet());
        barData.setBarWidth(0.9f);
        barChart.setData(barData);
//        barChart.setFitBars(true);
//        barChart.invalidate();

    }
}
