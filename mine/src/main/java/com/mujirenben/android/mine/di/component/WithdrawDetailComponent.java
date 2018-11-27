package com.mujirenben.android.mine.di.component;

import dagger.Component;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.di.module.WithdrawDetailModule;
import com.mujirenben.android.mine.mvp.ui.activity.WithdrawDetailActivity;

@ActivityScope
@Component(modules = WithdrawDetailModule.class, dependencies = AppComponent.class)
public interface WithdrawDetailComponent {
    void inject(WithdrawDetailActivity activity);
}