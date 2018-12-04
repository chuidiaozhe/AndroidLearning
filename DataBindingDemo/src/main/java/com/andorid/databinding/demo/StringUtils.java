package com.andorid.databinding.demo;

/**
 * Create by Xiangshifu
 * on 2018/11/29 3:24 PM
 */
public class StringUtils {

    public static String getName(User user){
        return  user.getFirsetName() + user.getLastName();
    }
}
