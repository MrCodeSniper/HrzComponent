package com.mujirenben.android.mine.di.module;


import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.mine.mvp.contract.MateriaContract;
import com.mujirenben.android.mine.mvp.model.MateriaModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;


@Module
public class MateriaModule {
    private MateriaContract.View view;

    /**
     * 构建MineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MateriaModule(MateriaContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MateriaContract.View provideMateriaView() {
            return this.view;
    }

    @FragmentScope
    @Provides
    MateriaContract.Model provideMateriaModel(MateriaModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}