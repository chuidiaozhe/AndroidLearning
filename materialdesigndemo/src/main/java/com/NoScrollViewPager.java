package com;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Create by Xiangshifu
 * on 2018/10/15 下午1:30
 */
public class NoScrollViewPager extends ViewPager {
    /*------------------------设置滚动tag------------------------*/
    private boolean noScroll;//默认false，可滚动

    public boolean isNoScroll() {
        return noScroll;
    }

    //设置不可滚动，setNoScroll(true)
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    /*-----------------------构造方法--------------------*/
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*------------   事件分发处理：    ------------------*/
    //return true： 自己处理
    //&&:一假必假,短路原则
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !noScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !noScroll && super.onTouchEvent(ev);
    }

}
