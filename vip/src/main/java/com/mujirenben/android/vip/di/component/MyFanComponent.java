package com.mujirenben.android.vip.di.component;

import dagger.Component;


import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.vip.di.module.MyFanModule;
import com.mujirenben.android.vip.mvp.ui.activity.MyFanActivity;

@ActivityScope
@Component(modules = MyFanModule.class, dependencies = AppComponent.class)
public interface MyFanComponent {
    void inject(MyFanActivity activity);
}