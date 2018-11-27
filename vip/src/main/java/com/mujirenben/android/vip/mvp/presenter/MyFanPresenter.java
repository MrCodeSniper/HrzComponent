package com.mujirenben.android.vip.mvp.presenter;

import android.app.Application;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.mvp.contract.MyFanContract;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.FanListBean;
import com.orhanobut.logger.Logger;

import java.util.Map;


@ActivityScope
public class MyFanPresenter extends BasePresenter<MyFanContract.Model, MyFanContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyFanPresenter(MyFanContract.Model model, MyFanContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取粉丝总览信息
     */
    public void getMyFanInfo(Map<String,String> map){
        mRootView.showLoading();
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.getMyFanInfo(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<FanHeaderInfo>(mErrorHandler) {
                    @Override
                    public void onNext(FanHeaderInfo headerInfo) {
                        if(null != headerInfo && headerInfo.isSuccess()){
                            mRootView.showFanInfo(headerInfo);
                        }else {
                            String msg = "未知错误，请重试";
                            if(headerInfo != null){
                                msg = headerInfo.getMessage()+":"+headerInfo.getCode();
                            }
                            mRootView.fanInfoException(msg);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.fanInfoException(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.fanInfoException(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }

    /**
     * 获取粉丝列表
     */
    public void getMyFanList(Map<String,String> map){
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.getMyFanList(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<FanListBean>(mErrorHandler) {
                    @Override
                    public void onNext(FanListBean listBean) {
                        if(listBean != null && listBean.isSuccess() && listBean.getData() != null){
                            mRootView.showFanList(listBean.getData());
                        }else {
                           String msg = "获取粉丝列表失败";
                           if(listBean != null){
                               msg = listBean.getMessage()+":"+listBean.getCode();
                           }
                           mRootView.fanListDataErrorLayout(msg);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.fanListDataErrorLayout(msg);
                            }
                            @Override
                            public void networkException(String msg) {
                                mRootView.fanListNetworkException(msg);
                            }
                        });
                        mRootView.hideLoading();
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
