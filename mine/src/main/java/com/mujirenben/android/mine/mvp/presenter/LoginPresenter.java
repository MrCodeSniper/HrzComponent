package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;

import com.google.gson.JsonObject;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.mine.login.LoginUtil;
import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.login.bean.MatchPhoneBean;
import com.mujirenben.android.mine.login.bean.WeixinAccessTokenResult;
import com.mujirenben.android.mine.mvp.contract.LoginContract;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.RequestBody;

public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
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
     * 获取微信AccessToken
     * @param code
     */
    public void getWeixinAccessToken(String appid, String secret, String code, String grant_type){
        mModel.getWeixinAccessToken(appid, secret, code, grant_type)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<WeixinAccessTokenResult>(mErrorHandler) {
                    @Override
                    public void onNext(WeixinAccessTokenResult accessTokenResult) {
                        if(accessTokenResult==null){
                            if(mRootView!=null){
                                mRootView.getWeixinAccessTokenFail(accessTokenResult.getErrmsg()+"");
                            }
                        }else {
                            if(mRootView!=null){
                                mRootView.onAccessTokeReturn(accessTokenResult);
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        //super.onError(t);
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.getWeixinAccessTokenFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.getWeixinAccessTokenFail(msg);
                            }
                        });
                    }
                });
    }

    /**
     * 获取微信AccessToken
     * @param code
     */
    public void getWeixinAccessToken(String code) {
        getWeixinAccessToken(Const.WECHAT_APPID, Const.WECHAT_SECRET, code,"authorization_code");
    }

    /**
     * 微信登录
     * @param accessToken
     * @param openId
     */
    public void LoginWithWeixin(String accessToken, String openId) {
        JsonObject paramJson = LoginUtil.getLoginWeixinParamJson(mApplication, accessToken, openId);
        LogUtil.d("login", "LoginWithWeixin rarams: " + paramJson.toString());

        RequestBody requestBody= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramJson.toString());
        mModel.loginWithWeixin(requestBody)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<LoginResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(LoginResultBean loginResultBean) {
                        if(loginResultBean==null){
                            String msg = "登录失败";
                            mRootView.loginWithWeixinFail(msg);
                        }else {
                            mRootView.onLoginSuccess(loginResultBean);
                        }
                    }

                    public void onError(Throwable t) {
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.loginWithWeixinFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.loginWithWeixinFail(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }

    /**
     * 匹配手机号
     * @param paramMap
     */
    public void matchPhone(HashMap<String,String> paramMap){
        mModel.matchPhone(paramMap)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<MatchPhoneBean>(mErrorHandler) {
                    @Override
                    public void onNext(MatchPhoneBean matchPhoneBean) {
                        if (matchPhoneBean != null && matchPhoneBean.isSuccess()){
                            mRootView.matchPhoneOnSuccess(matchPhoneBean);
                        }else {
                            String msg = "匹配手机号失败";
                            if(matchPhoneBean != null){
                                msg = matchPhoneBean.getMessage();
                            }
                            mRootView.matchPhoneOnFail(msg);
                        }
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                       // super.onError(t);
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.matchPhoneOnFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.matchPhoneOnFail(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }
}
