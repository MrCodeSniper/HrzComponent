package com.mujirenben.android.common.config;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.mujirenben.android.common.dagger.module.ApplicationModule;


/**
 * @创建者 CSDN_LQR
 * @描述 Gson配置
 */
public class MyGsonConfiguration implements ApplicationModule.GsonConfiguration {
    @Override
    public void configGson(Context context, GsonBuilder builder) {
        builder
        .serializeNulls()//支持序列化null的参数
        .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
    }
}
