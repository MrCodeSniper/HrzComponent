package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;


import com.mujirenben.android.mine.di.module.AboutHrzModule;
import com.mujirenben.android.mine.mvp.ui.activity.AboutHrzActivity;
import com.mujirenben.android.mine.mvp.ui.activity.SettingsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = AboutHrzModule.class, dependencies = AppComponent.class)
public interface AboutHrzComponent {
    void inject(AboutHrzActivity activity);
}
