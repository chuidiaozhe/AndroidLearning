package test.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import test.android.com.androidtest2.R;

/**
 * Create by Xiangshifu
 * on 2018/8/7 上午11:14
 */
public class ErrorView extends FrameLayout {
    private View errorView;
    private View noNetworkView;
    private View noDataView;

    private int errorRes = 0;
    private int noNetRes = 0;
    private int noDataRes = 0 ;

    private TextView tv_error_text;

    private Context mContext;


    public ErrorView(@NonNull Context context) {
        super(context);
        initView(context,null);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
         super(context, attrs, defStyleAttr);
    }

    private void initView(Context context,AttributeSet attrs){
        mContext = context;
        if(attrs != null){
           TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ErrorView);
           errorRes = typedArray.getResourceId(R.styleable.ErrorView_error,0);
           noDataRes = typedArray.getResourceId(R.styleable.ErrorView_no_network,0);
           typedArray.recycle();
        }
        if(errorRes != 0){
            errorView = LayoutInflater.from(mContext).inflate(errorRes,null);
        }else{
            errorView = LayoutInflater.from(mContext).inflate(R.layout.view_error,null);
            tv_error_text = errorView.findViewById(R.id.tv_error_text);
        }
        if(noNetRes != 0){
            noNetworkView = LayoutInflater.from(mContext).inflate(noNetRes,null);
        }else{
            noNetworkView = LayoutInflater.from(mContext).inflate(R.layout.view_no_network,null);
        }

        if(noDataRes != 0){
            noDataView = LayoutInflater.from(mContext).inflate(noDataRes,null);
        }else{
            noDataView = LayoutInflater.from(mContext).inflate(R.layout.view_no_data,null);
        }

        if(errorView != null){
            addView(errorView);
            errorView.setVisibility(View.GONE);
        }
        if(noNetworkView != null){
            addView(noNetworkView);
            noNetworkView.setVisibility(View.GONE);
            noNetworkView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        if(noDataView != null){
            addView(noDataView);
            noDataView.setVisibility(View.GONE);

        }
    }

    public void hideAll(){
        for (int i = 0; i < getChildCount() ; i++) {
            View view = getChildAt(i);
             if(view.getTag() != null){
                 String tag = (String) view.getTag();
                if(tag.equals("main")){
                    showView(view);
                    return;
                 }
            }
         }
    }

    private void allViewGone(){
        for (int i = 0; i < getChildCount() ; i++) {
            View view = getChildAt(i);
            view.setVisibility(View.GONE);
        }
    }

    public void shouErrorView(){
        showView(errorView);
    }


    public void shouNoNetworkView(){
        showView(noNetworkView);
    }

    public void shouNoDataView(){
        showView(noDataView);
    }

    public View getErrorView(){
        return errorView;
    }

    public void setErrorText( String memss){
        if(tv_error_text != null && memss != null){
            tv_error_text.setText(memss);
        }
    }

    public void setErrorTextColor(int color){
        if(tv_error_text != null){
            tv_error_text.setTextColor(color);
        }
    }




    public void showView(View view){
        allViewGone();
        if(view != null && this.indexOfChild(view) != -1){
            if(view.getVisibility() != View.VISIBLE){
                view.setVisibility(View.VISIBLE);
            }
             view.bringToFront();
         }
    }
}
