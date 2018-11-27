package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.di.module.SettingsModule;
import com.mujirenben.android.mine.mvp.ui.activity.SettingsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = SettingsModule.class, dependencies = AppComponent.class)
public interface SettingsComponent {
    void inject(SettingsActivity activity);
}
