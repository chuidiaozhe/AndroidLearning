package com.android.mpandroidchart.learning;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ItemBean> listItem = new ArrayList<>();
    private  RecyclerView list;

    private Context mContext;

    private RcyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         this.mContext = this;

        list = findViewById(R.id.list);

        initDate();
        mAdapter = new RcyclerAdapter();
        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(mAdapter);
    }


    private void  initDate(){
        listItem.add(new ItemBean("Getting Started",GettingStartedActivity.class));
        listItem.add(new ItemBean("Interaction with the Chart",InteractionWithTheChartActivity.class));
        listItem.add(new ItemBean("Highlighting",HighLightingActivity.class));
        listItem.add(new ItemBean("YAxis",YAxisActivity.class));
    }


    private void goActiivty(Class<?> cls){
        Intent intent = new Intent(mContext,cls);
        startActivity(intent);
    }

    class RcyclerAdapter extends RecyclerView.Adapter<RcyclerAdapter.ViewHolders>{

        @NonNull
        @Override
        public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ViewHolders viewHolder =  new ViewHolders(LayoutInflater.from(mContext).inflate(R.layout.item_main,viewGroup,false));
             return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolders viewHolders, final int position) {
               viewHolders.tvTitle.setText(listItem.get(position).getTitle());
               viewHolders.tvTitle.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                      goActiivty(listItem.get(position).cls);
                   }
               });
        }

        @Override
        public int getItemCount() {
            return  listItem.size();
        }

        class ViewHolders extends RecyclerView.ViewHolder{
              TextView tvTitle;
            public ViewHolders(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_title);
            }
        }
    }

    class ItemBean {
        String title;
        Class<?> cls;

        public ItemBean(String title, Class<?> cls) {
            this.title = title;
            this.cls = cls;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Class<?> getCls() {
            return cls;
        }

        public void setCls(Class<?> cls) {
            this.cls = cls;
        }
    }
}
