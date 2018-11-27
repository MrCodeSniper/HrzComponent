package com.mujirenben.android.home.dagger.module;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.home.mvp.contract.MyLocalUserContract;
import com.mujirenben.android.home.mvp.model.MyLocalUserModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mac on 2018/5/5.
 */
@Module
public class MyLocalUserModule {

    private MyLocalUserContract.View view;//在fragment中初始化


    public MyLocalUserModule(MyLocalUserContract.View view) {
        this.view = view;
    }


    //提供视图和数据的依赖给presenter


    @ActivityScope
    @Provides
    MyLocalUserContract.View provideUserView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    MyLocalUserContract.Model provideUserModel(MyLocalUserModel model){
        return model;
    }






}
