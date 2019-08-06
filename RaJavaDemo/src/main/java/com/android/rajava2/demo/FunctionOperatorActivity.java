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
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
 import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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
        findViewById(R.id.btn_doonLifecycle).setOnClickListener(this);
        findViewById(R.id.btn_doOnTerminate).setOnClickListener(this);
        findViewById(R.id.btn_doAfterTerminate).setOnClickListener(this);
        findViewById(R.id.btn_doFinally).setOnClickListener(this);
        findViewById(R.id.btn_onErrorReturn).setOnClickListener(this);
        findViewById(R.id.btn_onErrorResumeNext).setOnClickListener(this);
        findViewById(R.id.btn_onExceptionResumeNext).setOnClickListener(this);
        findViewById(R.id.btn_reTry).setOnClickListener(this);
        findViewById(R.id.btn_retryuntil).setOnClickListener(this);
        findViewById(R.id.btn_retryWhen).setOnClickListener(this);
        findViewById(R.id.btn_repeat).setOnClickListener(this);
        findViewById(R.id.btn_repeatWhen).setOnClickListener(this);
        findViewById(R.id.btn_subscribeOn).setOnClickListener(this);
        findViewById(R.id.btn_observeOn).setOnClickListener(this);
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
            case R.id.btn_doonLifecycle:
                doOnLifecyclerOperator();
                break;
            case R.id.btn_doOnTerminate:
                doOnTerminateOperator();
                break;
            case R.id.btn_doAfterTerminate:
                doAfterTerminate();
                break;
            case R.id.btn_doFinally:
                doFinallyOperator();
                break;
            case R.id.btn_onErrorReturn:
                onErrorReturnOperator();
                break;
            case R.id.btn_onErrorResumeNext:
                onErrorResumeNextOperator();
                break;
            case R.id.btn_onExceptionResumeNext:
                onExceptionResumeNextOperator();
                break;
            case R.id.btn_reTry:
                retryOperator();
                break;
            case R.id.btn_retryuntil:
                retryUntilOperator();
                break;
            case R.id.btn_retryWhen:
                retryWhenOperator();
                break;
            case R.id.btn_repeat:
                repeatOperator();
                break;
            case R.id.btn_repeatWhen:
                repeatWhenOperator();
                break;
            case R.id.btn_subscribeOn:
                subscribeOnOperator();
                break;
            case R.id.btn_observeOn:
                observeOnOperator();
                break;

        }
    }

    /**
     * 指定观察者的线程，每指定一次就会生效一次。
     *
     *  Schedulers.computation() :用于使用计算任务，如事件循环和回调处理
     *  Schedulers.immediate() : 当前线程
     *  Schedulers.io() : 用于IO密集型任务，如果异步阻塞IO操作
     *  Schedulers.newThread() : 创建一个新的线程
     *  AndroidSchedulers.mainThread() : Andorid 的UI线程，用于操作UI
     */
    private void observeOnOperator() {
        Observable.just(1,2,3)
                .observeOn(Schedulers.newThread())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                       log("=======flatMap thread=========" + Thread.currentThread().getName());
                        return Observable.just(" cai " + integer);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                     log("=======onSubscribe=======");
                    }

                    @Override
                    public void onNext(String s) {
                      log("======onNext thread=========" + Thread.currentThread().getName());
                      log("========onNext ==========" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("=======onError=======" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        log("=======onComplete=======");

                    }
                });
    }

    /**
     * 指定被观察者的线程，要注意的时，如果多次调用此方法，只有第一次有效。
     */
    private void subscribeOnOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                 log("======currentThread name =======" + Thread.currentThread().getName());
                 emitter.onNext(1);
                 emitter.onNext(2);
                 emitter.onNext(3);
                 emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.computation())
                .subscribeOn(Schedulers.newThread())
                .subscribe(getObserver());
    }

    /**
     * 这个方法可以会返回一个新的被观察者设定一定逻辑来决定是否重复发送事件
     * 这里分三种情况，如果新的被观察者返回 onComplete 或者 onError 事件，则旧的被观察者不会继续发送事件。如果被观察者返回其他事件，则会重复发送事件。
     */
    private void repeatWhenOperator() {
        Observable.create(new ObservableOnSubscribe < Integer > () {
            @Override
            public void subscribe(ObservableEmitter < Integer > e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                return Observable.empty();
//                return  Observable.error(new Throwable("djfdkjfkl"));
//                return  Observable.just(10);
            }
        }).subscribe(getObserver());
    }

    /**
     * 重复发送被观察者的事件，times 为发送次数。
     */
    private void repeatOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                 emitter.onNext(3);
                 emitter.onComplete();
            }
        }).repeat(2)
                .subscribe(getObserver());
    }

    /**
     * 当被观察者接收到异常或者错误事件时会回调该方法，这个方法会返回一个新的被观察者。如果返回的被观察者发送 Error 事件则之前的被观察者不会继续发送事件，如果发送正常事件则之前的被观察者会继续不断重试发送事件。
     */
    private void retryWhenOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("304"));
                emitter.onNext(3);
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                if(!throwableObservable.toString().equals("java.lang.Exception:404")){
                  return  Observable.just("可以忽略的异常");
                }else{
                    return  Observable.error(new Throwable("终止啦"));
                }
            }
        }).subscribe(getObserver());
    }

    /**
     * 出现错误事件之后，可以通过此方法判断是否继续发送事件。
     */
    int i = 0;
    private void retryUntilOperator() {
        i = 0;
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new NullPointerException());
                emitter.onNext(3);
            }
        }).retryUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Exception {
                log("=======getAsBoolean=======" + i);
                if(i == 6){
                    return  true;
                }
                return false;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                log("=======onSubscribe=========");
            }

            @Override
            public void onNext(Integer integer) {
                log("=======onNext========="+integer);
                i++;

            }

            @Override
            public void onError(Throwable e) {
                log("=======onError========="+e.getMessage());

            }

            @Override
            public void onComplete() {
                log("=======onComplete=========");

            }
        });
    }

    /**
     *如果出现错误事件，则会重新发送所有事件序列。times 是代表重新发的次数。
     */
    private void retryOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new NullPointerException());
                emitter.onNext(3);
            }
        }).retry(2)
          .subscribe(getObserver());
    }

    /**
     * 与 onErrorResumeNext() 作用基本一致，但是这个方法只能捕捉 Exception
     */
    private void onExceptionResumeNextOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new NullPointerException());
                emitter.onNext(3);
            }
        }).onExceptionResumeNext(new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {
                log("======onExceptionResumeNext=======");
                    observer.onNext(404);
                    observer.onComplete();
            }
        }).subscribe(getObserver());
    }

    /**
     * 当接收到 onError() 事件时，返回一个新的 Observable，并正常结束事件序列。
     */
    private void onErrorResumeNextOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new NullPointerException());
                emitter.onNext(3);
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                Observable observable  = Observable.just(4,5,6);
                log("======onErrorResumeNext=======");
                return observable;
            }
        }).subscribe(getObserver());
    }

    /**
     * 当接受到一个 onError() 事件之后回调，返回的值会回调 onNext() 方法，并正常结束该事件序列。
     */
    private void onErrorReturnOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new NullPointerException());
                emitter.onNext(3);
            }
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {
                log("=====onErrorReturn======");
                return 19;
            }
        }).subscribe(getObserver());
    }

    /**
     * 在所有事件发送完毕之后回调该方法。
     *
     * 这里可能你会有个问题，那就是 doFinally() 和 doAfterTerminate() 到底有什么区别？区别就是在于取消订阅，如果取消订阅之后 doAfterTerminate() 就不会被回调，而 doFinally() 无论怎么样都会被回调，且都会在事件序列的最后。*
     */
    private void doFinallyOperator() {
          Observable.create(new ObservableOnSubscribe<Integer>() {
              @Override
              public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                  emitter.onNext(1);
                  emitter.onNext(2);
                  emitter.onComplete();
                  emitter.onNext(3);
              }
          }).doFinally(new Action() {
              @Override
              public void run() throws Exception {
                  log("======doFinally=======");
              }
          }).doAfterTerminate(new Action() {
              @Override
              public void run() throws Exception {
                  log("=======doAfterTeminate========");
              }
          }).doOnDispose(new Action() {
              @Override
              public void run() throws Exception {
                  log("========doOnDispose=========");
              }
          }).subscribe(new Observer<Integer>() {
              private Disposable disposable;
              @Override
              public void onSubscribe(Disposable d) {
                  this.disposable = d;
                  log("=======onSubscribe=========");
              }

              @Override
              public void onNext(Integer integer) {
                   log("=======onNext=========" + integer);
//                   this.disposable.dispose();
              }

              @Override
              public void onError(Throwable e) {
                   log("========onError=========");
              }

              @Override
              public void onComplete() {
                   log("======onComplete=========");
              }
          });
    }

    /**
     * doOnTerminate 是在 onError 或者 onComplete 发送之前回调
     */
    private void doOnTerminateOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                  emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
                emitter.onNext(3);
            }
        }).doOnTerminate(new Action() {
            @Override
            public void run() throws Exception {
                log("=======doOnTerminate=======" );
            }
        }).subscribe(getObserver());
    }

    /**
     * doAfterTerminate 则是 onError 或者 onComplete 发送之后回调
     */
    private void doAfterTerminate() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new NullPointerException());
                emitter.onNext(3);
            }
        }).doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                 log("======doAfterTerminate======");
            }
        }).subscribe(getObserver());
    }

    /**
     * 在回调 onSubscribe 之前回调该方法的第一个参数的回调方法，可以使用该回调方法决定是否取消订阅。
     * doOnLifecycle() 第二个参数的回调方法的作用与 doOnDispose() 是一样的
     */
    private void doOnLifecyclerOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                 emitter.onNext(new Integer(1));
                 emitter.onNext(new Integer(2));
                 emitter.onNext(new Integer(3));
                 emitter.onComplete();
            }
        }).doOnLifecycle(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                log("===doOnLifecycle accept====" + disposable.isDisposed());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
               log("======= doOnLifecycle action ======");
            }
        }).doOnDispose(new Action() {
            @Override
            public void run() throws Exception {
               log("====doOnDispose action=====");
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                 log("====onSubscibe======");
                 this.disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                log("====onNext======" + integer);
//                this.disposable.dispose();
            }

            @Override
            public void onError(Throwable e) {
                log("====onError======" );

            }

            @Override
            public void onComplete() {
                log("====onComplete======" );

            }
        });
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
                Log.e(TAG,"================onError============="+e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.e(TAG,"================onComplete=============");

            }
        };
        return observer;
    }
}
