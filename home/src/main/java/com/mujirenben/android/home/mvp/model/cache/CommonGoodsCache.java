
package com.mujirenben.android.home.mvp.model.cache;

import com.mujirenben.android.home.mvp.model.entity.GoodsEntity;


import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;


/**
 * ================================================
 * 展示 {@link RxCache#using(Class)} 中需要传入的 Providers 的使用方式
 * ================================================
 */
public interface CommonGoodsCache {

    //rxcache风格缓存 2分钟时间 不同用户不同缓存  等
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<GoodsEntity>> getGoodsInfo(Observable<GoodsEntity> users, DynamicKey idLastUserQueried);







}
