package financer.kuaishoudan.com.recyclerviewdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import financer.kuaishoudan.com.recyclerviewdemo.R;
import financer.kuaishoudan.com.recyclerviewdemo.adapter.ListAdapter;
import financer.kuaishoudan.com.recyclerviewdemo.itemdecoration.LinearlayoutDecoration;
import financer.kuaishoudan.com.recyclerviewdemo.utils.Utils;

/**
 * Create by Xiangshifu
 * on 2019/1/15 10:54 AM
 */
public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_list);

        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置列表方向
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //是否反转显示
        linearLayoutManager.setReverseLayout(false);
        //添加分割线
//        recyclerView.addItemDecoration(new LinearlayoutDecoration());
        //官方添加分割线的方法,分割线的高度就是drawable的高度
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_item_decoration_shape));
        recyclerView.addItemDecoration(dividerItemDecoration);


         recyclerView.setLayoutManager(linearLayoutManager);

        ListAdapter listAdapter  = new ListAdapter(Utils.getUserData(),this);
        recyclerView.setAdapter(listAdapter);


    }
}
