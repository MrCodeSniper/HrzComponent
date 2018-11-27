package com.mujirenben.android.mine.di.module;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.mvp.contract.ProfitDetailsContract;
import com.mujirenben.android.mine.mvp.model.ProfitDetailsModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfitDetailsModule {

    private ProfitDetailsContract.View view;

    /**
     * 构建MineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ProfitDetailsModule(ProfitDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProfitDetailsContract.View provideLoginView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ProfitDetailsContract.Model provideLoginModel(ProfitDetailsModel model) {
        return model;
    }
}
