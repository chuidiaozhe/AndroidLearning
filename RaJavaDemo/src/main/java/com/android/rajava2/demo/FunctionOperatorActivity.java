package com.android.rajava2.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * Create by Xiangshifu
 * on 2018/10/18 下午5:32
 */
public class FunctionOperatorActivity extends AppCompatActivity  implements View.OnClickListener {
    public static final String TAG  = "testtest";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_operator);

        findViewById(R.id.btn_delay).setOnClickListener(this);
        findViewById(R.id.btn_dooneach).setOnClickListener(this);
        findViewById(R.id.btn_doonnext).setOnClickListener(this);
        findViewById(R.id.btn_doafternext).setOnClickListener(this);
        findViewById(R.id.btn_dooncomplete).setOnClickListener(this);
        findViewById(R.id.btn_doonerror).setOnClickListener(this);
        findViewById(R.id.btn_doonsubscribe).setOnClickListener(this);
        findViewById(R.id.btn_doondispose).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delay:
                delayOperator();
                break;
            case R.id.btn_dooneach:
                doOnEachOperator();
                break;
            case R.id.btn_doonnext:
                doOnNextOperator();
                break;
            case R.id.btn_doafternext:
                doAfterNextOperator();
                break;
            case R.id.btn_dooncomplete:
                doOnCompleteOperator();
                break;
            case R.id.btn_doonerror:
                doOnErrorOperator();
                break;
            case R.id.btn_doonsubscribe:
                doOnSubscribeOperator();
                break;
            case R.id.btn_doondispose:
                doOnDisposeOperator();
                break;
        }
    }

    /**
     * 延迟一段事件发送事件。
     */
    private void delayOperator(){
        Observable.just(1,2,3,4)
                 .delay(3,TimeUnit.SECONDS )
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log("====accept====" + integer);
                    }
                });
    }

    /**
     * Observable 每发送一件事件之前都会先回调这个方法。
     */
    private void doOnEachOperator(){
     Observable.create(new ObservableOnSubscribe<Integer>() {
         @Override
         public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
              emitter.onNext(1);
              emitter.onNext(2);
              emitter.onNext(3);
              emitter.onComplete();
         }
     }).doOnEach(new Consumer<Notification<Integer>>() {
         @Override
         public void accept(Notification<Integer> integerNotification) throws Exception {
            log("======doOnEach accept ======" + integerNotification.getValue());
         }
     }).subscribe(getObserver());
    }

    /**
     * Observable 每发送 onNext() 之前都会先回调这个方法。
     */
    private void doOnNextOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                log("=====doOnNext accept ======" + integer);
            }
        }).subscribe(getObserver());
    }

    /**
     * Observable 每发送 onNext() 之后都会回调这个方法。
     */
    private void doAfterNextOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).doAfterNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                log("=====onAfterNext accept =====" + integer);
            }
        }).subscribe(getObserver());
    }

    /**
     * Observable 每发送 onComplete() 之前都会回调这个方法。
     */
    private void doOnCompleteOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
               emitter.onNext(1);
               emitter.onNext(2);
               emitter.onNext(3);
               emitter.onComplete();
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                log("======doOnComplete run ======");
            }
        }).subscribe(getObserver());
    }

    /**
     * Observable 每发送 onError() 之前都会回调这个方法。
     */
    private void doOnErrorOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                  emitter.onNext(1);
                  emitter.onNext(2);
                  emitter.onNext(3);
                  emitter.onError(new NumberFormatException());
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                log("====doOnError accepter =====" + throwable.getMessage());
            }
        }).subscribe(getObserver());
    }

    /**
     * Observable 每发送 onSubscribe() 之前都会回调这个方法。
     */
    private void doOnSubscribeOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new NumberFormatException());
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                log(" =====doOnSubscribe accept====== " + disposable.isDisposed());
            }
        }).subscribe(getObserver());
    }

    /**
     * 当调用 Disposable 的 dispose() 之后回调该方法。
     */
    private void doOnDisposeOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).doOnDispose(new Action() {
            @Override
            public void run() throws Exception {
                log("=====doOnDispose run=======");
            }
        }).subscribe(new Observer<Integer>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                log("=====onSubscibe=====");
            }

            @Override
            public void onNext(Integer integer) {
                log("=====onNext ===== " + integer);
                disposable.dispose();
            }

            @Override
            public void onError(Throwable e) {
                 log("======onError=======" + e.getMessage());
            }

            @Override
            public void onComplete() {
               log("======onComplete=======");
            }
        });
    }

    private void log(String message){
        Log.e(TAG,message);
    }

    private Observer<Integer> getObserver(){
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG,"================onSubscribe=============");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG,"====onNext==== " + integer);

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"================onError=============");

            }

            @Override
            public void onComplete() {
                Log.e(TAG,"================onComplete=============");

            }
        };
        return observer;
    }
}
