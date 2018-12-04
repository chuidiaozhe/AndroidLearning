package com.andorid.databinding.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andorid.databinding.demo.databinding.ActivityExpressionsBinding;

/**
 * Create by Xiangshifu
 * on 2018/11/27 6:04 PM
 */
public class DatabindingExpressinosActivity  extends AppCompatActivity {
    private ActivityExpressionsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_expressions);
        binding.setUser(new User("乔","布斯"));
        binding.setClickListener(new ClickListener());
 //        int count = 2;
//        String plurals = getResources().getQuantityString(R.plurals.select,0 );
//        binding.tvPlurals.setText(plurals);


    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public class ClickListener{
        public void simpleClick(){
           showToast("简单的点击事件");
        }

        public void callBackUser(User user){
            showToast(user.getFirsetName()+user.getLastName());
        }

        public void onCheckChange(boolean isChecked){
          showToast("是否选中："+ isChecked);

        }

        public boolean onLongClickListener(){
            showToast("长按点击事件");
            return true;
        }
    }
}
