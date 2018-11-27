package com.mujirenben.android.mine.di.module;


import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.mvp.contract.WithdrawRecordContract;
import com.mujirenben.android.mine.mvp.model.WithdrawRecordModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;


@Module
public class UserRequestModule {
    private WithdrawRecordContract.View view;

    /**
     * 构建MineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserRequestModule(WithdrawRecordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WithdrawRecordContract.View provideUserRequestView() {
            return this.view;
    }

    @ActivityScope
    @Provides
    WithdrawRecordContract.Model provideUserRequestModel(WithdrawRecordModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}