
package com.mujirenben.android.common.datapackage.manager;

import android.app.Application;
import android.content.Context;

import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.datapackage.cache.Cache;
import com.mujirenben.android.common.datapackage.cache.CacheType;
import com.mujirenben.android.common.datapackage.resource.ResourceHelper;
import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.Preconditions;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.internal.RxCache;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 提供给 {@link IModel} 层必要的 Api 做数据处理
 * ================================================
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {

    @Inject
    Lazy<Retrofit> mRetrofit;
    @Inject
    Lazy<RxCache> mRxCache;

    @Inject
    Lazy<ResourceHelper> mResourceHelper;

    @Inject
    Application mApplication;

    @Inject
    Cache.Factory mCachefactory;//根据cachetype创建缓存

    private Cache<String, Object> mRetrofitServiceCache;//网络请求数据管理
    private Cache<String, Object> mCacheServiceCache; //统一三级缓存管理 rxcache
    private Cache<String, Object> mResourceServiceCache;//统一管理资源 数据库，sp，json
    private Cache<String, Object> mDBServiceCache;



    //@Inject注入参数
    @Inject
    public RepositoryManager() {
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     * 缓存retrofit接口
     * @param service
     * @param <T>
     * @return
     */
    @Override
    public synchronized <T> T obtainRetrofitService(Class<T> service) {
        if (mRetrofitServiceCache == null) //若内存缓存为空 放入
            mRetrofitServiceCache = mCachefactory.build(CacheType.RETROFIT_SERVICE_CACHE);
        Preconditions.checkNotNull(mRetrofitServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) mRetrofitServiceCache.get(service.getCanonicalName());//从内存缓存中拿出
        if (retrofitService == null) {
            retrofitService = mRetrofit.get().create(service);
            mRetrofitServiceCache.put(service.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    @Override
    public synchronized <T> T obtainCacheService(Class<T> cache) {//通过rxcache
        if (mCacheServiceCache == null)
            mCacheServiceCache = mCachefactory.build(CacheType.CACHE_SERVICE_CACHE);
        Preconditions.checkNotNull(mCacheServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T cacheService = (T) mCacheServiceCache.get(cache.getCanonicalName());
        if (cacheService == null) {
            cacheService = mRxCache.get().using(cache);
            mCacheServiceCache.put(cache.getCanonicalName(), cacheService);
        }
        return cacheService;
    }

    //为resource加上一层内存缓存 ResourceHelper对象缓存
    @Override
    public <T> T obtainTypeService(Class<T> datasource) {
        if(datasource.getCanonicalName().equals("com.mujirenben.android.common.datapackage.resource.ResourceHelper")){
            if(mResourceServiceCache==null){
                mResourceServiceCache = mCachefactory.build(CacheType.RESOURCE_SERVICE_CACHE);
            }
            Preconditions.checkNotNull(mResourceServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
            T resource = (T) mResourceServiceCache.get(datasource.getCanonicalName());//从内存缓存中拿出
            if (resource == null) {
                resource = (T) new ResourceHelper(getContext());
                mResourceServiceCache.put(datasource.getCanonicalName(),resource);
                Logger.d("----获取resource资源（不是缓存resourceHelper）----");
            }else {
                Logger.d("----获取resource资源（缓存resourceHelper）----");
            }
            return resource;
        }
        return null;
    }

    @Override
    public <T> T obtainDBService(Class<T> db) {
            if(mDBServiceCache==null){
                mDBServiceCache = mCachefactory.build(CacheType.DB_SERVICE_CACHE);
            }
            Preconditions.checkNotNull(mDBServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
            T resource = (T) mDBServiceCache.get(db.getCanonicalName());//从内存缓存中拿出
            if (resource == null) {
                resource = (T) ArmsUtils.obtainAppComponentFromContext(BaseApplication.getApplication()).getDBHelper();
                mDBServiceCache.put(db.getCanonicalName(),resource);
                Logger.d("----获取DB资源（不是缓存DBHelper）----");
            }else {
                Logger.d("----获取DB资源（缓存DBHelper）----");
            }
            return resource;
    }


    protected Disposable changeIOToMainThread(Observable<ResponseBody> observable , DisposableObserver<ResponseBody> consumer ){
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(consumer);
    }

    /**
     * 清理所有缓存
     */
    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll();
    }


    @Override
    public Context getContext() {
        return mApplication;
    }
}
