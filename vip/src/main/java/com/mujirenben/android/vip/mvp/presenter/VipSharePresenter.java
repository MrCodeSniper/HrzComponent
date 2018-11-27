package com.mujirenben.android.vip.mvp.presenter;

import android.graphics.Bitmap;

import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.vip.mvp.contract.VipShareContract;

import javax.inject.Inject;

public class VipSharePresenter extends BasePresenter<VipShareContract.Model, VipShareContract.View> {

    @Inject
    public VipSharePresenter(VipShareContract.Model model, VipShareContract.View view) {
        super(model, view);
    }

    public void saveImage(Bitmap bmp, boolean toShare) {
        mModel.saveSharingImage(bmp, new VipShareContract.Model.SaveCallback() {
            @Override
            public void onSaveStarted() {
                mRootView.onSavingSharingImageStarted();
            }

            @Override
            public void onSaveFailed() {
                mRootView.onSavingSharingImageFailed();
            }

            @Override
            public void onSaveCompleted(String path) {
                mRootView.onSavingSharingImageCompleted(path, toShare);
            }
        }, toShare);
    }

}
