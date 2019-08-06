package com.android.rajava2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.rajava2.demo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by Xiangshifu
 * on 2019/1/6 7:18 AM
 */
public class TimeActivity  extends AppCompatActivity  implements TextWatcher {
    private EditText edt_fatie;
    private EditText edt_huitie1;
    private EditText edt_huitie2;
    private EditText restult_1;
    private EditText restult_2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        edt_fatie  = findViewById(R.id.edt_fatie);
        edt_huitie1  = findViewById(R.id.edt_huitie1);
        edt_huitie2  = findViewById(R.id.edt_huitie2);
        restult_1  = findViewById(R.id.restult_1);
        restult_2  = findViewById(R.id.restult_2);

        edt_fatie.addTextChangedListener(this);
        edt_huitie1.addTextChangedListener(this);
        edt_huitie2.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
         setResult1();
         setResult2();
    }

    private void setResult1(){
        long time = getLongTime(edt_huitie1.getText().toString()) - getLongTime(edt_fatie.getText().toString());

        restult_1.setText(time/(1000*60L) + "");

    }

    private void setResult2(){
        long time = getLongTime(edt_huitie2.getText().toString()) - getLongTime(edt_huitie1.getText().toString());

        restult_2.setText(time/(1000*60L) + "");
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 ahh:mm");
    public long  getLongTime(String time){
        if(TextUtils.isEmpty(time)){
            return  0L;
        }else{
            try {
                Date date = simpleDateFormat.parse(time.trim());
                return  date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                return  0l;
            }
        }
    }
}
