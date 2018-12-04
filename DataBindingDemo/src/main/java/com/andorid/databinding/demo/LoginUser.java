package com.andorid.databinding.demo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Create by Xiangshifu
 * on 2018/11/29 3:02 PM
 */
public class LoginUser extends BaseObservable {
    private String name;
    private String password;

    public LoginUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Bindable
    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.andorid.databinding.demo.BR.name);
     }

     @Bindable
    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(com.andorid.databinding.demo.BR.password);
    }
}
