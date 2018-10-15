package com.andorid.material.design.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Create by Xiangshifu
 * on 2018/9/17 下午1:36
 */
public class CoordinatorLayoutActivity  extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_coordinator_layout);
       findViewById(R.id.fab_add).setOnClickListener(v -> {
           Snackbar.make(findViewById(R.id.fab_add),"测试Snackbar",Snackbar.LENGTH_SHORT).show();
       });
     }
}
