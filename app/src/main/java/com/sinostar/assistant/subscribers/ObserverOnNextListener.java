package com.sinostar.assistant.subscribers;


public interface ObserverOnNextListener<T> {
    void onNext(T t);

    void onError(Throwable e);

}