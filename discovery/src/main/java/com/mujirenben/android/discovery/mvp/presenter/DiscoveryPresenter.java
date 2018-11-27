package com.mujirenben.android.discovery.mvp.presenter;

import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.discovery.mvp.contract.DiscoveryContract;
import com.mujirenben.android.discovery.mvp.model.entity.DiscoveryBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

public class DiscoveryPresenter
        extends BasePresenter<DiscoveryContract.Model, DiscoveryContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public DiscoveryPresenter(DiscoveryContract.Model model, DiscoveryContract.View view) {
        super(model, view);
    }

    public void clickShare(long id) {
        mModel.clickShare(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<DiscoveryBean>(mErrorHandler) {

                    @Override
                    public void onNext(DiscoveryBean recommendBean) {

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

    public void loadMaterialData(final boolean loadMore, int page, int pageSize) {
        mRootView.showLoading();
        mModel.loadMaterialData(page, pageSize)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<DiscoveryBean>(mErrorHandler) {

                    @Override
                    public void onNext(DiscoveryBean recommendBean) {
                        if (loadMore) {
                            mRootView.onMoreMaterialDataLoaded(recommendBean);
                        } else {
                            mRootView.onMaterialDataLoaded(recommendBean);
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
                        mRootView.hideLoading();

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
                    }
                });
    }

    public void loadRecommendData(final boolean loadMore, int page, int pageSize) {
        mRootView.showLoading();
        mModel.loadRecommendData(page, pageSize)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<DiscoveryBean>(mErrorHandler) {

                    @Override
                    public void onNext(DiscoveryBean recommendBean) {
                        if (loadMore) {
                            mRootView.onMoreRecommendDataLoaded(recommendBean);
                        } else {
                            mRootView.onRecommendDataLoaded(recommendBean);
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
                        mRootView.hideLoading();

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
                    }
                });
    }
}
