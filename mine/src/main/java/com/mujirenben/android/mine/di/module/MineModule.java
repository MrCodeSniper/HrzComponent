package com.mujirenben.android.mine.di.module;


import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.mine.mvp.contract.MineContract;
import com.mujirenben.android.mine.mvp.model.MineModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;


@Module
public class MineModule {
    private MineContract.View view;

    /**
     * 构建MineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MineModule(MineContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
        MineContract.View provideMineView() {
            return this.view;
    }

    @FragmentScope
    @Provides
    MineContract.Model provideMineModel(MineModel model) {
        return model;
    }

    //提供就可以再fragment里inject了

    @FragmentScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}