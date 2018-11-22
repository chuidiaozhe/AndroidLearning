package com.android.rajava2.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Create by Xiangshifu
 * on 2018/10/16 下午6:10
 */
public class GroupActivity extends AppCompatActivity  implements View.OnClickListener{
    public static final String TAG  = "testtest";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        findViewById(R.id.btn_concat).setOnClickListener(this);
        findViewById(R.id.btn_concatarray).setOnClickListener(this);
        findViewById(R.id.btn_merge).setOnClickListener(this);
        findViewById(R.id.btn_conncatarray_delayerror).setOnClickListener(this);
        findViewById(R.id.btn_mergearray_delayerror).setOnClickListener(this);
        findViewById(R.id.btn_zip).setOnClickListener(this);
        findViewById(R.id.btn_combinelatest).setOnClickListener(this);
        findViewById(R.id.btn_combinelatestdelayerror).setOnClickListener(this);
        findViewById(R.id.btn_reduce).setOnClickListener(this);
        findViewById(R.id.btn_collect).setOnClickListener(this);
        findViewById(R.id.btn_startWith).setOnClickListener(this);
        findViewById(R.id.btn_startWithArray).setOnClickListener(this);
        findViewById(R.id.btn_count).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_concat:
                concatOperator();
                break;
            case R.id.btn_concatarray:
                concatarrayOperator();
                break;
            case R.id.btn_merge:
                mergeOperagor();
                break;
            case R.id.btn_conncatarray_delayerror:
                concatArrayDelayErrorOperator();
                break;
            case R.id.btn_mergearray_delayerror:
                mergeArrayDelayErrorOperator();
                break;
            case R.id.btn_zip:
                zipOperator();
                break;
            case R.id.btn_combinelatest:
                combineLatestOperator();
                break;
            case R.id.btn_combinelatestdelayerror:
                combinlatestDelayErrorOperator();
                break;
            case R.id.btn_reduce:
                reduceOperator();
                break;
            case R.id.btn_collect:
                collectOperator();
                break;
            case R.id.btn_startWith:
                startWithOperator();
                break;
            case R.id.btn_startWithArray:
                startWithArrayOperator();
                break;
            case R.id.btn_count:
                countOperator();
                break;
        }
    }

    /**
     * 可以将多个观察者组合在一起，然后按照之前发送顺序发送事件。需要注意的是，concat() 最多只可以发送4个事件。
     */
    private void concatOperator(){
        Observable.concat(Observable.just(1,2),Observable.just(3,4),Observable.just(5,6),Observable.just(7,8))
                .subscribe(getObserver());
    }

    /**
     * 与 concat() 作用一样，不过 concatArray() 可以发送多于 4 个被观察者。
     */
    @SuppressWarnings({"unchecked","varargs"})
    private void concatarrayOperator(){
         Observable.concatArray(Observable.just(1,2),Observable.just(3,4),Observable.just(5,6),Observable.just(7,8),Observable.just(9,10))
                .subscribe(getObserver());
    }

    /**
     * 这个方法月 concat() 作用基本一样，知识 concat() 是串行发送事件，而 merge() 并行发送事件。
     */
    private void mergeOperagor(){
        Observable.merge(Observable.interval(1,1,TimeUnit.SECONDS).map(new Function<Long, String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                return "A" + aLong;
            }
        }),Observable.interval(1,1,TimeUnit.SECONDS).map(new Function<Long, String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                return "B" + aLong;
            }
        })).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
              Log.e(TAG,"=====onNext=====" +s );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


//        Observable.concat(Observable.interval(1,1,TimeUnit.SECONDS).map(new Function<Long, String>() {
//            @Override
//            public String apply(Long aLong) throws Exception {
//                return "A" + aLong;
//            }
//        }),Observable.interval(1,1,TimeUnit.SECONDS).map(new Function<Long, String>() {
//            @Override
//            public String apply(Long aLong) throws Exception {
//                return "B" + aLong;
//            }
//        })).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e(TAG,"=====onNext=====" +s );
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

    }

    /**
     *在 concatArray() 和 mergeArray() 两个方法当中，如果其中有一个被观察者发送了一个 Error 事件，那么就会停止发送事件，如果你想 onError() 事件延迟到所有被观察者都发送完事件后再执行的话，就可以使用  concatArrayDelayError() 和 mergeArrayDelayError()
     */
    @SuppressWarnings({"unchecked","varargs"})
    private void concatArrayDelayErrorOperator(){
//        Observable.concatArray(Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onError(new NumberFormatException());
//            }
//        }),Observable.just(1,2,3,4,5,6))
//                .subscribe(getObserver());

        Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onError(new NumberFormatException());
            }
        }),Observable.just(1,2,3,4,5,6)).subscribe(getObserver());
    }

    /**
     *在 concatArray() 和 mergeArray() 两个方法当中，如果其中有一个被观察者发送了一个 Error 事件，那么就会停止发送事件，如果你想 onError() 事件延迟到所有被观察者都发送完事件后再执行的话，就可以使用  concatArrayDelayError() 和 mergeArrayDelayError()
     */
    @SuppressWarnings({"unchecked","varargs"})
    private void mergeArrayDelayErrorOperator(){
//        Observable.mergeArray(Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onError(new NumberFormatException());
//            }
//        }),Observable.just(1,2,3,4,5,6))
//                .subscribe(getObserver());
        Observable.mergeArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onError(new NumberFormatException());
            }
        }),Observable.just(1,2,3,4,5,6))
                .subscribe(getObserver());
    }

    /**
     * 会将多个被观察者合并，根据各个被观察者发送事件的顺序一个个结合起来，最终发送的事件数量会与源 Observable 中最少事件的数量一样。
     */
    private void zipOperator(){
        Observable<String> observable1 = Observable.intervalRange(1,6,1,1,TimeUnit.SECONDS).map(new Function<Long,String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                String s1 = "A" + aLong;

                return s1;
            }
        });
        Observable<String> observable2 = Observable.intervalRange(1,3,1,1,TimeUnit.SECONDS).map(new Function<Long,String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                String s1 = "B" + aLong;

                return s1;
            }
        });

        Observable.zip(observable1,observable2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {
                return s +" - "+ s2;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG,"================onSubscribe=============");

            }

            @Override
            public void onNext(String o) {
                Log.e(TAG," ======onNext=====" +  o);

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"================onError=============");

            }

            @Override
            public void onComplete() {
                Log.e(TAG,"================onComplete=============");

            }
        });

    }

    /**
     * combineLatest() 的作用与 zip() 类似，但是 combineLatest() 发送事件的序列是与发送的时间线有关的，当 combineLatest() 中所有的 Observable 都发送了事件，只要其中有一个 Observable 发送事件，这个事件就会和其他 Observable 最近发送的事件结合起来发送
     */
    private void combineLatestOperator(){
        Observable<String> observable1 = Observable.intervalRange(1,6,1,1,TimeUnit.SECONDS).map(new Function<Long,String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                String s1 = "A" + aLong;

                return s1;
            }
        });
        Observable<String> observable2 = Observable.intervalRange(1,3,1,1,TimeUnit.SECONDS).map(new Function<Long,String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                String s1 = "B" + aLong;

                return s1;
            }
        });

        Observable.combineLatest(observable1, observable2, new BiFunction<String,String,String>() {
            @Override
            public String apply(String o, String o2) throws Exception {
                return o + " - " + o2;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG,"================onSubscribe=============");

            }

            @Override
            public void onNext(String o) {
                Log.e(TAG," ======onNext=====" +  o);

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"================onError=============");

            }

            @Override
            public void onComplete() {
                Log.e(TAG,"================onComplete=============");

            }
        });
    }

    /**
     * combineLatest() 的作用与 zip() 类似，但是 combineLatest() 发送事件的序列是与发送的时间线有关的，当 combineLatest() 中所有的 Observable 都发送了事件，只要其中有一个 Observable 发送事件，这个事件就会和其他 Observable 最近发送的事件结合起来发送，这样可能还是比较抽象
     *
     * 作者：singwhatiwanna
     * 链接：https://juejin.im/post/5b17560e6fb9a01e2862246f
     * 来源：掘金
      */
    @SuppressWarnings({"unchecked","varargs"})
    private void combinlatestDelayErrorOperator(){
        //ToDo 不理解这个操作符，需要后期再次查看
        Observable<String> observable1 = Observable.intervalRange(1,6,1,1,TimeUnit.SECONDS).map(new Function<Long,String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                String s1 = "A" + aLong;

                return s1;
            }
        });
        Observable<String> observable2 = Observable.intervalRange(1,3,1,1,TimeUnit.SECONDS).map(new Function<Long,String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                String s1 = "B" + aLong;

                return s1;
            }
        });

//        Observable.combineLatestDelayError(new ObservableSource[]{observable1, observable2}, new Function<String,String>() {
//            @Override
//            public String apply(String o) throws Exception {
//                return o;
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e(TAG,"====onSubscribe==== ");
//
//            }
//
//            @Override
//            public void onNext(String o) {
//               Log.e(TAG,"====onNext==== "+o);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG,"====onError==== "+ e.getMessage());
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG,"====onComplete==== ");
//
//            }
//        });

    }

    /**
     * 与 scan() 操作符的作用也是将发送数据以一定逻辑聚合起来，这两个的区别在于 scan() 每处理一次数据就会将事件发送给观察者，而 reduce() 会将所有数据聚合在一起才会发送事件给观察者。
     */
    private  void reduceOperator(){
        Observable.just(1,2,3,4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.e(TAG ," =====apply ====  integer: " + integer + " integer:" + integer2 );
                        return integer2 + integer;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG," ======accept===== " + integer);
            }
        });
    }

    /**
     * 将数据收集到数据结构当中。
     */
    private void collectOperator(){
        Observable.just(1,2,3,4)
                .collect(new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integer, Integer integer2) throws Exception {
                        integer.add(integer2);
                    }
                }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> list) throws Exception {
               Log.e(TAG," =====accept==== "  + list.size());
            }
        });
    }

    /**
     * 在发送事件之前追加事件，startWith() 追加一个事件，startWithArray() 可以追加多个事件。追加的事件会先发出。
     */
    private void startWithOperator(){
        Observable.just(1,2,3,4)
                .startWith(5)
                .startWith(6)
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG," =====accept==== "  + integer);

            }
        });
    }

    /**
     *在发送事件之前追加事件，startWith() 追加一个事件，startWithArray() 可以追加多个事件。追加的事件会先发出。
     */
    private void startWithArrayOperator(){
        Observable.just(1,2,3)
                .startWithArray(4,5,6)
                .startWith(7)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG," =====accept==== "  + integer);

                    }
                });
    }

    /**
     * 返回被观察者发送事件的数量。
     */
    private void countOperator(){
        Observable.just(1,2,3,4)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "发送事件次数accept: " + aLong);
                    }
                });
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
