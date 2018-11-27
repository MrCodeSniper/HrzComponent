package com.mujirenben.android.mine.di.module;

import com.mujirenben.android.mine.mvp.contract.AboutHrzContract;
import com.mujirenben.android.mine.mvp.model.AboutHrzModel;

import dagger.Module;
import dagger.Provides;

@Module
public class AboutHrzModule {

    private AboutHrzContract.View view;

    public AboutHrzModule(AboutHrzContract.View view) {
        this.view = view;
    }

    @Provides
    AboutHrzContract.View provideAboutHrzView() {
        return this.view;
    }

    @Provides
    AboutHrzContract.Model provideSettingsModel(AboutHrzModel model) {
        return model;
    }
}
