package com.sinostar.assistant.subscribers;


import org.json.JSONException;

public interface ObserverOnNextListener<T> {
    void onNext(T t);

    void onError(Throwable e);

}