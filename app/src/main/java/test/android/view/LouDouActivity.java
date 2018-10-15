package test.android.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import test.android.com.androidtest2.R;

public class LouDouActivity  extends AppCompatActivity{
    private LouDouView louDouView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loudou);
        louDouView = findViewById(R.id.loudou);
        List<Integer> list = new ArrayList<>();
        list.add(30);
        list.add(10);
        list.add(50);
        list.add(100);
        list.add(30);
        list.add(0);
        list.add(0);
        list.add(20);
        louDouView.setDataList(list);

    }
}
