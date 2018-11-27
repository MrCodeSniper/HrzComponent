package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;

import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.mine.mvp.contract.WithdrawRecordContract;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawListBean;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class WithdrawRecordPresenter extends BasePresenter<WithdrawRecordContract.Model, WithdrawRecordContract.View> {


    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WithdrawRecordPresenter(WithdrawRecordContract.Model model, WithdrawRecordContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 提现记录
     * @param map
     */
    public void getWithdrawRecordData(HashMap<String,String> map) {
        mRootView.showLoading();
        mModel.getWithdrawRecordData(map)//false使用缓存
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<WithdrawListBean>(mErrorHandler) {
                    @Override
                    public void onNext(WithdrawListBean resourcesBean) {
                        if(resourcesBean != null && resourcesBean.isSuccess()&& resourcesBean.getData() != null){
                            mRootView.showWithdrawRecordSuccess(resourcesBean.getData());
                            return;
                        }
                        if (resourcesBean == null || !resourcesBean.isSuccess()|| resourcesBean.getData() == null) {
                            String msg = "数据异常";
                            if (resourcesBean != null){
                                msg = resourcesBean.getMessage();
                            }
                            mRootView.showDataErrorLayout(msg);
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                       ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                mRootView.showDataErrorLayout(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.showMessage(msg);
                            }
                        });
                       mRootView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRootView.hideLoading();
                    }
                });
    }

}
