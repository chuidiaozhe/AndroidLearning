package com.android.rajava2.demo;

import android.os.Bundle;
 import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
 import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
 import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.observable.ObservableElementAt;

/**
 * Create by Xiangshifu
 * on 2018/10/15 下午4:31
 * 创建操作符的使用
 */
public class CreateOperatorActivity extends AppCompatActivity  implements View.OnClickListener{
    public static final String TAG  = "testtest";
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_operator);

        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_just).setOnClickListener(this);
        findViewById(R.id.btn_fromarray).setOnClickListener(this);
        findViewById(R.id.btn_fromCallable).setOnClickListener(this);
        findViewById(R.id.btn_fromFuture).setOnClickListener(this);
        findViewById(R.id.btn_fromIterable).setOnClickListener(this);
        findViewById(R.id.btn_defer).setOnClickListener(this);
        findViewById(R.id.btn_timer).setOnClickListener(this);
        findViewById(R.id.btn_interval).setOnClickListener(this);
        findViewById(R.id.btn_intervalRange).setOnClickListener(this);
        findViewById(R.id.btn_range).setOnClickListener(this);
        findViewById(R.id.btn_rangeLong).setOnClickListener(this);
        findViewById(R.id.btn_never).setOnClickListener(this);
        findViewById(R.id.btn_empty).setOnClickListener(this);
        findViewById(R.id.btn_error).setOnClickListener(this);

     }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create:
                createOperator();
                break;
            case R.id.btn_just:
                justOperator();
                break;
            case R.id.btn_fromarray:
                fromArrayOperator();
                break;
            case R.id.btn_fromCallable:
                fromCallableOperator();
                break;
            case R.id.btn_fromFuture:
                fromFetureOperator();
                break;
            case R.id.btn_fromIterable:
                fromIteratorOperator();
                break;
            case R.id.btn_defer:
                deferOperator();
                break;
            case R.id.btn_timer:
                timerOperator();
                break;
            case R.id.btn_interval:
                intervalOperator();
                break;
            case R.id.btn_intervalRange:
                intervalRangeOperator();
                break;
            case R.id.btn_range:
                rangeOperator();
                break;
            case R.id.btn_rangeLong:
                rangLongOperator();
                break;
            case R.id.btn_empty:
                emptyOperator();
                break;
            case R.id.btn_never:
                neverOperator();
                break;
            case R.id.btn_error:
                errorOperator();
                break;
        }
    }

    /**
     * 获得一个观察者
     * @return
     */
    private Observer<String> getObserver(){
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
        return  observer;

    }

    /**
     * create操作符
     */
    private void createOperator(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Create Operator");
                emitter.onComplete();
            }
        }).subscribe(getObserver());
     }

    /**
     * just操作符
     * 创建一个被观察者，并发送事件，发送的事件不可以超过10个以上。
     */
     private void justOperator(){
        Observable.just("just 001","just 002").subscribe(getObserver());
     }

    /**
     * 这个方法和 just() 类似，只不过 fromArray 可以传入多于10个的变量，并且可以传入一个数组。
     */
     private void fromArrayOperator(){
         String[] arr = new String[]{"fromArr0,formArr1,formArr2"};
         Observable.fromArray(arr).subscribe(getObserver());
     }

    /**
     * 这里的 Callable 是 java.util.concurrent 中的 Callable，Callable 和 Runnable 的用法基本一致，只是它会返回一个结果值，这个结果值就是发给观察者的。
     */
     private void fromCallableOperator(){
         Observable.fromCallable(new Callable<String>() {
             @Override
             public String call() throws Exception {
                 return "from callable";
             }
         }).subscribe(getObserver());
     }

    /**
     * 参数中的 Future 是 java.util.concurrent 中的 Future，Future 的作用是增加了 cancel() 等方法操作 Callable，它可以通过 get() 方法来获取 Callable 返回的值。
     *
     * doOnSubscribe() 的作用就是只有订阅时才会发送事件，具体会在下面讲解。
     */
     private void fromFetureOperator(){
         final FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
             @Override
             public String call() throws Exception {
                 return "future task from feture ";
             }
         });

         Observable.fromFuture(futureTask).doOnSubscribe(new Consumer<Disposable>() {
             @Override
             public void accept(Disposable disposable) throws Exception {
                         futureTask.run();
             }
         }).subscribe(getObserver());
     }

    /**
     * 直接发送一个 List 集合数据给观察者
     */
     private void fromIteratorOperator(){
         List<String> dataList = new ArrayList<>();
         dataList.add("dfd");
         dataList.add("dfadfa");
         dataList.add("hfgfg");
         dataList.add("treyrt");
         Observable<String> observable = Observable.fromIterable(dataList);
         observable.subscribe(getObserver());
     }

    /**
     * 这个方法的作用就是直到被观察者被订阅后才会创建被观察者。
     * 因为 defer() 只有观察者订阅的时候才会创建新的被观察者，所以每订阅一次就会打印一次，
     */
     private void  deferOperator(){
     Observable<String> observable =  Observable.defer(new Callable<ObservableSource<String>>() {
             @Override
             public ObservableSource<String> call() throws Exception {
                 return Observable.just("defer");
             }
         });

     observable.subscribe(getObserver());
     }

    /**
     * 当到指定时间后就会发送一个 0L 的值给观察者。
     */
     private void timerOperator(){
         Observable<Long> observable = Observable.timer(1,TimeUnit.SECONDS);
          Observer<Long> observer = new Observer<Long>() {
              @Override
              public void onSubscribe(Disposable d) {
                  Log.e(TAG,"=======onSubscribe=======");
              }

              @Override
              public void onNext(Long aLong) {
                 Log.e(TAG,"=======onNext======="+aLong);
              }

              @Override
              public void onError(Throwable e) {
                  Log.e(TAG,"=======onError=======");
              }

              @Override
              public void onComplete() {
                  Log.e(TAG,"=======onComplete=======");
              }
          };
          observable.subscribe(observer);
     }

    /**
     * 每隔一段时间就会发送一个事件，这个事件是从0开始，不断增1的数字。
     */
     private void intervalOperator(){
         //延迟三秒后每一秒发送一次
         Observable<Long> observable = Observable.interval(3,1,TimeUnit.SECONDS);
         Observer<Long> observer = new Observer<Long>() {
             @Override
             public void onSubscribe(Disposable d) {
                 Log.e(TAG,"=======onSubscribe=======");
             }

             @Override
             public void onNext(Long aLong) {
                 Log.e(TAG,"=======onNext======="+aLong);
             }

             @Override
             public void onError(Throwable e) {
                 Log.e(TAG,"=======onError=======");
             }

             @Override
             public void onComplete() {
                 Log.e(TAG,"=======onComplete=======");
             }
         };
         observable.subscribe(observer);
     }

    /**
     * 可以指定发送事件的开始值和数量，其他与 interval() 的功能一样。
     */
     private void intervalRangeOperator(){
         Observable<Long> observable = Observable.intervalRange(10,10,3,1,TimeUnit.SECONDS);
         Observer<Long> observer = new Observer<Long>() {
             @Override
             public void onSubscribe(Disposable d) {
                 Log.e(TAG,"=======onSubscribe=======");
             }

             @Override
             public void onNext(Long aLong) {
                 Log.e(TAG,"=======onNext======="+aLong);
             }

             @Override
             public void onError(Throwable e) {
                 Log.e(TAG,"=======onError=======");
             }

             @Override
             public void onComplete() {
                 Log.e(TAG,"=======onComplete=======");
             }
         };
         observable.subscribe(observer);
     }

    /**
     * 同时发送一定范围的事件序列。
     */
     private void rangeOperator(){
         Observable<Integer> observable = Observable.range(2,6);
         Observer<Integer> observer = new Observer<Integer>() {
             @Override
             public void onSubscribe(Disposable d) {
                 Log.e(TAG,"=======onSubscribe=======");
             }

             @Override
             public void onNext(Integer aLong) {
                 Log.e(TAG,"=======onNext======="+aLong);
             }

             @Override
             public void onError(Throwable e) {
                 Log.e(TAG,"=======onError=======");
             }

             @Override
             public void onComplete() {
                 Log.e(TAG,"=======onComplete=======");
             }
         };
         observable.subscribe(observer);
     }

    /**
     * 作用与 range() 一样，只是数据类型为 Long
     */
     private void rangLongOperator(){
         Observable<Long> observable = Observable.rangeLong(2,6);
         Observer<Long> observer = new Observer<Long>() {
             @Override
             public void onSubscribe(Disposable d) {
                 Log.e(TAG,"=======onSubscribe=======");
             }

             @Override
             public void onNext(Long aLong) {
                 Log.e(TAG,"=======onNext======="+aLong);
             }

             @Override
             public void onError(Throwable e) {
                 Log.e(TAG,"=======onError=======");
             }

             @Override
             public void onComplete() {
                 Log.e(TAG,"=======onComplete=======");
             }
         };
         observable.subscribe(observer);
     }

    /**
     * 直接发送 onComplete() 事件
     */
     private void emptyOperator(){
         Observable<String> observable = Observable.empty();
         observable.subscribe(getObserver());
     }

    /**
     * 不发送任何事件
     */
     private void neverOperator(){
         Observable<String> observable = Observable.never();
         observable.subscribe(getObserver());
     }

    /**
     * 发送 onError() 事件
     */
     private void errorOperator(){
         Observable<String> observable =  Observable.error(new Throwable("error operator"));
         observable.subscribe(getObserver());
     }
}
