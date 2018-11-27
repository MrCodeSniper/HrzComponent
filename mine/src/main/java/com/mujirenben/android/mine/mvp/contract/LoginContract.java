package com.mujirenben.android.mine.mvp.contract;

import android.app.Activity;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.login.bean.MatchPhoneBean;
import com.mujirenben.android.mine.login.bean.WeixinAccessTokenResult;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface LoginContract {
    interface View extends IView {
        Activity getActivity();
        /**
         * 获取AccessToken成功
         * @param weixinAccessTokenResult
         */
        void onAccessTokeReturn(WeixinAccessTokenResult weixinAccessTokenResult);

        /**
         * 获取AccessToken失败
         * @param msg
         */
        void getWeixinAccessTokenFail(String msg);

        /**
         * 微信登录成功
         * @param loginResultBean
         */
        void onLoginSuccess(LoginResultBean loginResultBean);

        /**
         * 微信登录失败
         * @param msg
         */
        void loginWithWeixinFail(String msg);


        /**
         * 匹配手机号
         * @param matchPhoneBean
         */
        void matchPhoneOnSuccess(MatchPhoneBean matchPhoneBean);

        /**
         * 匹配手机号失败
         * @param msg
         */
        void matchPhoneOnFail(String msg);
    }

    interface Model extends IModel {
        Observable<WeixinAccessTokenResult> getWeixinAccessToken(String appid, String secret, String code, String grant_type);
        Observable<LoginResultBean> loginWithWeixin(RequestBody requestBody);
        Observable<MatchPhoneBean> matchPhone(HashMap<String, String> paramMap);
    }
}

