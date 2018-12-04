package com.andorid.databinding.demo;

import android.app.LauncherActivity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.andorid.databinding.demo.databinding.ActivityRecyclerviewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2018/11/28 6:16 PM
 */
public class RecyclerViewActivity extends AppCompatActivity {
    private ActivityRecyclerviewBinding dataBinding;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_recyclerview);
        dataBinding.setPresent(new Present());

        List<User> userList = new ArrayList<>();
        userList.add(new User("乔","布斯"));
        userList.add(new User("李","小龙"));
        userList.add(new User("商","鞅"));
        userList.add(new User("李","世民"));
        userList.add(new User("刘","彻"));
        userList.add(new User("美","女"));


        dataBinding.recycler.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter  = new RecyclerViewAdapter(userList,this);
        dataBinding.recycler.setAdapter(recyclerViewAdapter);

    }

    public  class Present{
        public void addItemClick(){
           recyclerViewAdapter.addItem();
        }

        public void removeItemClick(){
            recyclerViewAdapter.removeItem();
        }
    }
}
