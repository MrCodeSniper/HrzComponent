package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;

import com.google.gson.JsonObject;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.mine.login.LoginUtil;
import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.login.bean.PhoneVerifyCodeBean;
import com.mujirenben.android.mine.mvp.contract.SmsCodeVerifyContract;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.RequestBody;

public class SmsCodeVerifyPresenter extends BasePresenter<SmsCodeVerifyContract.Model, SmsCodeVerifyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SmsCodeVerifyPresenter(SmsCodeVerifyContract.Model model, SmsCodeVerifyContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        unDispose();
    }

    /**
     * 带有SecToken登录
     * @param phone
     * @param verifyCode
     */
    public void loginWithPhone(String phone, String verifyCode,boolean needToken) {
        JsonObject paramJson = LoginUtil.getLoginPhoneParamJson(mApplication, phone, verifyCode,needToken);
        LogUtil.d("login", "LoginWithPhone rarams: " + paramJson.toString());
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramJson.toString());
        mModel.loginWithPhone(requestBody)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<LoginResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(LoginResultBean loginResultBean) {
                        if (loginResultBean == null) {
                            mRootView.onLoginFail("登录失败");
                        } else if (loginResultBean.getCode().equals(Consts.REQUEST_SUCCESS)){
                            mRootView.onLoginSuccess(loginResultBean);
                        } else {
                            mRootView.onLoginFail(loginResultBean.getMessage());
                        }
                    }

                    public void onError(Throwable t) {
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.onLoginFail(msg   );
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.onLoginFail(msg);
                            }
                        });
                        mRootView.hideLoading();

                    }
                });
    }

    /**
     * 获取手机验证码
     * @param phone
     */
    public void getSmsVerifyCode(String phone) {
        HashMap<String, String> paramMap = LoginUtil.getSmsVerifyCodeParamMap(mApplication, phone);
        LogUtil.d("login", "getSmsVerifyCode: " + paramMap.toString());
        mModel.getPhoneVerifyCode(paramMap)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<PhoneVerifyCodeBean>(mErrorHandler) {
                    @Override
                    public void onNext(PhoneVerifyCodeBean pvc) {
                        if ((pvc == null || pvc.getData() == null) && !pvc.isSuccess() ) {
                            String msg = "获取验证失败";
                            mRootView.onPhoneVerifyCodeReturnFail(msg);
                        } else {
                            mRootView.onPhoneVerifyCodeReturn(pvc);
                        }
                    }
                    public void onError(Throwable t) {
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.onPhoneVerifyCodeReturnFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.onPhoneVerifyCodeReturnFail(msg);
                            }
                        });
                    }
                });
    }
}
