package com.mujirenben.android.mine.di.module;

import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.mine.mvp.contract.MineContract;
import com.mujirenben.android.mine.mvp.contract.NotificationSettingsContract;
import com.mujirenben.android.mine.mvp.model.MineModel;
import com.mujirenben.android.mine.mvp.model.NotificationSettingsModel;
import com.mujirenben.android.mine.mvp.ui.activity.NotificationSettingsActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationSettingsModule {

    private NotificationSettingsContract.View view;

    public NotificationSettingsModule(NotificationSettingsContract.View view) {
        this.view = view;
    }

    @Provides
    NotificationSettingsContract.View provideNotificationSettingsView() {
        return this.view;
    }

    @Provides
    NotificationSettingsContract.Model provideNotificationSettingsModel(NotificationSettingsModel model) {
        return model;
    }
}
