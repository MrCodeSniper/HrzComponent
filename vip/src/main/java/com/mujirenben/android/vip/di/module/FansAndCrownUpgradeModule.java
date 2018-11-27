package com.mujirenben.android.vip.di.module;

import com.mujirenben.android.vip.mvp.contract.FansAndCrownUpgradeContract;
import com.mujirenben.android.vip.mvp.model.FansAndCrownUpgradeModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FansAndCrownUpgradeModule {

    private FansAndCrownUpgradeContract.View view;

    public FansAndCrownUpgradeModule(FansAndCrownUpgradeContract.View view) {
        this.view = view;
    }

    @Provides
    public FansAndCrownUpgradeContract.View provideFansAndCrownView() {
        return view;
    }

    @Provides
    public FansAndCrownUpgradeContract.Model provideFansAndCrownModel(
            FansAndCrownUpgradeModel model) {
        return model;
    }
}
