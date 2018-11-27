package com.mujirenben.android.vip.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.entity.SearchResult;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.vip.mvp.contract.VipContract;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;
import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MediaType;
import okhttp3.RequestBody;


@ActivityScope
public class VipPresenter extends BasePresenter<VipContract.Model, VipContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public VipPresenter(VipContract.Model model, VipContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取粉丝信息
     */
    public void getMyFanInfo(HashMap<String,String> map){
        String postJson = new Gson().toJson(map);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.getMyFanInfo(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable) )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<FanHeaderInfo>(mErrorHandler) {
                    @Override
                    public void onNext(FanHeaderInfo headerInfo) {
                        if(null != headerInfo && headerInfo.isSuccess()){
                            if(mRootView!=null){
                                mRootView.showVipInfo(headerInfo);
                            }
                        }else {
                            String msg = "未知错误，请重试";
                            if(headerInfo != null){
                                msg = headerInfo.getMessage()+":"+headerInfo.getCode();
                            }
                            if(mRootView!=null){
                                mRootView.vipInfoException(msg);
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                        //super.onError(t);
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                if(mRootView!=null){
                                    mRootView.vipInfoException(msg);
                                }
                            }
                            @Override
                            public void networkException(String msg) {
                                if(mRootView!=null){
                                    mRootView.vipInfoException(msg);
                                }
                            }
                        });

                    }
                });
    }

    /**
     * 获取轮播图
     * @param map
     */
    public void getVipBanner(HashMap<String,String> map){
        String postJson = new Gson().toJson(map);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.getVipBanner(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<VipBannerBean>(mErrorHandler) {
                    @Override
                    public void onNext(VipBannerBean bannerBean) {
                        if(bannerBean != null && bannerBean.isSuccess()){
                            mRootView.vipBannerOnSuccess(bannerBean);
                        }else {
                            String msg = "轮播图获取失败";
                            if(bannerBean != null){
                                mRootView.vipBannerOnFail(msg);
                            }
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
                                mRootView.vipBannerOnFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.vipBannerOnFail(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });

    }

    /**
     * 获取公告栏
     * @param map
     */
    public void getNoticeList(HashMap<String,String> map){
        mModel.getNoticeList(map)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<NoticeBean>(mErrorHandler) {
                    @Override
                    public void onNext(NoticeBean noticeBean) {
                        if(noticeBean != null && noticeBean.isSuccess() && noticeBean.getData() != null){
                            if(mRootView!=null){
                                mRootView.noticeListOnSuccess(noticeBean.getData());
                            }
                        }else {
                            String msg = "获取公告数据失败";
                            if(noticeBean != null){
                                msg = noticeBean.getMessage();
                            }
                            if(mRootView!=null){
                                mRootView.noticeListOnFail(msg);
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        //super.onError(t);
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.noticeListOnFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.noticeListOnFail(msg);
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


    public void requestSearchGoodsData(Map<String, String> params) {

        mRootView.showLoading();
        mModel.getSearchGoods(params)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<SearchResult>(mErrorHandler) {
                    @Override
                    public void onNext(SearchResult resourcesBean) {
                        if (resourcesBean == null || resourcesBean.getData() == null
                                || resourcesBean.getData().getList() == null
                                || resourcesBean.getData().getList().size() == 0) {
                            if(mRootView != null){
                                mRootView.searchResultNodata();
                            }
                            return;
                        }
                        if(mRootView != null){
                            mRootView.showGoods(resourcesBean);
                        }
                    }


                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if(mRootView!=null){
                            mRootView.hideLoading();
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if(mRootView!=null){
                            mRootView.hideLoading();
                        }
                    }
                });
    }
}
