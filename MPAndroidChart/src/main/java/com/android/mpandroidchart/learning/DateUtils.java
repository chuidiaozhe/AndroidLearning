package com.android.mpandroidchart.learning;

import android.graphics.Color;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    public static LineData getLineData(){


        LineDataSet lineDataSet = new LineDataSet(getEntryList2(),"语文");
        lineDataSet.setCircleColor(Color.GREEN);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setValueTextColor(Color.RED);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }

    public static List<Entry> getEntryList(){
        List<Entry> entryList2 = new ArrayList<>();
        entryList2.add(new Entry(10,90));
        entryList2.add(new Entry(40,98));
        entryList2.add(new Entry(60,10));
        entryList2.add(new Entry(20,50));
        entryList2.add(new Entry(30,55));
        entryList2.add(new Entry(40,98));
        entryList2.add(new Entry(50,10));
        entryList2.add(new Entry(60,10));
        entryList2.add(new Entry(20,50));
        entryList2.add(new Entry(30,55));
        entryList2.add(new Entry(40,98));
        entryList2.add(new Entry(50,10));
        entryList2.add(new Entry(60,10));
        entryList2.add(new Entry(70,87));
        entryList2.add(new Entry(80,90));
        return  entryList2;
    }


    public static List<Entry> getEntryList2(){
        List<Entry> entryList = new ArrayList<>();
        entryList.add(new Entry(10,90));
        entryList.add(new Entry(20,50));
        entryList.add(new Entry(30,55));
        entryList.add(new Entry(40,98));
        entryList.add(new Entry(50,10));
        entryList.add(new Entry(60,10));
        entryList.add(new Entry(70,87));
        entryList.add(new Entry(80,90));
        return entryList;
    }

    public static List<BarEntry> getBarEntry(){
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0f, 30f));
        barEntries.add(new BarEntry(1f, 80f));
        barEntries.add(new BarEntry(2f, 60f));
        barEntries.add(new BarEntry(3f, 50f));
        // gap of 2f
        barEntries.add(new BarEntry(5f, 70f));
        barEntries.add(new BarEntry(6f, 60f));
        return barEntries;
    }

    public static BarDataSet getBarDataSet(){
        return new BarDataSet(getBarEntry(),"BarDataSet");
    }


}
