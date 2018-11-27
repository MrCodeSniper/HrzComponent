package com.mujirenben.android.vip.mvp.contract;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.vip.mvp.model.bean.VipUpgradeInfo;

import io.reactivex.Observable;

public interface ShopKeeperUpgradeContract {

    interface View extends IView {
        default void showMessage(@NonNull String message) {}
        default void launchActivity(@NonNull Intent intent) {}
        default void killMyself() {}

        void onVipUpgradeInfoLoaded(VipUpgradeInfo info);
    }

    interface Model extends IModel {
        Observable<VipUpgradeInfo> loadVipUpgradeInfo();
    }
}
