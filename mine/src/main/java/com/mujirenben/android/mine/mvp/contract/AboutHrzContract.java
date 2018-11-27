package com.mujirenben.android.mine.mvp.contract;

import android.graphics.Bitmap;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.mvp.model.AboutHrzModel;
import com.mujirenben.android.mine.mvp.model.bean.AppUpgradeInfo;

import io.reactivex.Observable;

public interface AboutHrzContract {

    interface View extends IView {

        void onRetrieveAppUpgradeInfoCompleted(AppUpgradeInfo info);

        // 下载apk相关
        void onDownloadStart();
        void onDownloadCompleted(String apkFilePath);
        void onDownloadFailed();
        void onDownloadUpgrade(long downloadedSize, long totalSize);
        void onDownloadCanceled();

        void onRetrieveAppUpgradeInfoEmpty();

    }

    interface Model extends IModel {

        Bitmap getAppQrCode();
        String getAppVersion();

        void checkUpgrade();

        String getHotline();

        Bitmap getOfficialAccountQrCode();

        Observable<AppUpgradeInfo> getAppUpgradeInfo();

        // 下载apk相关
        void startDownload(String uri, AboutHrzModel.DownloadListener listener);
        void stopDownload();
        void pauseDownload();
        void resumeDownload();
    }
}
