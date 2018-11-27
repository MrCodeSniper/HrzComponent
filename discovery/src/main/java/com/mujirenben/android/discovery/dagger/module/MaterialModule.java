package com.mujirenben.android.discovery.dagger.module;

import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.discovery.mvp.contract.DiscoveryContract;
import com.mujirenben.android.discovery.mvp.model.DiscoveryModel;

import dagger.Module;
import dagger.Provides;

@Module
public class MaterialModule {

    private DiscoveryContract.View mView;

    public MaterialModule(DiscoveryContract.View view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    DiscoveryContract.View provideMaterialView() {
        return mView;
    }

    @FragmentScope
    @Provides
    DiscoveryContract.Model provideMaterialModel(DiscoveryModel model) {
        return model;
    }
}
