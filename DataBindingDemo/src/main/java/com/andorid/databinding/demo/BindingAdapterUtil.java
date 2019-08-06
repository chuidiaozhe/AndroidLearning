package com.andorid.databinding.demo;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Create by Xiangshifu
 * on 2018/11/29 11:21 AM
 */
public class BindingAdapterUtil {
     @BindingAdapter({"imageUrl","placeholder"})
    public static void loadImage(ImageView imageView, String url, Drawable placeDrawable){
         RequestOptions requestOptions  = new RequestOptions()
                 .skipMemoryCache(true)
                 .placeholder(placeDrawable);

         Glide.with(imageView.getContext())
                 .setDefaultRequestOptions(requestOptions)
                 .load(url)
                 .into(imageView);
    }

    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color){
     return new ColorDrawable(color);
    }

}
