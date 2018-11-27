package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.mine.mvp.contract.WithDrawContract;
import com.mujirenben.android.mine.mvp.model.bean.AccountInfoBean;
import com.mujirenben.android.mine.mvp.model.bean.LockFanBean;
import com.mujirenben.android.mine.mvp.model.bean.VerifyCodeBean;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawResultBean;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author: panrongfu
 * @date: 2018/8/6 16:32
 * @describe:
 */
@ActivityScope
public class WithdrawPresenter extends BasePresenter<WithDrawContract.Model,WithDrawContract.View>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WithdrawPresenter(WithDrawContract.Model model, WithDrawContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取账户信息
     * @param map
     */
    public void getWithdrawInfo(HashMap<String,String> map) {
        mRootView.showLoading();
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.getWithdrawInfo(map)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<AccountInfoBean>(mErrorHandler) {
                    @Override
                    public void onNext(AccountInfoBean bean) {
                        if(bean != null && bean.getData()!= null ){
                            mRootView.showAccountInfo(bean.getData());
                        }else {
                            String errorMsg = bean.getMessage();
                            if(TextUtils.isEmpty(errorMsg)){
                                errorMsg = "未知错误";
                            }
                            mRootView.showAccountInfoError(errorMsg);
                        }
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                       ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.showMessage(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.showMessage(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }

    /**
     * 申请提现
     * @param map
     */
    public void applyWithdraw(HashMap<String,String> map) {
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.applyWithdraw(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<WithdrawResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(WithdrawResultBean bean) {
                        if(bean != null && bean.isSuccess() ){
                            mRootView.withdrawOnSuccess();
                        }else {
                            String errorMsg = bean.getMessage();
                            if(TextUtils.isEmpty(errorMsg)){
                                errorMsg = "未知错误";
                            }
                            mRootView.withdrawOnFail(errorMsg);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.showMessage(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.showMessage(msg);
                            }
                        });

                    }
                });
    }

    /**
     * 锁粉
     * @param map
     * @return
     */
    public void applyLockFan(HashMap<String,String> map){
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.applyLockFan(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable ->addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<LockFanBean>(mErrorHandler) {
                    @Override
                    public void onNext(LockFanBean lockFanBean) {
                        if (lockFanBean != null && lockFanBean.isSuccess()){
                            mRootView.lockFanOnSuccess(lockFanBean.getMessage());
                        }else {
                            String msg = "绑定邀请人失败，请重试";
                            if(lockFanBean != null ){
                                msg = lockFanBean.getMessage();
                            }
                            mRootView.lockFanOnFail(msg);
                        }
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
                                mRootView.lockFanOnFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.lockFanOnFail(msg);
                            }
                        });
                       mRootView.hideLoading();
                    }
                });
    }

    /**
     * 发送手机验证码
     * @param map
     * @return
     */

    public void sendPhoneVerify(HashMap<String,String> map){
        mModel.sendPhoneVerify(map)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<VerifyCodeBean>(mErrorHandler) {
                    @Override
                    public void onNext(VerifyCodeBean verifyBean) {
                        if(verifyBean != null && verifyBean.isSuccess()){
                            mRootView.verifyCodeOnSuccess(verifyBean);
                        }else {
                            String msg = "获取手机验证失败";
                            if(verifyBean != null){
                                msg = verifyBean.getMessage();
                            }
                            mRootView.verifyCodeOnFail(msg);
                        }
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
                                mRootView.verifyCodeOnFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.verifyCodeOnFail(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }

    /**
     * 验证手机验证码
     * @param map
     * @return
     */
    public void verifyVcode(HashMap<String,String> map){
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.verifyVcode(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<Object>(mErrorHandler) {
                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
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


}
