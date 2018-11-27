package com.mujirenben.android.vip.mvp.model;

import android.app.Application;

import com.google.gson.Gson;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.entity.SearchResult;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.common.service.GoodsService;
import com.mujirenben.android.vip.mvp.contract.VipContract;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;
import com.mujirenben.android.vip.mvp.model.bean.ShopBean;
import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;

import com.mujirenben.android.vip.mvp.model.service.VipService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import okhttp3.RequestBody;


@ActivityScope
public class VipModel extends BaseModel implements VipContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public VipModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<FanHeaderInfo> getMyFanInfo(RequestBody requestBody) {
        return mRepositoryManager.obtainRetrofitService(VipService.class).getMyFanInfo(requestBody);
    }

    @Override
    public Observable<VipBannerBean> getVipBanner(RequestBody requestBody) {
        return mRepositoryManager.obtainRetrofitService(VipService.class).getVipBanner(requestBody);
    }

    @Override
    public Observable<NoticeBean> getNoticeList(HashMap<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(VipService.class).getNoticeList(map);
    }

    @Override
    public Observable<SearchResult> getSearchGoods(Map<String, String> params) {
        return mRepositoryManager.obtainRetrofitService(VipService.class).getSelfGoods(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}