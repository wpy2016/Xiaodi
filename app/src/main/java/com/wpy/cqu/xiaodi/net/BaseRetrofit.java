package com.wpy.cqu.xiaodi.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangpeiyu on 2018/4/5.
 */

public class BaseRetrofit {

    static Retrofit retrofit;

    public synchronized static Retrofit getInstance() {
        if (null == retrofit) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://119.29.164.153:1688/") // 设置网络请求的Url地址
                    .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
