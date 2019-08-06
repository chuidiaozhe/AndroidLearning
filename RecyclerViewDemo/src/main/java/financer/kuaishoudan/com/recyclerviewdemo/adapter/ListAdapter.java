package financer.kuaishoudan.com.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;
import java.util.ListIterator;

import financer.kuaishoudan.com.recyclerviewdemo.R;
import financer.kuaishoudan.com.recyclerviewdemo.modle.UserInfoModle;

/**
 * Create by Xiangshifu
 * on 2019/1/15 10:47 AM
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<UserInfoModle> userInfoModleList;
    private Context mContext;

    public ListAdapter(List<UserInfoModle> userInfoModleList, Context mContext) {
        this.userInfoModleList = userInfoModleList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          ListViewHolder listViewHolder = new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list,viewGroup,false));

        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
               UserInfoModle userInfoModle  = userInfoModleList.get(i);
               listViewHolder.tvText.setText(userInfoModle.getUserName() + "\n"
                + userInfoModle.getAge() + "\n"
                + userInfoModle.getAddress());
        listViewHolder.btnDelete.setOnClickListener((View v)->{
            userInfoModleList.remove(listViewHolder.getAdapterPosition());
            notifyItemRemoved(listViewHolder.getAdapterPosition());
               Toast.makeText(mContext, "position :" + i, Toast.LENGTH_SHORT).show();
               });

               listViewHolder.swipe.setSwipeEnable(i % 3 == 0 ? true : false);

    }

    @Override
    public int getItemCount() {
        if(userInfoModleList == null){
            return 0;
        }else{
            return userInfoModleList.size();
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
          public TextView tvText;
          public Button btnDelete;
          public SwipeMenuLayout swipe;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            swipe = itemView.findViewById(R.id.swipe);
        }
    }
}
