package financer.kuaishoudan.com.recyclerviewdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import financer.kuaishoudan.com.recyclerviewdemo.R;
import financer.kuaishoudan.com.recyclerviewdemo.adapter.ListAdapter;
import financer.kuaishoudan.com.recyclerviewdemo.utils.Utils;

/**
 * Create by Xiangshifu
 * on 2019/1/15 11:23 AM
 */
public class StaggeredGridActivity  extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_list);

        recyclerView = findViewById(R.id.recyclerview);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        ListAdapter listAdapter  = new ListAdapter(Utils.getUserInfoModleList(),this);
        recyclerView.setAdapter(listAdapter);


    }
}
