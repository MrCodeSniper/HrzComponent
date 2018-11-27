package com.mujirenben.android.vip.di.module;

import com.mujirenben.android.vip.mvp.contract.FansAndCrownUpgradeContract;
import com.mujirenben.android.vip.mvp.contract.ShopKeeperUpgradeContract;
import com.mujirenben.android.vip.mvp.model.FansAndCrownUpgradeModel;
import com.mujirenben.android.vip.mvp.model.ShopKeeperUpgradeModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ShopKeeperUpgradeModule {

    private ShopKeeperUpgradeContract.View view;

    public ShopKeeperUpgradeModule(ShopKeeperUpgradeContract.View view) {
        this.view = view;
    }

    @Provides
    public ShopKeeperUpgradeContract.View provideShopKeeperView() {
        return view;
    }

    @Provides
    public ShopKeeperUpgradeContract.Model provideShopKeeperModel(
            ShopKeeperUpgradeModel model) {
        return model;
    }
}
