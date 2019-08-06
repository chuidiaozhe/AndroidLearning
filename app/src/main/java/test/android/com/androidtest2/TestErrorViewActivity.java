package test.android.com.androidtest2;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.android.view.ErrorView;

/**
 * Create by Xiangshifu
 * on 2018/8/7 上午11:47
 */
public class TestErrorViewActivity extends AppCompatActivity  implements View.OnClickListener{
    private ErrorView errorView;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_test_errorview);
        errorView = findViewById(R.id.errorview);

        findViewById(R.id.btn_error).setOnClickListener(this);
        findViewById(R.id.btn_no_data).setOnClickListener(this);
        findViewById(R.id.btn_no_network).setOnClickListener(this);
        findViewById(R.id.btn_show_main).setOnClickListener(this);

        errorView.setErrorText("fdkjadjfadjkl");

            errorView.setErrorTextColor(ContextCompat.getColor(this,R.color.colorPrimary));


        errorView.getErrorView().findViewById(R.id.doutChart2);


     }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_error:
                errorView.shouErrorView();
                break;
            case R.id.btn_no_data:
                errorView.shouNoDataView();
                break;
            case R.id.btn_no_network:
                errorView.shouNoNetworkView();
                break;
            case R.id.btn_show_main:
                errorView.hideAll();
                break;
        }
    }
}
