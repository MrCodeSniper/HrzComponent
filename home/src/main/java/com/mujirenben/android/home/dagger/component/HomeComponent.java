package com.mujirenben.android.home.dagger.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.home.dagger.module.HomeModule;
import com.mujirenben.android.home.mvp.ui.fragment.HomeModifyFragment;

import dagger.Component;

/**
 * Created by mac on 2018/5/5.
 */
@FragmentScope
@Component(modules = HomeModule.class ,dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeModifyFragment fragment);
}
