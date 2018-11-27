package com.mujirenben.android.mine.mvp.model;

import android.app.Application;

import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.datapackage.resource.SharePreferenceHelper;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.mine.mvp.contract.NotificationSettingsContract;

import javax.inject.Inject;

public class NotificationSettingsModel extends BaseModel implements NotificationSettingsContract.Model {

    public static final String SP_FILE_NAME = "notification_settings";

    @Inject
    Application mApplication;

    @Inject
    public NotificationSettingsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public boolean saveConfiguration(String spKey, boolean spValue) {
        LogUtil.i("TAG", "saveConfiguration spValue = " + spValue + " mApplication = " + mApplication);
        new SharePreferenceHelper(mApplication).setBoolean(spKey, SP_FILE_NAME, spValue);
        return true;
    }

    @Override
    public boolean isConfigOn(String spKey, boolean defValue) {
        return new SharePreferenceHelper(mApplication).getBooleanValue(SP_FILE_NAME, spKey, defValue);
    }
}
