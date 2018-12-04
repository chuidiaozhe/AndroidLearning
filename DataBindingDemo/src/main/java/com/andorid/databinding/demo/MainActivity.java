package com.andorid.databinding.demo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andorid.databinding.demo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mainBinding =  DataBindingUtil.setContentView(this,R.layout.activity_main);
       mainBinding.setString("测试");
       mainBinding.setClick(new MainActivityClickListener());


    }
    public class MainActivityClickListener{
        public void openExpressionsActivity(){
            Intent intent = new Intent(MainActivity.this,DatabindingExpressinosActivity.class);
            startActivity(intent);
        }
        public void openRecyclerViewActivity(){
            Intent intent = new Intent(MainActivity.this,RecyclerViewActivity.class);
            startActivity(intent);
        }
        public void openBindingAdapterActivity(){
            Intent intent = new Intent(MainActivity.this,BindingAdapterActivity.class);
            startActivity(intent);
        }
        public void openTwoWayActivity(){
            Intent intent = new Intent(MainActivity.this,TwoWayActivity.class);
            startActivity(intent);
        }
    }

}
