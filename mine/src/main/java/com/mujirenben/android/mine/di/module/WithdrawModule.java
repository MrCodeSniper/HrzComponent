package com.mujirenben.android.mine.di.module;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.mvp.contract.WithDrawContract;
import com.mujirenben.android.mine.mvp.model.WithdrawModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author: panrongfu
 * @date: 2018/8/6 17:16
 * @describe:
 */
@Module
public class WithdrawModule {
    private WithDrawContract.View view;

    public WithdrawModule(WithDrawContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WithDrawContract.View provideWithDrawView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WithDrawContract.Model provideWithDrawModel(WithdrawModel model) {
        return model;
    }
}
