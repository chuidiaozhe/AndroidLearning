package com.tablayout.learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.andorid.material.design.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2018/10/15 上午11:28
 */
public class SettingFragmentActivity  extends AppCompatActivity  implements View.OnClickListener {
    private CheckBox cbHome;
    private CheckBox cbOrder;
    private CheckBox cbSupplier;
    private CheckBox cbPerson;

    private ArrayList<Integer> userSelect = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_fragment);

        cbHome = findViewById(R.id.cb_home);
        cbOrder = findViewById(R.id.cb_order);
        cbSupplier = findViewById(R.id.cb_supplier);
        cbPerson = findViewById(R.id.cb_person);

        findViewById(R.id.go_home).setOnClickListener(this);

        cbHome.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                userSelect.add(Constant.HOME_FRAGMENT);
            }else{
                userSelect.remove(Constant.HOME_FRAGMENT);
            }
        });


        cbOrder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                userSelect.add(Constant.ORDER_FRAGMENT);
            }else{
                userSelect.remove(Constant.ORDER_FRAGMENT);
            }
        });


        cbSupplier.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                userSelect.add(Constant.SUPPLIER_FRAGMENT);
            }else{
                userSelect.remove(Constant.SUPPLIER_FRAGMENT);
            }
        });


        cbPerson.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                userSelect.add(Constant.PERSON_FRAGMENT);
            }else{
                userSelect.remove(Constant.PERSON_FRAGMENT);
            }
        });

    }
    private void removeData(Integer type){
        if(userSelect.contains(type)){
            userSelect.remove(type);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_home:
                Intent intent = new Intent(SettingFragmentActivity.this,HomeActivity.class);
                intent.putIntegerArrayListExtra(Constant.KEY_FRAGMENT_TYPE,userSelect);
                startActivity(intent);
                break;
        }

    }
}
