package com.mujirenben.android.vip.mvp.model;

import android.app.Application;

import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.vip.mvp.contract.ShopKeeperUpgradeContract;
import com.mujirenben.android.vip.mvp.model.bean.VipUpgradeInfo;
import com.mujirenben.android.vip.mvp.model.service.VipService;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ShopKeeperUpgradeModel extends BaseModel implements ShopKeeperUpgradeContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ShopKeeperUpgradeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<VipUpgradeInfo> loadVipUpgradeInfo() {
        Map<String, String> params = HttpParamUtil.getCommonSignParamMap(mApplication, null);
        return mRepositoryManager.obtainRetrofitService(VipService.class).loadVipUpgradeInfo(params);
    }
}
