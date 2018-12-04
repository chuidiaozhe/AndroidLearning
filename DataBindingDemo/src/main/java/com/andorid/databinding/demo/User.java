package com.andorid.databinding.demo;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

/**
 * Create by Xiangshifu
 * on 2018/11/27 5:40 PM
 */
public class User extends ViewModel implements Observable {
    private String firsetName;
    private String lastName;
    private String headImage;
    private PropertyChangeRegistry registry = new PropertyChangeRegistry();

    public User(String firsetName, String lastName) {
        this.firsetName = firsetName;
        this.lastName = lastName;
    }

    public User(String firsetName, String lastName, String headImage) {
        this.firsetName = firsetName;
        this.lastName = lastName;
        this.headImage = headImage;
    }

    public String getHeadImage() {
        return headImage == null ? "" : headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public PropertyChangeRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(PropertyChangeRegistry registry) {
        this.registry = registry;
    }

    public String getFirsetName() {
        return firsetName == null ? "" : firsetName;
    }

    public void setFirsetName(String firsetName) {
        this.firsetName = firsetName;
    }

    public String getLastName() {
        return lastName == null ? "" : lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
          registry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
      registry.remove(callback);
    }

}
