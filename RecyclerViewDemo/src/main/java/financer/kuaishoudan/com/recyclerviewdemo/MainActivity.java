package financer.kuaishoudan.com.recyclerviewdemo;

 import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import financer.kuaishoudan.com.recyclerviewdemo.activity.GridActivity;
 import financer.kuaishoudan.com.recyclerviewdemo.activity.ListActivity;
 import financer.kuaishoudan.com.recyclerviewdemo.activity.StaggeredGridActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_list).setOnClickListener(this);
        findViewById(R.id.btn_grid).setOnClickListener(this);
        findViewById(R.id.btn_staggered).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_list:
                goActivity(ListActivity.class);
                break;
            case R.id.btn_grid:
                goActivity(GridActivity.class);
                break;
            case R.id.btn_staggered:
                goActivity(StaggeredGridActivity.class);
                break;
        }
    }

    private void goActivity(Class<?> activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }
}
