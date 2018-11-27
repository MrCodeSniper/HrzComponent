package com.mujirenben.android.mine.di.module;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.mine.mvp.contract.LoginContract;
import com.mujirenben.android.mine.mvp.contract.MineContract;
import com.mujirenben.android.mine.mvp.model.LoginModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    private LoginContract.View view;

    /**
     * 构建MineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LoginContract.View provideLoginView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LoginContract.Model provideLoginModel(LoginModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
