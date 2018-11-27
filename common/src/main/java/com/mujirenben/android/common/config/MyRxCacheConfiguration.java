package com.mujirenben.android.common.config;

import android.content.Context;

import com.mujirenben.android.common.dagger.module.ClientModule;

import io.rx_cache2.internal.RxCache;

public class MyRxCacheConfiguration implements ClientModule.RxCacheConfiguration {
    @Override
    public RxCache configRxCache(Context context, RxCache.Builder builder) {
        // 当数据无法加载时，使用过期数据
        builder.useExpiredDataIfLoaderNotAvailable(true);
        // 想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 fastjson, 请 return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());
       // 否则请 return null;
        return null;
    }
}
