package financer.kuaishoudan.com.recyclerviewdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import financer.kuaishoudan.com.recyclerviewdemo.R;
import financer.kuaishoudan.com.recyclerviewdemo.adapter.ListAdapter;
import financer.kuaishoudan.com.recyclerviewdemo.utils.Utils;

/**
 * Create by Xiangshifu
 * on 2019/1/15 10:54 AM
 */
public class GridActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_list);

        recyclerView = findViewById(R.id.recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return i%3+1;
            }
        });
         //设置列表方向
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

         recyclerView.setLayoutManager(gridLayoutManager);

        ListAdapter listAdapter  = new ListAdapter(Utils.getUserData(),this);
        recyclerView.setAdapter(listAdapter);

    }
}
