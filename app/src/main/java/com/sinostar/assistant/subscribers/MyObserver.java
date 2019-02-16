package com.sinostar.assistant.subscribers;

import android.content.Context;
import android.util.Log;

import io.reactivex.disposables.Disposable;

/**
 * Rxjava回调封装
 */

public class MyObserver<T> implements io.reactivex.Observer<T> {
    private static final String TAG = "MyObserver";
    private ObserverOnNextListener listener;
    private Context context;

    public MyObserver(Context context, ObserverOnNextListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: ");
        //添加业务处理
    }

    @Override
    public void onNext(T t) {

        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ", e);
        //添加业务处理
        listener.onError(e);
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
        //添加业务处理
//        listener.onComplete();
    }
}