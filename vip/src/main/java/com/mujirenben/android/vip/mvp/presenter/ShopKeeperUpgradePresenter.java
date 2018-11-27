package com.mujirenben.android.vip.mvp.presenter;

import android.util.Log;

import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.vip.mvp.contract.ShopKeeperUpgradeContract;
import com.mujirenben.android.vip.mvp.model.bean.VipUpgradeInfo;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

public class ShopKeeperUpgradePresenter
        extends BasePresenter<ShopKeeperUpgradeContract.Model, ShopKeeperUpgradeContract.View> {

    @Inject
    public ShopKeeperUpgradePresenter(ShopKeeperUpgradeContract.Model model,
                                      ShopKeeperUpgradeContract.View view) {
        super(model, view);
    }

    public void loadVipUpgradeInfo() {
        mModel.loadVipUpgradeInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribe(new Observer<VipUpgradeInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VipUpgradeInfo info) {
                        Log.i(TAG, "loadVipUpgradeInfo >> info=" + info);
                        mRootView.onVipUpgradeInfoLoaded(info);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "loadVipUpgradeInfo >> error=" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
