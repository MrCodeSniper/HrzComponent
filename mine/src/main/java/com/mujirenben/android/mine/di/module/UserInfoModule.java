package com.mujirenben.android.mine.di.module;


import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.mvp.contract.UserInfoContract;
import com.mujirenben.android.mine.mvp.model.UserInfoModel;

import dagger.Module;
import dagger.Provides;


@Module
public class UserInfoModule {
    private UserInfoContract.View view;

    /**
     * 构建MineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserInfoModule(UserInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserInfoContract.View provideUserInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserInfoContract.Model provideUserInfoModel(UserInfoModel model) {
        return model;
    }
}