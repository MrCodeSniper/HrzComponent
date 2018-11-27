package com.mujirenben.android.mine.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.mine.mvp.contract.WithdrawRecordContract;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawListBean;
import com.mujirenben.android.mine.mvp.model.service.WithdrawService;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.RequestBody;


@ActivityScope
public class WithdrawRecordModel extends BaseModel implements WithdrawRecordContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WithdrawRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }



    public Observable<WithdrawListBean> getWithdrawRecordData(HashMap<String,String> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return mRepositoryManager.obtainRetrofitService(WithdrawService.class).getWithdrawRecord(map);

    }
}