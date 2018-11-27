package com.mujirenben.android.home.dagger.module;

import android.content.Context;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.home.mvp.contract.HomeContract;
import com.mujirenben.android.home.mvp.model.HomeModel;
import com.mujirenben.android.home.mvp.model.entity.FindsBean;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mac on 2018/5/5.
 */
@Module
public class HomeModule {

    private HomeContract.View view;//在fragment中初始化


    public HomeModule(HomeContract.View view) {
        this.view = view;
    }


    //提供视图和数据的依赖给presenter


    @FragmentScope
    @Provides
    HomeContract.View provideUserView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    HomeContract.Model provideUserModel(HomeModel model){
        return model;
    }

    @FragmentScope
    @Provides
    List<FindsBean.ContentBean> provideUserList() {
        return new ArrayList<>();
    }





}
