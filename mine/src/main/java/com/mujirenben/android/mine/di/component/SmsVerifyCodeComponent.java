package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.di.module.SmsVerifyCodeModule;
import com.mujirenben.android.mine.mvp.ui.activity.SmsCodeVerifyActivity;

import dagger.Component;

@ActivityScope
@Component(modules = SmsVerifyCodeModule.class, dependencies = AppComponent.class)
public interface SmsVerifyCodeComponent {
    void inject(SmsCodeVerifyActivity activity);
}
