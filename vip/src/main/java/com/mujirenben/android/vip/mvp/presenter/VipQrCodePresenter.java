package com.mujirenben.android.vip.mvp.presenter;

import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.vip.mvp.contract.VipQrCodeContract;
import com.mujirenben.android.vip.mvp.model.bean.InvitationCodeBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class VipQrCodePresenter extends
        BasePresenter<VipQrCodeContract.Model, VipQrCodeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public VipQrCodePresenter(VipQrCodeContract.Model model, VipQrCodeContract.View view) {
        super(model, view);
    }

    public void loadUserInfo() {
        mModel.loadInvitaionCode()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<InvitationCodeBean>(mErrorHandler) {
                    @Override
                    public void onNext(InvitationCodeBean userInfo) {
                        if(mRootView!=null){
                            mRootView.onUserInfoLoaded(userInfo);
                        }
                    }
                });
    }

    public void copyTextToClipboard(String text) {
        mModel.copyTextToClipboard(text, () -> {
            if(mRootView!=null){
                mRootView.onTextCopied(text);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        unDispose();
    }
}
