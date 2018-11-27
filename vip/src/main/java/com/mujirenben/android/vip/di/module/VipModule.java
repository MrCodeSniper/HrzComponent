package com.mujirenben.android.vip.di.module;



import dagger.Module;
import dagger.Provides;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.vip.mvp.contract.VipContract;
import com.mujirenben.android.vip.mvp.model.VipModel;


@Module
public class VipModule {
    private VipContract.View view;

    /**
     * 构建VipModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VipModule(VipContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VipContract.View provideVipView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VipContract.Model provideVipModel(VipModel model) {
        return model;
    }
}