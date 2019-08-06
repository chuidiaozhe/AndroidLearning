package com.android.rajava2.demo;

import android.arch.core.util.Function;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @see {#https://juejin.im/post/5b17560e6fb9a01e2862246f}
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG  = "testtest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_create_operator).setOnClickListener(this);
        findViewById(R.id.btn_switch_operator).setOnClickListener(this);
        findViewById(R.id.btn_group_operator).setOnClickListener(this);
        findViewById(R.id.btn_function_operator).setOnClickListener(this);
        findViewById(R.id.btn_filter_operator).setOnClickListener(this);
        findViewById(R.id.btn_condition_operator).setOnClickListener(this);

        simple();
    }

    @Override
    public void onClick(View v) {
        Intent intent ;
        switch (v.getId()){
            case R.id.btn_create_operator:
                intent = new Intent(MainActivity.this,CreateOperatorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_switch_operator:
                intent = new Intent(MainActivity.this,SwitchOperatorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_group_operator:
                intent = new Intent(MainActivity.this,GroupActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_function_operator:
                intent = new Intent(MainActivity.this,FunctionOperatorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_filter_operator:
                intent = new Intent(MainActivity.this,FilterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_condition_operator:
                intent = new Intent(MainActivity.this,ConditionActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void simple(){
        //创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "=========================currentThread name: " + Thread.currentThread().getName());
                emitter.onNext("请求成功！");
                 emitter.onNext("发送success");
                 emitter.onComplete();
                 emitter.onNext("哈哈啊");
            }
        });

        //创建观察者
        Observer<String> observer  = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG," ========onSubscribe======== ");

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG," ========onNext======== " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG," ========onError======== ");

            }

            @Override
            public void onComplete() {
                Log.e(TAG," ========onComplete======== ");
            }
        };
        //订阅
        observable.subscribe(observer);

        //链式调用
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                Log.d(TAG, "=========================currentThread name: " + Thread.currentThread().getName());
//                emitter.onNext("请求成功！");
//                emitter.onNext("发送success");
//                emitter.onComplete();
//                emitter.onNext("哈哈啊");
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e(TAG," ========onSubscribe======== ");
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e(TAG," ========onNext======== " + s);
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG," ========onError======== ");
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG," ========onComplete======== ");
//
//            }
//        });
    }


}
