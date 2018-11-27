package com.mujirenben.android.mine.di.module;

import dagger.Module;
import dagger.Provides;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.mvp.contract.WithdrawDetailContract;
import com.mujirenben.android.mine.mvp.model.WithdrawDetailModel;


@Module
public class WithdrawDetailModule {
    private WithdrawDetailContract.View view;

    /**
     * 构建WithdrawDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WithdrawDetailModule(WithdrawDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WithdrawDetailContract.View provideWithdrawDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WithdrawDetailContract.Model provideWithdrawDetailModel(WithdrawDetailModel model) {
        return model;
    }
}