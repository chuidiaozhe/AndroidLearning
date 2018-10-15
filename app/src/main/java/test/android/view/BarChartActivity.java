package test.android.view;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import test.android.com.androidtest2.R;

/**
 * Create by Xiangshifu
 * on 2018/7/12 下午4:55
 */
public class BarChartActivity extends AppCompatActivity {
    private BarChart barChart;
    private  FunnelChart funnelChart;
    private DountChartView dountChartView;
    private DountChartView2 dountChartView2;
    private CutLineLayout cutLineLayout ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        barChart = findViewById(R.id.barchart);
        funnelChart = findViewById(R.id.fullchart);
        dountChartView = findViewById(R.id.doutChart);
        dountChartView2 = findViewById(R.id.doutChart2);
        cutLineLayout = findViewById(R.id.cutline);
        List<CharData> charData = new ArrayList<>();
             CharData data  = new CharData(50, Color.parseColor("#FF58CFFF"));
            data.setBottomLable("我"  );
            data.setLabe("10" );
            data.setPercent(0.1f);
            data.setData(10);


        CharData data2  = new CharData(50, Color.parseColor("#FF2AD9B6"));
        data2.setBottomLable("店平均3"  );
        data2.setLabe("4.1" );
        data2.setPercent(0.1f);
        data2.setData(4.1f);

        CharData data3  = new CharData(50, Color.parseColor("#FFFF0000"));
        data3.setBottomLable("店平均2"  );
        data3.setLabe("60" );
        data3.setPercent(0.45f);
        data3.setData(60f);

        CharData data4 = new CharData(50, Color.parseColor("#FFFF00ff"));
        data4.setBottomLable("店平均1"  );
        data4.setLabe("60" );
        data4.setPercent(0.01f);

        CharData data5 = new CharData(50,Color.parseColor("#FFff9988"));
        data5.setBottomLable("进店");
        data5.setLabe("40");
        data5.setPercent(0.05f);

        CharData data6 = new CharData(50,Color.parseColor("#FFff3333"));
        data6.setBottomLable("进店当地");
        data6.setLabe("40");
        data6.setPercent(0.05f);

        CharData data7 = new CharData(50,Color.parseColor("#FFff33F8"));
        data7.setBottomLable("当地当地当地");
        data7.setLabe("40");
        data7.setPercent(0.01f);


        CharData data8 = new CharData(50,Color.parseColor("#FFff3344"));
        data8.setBottomLable("东江大酒店");
        data8.setLabe("40");
        data8.setPercent(0.01f);


        charData.add(data2);
        charData.add(data);
        charData.add(data4);
        charData.add(data5);
        charData.add(data4);
        charData.add(data6);
        charData.add(data4);
        charData.add(data7);
        charData.add(data8);
        charData.add(data2);
        charData.add(data3);


        barChart.gethLinePaint().setPathEffect(new DashPathEffect(new float[]{10,10.0f},5.0f));
        barChart.setDataList(charData);

        funnelChart.setChartLableFormat(new ChartLableFormat() {
            @Override
            public String formatLable(CharData data) {
                return data.getBottomLable() + data.getData();
            }
        });
        funnelChart.setDataList(charData);
        dountChartView.setDataList(charData);
        dountChartView.drawTopInfo("68%");
        dountChartView.drawBottomInfo("当日完成率");

        dountChartView2.drawTopInfo("68%");
        dountChartView2.drawBottomInfo("当日完成率");
        dountChartView2.setDataList(charData);

        cutLineLayout.setDataList(charData);
//         barChart.gethLinePaint().setColor(Color.parseColor("#FFDCDCDC"));
//        barChart.gethLinePaint().setStrokeWidth(3);
//        barChart.getSidePaint().setColor(Color.BLACK);
//        barChart.getBottomLablePaint().setColor(Color.parseColor("#FF666666"));
//        barChart.getSidePaint().setStrokeWidth(3);
//        barChart.getSidePaint().setColor(Color.parseColor("#FFDCDCDC"));
//        barChart.getBottomLablePaint().setTextSize(30);
//        barChart.getLablePaint().setTextSize(30);
//        barChart.getLablePaint().setColor(Color.BLUE);
//        barChart.getRectPaint().setStrokeCap(Paint.Cap.SQUARE);

    }
}
