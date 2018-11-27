package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.di.module.UserRequestModule;
import com.mujirenben.android.mine.mvp.ui.activity.WithdrawRecordActivity;

import dagger.Component;

@ActivityScope
@Component(modules = UserRequestModule.class, dependencies = AppComponent.class)
public interface UserRequstComponent {
    void inject(WithdrawRecordActivity activity);
}