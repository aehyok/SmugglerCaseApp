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
    private ACache aCache;
    private Context mContext;

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
                .baseUrl(Constent.URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(RetrofitService.class);
        return service;
    }

}
