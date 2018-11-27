package com.mujirenben.android.home.mvp.model;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.home.mvp.model.remote.GoodsCodeService;
import com.mujirenben.android.home.mvp.contract.QrCodeContract;
import com.mujirenben.android.home.mvp.model.entity.GoodsEntity;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by mac on 2018/5/5.
 */
@ActivityScope
public class QrCodeModel extends BaseModel implements QrCodeContract.Model {



    @Inject
    public QrCodeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }




    @Override
    public Observable<GoodsEntity> getGoodsInfo(String packages, String qrcode, String city, String appkey) {
        return mRepositoryManager.obtainRetrofitService(GoodsCodeService.class).getGoodsInfo(packages,qrcode,city,appkey);

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }
}
