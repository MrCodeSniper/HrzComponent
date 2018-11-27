package com.mujirenben.android.mine.mvp.presenter;

import android.graphics.Bitmap;

import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.mine.mvp.contract.AboutHrzContract;
import com.mujirenben.android.mine.mvp.model.AboutHrzModel;
import com.mujirenben.android.mine.mvp.model.bean.AppUpgradeInfo;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class AboutHrzPresenter extends BasePresenter<AboutHrzContract.Model, AboutHrzContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public AboutHrzPresenter(AboutHrzContract.Model model, AboutHrzContract.View view) {
        super(model, view);
    }

    public Bitmap getAppQrCode() {
        return mModel.getAppQrCode();
    }

    public String getAppVersion() {
        return mModel.getAppVersion();
    }

    public void checkUpgrade() {
        mModel.getAppUpgradeInfo()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDispose(disposable);
            }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<AppUpgradeInfo>(mErrorHandler) {
                    @Override
                    public void onNext(AppUpgradeInfo appUpgradeInfo) {
                        if(null==appUpgradeInfo){
                            mRootView.onRetrieveAppUpgradeInfoEmpty();
                        }else {
                            mRootView.onRetrieveAppUpgradeInfoCompleted(appUpgradeInfo);
                        }
                    }
                });
    }

    public String getHotline() {
        return mModel.getHotline();
    }

    public Bitmap getOfficialAccountQrCode() {
        return mModel.getOfficialAccountQrCode();
    }

    public void downloadApk(String uri) {
        mModel.startDownload(uri, new AboutHrzModel.DownloadListener() {
            @Override
            public void onDownloadStart() {
                mRootView.onDownloadStart();
            }

            @Override
            public void onDownloadCompleted(String apkPath) {
                mRootView.onDownloadCompleted(apkPath);
            }

            @Override
            public void onDownloadFailed() {
                mRootView.onDownloadFailed();
            }

            @Override
            public void onDownloadUpgrade(long downloadedSize, long totalSize) {
                mRootView.onDownloadUpgrade(downloadedSize, totalSize);
            }

            @Override
            public void onDownloadCanceled() {
                mRootView.onDownloadCanceled();
            }
        });
    }

    public void cancelDownloadApk() {
        mModel.stopDownload();
    }
}
