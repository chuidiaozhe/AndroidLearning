package com.guide.learning;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.andorid.material.design.demo.R;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.RelativeGuide;

/**
 * Create by Xiangshifu
 * on 2018/10/10 上午10:12
 * @see  {https://github.com/huburt-Hu/NewbieGuide}
 * @description 引导层的使用
 */
public class GuideActivity extends AppCompatActivity  implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        findViewById(R.id.btn_simple_user).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_simple_user:
                //引导层的简单使用
                simpleUse();
                break;
        }
    }

    private void simpleUse(){
//        NewbieGuide.with(this).addGuidePage(GuidePage.newInstance().addHighLight(findViewById(R.id.btn_simple_user)).setLayoutRes(R.layout.activity_main)).setLabel("simple_guide").alwaysShow(true).show();
         NewbieGuide.with(this).addGuidePage(GuidePage.newInstance().addHighLight(findViewById(R.id.btn_simple_user),new RelativeGuide(R.layout.activity_main,Gravity.RIGHT,100)).setBackgroundColor(Color.parseColor("#33333333"))).setLabel("ssss").alwaysShow(true).show();
    }
}
