package com.mujirenben.android.vip.di.module;

import dagger.Module;
import dagger.Provides;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.vip.mvp.contract.MyFanContract;
import com.mujirenben.android.vip.mvp.model.MyFanModel;


@Module
public class MyFanModule {
    private MyFanContract.View view;

    /**
     * 构建MyFanModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyFanModule(MyFanContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyFanContract.View provideMyFanView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyFanContract.Model provideMyFanModel(MyFanModel model) {
        return model;
    }
}