package com.andorid.databinding.demo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Create by Xiangshifu
 * on 2018/11/29 10:26 AM
 */
public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<User> dataList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public RecyclerViewAdapter(List<User> list, Context context) {
        this.dataList = list;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        if(this.dataList == null){
            dataList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(mLayoutInflater,R.layout.item_recycler_view,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(dataBinding);
          return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
          User user = dataList.get(i);
          myViewHolder.bind(user);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void addItem(){
        dataList.add(new User(dataList.size() + "哈","哈哈哈"));
        notifyItemInserted(dataList.size());
    }

    public void removeItem(){
        if(dataList.size() > 0){
            dataList.remove(0);
            notifyItemRemoved(0);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding dataBinding;

        public MyViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }

        public void bind(Object obj){
             dataBinding.setVariable(BR.user,obj);
             dataBinding.executePendingBindings();
        }
    }
}
