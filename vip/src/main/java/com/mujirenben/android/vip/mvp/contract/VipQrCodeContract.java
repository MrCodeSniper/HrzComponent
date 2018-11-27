package com.mujirenben.android.vip.mvp.contract;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.vip.mvp.model.bean.InvitationCodeBean;

import io.reactivex.Observable;

public interface VipQrCodeContract {

    interface View extends IView {
        void onUserInfoLoaded(InvitationCodeBean userInfo);
        void onTextCopied(String text);

        default void showMessage(@NonNull String message) {}
        default void launchActivity(@NonNull Intent intent) {}
        default void killMyself() {}
    }

    interface Model extends IModel {

        Observable<InvitationCodeBean> loadInvitaionCode();
        void copyTextToClipboard(String text, Runnable taskAfterCopied);
    }
}
