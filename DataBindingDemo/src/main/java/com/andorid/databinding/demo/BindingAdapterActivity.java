package com.andorid.databinding.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andorid.databinding.demo.databinding.ActivityBindingAdapterBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Create by Xiangshifu
 * on 2018/11/29 11:31 AM
 *  Databinding 自定义属性
 */
public class BindingAdapterActivity extends AppCompatActivity {
    ActivityBindingAdapterBinding dataBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_binding_adapter);
        User user = new User("乔","布斯","https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=e948e7bdc88065386fe7ac41f6b4ca21/8694a4c27d1ed21b74af4c16af6eddc450da3fda.jpg");
        dataBinding.setUser(user);
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.ic_launcher_background);
//        Glide.with(this).setDefaultRequestOptions(requestOptions).load(user.getHeadImage()).into(dataBinding.ivImage);

    }
}
