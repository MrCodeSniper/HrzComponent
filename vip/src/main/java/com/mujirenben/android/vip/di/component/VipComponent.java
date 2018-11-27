package com.mujirenben.android.vip.di.component;

import dagger.Component;


import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.vip.di.module.VipModule;



import com.mujirenben.android.vip.mvp.ui.fragment.VipFragment;

@ActivityScope
@Component(modules = VipModule.class, dependencies = AppComponent.class)
public interface VipComponent {
    void inject(VipFragment fragment);
}