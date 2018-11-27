package com.mujirenben.android.mine.di.module;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.mvp.contract.SmsCodeVerifyContract;
import com.mujirenben.android.mine.mvp.model.SmsCodeVerifyModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;

@Module
public class SmsVerifyCodeModule {
    private SmsCodeVerifyContract.View view;

    /**
     * 构建MineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SmsVerifyCodeModule(SmsCodeVerifyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SmsCodeVerifyContract.View provideVerifyCodeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SmsCodeVerifyContract.Model provideVerifyCodeModel(SmsCodeVerifyModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
