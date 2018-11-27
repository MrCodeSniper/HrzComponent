package com.mujirenben.android.mine.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.mine.mvp.contract.MateriaContract;
import com.mujirenben.android.mine.mvp.model.bean.MateriaCircleBean;
import com.mujirenben.android.mine.mvp.model.bean.MateriaFriendsBean;
import com.mujirenben.android.mine.mvp.model.service.MineCache;
import com.mujirenben.android.mine.mvp.model.service.MateriaService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;


@FragmentScope
public class MateriaModel extends BaseModel implements MateriaContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MateriaModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MateriaFriendsBean> getMateriaDataFromFriends(int lastIdQueried, boolean update) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager.obtainRetrofitService(MateriaService.class).getSearchGoods())
                .flatMap((Function<Observable<MateriaFriendsBean>, ObservableSource<MateriaFriendsBean>>) listObservable -> {
                    //对网络或者缓存的数据  再进行一次rxcache缓存 对缓存进行控制
                    return mRepositoryManager.obtainCacheService(MineCache.class)
                            .getMateriaCache(listObservable, new DynamicKey(lastIdQueried), new EvictDynamicKey(update))
                            .map(listReply -> listReply.getData());
                });
    }

    @Override
    public Observable<MateriaCircleBean> getMateriaDataFromCircle(int lastIdQueried, boolean update) {
        return Observable.just(mRepositoryManager.obtainRetrofitService(MateriaService.class).getCircleMateriaData())
                .flatMap(new Function<Observable<MateriaCircleBean>, ObservableSource<MateriaCircleBean>>() {
                    @Override
                    //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
                    public ObservableSource<MateriaCircleBean> apply(@NonNull Observable<MateriaCircleBean> listObservable) throws Exception {
                        //对网络或者缓存的数据  再进行一次rxcache缓存 对缓存进行控制
                        return mRepositoryManager.obtainCacheService(MineCache.class)
                                .getMateriaCircleCache(listObservable
                                        , new DynamicKey(lastIdQueried)
                                        , new EvictDynamicKey(update))
                                .map(new Function<Reply<MateriaCircleBean>, MateriaCircleBean>() {
                                    @Override
                                    public MateriaCircleBean apply(Reply<MateriaCircleBean> listReply) throws Exception {
                                        return listReply.getData();
                                    }
                                });
                    }
                });
    }
}