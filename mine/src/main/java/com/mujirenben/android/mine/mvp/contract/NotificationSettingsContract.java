package com.mujirenben.android.mine.mvp.contract;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;

public interface NotificationSettingsContract {

    interface View extends IView {}

    interface Model extends IModel {
        boolean saveConfiguration(String spKey, boolean spValue);
        boolean isConfigOn(String spKey, boolean defValue);
    }
}
