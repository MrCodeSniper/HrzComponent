package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.mine.login.bean.LogoutResultBean;
import com.mujirenben.android.mine.mvp.contract.SettingsContract;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SettingsPresenter extends BasePresenter<SettingsContract.Model, SettingsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SettingsPresenter(SettingsContract.Model model, SettingsContract.View view) {
        super(model, view);
    }

    public boolean saveConfig(String spKey, boolean spValue) {
        return mModel.saveConfig(spKey, spValue);
    }

    public boolean isConfigOn(String spKey, boolean defValue) {
        return mModel.isConfigOn(spKey, defValue);
    }

    public String getAppVersion() {
        return mModel.getAppVersion();
    }

    public void clearLocalCache() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mModel.clearLocalCache();
                mRootView.onCacheCleared();
            }
        });
    }

    public void getLocalCacheSize() {
        mModel.getLocalCacheSize()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Float>() {
                    @Override
                    public void accept(Float aFloat) throws Exception {
                        mRootView.onCacheSizeComputed(aFloat);
                    }
                });
    }

    public void logout(Context context) {
        RetrofitUrlManager.getInstance().putDomain("logout", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String,String> map = HttpParamUtil.getCommonSignParamMap(context,null);
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.logOut(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<LogoutResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(LogoutResultBean logoutResultBean) {
                        if(logoutResultBean != null && (logoutResultBean.isSuccess()||logoutResultBean.getCode().equals("90009"))){
                            mRootView.logOutSuccess();
                        }else {
                            String msg = "退出账号失败";
                            if(logoutResultBean != null ){
                                msg = logoutResultBean.getMessage()+ ":"+ logoutResultBean.getCode();
                            }
                            mRootView.logOutFail(msg);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                       // super.onError(t);
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.logOutFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.logOutFail(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }

    public void clearSqlite(){
        mModel.deleteAllUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isDeleteSuccess -> {
                    if(isDeleteSuccess){
                        mRootView.deleteUserInfoSuccess();
                    }else {
                        mRootView.deleteUserInfoFail();
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
