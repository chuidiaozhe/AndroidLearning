package com.android.mpandroidchart.learning;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @see {https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started}
 * */
public class GettingStartedActivity extends AppCompatActivity {
    private LineChart lineChart;
    private BarChart barChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        lineChart = findViewById(R.id.lineChart);
        barChart = findViewById(R.id.barChart);

         lineChart.setData(DateUtils.getLineData());


    }
}
