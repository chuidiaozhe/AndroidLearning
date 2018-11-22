package com.android.rajava2.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.rajava2.bean.Person;
import com.android.rajava2.bean.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
 import io.reactivex.observables.GroupedObservable;

/**
 * Create by Xiangshifu
 * on 2018/10/16 上午11:39
 */
public class SwitchOperatorActivity  extends AppCompatActivity  implements View.OnClickListener{
    public static final String TAG  = "testtest";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swtich_activity);

        findViewById(R.id.btn_map).setOnClickListener(this);
        findViewById(R.id.btn_flatMap).setOnClickListener(this);
        findViewById(R.id.btn_concatMap).setOnClickListener(this);
        findViewById(R.id.btn_buffer).setOnClickListener(this);
        findViewById(R.id.btn_groupBy).setOnClickListener(this);
        findViewById(R.id.btn_scan).setOnClickListener(this);
        findViewById(R.id.btn_window).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_map:
                mapOperatior();
                break;
            case R.id.btn_flatMap:
                flatMapOperator();
                break;
            case R.id.btn_concatMap:
                concatMapOperator();
                break;
            case R.id.btn_buffer:
                bufferOperator();
                break;
            case R.id.btn_groupBy:
                try {
                    groupbyOperator();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_scan:
                scanOperator();
                break;
            case R.id.btn_window:
                windowOperator();
                break;
        }

    }

    /**
     * map可以将被观察者发送的数据类型转换为其它类型
     */
    private void mapOperatior(){
        Observable.just(1,2,3).
                map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "I am is " + integer;
                    }
                })
                .subscribe(getStringObserver());
    }

    /**
     * flatMap() 其实与 map() 类似，但是 flatMap() 返回的是一个 Observerable。现在用一个例子来说明 flatMap() 的用法。
     */
    private void flatMapOperator(){
        Observable.fromIterable(getPersonList())
                .flatMap(new Function<Person, ObservableSource<Plan>>() {
                    @Override
                    public ObservableSource<Plan> apply(Person person) throws Exception {
                        return Observable.fromIterable(person.getPlanList());
                    }
                })
                .flatMap(new Function<Plan, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Plan plan) throws Exception {
                        Log.e(TAG,"=============="+plan.getTime()+"====================");
                        return Observable.fromIterable(plan.getActionList());
                    }
                }).subscribe(getStringObserver());
    }

    /**
     * concatMap() 和 flatMap() 基本上是一样的，只不过 concatMap() 转发出来的事件是有序的，而 flatMap() 是无序的。
     */
    private void concatMapOperator(){
        Observable.fromIterable(getPersonList())
                .concatMap(new Function<Person, ObservableSource<Plan>>() {
                    @Override
                    public ObservableSource<Plan> apply(Person person) throws Exception {
                        return Observable.fromIterable(person.getPlanList());
                    }
                })
                .concatMap(new Function<Plan, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Plan plan) throws Exception {
                        return Observable.fromIterable(plan.getActionList());
                    }
                })
                .subscribe(getStringObserver());
    }

    /**
     * 从需要发送的事件当中获取一定数量的事件，并将这些事件放到缓冲区当中一并发出。
     */
    private void bufferOperator(){
        Observable.just(1,2,3,4,5,6,7,8,9)
                .buffer(3,4)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                      Log.e(TAG," size :  "  + integers.size());
                      for (Integer i : integers){
                          Log.e(TAG,"元素 ： " + i);
                      }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 将发送的数据进行分组，每个分组都会返回一个被观察者。
     */
    private void groupbyOperator ()  {
        Observable.just(1,20,40,30,6,7,99,11,5).groupBy(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                if(integer > 80){
                    return  1;
                }else if(integer > 40){
                    return  2;
                }else if(integer > 10){
                    return  3;
                }else{
                    return 4;
                }
             }
        }).subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                 Log.e(TAG,"key : " + integerIntegerGroupedObservable.getKey());
                 integerIntegerGroupedObservable.map(new Function<Integer, String>() {
                     @Override
                     public String apply(Integer integer) throws Exception {
                         return integer + "";
                     }
                 }).subscribe(getStringObserver());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 将数据以一定的逻辑聚合起来。(前后两个数据的组合)
     */
    private void scanOperator(){
        Observable.just(1,2,3,4,5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.d(TAG, "====================apply ");
                        Log.d(TAG, "====================integer " + integer);
                        Log.d(TAG, "====================integer2 " + integer2);

                        return integer + integer2;
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "";
                    }
                }).subscribe(getStringObserver());
    }

    /**
     * 发送指定数量的事件时，就将这些事件分为一组。window 中的 count 的参数就是代表指定的数量，例如将 count 指定为2，那么每发2个数据就会将这2个数据分成一组。
     */
    private void windowOperator(){
        Observable.just(1,2,3,4,5)
                .window(3)
                .subscribe(new Observer<Observable<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Observable<Integer> integerObservable) {
                        Log.e(TAG,"========onNext======");

                        integerObservable.subscribe(new Observer<Integer>() {
                           @Override
                           public void onSubscribe(Disposable d) {

                           }

                           @Override
                           public void onNext(Integer integer) {
                                   Log.e(TAG,"========onNext======" + integer);
                           }

                           @Override
                           public void onError(Throwable e) {

                           }

                           @Override
                           public void onComplete() {

                           }
                       });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<Person> getPersonList(){
        List<Person> personList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            String name = "personNmae";
            List<Plan> planList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                String time = ( j + 4 )+ "";
                String content = "计划内容" + j;
                List<String> action = new ArrayList<>();
                for (int k = 0; k < 3 ; k++) {
                    action.add( i + "    "+j + "  action  " + k);
                }
                Plan  plan = new Plan(time,content);
                plan.setActionList(action);
                planList.add(plan);
            }
            Person person = new Person(name + (i + 1),planList);
            personList.add(person);
        }

        return  personList;
    }

    private Observer<String> getStringObserver(){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG,"=======onSubscribe=======");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"=======onNext=======" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"=======onError=======" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"=======onComplete=======");
            }
        };
        return  observer;
    }
}
