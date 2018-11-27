package com.mujirenben.android.vip.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.vip.di.module.VipShareModule;
import com.mujirenben.android.vip.mvp.ui.activity.VipShareActivity;


import dagger.Component;

@ActivityScope
@Component(modules = VipShareModule.class, dependencies = AppComponent.class)
public interface VipShareComponent {

    void inject(VipShareActivity activity);
}
