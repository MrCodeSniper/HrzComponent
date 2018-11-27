package com.mujirenben.android.discovery.dagger.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.discovery.dagger.module.RecommendModule;
import com.mujirenben.android.discovery.mvp.ui.fragment.MaterialFragment;
import com.mujirenben.android.discovery.mvp.ui.fragment.RecommendFragment;

import dagger.Component;

@FragmentScope
@Component(modules = RecommendModule.class, dependencies = AppComponent.class)
public interface RecommendComponent {
    void inject(RecommendFragment fragment);
}
