
package com.mujirenben.android.mine.mvp.model.service;

import com.mujirenben.android.mine.mvp.model.bean.MateriaCircleBean;
import com.mujirenben.android.mine.mvp.model.bean.MateriaFriendsBean;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawListBean;

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
public interface MineCache {

    //rxcache风格缓存2分钟时间不同用户不同缓存等




    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<MateriaFriendsBean>> getMateriaCache(Observable<MateriaFriendsBean> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);


    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<MateriaCircleBean>> getMateriaCircleCache(Observable<MateriaCircleBean> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<WithdrawListBean>> getWithdrawRecordCache(Observable<WithdrawListBean> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);
}
