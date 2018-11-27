package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.di.module.WithdrawModule;
import com.mujirenben.android.mine.mvp.ui.activity.WithdrawDepositActivity;

import dagger.Component;

/**
 * @author: panrongfu
 * @date: 2018/8/6 17:16
 * @describe:
 */
@ActivityScope
@Component(modules = WithdrawModule.class,dependencies = AppComponent.class)
public interface WithdrawComponent {
    void inject(WithdrawDepositActivity activity);
}
