package com.mujirenben.android.vip.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.vip.di.module.FansAndCrownUpgradeModule;
import com.mujirenben.android.vip.di.module.ShopKeeperUpgradeModule;
import com.mujirenben.android.vip.mvp.ui.fragment.CrownUpgradeFragment;
import com.mujirenben.android.vip.mvp.ui.fragment.FansUpgradeFragment;
import com.mujirenben.android.vip.mvp.ui.fragment.ShopKeeperUpgradeFragment;

import dagger.Component;

@FragmentScope
@Component(modules = ShopKeeperUpgradeModule.class, dependencies = AppComponent.class)
public interface ShopKeeperUpgradeComponent {
    void inject(ShopKeeperUpgradeFragment fragment);
}
