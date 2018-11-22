package com.andorid.camera.demo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
 import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
 import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Create by Xiangshifu
 * on 2018/11/21 1:25 PM
 *
 *   系统拍照测试
 */
public class TakingPhotoActivity extends AppCompatActivity  implements View.OnClickListener{
    //拍照
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    //保存图片
    public static final int REQUEST_SAVE_IMAGE_CAPTURE = 3;
    //裁剪图片
    public static final int REQUEST_CROP_IMAGE = 4;
    //相册选择图片
    public static final int REQUEST_PICK_IMAGE = 5;

    public static final int FLAG_PERMISSION_CAMERA = 1001;
    public static final int FLAG_PERMISSION_EXTRA = 1002;

    private ImageView imageView;

    private boolean isCrop = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_photo);

        Toast.makeText(this, "是否有相机：" + checkHasSystemFeature(), Toast.LENGTH_SHORT).show();

        imageView = findViewById(R.id.iv_image);

        findViewById(R.id.btn_with_camera_app).setOnClickListener(this);
        findViewById(R.id.btn_save_full_image).setOnClickListener(this);
        findViewById(R.id.btn_crop_image).setOnClickListener(this);
        findViewById(R.id.btn_pick_image).setOnClickListener(this);
    }

    /**
     * 检查是否具有摄像头
     */
    private boolean checkHasSystemFeature(){
        PackageManager packageManager = this.getPackageManager();
        return  packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_with_camera_app:
                takePhotoWithForCamrea();
                break;
            case R.id.btn_save_full_image:
                //拍照保存完整图片
                isCrop = false;
                saveFullImage();
                break;
            case R.id.btn_crop_image:
                //裁剪图片
                isCrop = true;
                saveFullImage();
                break;
            case R.id.btn_pick_image:
                //从相册选择图片并裁剪
                Intent pickIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickIntent,REQUEST_PICK_IMAGE);
                break;
        }

    }

    /**
     * 用相机应用拍照
     */
    private void takePhotoWithForCamrea(){
       int permissionCode =  ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
       //检查是否有相机权限
       if(permissionCode == PackageManager.PERMISSION_GRANTED){
           Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           if(takePhotoIntent.resolveActivity(getPackageManager()) != null){
               startActivityForResult(takePhotoIntent,REQUEST_IMAGE_CAPTURE);
           }
       }else{
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},FLAG_PERMISSION_CAMERA);
       }

    }

    File photoFile = null;

    /**
     * 保存完成图片
     */
    private void saveFullImage(){
        int permissionCode =  ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        if(permissionCode == PackageManager.PERMISSION_GRANTED){
            int permissionExtra = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionExtra == PackageManager.PERMISSION_GRANTED){
                 Intent saveIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 if(saveIntent.resolveActivity(getPackageManager()) != null){
                     try {
                         photoFile = createImageFile();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     if(photoFile != null){
                         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                             //如果是7.0以上，使用FileProvider，否则会报错
                             saveIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                             //设置拍照后图片保存的位置
                             Uri photoUri = FileProvider.getUriForFile(this,"com.andorid.camera.demo.fileprovider",photoFile);
                             saveIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                         }else{
                             saveIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
                         }
                         //设置图片保存的格式
                         saveIntent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
                         startActivityForResult(saveIntent,REQUEST_SAVE_IMAGE_CAPTURE);
                     }
                 }
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},FLAG_PERMISSION_EXTRA);
            }
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},FLAG_PERMISSION_CAMERA);
        }
    }

    String mCurrentPhotoPath;
    /**
     * 创建一个存储图片的文件
     * @return
     */
    private File createImageFile() throws IOException {
         String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss" ).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg",storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE ){
            if(resultCode == RESULT_OK){
                if(data != null){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(bitmap);
                }else{
                    Toast.makeText(this, " data为空！", Toast.LENGTH_SHORT).show();
                }
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "用户取消！", Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_FIRST_USER){
                Toast.makeText(this, "RESULT_FIRST_USER！", Toast.LENGTH_SHORT).show();

            }

        }else if(requestCode == REQUEST_SAVE_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){
                //数据已保存至指定uri，所以此处data为空
                if(photoFile != null){
                    Log.e("testtest","photo_file  path :" + photoFile.getAbsolutePath());
                    if(isCrop){
                      cropImage(Uri.fromFile(photoFile));
                    }else{
                        setPic(photoFile);
                        //将保存的图片添加到图库中
                        Intent addGalleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(photoFile);
                        addGalleryIntent.setData(uri);
                        this.sendBroadcast(addGalleryIntent);
                    }
                }
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "用户取消！", Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_FIRST_USER){
                Toast.makeText(this, "RESULT_FIRST_USER！", Toast.LENGTH_SHORT).show();

            }
        }else if(requestCode == REQUEST_CROP_IMAGE){
            //裁剪图片成功
            if(cropFile != null){
                setPic(cropFile);
            }else{
                Log.e("testtest","========cropFile is null=========");
            }
        }else if(requestCode == REQUEST_PICK_IMAGE){
            //从相册选择照片
            if(resultCode == RESULT_OK && data != null){
              Uri uri =  data.getData();
               cropImage(uri);
            }
        }
    }

    private File cropFile;
    private void cropImage(Uri imageSourceUri){
        if(imageSourceUri != null){
             try {
                //创建一个裁剪后保存图片的File
                cropFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(cropFile != null){
               Intent cropIntent = new Intent("com.android.camera.action.CROP");
               cropIntent.putExtra("crop",true);//是否裁剪
                cropIntent.putExtra("aspectX",1);//x方向上的比例
                cropIntent.putExtra("aspectY",1);//y方向上的比例
                cropIntent.putExtra("outputX",500);//裁剪区的宽
                cropIntent.putExtra("outputY",500);//裁剪去的高
                cropIntent.putExtra("scale",true);//是否保留比例
                cropIntent.putExtra("return-data",false);//是否在intent中返回图片
                cropIntent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //设置数据源，必须是由FileProvider创建的ContentUri
                    File file = getFileByUri(imageSourceUri);
                    if(file != null){
                       try {
                           Uri uri = FileProvider.getUriForFile(this,"com.andorid.camera.demo.fileprovider",file);
                           cropIntent.setDataAndType(uri,"image/*");
                       }catch (Exception e){
                           cropIntent.setDataAndType(imageSourceUri,"image/*");
                       }
                    }
                    Uri uri = Uri.fromFile(cropFile);
                    //设置输出，不需要ContentUri，否则失败
                    cropIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);

                }else{
                   cropIntent.setDataAndType(imageSourceUri,"image/*");
                   cropIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(cropFile));
                }
                startActivityForResult(cropIntent,REQUEST_CROP_IMAGE);
            }
        }

    }

    /**
     * 将一个Uri转换成File路径
     * @param context
     * @param uri
     * @return
     */
    public   String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 根据Uri获取文件
     * @param uri
     * @return
     */
    public File getFileByUri(Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append( MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] {  MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex( MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex( MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
//            Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }


    /**
     * 缩放bitmap
     * @param file
     */
    private  void setPic(File file){
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH =  bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FLAG_PERMISSION_CAMERA && permissions != null && permissions.length >= 1 && grantResults != null && grantResults.length >= 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //权限同意
                Toast.makeText(this,"相机权限同意",Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == FLAG_PERMISSION_EXTRA && permissions != null && permissions.length >= 1 && grantResults != null && grantResults.length >= 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //权限同意
                Toast.makeText(this,"存储权限同意",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
