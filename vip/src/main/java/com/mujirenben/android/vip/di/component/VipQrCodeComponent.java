package com.mujirenben.android.vip.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.vip.di.module.VipQrCodeModule;
import com.mujirenben.android.vip.mvp.ui.activity.VipQrCodeActivity;

import dagger.Component;

@ActivityScope
@Component(modules = VipQrCodeModule.class, dependencies = AppComponent.class)
public interface VipQrCodeComponent {

    void inject(VipQrCodeActivity activity);
}
