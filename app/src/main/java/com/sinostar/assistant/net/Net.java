package com.sinostar.assistant.net;

import android.content.Context;


import com.sinostar.assistant.utils.ACache;
import com.sinostar.assistant.utils.Constent;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit请求封装
 */
public class Net {
    private static Net net;
    private RetrofitService service;

    public static Net getInstance() {
        if (net == null) {
            net = new Net();
        }
        return net;
    }

    public RetrofitService serviceProvider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constent.URL)   // 设置网络请求的Url地址
                .addConverterFactory(new NullOnEmptyConverterFactory())    // 设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())        // 设置数据解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava平台
                .build();
        service = retrofit.create(RetrofitService.class);    //创建网络请求接口实例
        return service;
    }

    public RetrofitService serviceProvider(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)   // 设置网络请求的Url地址
                .addConverterFactory(new NullOnEmptyConverterFactory())    // 设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())        // 设置数据解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava平台
                .build();
        service = retrofit.create(RetrofitService.class);    //创建网络请求接口实例
        return service;
    }
}
