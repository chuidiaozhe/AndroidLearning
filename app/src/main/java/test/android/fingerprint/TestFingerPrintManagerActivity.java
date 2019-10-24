package test.android.fingerprint;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import test.android.com.androidtest2.R;


/**
 * Create by Xiangshifu
 * on 2019-10-24 17:11
 *   学习指纹调用
 *   @link {https://www.jianshu.com/p/dd8bcc224f24}
 *
 */
public class TestFingerPrintManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_finger_print_manager);

        findViewById(R.id.btn_click).setOnClickListener(v -> {
//            clickListener();
            clickListener2();
        });

    }

    private  void  clickListener2(){
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(this);
        android.support.v4.os.CancellationSignal cancellationSignal = new android.support.v4.os.CancellationSignal();
        fingerprintManagerCompat.authenticate(null, 0, cancellationSignal, new FingerprintManagerCompat.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                super.onAuthenticationError(errMsgId, errString);
//                失败多次会执行，Android端如果认证失败五次会有30s的不可操作期，可以在这里给出提示，判断code等于7
                if(errMsgId == FingerprintManager.FINGERPRINT_ERROR_CANCELED){
                    //用户取消解锁
                    Toast.makeText(TestFingerPrintManagerActivity.this, "用户取消指纹解锁 ！" , Toast.LENGTH_SHORT).show();
                }else if(errMsgId == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT){
                    Toast.makeText(TestFingerPrintManagerActivity.this, "用户操作过于频繁！ " , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TestFingerPrintManagerActivity.this, errString , Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                super.onAuthenticationHelp(helpMsgId, helpString);
                //一些错误会回调这个方法，可酌情处理
                Toast.makeText(TestFingerPrintManagerActivity.this, "手机指纹解锁失败 ：！ onAuthenticationHelp" , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(TestFingerPrintManagerActivity.this, "手机指纹解锁成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(TestFingerPrintManagerActivity.this, "手机指纹解锁失败！", Toast.LENGTH_SHORT).show();
            }
        }, null);
    }


    /**
     * 判断手机是否支持指纹识别
     * @return
     */
    private boolean supportFingerprint(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            Toast.makeText(this,"手机版本太低不支持指纹锁！",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            KeyguardManager keyguardManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
            FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(this);
            if(!fingerprintManagerCompat.isHardwareDetected()){
                Toast.makeText(this,"手机不支持指纹锁！",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(!fingerprintManagerCompat.hasEnrolledFingerprints()){
                Toast.makeText(this,"手机未录入指纹！",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(!keyguardManager.isDeviceSecure()){
                Toast.makeText(this,"手机没有设置指纹锁屏！",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void clickListener(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //指纹是在android6.0系统新增加的功能，最小api等级要求23。
            FingerprintManager fingerprintManagerCompat;
            //获得FingerPrintManager
            fingerprintManagerCompat = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);

            //检查手机硬件是否支持指纹
            try{
              boolean isHardwareDetected =  fingerprintManagerCompat.isHardwareDetected();
              if(isHardwareDetected){
//                  Toast.makeText(this, "手机支持指纹锁", Toast.LENGTH_SHORT).show();
              }else{
                  Toast.makeText(this, "手机不支持指纹锁", Toast.LENGTH_SHORT).show();
                   return;
              }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "手机不支持指纹锁", Toast.LENGTH_SHORT).show();

            }

            //是否录入指纹，有些设备即使录入指纹，但是没有开启锁屏密码的话此方法还是返回false
            try{
                boolean hasEnrolledFingerprints =  fingerprintManagerCompat.hasEnrolledFingerprints();
                if(hasEnrolledFingerprints){
//                    Toast.makeText(this, "手机已录入指纹锁", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "手机没有录入指纹锁", Toast.LENGTH_SHORT).show();
                    return;
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "手机没有录入指纹锁", Toast.LENGTH_SHORT).show();

            }

            //是否开始密码锁屏
            try{
                KeyguardManager keyguardManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
                boolean isKeyguardSecure = keyguardManager.isKeyguardSecure();
//                Toast.makeText(this, "手机密码锁屏：" + isKeyguardSecure, Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "手机密码锁屏 异常", Toast.LENGTH_SHORT).show();

            }



            final CancellationSignal[] cancellationSignal = {new CancellationSignal()};
//            CryptoObject：是一个加密的对象，指纹扫描器会使用这个对象来判断扫描结果的合法性，正常使用来说传null即可，感兴趣的朋友可以去官  网查看详细解释
//            flags：暂时为0，目前没有其他介绍
//            CancellationSignal：这个是我们上边介绍到的用来取消当前扫描操作
//            AuthenticationCallback：回调，这个是最关键的，回调中会有四个方法

            fingerprintManagerCompat.authenticate(null, cancellationSignal[0], 0, new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errMsgId, CharSequence errString) {
                    super.onAuthenticationError(errMsgId, errString);
                    if (errMsgId == 5) {//取消识别
                        Toast.makeText(TestFingerPrintManagerActivity.this, " onAuthenticationError：指纹识别取消 ", Toast.LENGTH_SHORT).show();

                    } else if (errMsgId == 7) {
                        Toast.makeText(TestFingerPrintManagerActivity.this, " onAuthenticationError：操作过于频繁，请稍后重试 ", Toast.LENGTH_SHORT).show();
                        if (cancellationSignal[0] != null) {
                            cancellationSignal[0].cancel();
                            cancellationSignal[0] = null;
                        }
                    }
                }

                @Override
                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                    super.onAuthenticationHelp(helpCode, helpString);
                    Toast.makeText(TestFingerPrintManagerActivity.this, " onAuthenticationHelp ", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(TestFingerPrintManagerActivity.this, " onAuthenticationSucceeded ： 指纹识别成功！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(TestFingerPrintManagerActivity.this, " onAuthenticationFailed ： 指纹识别失败！ ", Toast.LENGTH_SHORT).show();

                }
            }, null);
        }else{
            Toast.makeText(this, "当前系统版本不支持指纹锁！", Toast.LENGTH_SHORT).show();
        }
    }
}
