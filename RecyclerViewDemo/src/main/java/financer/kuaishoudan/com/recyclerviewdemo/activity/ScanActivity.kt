package financer.kuaishoudan.com.recyclerviewdemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import financer.kuaishoudan.com.recyclerviewdemo.R
import kotlinx.android.synthetic.main.activity_scan.*;

/**
 * Create by Xiangshifu
 * on 2019/3/29 5:49 PM
 */

class ScanActivity : AppCompatActivity(),QRCodeView.Delegate{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        zxingview.setDelegate(this)
     }

    override fun onStart() {
        super.onStart()
        zxingview.startCamera()
        zxingview.startSpotAndShowRect()

        zxingview.changeToScanBarcodeStyle() // 切换成扫描条码样式
        zxingview.setType(BarcodeType.ONLY_CODE_128, null)
        zxingview.startSpotAndShowRect() // 显示扫描框，并开始识别
    }

    override fun onStop() {
        zxingview.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        zxingview.onDestroy()
        super.onDestroy()
    }

    override fun onScanQRCodeSuccess(result: String?) {
        Toast.makeText(this@ScanActivity,"扫描结果$result",Toast.LENGTH_SHORT).show()
        zxingview.startSpot()
     }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        var tipText = zxingview.getScanBoxView().getTipText()
        val ambientBrightnessTip = "环境过暗，请打开闪光灯"
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                zxingview.getScanBoxView().setTipText(tipText + ambientBrightnessTip)
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                zxingview.getScanBoxView().setTipText(tipText)
            }
        }
        zxingview.startSpot()
    }

    override fun onScanQRCodeOpenCameraError() {
          Toast.makeText(this@ScanActivity,"打开相机出错！",Toast.LENGTH_SHORT).show()
     }

}