package financer.kuaishoudan.com.recyclerviewdemo.activity

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import financer.kuaishoudan.com.recyclerviewdemo.R
import financer.kuaishoudan.com.recyclerviewdemo.modle.UserInfoModle
import kotlinx.android.synthetic.main.activity_test.*
 import android.content.Intent
 import android.R.attr.data
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
 import java.security.Permission


/**
 * Create by Xiangshifu
 * on 2019/3/29 3:02 PM
 */

class TestActivity : AppCompatActivity(){
    val REQUEST_CODE : Int = 1001

    lateinit var list : MutableList<UserInfoModle>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        tv_text.setText("测试数据")
        btn.setOnClickListener {
           Toast.makeText(this@TestActivity,"toast显示",Toast.LENGTH_SHORT).show()
//            val intent = Intent(this@TestActivity, CaptureActivity::class.java)
            if(ActivityCompat.checkSelfPermission(this@TestActivity,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
//                startActivityForResult(intent, REQUEST_CODE)
                var intent : Intent = Intent(this@TestActivity,ScanActivity::class.java)
                startActivity(intent)
            }else{
                ActivityCompat.requestPermissions(this@TestActivity, arrayOf(Manifest.permission.CAMERA),10)
            }
//              IntentIntegrator(this@TestActivity).initiateScan();
        }

//        iv_img.setImageResource(R.drawable.notification_icon_background)

        list = mutableListOf()


       for (i in 1 .. 10 step 4){
           list.add(UserInfoModle("name$i","adress$i",i))
       }
//        for (modle in list) {
//            Log.e("testtest","userNmae=${modle.userName}  useradress =${modle.address} useAge=${modle.age}")
//        }

        list.forEach {
            Log.e("testtest","userNmae=${it.userName}  useradress =${it.address} useAge=${it.age}")
         }

        actionSum(list)
       }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        /**
//         * 处理二维码扫描结果
//         */
//        if (requestCode == REQUEST_CODE) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                var  bundle = data.getExtras();
//                if (bundle == null) {
//                    return
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    var result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(this@TestActivity, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
    }


    /**
     * 总数操作
     * */
    fun actionSum(list : MutableList<UserInfoModle>)  {
        var result : Boolean
        //集合中至少有一个元素满足条件，则返回true
        result = list.any { it.age > 3 }
        //集合中所有元素满足条件，则返回true
        result = list.all { it.age > 3 }
        //返回结合中满足条件的元素个数
        var count : Int = list.count { it.age > 3 }
        Log.e("testtest" ,"count ======== $count")
        //在给定初始值的基础上，从第一项到最后一项进行累加
       var ageCount = list.fold(10,{
            acc,userinfo ->
            acc + userinfo.age
        })
        Log.e("testtest" ,"ageCount ======== $ageCount")
       //在给定初始值到基础上，从最后一项到第一项进行累加，与fold只是方向不同
       var ageCountRight = list.foldRight(10,{
            userinfo,acc ->
            userinfo.age + acc;
        })
        Log.e("testtest" ,"ageCountRight ======== $ageCountRight")


        Log.e("testtest" ,"result ======== $result")

    }
}