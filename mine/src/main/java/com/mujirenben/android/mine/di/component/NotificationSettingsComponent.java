package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.di.module.NotificationSettingsModule;
import com.mujirenben.android.mine.di.module.UserInfoModule;
import com.mujirenben.android.mine.mvp.ui.activity.NotificationSettingsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = NotificationSettingsModule.class, dependencies = AppComponent.class)
public interface NotificationSettingsComponent {
    void inject(NotificationSettingsActivity activity);
}
