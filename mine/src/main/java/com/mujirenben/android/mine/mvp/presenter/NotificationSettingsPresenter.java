package com.mujirenben.android.mine.mvp.presenter;

import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.mine.mvp.contract.NotificationSettingsContract;

import javax.inject.Inject;

public class NotificationSettingsPresenter extends
        BasePresenter<NotificationSettingsContract.Model, NotificationSettingsContract.View> {

    @Inject
    public NotificationSettingsPresenter(NotificationSettingsContract.Model model,
                                         NotificationSettingsContract.View view) {
        super(model, view);
    }

    public boolean saveConfiguration(String spKey, boolean spValue) {
        return mModel.saveConfiguration(spKey, spValue);
    }

    public boolean isConfigOn(String spKey, boolean defVaule) {
        return mModel.isConfigOn(spKey, defVaule);
    }
}
