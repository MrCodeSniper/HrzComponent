package com.mujirenben.android.home.dagger.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.home.dagger.module.QrCodeModule;
import com.mujirenben.android.home.mvp.ui.activity.MyCapturesActivity;

import dagger.Component;

/**
 * Created by mac on 2018/5/5.
 */
@ActivityScope
@Component(modules = QrCodeModule.class ,dependencies = AppComponent.class)
public interface QrCodeComponent {
    void inject(MyCapturesActivity activity);//注入homefragment
}
