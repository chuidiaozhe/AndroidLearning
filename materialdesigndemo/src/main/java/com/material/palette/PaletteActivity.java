package com.material.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andorid.material.design.demo.R;

import java.util.List;

/**
 * Create by Xiangshifu
 * on 2018/11/26 2:35 PM
 * @see {https://www.jianshu.com/p/dfa9aac6143d}
 *
 *Palette是调色版的意思，用它能获取到Bitmap中一些活跃的颜色，其他控件通过设置这些颜色来优化界面色彩搭配。
 */
public class PaletteActivity extends AppCompatActivity {
    private ImageView ivImage;
    private LinearLayout llColor;
    private TextView tvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        ivImage = findViewById(R.id.iv_image);
        llColor = findViewById(R.id.ll_color);
        tvText = findViewById(R.id.tv_text);

        Palette palette  = createPaletteSync(BitmapFactory.decodeResource(getResources(),R.drawable.test1));
        ivImage.setImageResource(R.drawable.test1);

        //获取到柔和的深色的颜色
     int darkMutedColor =   palette.getDarkMutedColor(Color.BLUE);
     //获取到活跃的深色颜色
     int darkVibrantColor =    palette.getDarkVibrantColor(Color.YELLOW);
//        // 获取到柔和的深色的颜色（可传默认值）
//        palette.getDarkMutedColor(Color.BLUE);
//        // 获取到活跃的深色的颜色（可传默认值）
//        palette.getDarkVibrantColor(Color.BLUE);
//        // 获取到柔和的明亮的颜色（可传默认值）
//        palette.getLightMutedColor(Color.BLUE);
//        // 获取到活跃的明亮的颜色（可传默认值）
//        palette.getLightVibrantColor(Color.BLUE);
//        // 获取图片中最活跃的颜色（也可以说整个图片出现最多的颜色）（可传默认值）
//        palette.getVibrantColor(Color.BLUE);
//        // 获取图片中一个最柔和的颜色（可传默认值）
//        palette.getMutedColor(Color.BLUE);
//// ...  这里省略其他的方法。

     llColor.removeAllViews();
     addView(darkMutedColor);
     addView(darkVibrantColor);

     Palette.Swatch swatch = palette.getDarkMutedSwatch();
     if(swatch == null){
      List<Palette.Swatch> swatchList =  palette.getSwatches();
      if(swatchList != null){
          for (Palette.Swatch swat : swatchList){
              if(swat != null){
                  swatch = swat;
                  break;
              }
          }
      }
     }

     if(swatch != null){
         int rgbColor =   swatch.getRgb();
         addView(rgbColor);
         int textColor = swatch.getBodyTextColor();
         tvText.setTextColor(textColor);
     }

     changeStatusBarColor();
    }

    /**
     * 改变状态栏颜色值
     */
    private void changeStatusBarColor(){
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.RED);

        }
    }

    private void addView(int color){
        View view = new View(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100,100);
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(color);
        llColor.addView(view);
    }

    /**
     * 同步生成Palette
     * @param bitmap
     * @return
     */
    private Palette createPaletteSync(Bitmap bitmap){
       Palette palette =    Palette.from(bitmap).generate();
       return  palette;
    }

    /**
     *异步生成plette，在不同的线程中调用
     * @param bitmap
     */
    private void createPalette(Bitmap bitmap){
          Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {

            }
        });
    }
}
