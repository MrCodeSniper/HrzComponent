package com.mujirenben.android.discovery.dagger.module;

import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.discovery.mvp.contract.DiscoveryContract;
import com.mujirenben.android.discovery.mvp.model.DiscoveryModel;

import dagger.Module;
import dagger.Provides;

@Module
public class RecommendModule {

    private DiscoveryContract.View mView;

    public RecommendModule(DiscoveryContract.View view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    DiscoveryContract.View provideRecommendView() {
        return mView;
    }

    @FragmentScope
    @Provides
    DiscoveryContract.Model provideRecommendModel(DiscoveryModel model) {
        return model;
    }
}
