package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;
import android.content.Context;

import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.DateTimeUtil;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.mine.mvp.contract.ProfitDetailsContract;
import com.mujirenben.android.mine.mvp.model.bean.ProfitListDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMainDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMonthDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitNoticeBean;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class ProfitDetailsPresenter extends BasePresenter<ProfitDetailsContract.Model, ProfitDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ProfitDetailsPresenter(ProfitDetailsContract.Model model, ProfitDetailsContract.View rootView) {
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
     * 获取收益详细顶部收益明细
     *
     * @param context
     */
    public void getProfitMainDetails(Context context) {
        HashMap<String, String> paramMap = HttpParamUtil.getCommonSignParamMap(context, null);
        mModel.getProfitMainDetails(paramMap)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<ProfitMainDetailsBean>(mErrorHandler) {
                    @Override
                    public void onNext(ProfitMainDetailsBean profitMainDetailsBean) {
                        if (profitMainDetailsBean == null) {
                            String msg = "获取收益失败";
                            mRootView.onLoadDataError(msg);
                        } else {
                            mRootView.bindMainProfitData(profitMainDetailsBean);
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
                                mRootView.onLoadDataError(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.onLoadDataError(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }

    /**
     * 获取收益详细当月收益明细
     *
     * @param context
     */
    public void getProfitMonthDetails(Context context) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("time", DateTimeUtil.getMonthTime());
        HashMap<String, String> paramMap = HttpParamUtil.getCommonSignParamMap(context, hashMap);

        mModel.getProfitMonthDetails(paramMap)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<ProfitMonthDetailsBean>(mErrorHandler) {
                    @Override
                    public void onNext(ProfitMonthDetailsBean profitMonthDetailsBean) {
                        if (profitMonthDetailsBean == null) {
                            String msg = "获取数据失败";
                            mRootView.onLoadDataError(msg);
                        } else {
                            mRootView.bindProfitMonthData(profitMonthDetailsBean);
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
                                mRootView.onLoadDataError(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.onLoadDataError(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }

    /**
     * 获取收益详细顶部收益明细
     */
    public void getProfitNotice() {
        mModel.getProfitNotice()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<ProfitNoticeBean>(mErrorHandler) {
                    @Override
                    public void onNext(ProfitNoticeBean profitNoticeBean) {
                        if (profitNoticeBean == null) {
                            String msg = "获取收益明细失败";
                            mRootView.onLoadDataError(msg);
                        } else {
                            mRootView.bindProfitNoticeData(profitNoticeBean);
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
                                mRootView.onLoadDataError(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.onLoadDataError(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }

    /**
     * 获取收益详细顶部收益明细
     */
    public void getProfitListByType(Context context, int type, int pageNumber, int pageSize) {
        mModel.getProfitListByType(context, type, pageNumber, pageSize)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<ProfitListDetailsBean>(mErrorHandler) {
                    @Override
                    public void onNext(ProfitListDetailsBean profitListDetailsBean) {
                        if (profitListDetailsBean == null) {
                            if (pageNumber > 1) {
                                mRootView.onLoadMoreFailure();
                            } else {
                                String msg = "获取数据失败";
                                mRootView.onLoadListDataError(msg);
                            }
                        } else {
                            if(profitListDetailsBean != null && profitListDetailsBean.getData() != null){
                                profitListDetailsBean.setDataType(type);
                                mRootView.bindProfitListData(profitListDetailsBean);
                            }else {
                                String msg = profitListDetailsBean.getMessage();
                                mRootView.onLoadListDataError(msg);
                            }
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
                                mRootView.onLoadListDataError(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.onLoadListNetworkError(msg);
                            }
                        });
                        mRootView.hideLoading();
                    }
                });
    }
}
