package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.mine.di.module.MineModule;
import com.mujirenben.android.mine.mvp.ui.activity.SmsCodeVerifyActivity;
import com.mujirenben.android.mine.mvp.ui.activity.UserInfoActivity;
import com.mujirenben.android.mine.mvp.ui.fragment.MineFragment;

import dagger.Component;

@FragmentScope
@Component(modules = MineModule.class, dependencies = AppComponent.class)
public interface MineComponent {
    void inject(MineFragment fragment);
    void inject(UserInfoActivity activity);
}