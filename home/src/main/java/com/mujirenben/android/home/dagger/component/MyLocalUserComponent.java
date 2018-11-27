package com.mujirenben.android.home.dagger.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.home.dagger.module.MyLocalUserModule;
import com.mujirenben.android.home.mvp.ui.activity.MyQrCodeActivity;

import dagger.Component;

/**
 * Created by mac on 2018/5/5.
 */
@ActivityScope
@Component(modules = MyLocalUserModule.class ,dependencies = AppComponent.class)
public interface MyLocalUserComponent {
    void inject(MyQrCodeActivity activity);//注入homefragment
}
