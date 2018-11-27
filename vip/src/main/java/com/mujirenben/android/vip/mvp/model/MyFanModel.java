package com.mujirenben.android.vip.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import javax.inject.Inject;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.vip.mvp.contract.MyFanContract;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.FanListBean;
import com.mujirenben.android.vip.mvp.model.service.FanService;

import io.reactivex.Observable;
import okhttp3.RequestBody;


@ActivityScope
public class MyFanModel extends BaseModel implements MyFanContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyFanModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<FanHeaderInfo> getMyFanInfo(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(FanService.class).getMyFanInfo(body);
    }

    @Override
    public Observable<FanListBean> getMyFanList(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(FanService.class).getMyFanList(body);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}