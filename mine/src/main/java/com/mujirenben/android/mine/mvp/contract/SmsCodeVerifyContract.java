package com.mujirenben.android.mine.mvp.contract;

import android.app.Activity;

import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.login.bean.PhoneVerifyCodeBean;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface SmsCodeVerifyContract {
    interface View extends IView {
        Activity getActivity();

        void onPhoneVerifyCodeReturn(PhoneVerifyCodeBean phoneVerifyCodeBean);
        void onLoginSuccess(LoginResultBean loginResultBean);

        void onLoginFail(String errorCode);
        void onPhoneVerifyCodeReturnFail(String msg);
    }

    interface Model extends IModel {
        Observable<PhoneVerifyCodeBean> getPhoneVerifyCode(HashMap<String, String> paramMap);
        Observable<LoginResultBean> loginWithPhone(RequestBody requestBody);
        void saveUserInfo(UserBean bean);
    }
}
