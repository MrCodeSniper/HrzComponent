package com.mujirenben.android.mine.di.module;

import com.mujirenben.android.mine.mvp.contract.SettingsContract;
import com.mujirenben.android.mine.mvp.model.SettingsModel;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

    private SettingsContract.View view;

    public SettingsModule(SettingsContract.View view) {
        this.view = view;
    }

    @Provides
    SettingsContract.View provideSettingsView() {
        return this.view;
    }

    @Provides
    SettingsContract.Model provideSettingsModel(SettingsModel model) {
        return model;
    }
}
