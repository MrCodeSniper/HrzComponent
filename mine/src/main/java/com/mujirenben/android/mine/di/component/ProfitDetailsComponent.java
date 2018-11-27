package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.di.module.ProfitDetailsModule;
import com.mujirenben.android.mine.mvp.ui.activity.ProfitDetailsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ProfitDetailsModule.class, dependencies = AppComponent.class)
public interface ProfitDetailsComponent {
    void inject(ProfitDetailsActivity activity);
}
