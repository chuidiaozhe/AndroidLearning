package test.android.com.androidtest2;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsReaderView;
import com.tencent.smtt.sdk.ValueCallback;

import java.io.File;

import javax.security.auth.login.LoginException;

import tbsplus.tbs.tencent.com.tbsplus.TbsPlus;
import test.android.view.zoomimageview.ZoomImageView;

/**
 * Create by Xiangshifu
 * on 2018/7/30 下午2:25
 */
public class ReaderViewActivity extends Activity {
    private TbsReaderView tbsReaderView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_view);

        tbsReaderView =  new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        });
        LinearLayout linearLayout = findViewById(R.id.ll_parent);

             File file = new File(getIntent().getStringExtra("fileName"));
             if(isImage(file.getPath())){
                 ZoomImageView zoomImageView = new ZoomImageView(this);
                 zoomImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                 zoomImageView.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
                 linearLayout.addView(zoomImageView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
             }else{
                 linearLayout.addView(tbsReaderView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        Bundle bundle = new Bundle();
        bundle.putString("filePath", file.getPath());
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());

        boolean result = tbsReaderView.preOpen(parseFormat(file.getPath()), false);
        if (result) {
            tbsReaderView.openFile(bundle);
        }
             }



      }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    private boolean isImage(String fielPath){
        String format = parseFormat(fielPath).toUpperCase();
        if(format.equals("JPG")||format.equals("PNG")||format.equals("JPEG")){
            return true;
        }else{
            return  false;
        }
    }

    /**
     * 根据文件后缀名获得对应的MIME类型
     * @param filePath
     * @return
     */
    public String  getMimeType(String filePath){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "*/*";
        if(filePath != null){
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            }catch (Exception e){
                e.printStackTrace();
                return mime;
            }
        }

        return  mime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tbsReaderView != null){
            tbsReaderView.onStop();
            tbsReaderView = null;
        }
    }
}
