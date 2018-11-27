package com.mujirenben.android.discovery.dagger.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.discovery.dagger.module.MaterialModule;
import com.mujirenben.android.discovery.mvp.ui.fragment.MaterialFragment;

import dagger.Component;

@FragmentScope
@Component(modules = MaterialModule.class, dependencies = AppComponent.class)
public interface MaterialComponent {

    void inject(MaterialFragment fragment);
}
