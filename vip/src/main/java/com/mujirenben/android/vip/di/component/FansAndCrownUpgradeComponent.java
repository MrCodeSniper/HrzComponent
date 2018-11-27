package com.mujirenben.android.vip.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.vip.di.module.FansAndCrownUpgradeModule;
import com.mujirenben.android.vip.mvp.ui.fragment.CrownUpgradeFragment;
import com.mujirenben.android.vip.mvp.ui.fragment.FansUpgradeFragment;

import dagger.Component;

@FragmentScope
@Component(modules = FansAndCrownUpgradeModule.class, dependencies = AppComponent.class)
public interface FansAndCrownUpgradeComponent {
    void inject(FansUpgradeFragment fragment);
    void inject(CrownUpgradeFragment fragment);
}
