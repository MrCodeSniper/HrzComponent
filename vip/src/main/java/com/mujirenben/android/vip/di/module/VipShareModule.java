package com.mujirenben.android.vip.di.module;

import com.mujirenben.android.vip.mvp.contract.VipShareContract;
import com.mujirenben.android.vip.mvp.model.VipShareModel;

import dagger.Module;
import dagger.Provides;

@Module
public class VipShareModule {

    private VipShareContract.View view;

    public VipShareModule(VipShareContract.View view) {
        this.view = view;
    }

    @Provides
    public VipShareContract.View provideVipShareView() {
        return view;
    }

    @Provides
    public VipShareContract.Model provideVipShareModel(VipShareModel model) {
        return model;
    }
}
