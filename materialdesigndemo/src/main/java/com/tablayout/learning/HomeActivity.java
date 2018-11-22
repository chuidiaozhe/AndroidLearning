package com.tablayout.learning;

 import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.NoScrollViewPager;
import com.andorid.material.design.demo.R;

import java.util.ArrayList;
import java.util.List;

import static com.tablayout.learning.Constant.*;

/**
 * Create by Xiangshifu
 * on 2018/10/15 上午11:28
 */
public class HomeActivity extends AppCompatActivity {
    private ArrayList<Integer> userSelect;
    private TabLayout tabLayout;
    private NoScrollViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();

    private List<String> titleList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = findViewById(R.id.tabMode);
        viewPager = findViewById(R.id.view_pager);

        viewPager.setNoScroll(true);

        userSelect = getIntent().getIntegerArrayListExtra(KEY_FRAGMENT_TYPE);
        if (userSelect == null || userSelect.size() == 0) {
            if (userSelect == null) {
                userSelect = new ArrayList<>();
            }
            userSelect.add(PERSON_FRAGMENT);
        }


        fragments.clear();
        for (int frag : userSelect){
           if(frag == Constant.HOME_FRAGMENT){
               fragments.add(Fragment.instantiate(this,FragmentHome.class.getName()));
               titleList.add("首页");
           }else if(frag == Constant.ORDER_FRAGMENT){
               fragments.add(Fragment.instantiate(this,FragmentOrder.class.getName()));
               titleList.add("订单");

           }else if(frag == Constant.SUPPLIER_FRAGMENT){
               fragments.add(Fragment.instantiate(this,FragmentSupplier.class.getName()));
               titleList.add("商户");

           }else if(frag == Constant.PERSON_FRAGMENT){
               fragments.add(Fragment.instantiate(this,FragmentPerson.class.getName()));
               titleList.add("个人");

           }
        }

        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount() ; i++) {
            TabLayout.Tab tba  = tabLayout.getTabAt(i);
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.view_tab_item,null);
            TextView tv = view.findViewById(R.id.tv_table);
             tv.setText(titleList.get(i));
            tba.setCustomView(view);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabSelect(tab,true);

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
               setTabSelect(tab,false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setTabSelect(TabLayout.Tab tab,boolean isSelect){
        if(tab != null){
           View view = tab.getCustomView();
           if(view != null){
               TextView tv = view.findViewById(R.id.tv_table);
               tv.setSelected(isSelect);

           }
        }
    }


    class MyViewPagerAdapter extends FragmentPagerAdapter{

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }


    }
}
