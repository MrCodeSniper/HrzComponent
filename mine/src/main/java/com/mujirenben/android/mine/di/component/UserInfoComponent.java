package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.di.module.UserInfoModule;
import com.mujirenben.android.mine.mvp.ui.activity.SaveShipAddressActivitity;
import com.mujirenben.android.mine.mvp.ui.activity.ShipAddressActivity;

import dagger.Component;

@ActivityScope
@Component(modules = UserInfoModule.class, dependencies = AppComponent.class)
public interface UserInfoComponent {
    void inject(SaveShipAddressActivitity activity);
    void inject(ShipAddressActivity activity);
}