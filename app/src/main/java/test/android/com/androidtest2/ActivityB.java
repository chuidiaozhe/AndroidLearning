package test.android.com.androidtest2;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yigame.android.com.mylibrary.MyActivityLib;

public class ActivityB extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "testtest";
    private EditText edtUrl;

    private final static  int TAG_START_DOWNLOAD  = 100;
    private final static int TAG_CHECK_DOWNLOAD = 101;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            long downloadID = (long) msg.obj;
            switch (msg.what){
                case TAG_START_DOWNLOAD:
                    getDownloadFile(downloadID);
                    break;
                case TAG_CHECK_DOWNLOAD:
                    getDownloadFile(downloadID);
                    break;
            }
        }
    };
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        edtUrl = findViewById(R.id.edt_downloadUrl);

        findViewById(R.id.btn_share).setOnClickListener(this);
        findViewById(R.id.btn_open_pdf).setOnClickListener(this);
        findViewById(R.id.btn_open_doc).setOnClickListener(this);
        findViewById(R.id.btn_open_execle).setOnClickListener(this);
        findViewById(R.id.btn_open_PPT).setOnClickListener(this);
        findViewById(R.id.btn_open_image).setOnClickListener(this);
        findViewById(R.id.btn_download).setOnClickListener(this);

        initKeyBoard();

        setEtFilter(edtUrl);

      }
    KeyboardView keyboardView;
      private  void initKeyBoard(){
          final Keyboard keyboard = new Keyboard(this,R.xml.number_key);
          keyboardView  = findViewById(R.id.keyboard);
          keyboardView.setKeyboard(keyboard);
          keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
              // 按下 key 时执行
              @Override
              public void onPress(int primaryCode) {
                  Log.d(TAG, "onPress: "+primaryCode);
              }

              // 释放 key 时执行
              @Override
              public void onRelease(int primaryCode) {
                  Log.d(TAG, "onRelease: "+primaryCode);
              }

              // 点击 key 时执行
              @Override
              public void onKey(int primaryCode, int[] keyCodes) {

                  Editable editable = edtUrl.getText();
                  int start = edtUrl.getSelectionStart();
                  switch (primaryCode) {
//                      case Keyboard.KEYCODE_SHIFT:// 设置shift状态然后刷新页面
//                          keyboard.setShifted(!keyboard.isShifted());
//                          keyboardView.invalidateAllKeys();
//                          break;
                      case Keyboard.KEYCODE_DELETE:// 点击删除键，长按连续删除
                          if (editable != null && editable.length() > 0 && start > 0) {
                              editable.delete(start - 1, start);
                          }
                          break;
//                      case -10:// 自定义code，切换到拼音键盘
//                          keyboardView.setKeyboard(keyboard);
//                          break;
//                      case -11:// 自定义code，切换到字母键盘
//                          keyboardView.setKeyboard(keyboard);
//                          break;
//                      case -12:// 自定义code
//                          // 切换到符号键盘，待实现
//                          break;
                      default:// 数值code
                          if (primaryCode >= 97 && primaryCode <= 97 + 26) {// 按下字母键
                              editable.insert(start, "A");
                          } else {// 其他code值，转字符在输入框中显示
                            List<Keyboard.Key> keyList = keyboard.getKeys();
                              editable.insert(start, primaryCode+"" );
                          }
                          break;
                  }
              }
              // 设置了 keyOutputText 属性后执行。
              @Override
              public void onText(CharSequence charSequence) {
                    edtUrl.append(charSequence);
              }

              @Override
              public void swipeLeft() {

              }

              @Override
              public void swipeRight() {

              }

              @Override
              public void swipeDown() {

              }

              @Override
              public void swipeUp() {

              }
          });

      }

    public   void setEtFilter(EditText et) {
        if (et == null) {
            return;
        }
        //表情过滤器
        InputFilter emojiFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
        //特殊字符过滤器
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[a-zA-Z]|[\u4e00-\u9fa5]+" ;
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return source;
                } else {
                    return "";
                }

            }
        };

        et.setFilters(new InputFilter[]{specialCharFilter});

    }

    @Override
    public void onClick(View view) {
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        switch (view.getId()){
            case R.id.btn_share:
//                File file = new File("/storage/emulated/0/Tencent/MicroMsg/Download/关于及时处理展业过程中各类纠纷矛盾的通知.pdf");
                File file = new File("/storage/emulated/0/oacrm/data/photo/29/13718.jpg");
                shareFile(ActivityB.this,file);




                break;
            case R.id.btn_open_pdf:
                //打开文件

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            100);
                }else{
                    Intent intent = new Intent(ActivityB.this,ReaderViewActivity.class);
                    intent.putExtra("fileName","/storage/emulated/0/Tencent/MicroMsg/Download/关于及时处理展业过程中各类纠纷矛盾的通知.pdf");
                    startActivity(intent);
                }
                break;
            case R.id.btn_open_PPT:
                //打开文件
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            100);
                }else{
                    Intent intent = new Intent(ActivityB.this,ReaderViewActivity.class);
                    intent.putExtra("fileName","/storage/emulated/0/tencent/qqfile_recv/极致车网-平安租赁二手车(1).pptx");
                    startActivity(intent);
                }
                break;
            case R.id.btn_open_doc:
                //打开文件
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            100);
                }else{
                    Intent intent = new Intent(ActivityB.this,ReaderViewActivity.class);
                    intent.putExtra("fileName","/storage/emulated/0/tencent/qqfile_recv/HowToLoadX5Core.doc");
                    startActivity(intent);
                }
                break;
            case R.id.btn_open_execle:
                //打开文件
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            100);
                }else{
                    Intent intent = new Intent(ActivityB.this,ReaderViewActivity.class);
                    intent.putExtra("fileName","/storage/emulated/0/tencent/qqfile_recv/【TBS第三方】官网43612 SDK发布前测试报告.xlsx");
                    startActivity(intent);
                }
                break;
                case R.id.btn_open_image:
                //打开文件
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            100);
                }else{
                    Intent intent = new Intent(ActivityB.this,ReaderViewActivity.class);
                    intent.putExtra("fileName","/storage/emulated/0/DCIM/Camera/IMG_20140721_175037-1.jpg");
                    startActivity(intent);
                }
                break;
            case R.id.btn_download:
                //下载
                String url = edtUrl.getText().toString();
                if(TextUtils.isEmpty(url)){
                    Toast.makeText(ActivityB.this, "请输入要下载的地址", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityB.this,MyActivityLib.class);
                    startActivity(intent);
                    return;
                }
                downLoadFile(url);
//                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                DownloadManager.Query query = new DownloadManager.Query();
//                Cursor  cursor = downloadManager.query(query);
//                if(cursor != null){
//                    while (cursor.moveToNext()){
//                    String url = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
//                    Log.e("testtest","=============" + url);
//                     }
//                }else{
//                    Log.e("testtest","====== cursor is null=======");
//                }
                break;
        }
    }

    private  void downLoadFile(String url){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS,url.substring(url.lastIndexOf("/")));
        // 设置一些基本显示信息
        request.setTitle("title");
        request.setDescription("description");
        request.setMimeType("application/vnd.android.package-archive");

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //开始下载
       long downloadId =  downloadManager.enqueue(request);
        Toast.makeText(this, "下载开始，下载ID：" + downloadId, Toast.LENGTH_SHORT).show();
         getDownLoadStatus(downloadId);
     }

    private void getDownLoadStatus(long downloadId){
         Message message = new Message();
         message.obj = downloadId;
         message.what = TAG_START_DOWNLOAD;
         mHandler.sendMessage(message);
          DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query  query =  new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if(cursor != null && cursor.moveToFirst()){
            int status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
            switch (status){
                case DownloadManager.STATUS_SUCCESSFUL:
                    break;
                case DownloadManager.STATUS_FAILED:
                    break;
                case DownloadManager.STATUS_PAUSED:
                    break;
                case DownloadManager.STATUS_RUNNING:
                    break;
                case DownloadManager.STATUS_PENDING:
                    break;
            }
            cursor.close();
        }
    }

    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private String getDownloadFile( long downloadId){
        String filePath = "";
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query  query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if(cursor != null && cursor.moveToFirst()){
            filePath = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
            //下载文件总共的大小
          long totalSize =   cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
          //当前下载的大小，以字节为单位
          long currentSize = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
          float percent = 0.0f;
          if(totalSize  >  0){
            percent = currentSize / (totalSize*1.0f) * 100;
          }
            Log.e("testtest","  download size :   currentSize :" + currentSize + "    totalSize :" + totalSize + " 百分比 ： " +  decimalFormat.format(percent) + "%");
            if( totalSize > 0 && totalSize <= currentSize){
                Log.e("testtets","文件路径 ： " + filePath);
                Intent intent = new Intent(ActivityB.this,ReaderViewActivity.class);
                intent.putExtra("fileName",getRealFilePath(this,Uri.parse(filePath)));
                startActivity(intent);
                mHandler.removeMessages(TAG_CHECK_DOWNLOAD);
            }else{
                Message message = new Message();
                message.what = TAG_CHECK_DOWNLOAD;
                message.obj = downloadId;
                mHandler.sendMessageDelayed(message,1000);
            }
            cursor.close();
        }

         return filePath;
    }
    public static String getRealFilePath( final Context context, final Uri uri ) {
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
    public void shareFile(Context context, File file){
          if(file != null && file.exists()){

//              Intent textIntent = new Intent(Intent.ACTION_SEND);
//
//              textIntent.setType("text/plain");
//
//              textIntent.putExtra(Intent.EXTRA_TEXT, "这是一段分享的文字");
//
//              startActivity(Intent.createChooser(textIntent, "分享"));

               Intent share = new Intent(Intent.ACTION_SEND);
              share.setType(getMimeType(file.getAbsolutePath()));
//              share.setType("image/jpeg");
              share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
              share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
              context.startActivity(Intent.createChooser(share,"分享文件"));
          }else{
              Toast.makeText(context,"分享文件不存在！",Toast.LENGTH_SHORT).show();
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
                if(TextUtils.isEmpty(mime)){
                    mime = "*/*";
                }
            }catch (Exception e){
                e.printStackTrace();
                return mime;
            }
        }
        return  mime;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
