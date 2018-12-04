package com.andorid.databinding.demo;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.andorid.databinding.demo.databinding.ActivityTwoWayBinding;

/**
 * Create by Xiangshifu
 * on 2018/11/29 2:06 PM
 * 双向绑定
 */
public class TwoWayActivity  extends AppCompatActivity {
    ActivityTwoWayBinding dataBinding;
      @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_two_way);

        final LoginUser loginUser = new LoginUser("张三","123456");
        loginUser.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(loginUser != null && !TextUtils.isEmpty(loginUser.getName()) && !TextUtils.isEmpty(loginUser.getPassword())){
                    dataBinding.btnLogin.setBackgroundColor(Color.GREEN);
                }else{
                    dataBinding.btnLogin.setBackgroundColor(Color.GRAY);
                }
            }
        });
        dataBinding.setLoginuser(loginUser);
    }
}
