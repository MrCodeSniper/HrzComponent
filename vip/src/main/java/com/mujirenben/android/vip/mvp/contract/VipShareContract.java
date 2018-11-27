package com.mujirenben.android.vip.mvp.contract;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;

public interface VipShareContract {

    interface View extends IView {

        void onSavingSharingImageStarted();
        void onSavingSharingImageCompleted(String path, boolean toShare);
        void onSavingSharingImageFailed();

        default void showMessage(@NonNull String message) {}
        default void launchActivity(@NonNull Intent intent) {}
        default void killMyself() {}
    }

    interface Model extends IModel {

        interface SaveCallback {
            void onSaveStarted();
            void onSaveFailed();
            void onSaveCompleted(String path);
        }

        void saveSharingImage(Bitmap image, SaveCallback cb, boolean toShare);
    }
}
