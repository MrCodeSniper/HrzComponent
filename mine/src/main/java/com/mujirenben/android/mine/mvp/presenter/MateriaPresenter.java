package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;

import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.mine.mvp.contract.MateriaContract;
import com.mujirenben.android.mine.mvp.model.bean.MateriaCircleBean;
import com.mujirenben.android.mine.mvp.model.bean.MateriaFriendsBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@FragmentScope
public class MateriaPresenter extends BasePresenter<MateriaContract.Model, MateriaContract.View> {


    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MateriaPresenter(MateriaContract.Model model, MateriaContract.View rootView) {
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




    public void getMateriaDataFromFriends() {
        mModel.getMateriaDataFromFriends(1, false)//false使用缓存
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mRootView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<MateriaFriendsBean>(mErrorHandler) {
                    @Override
                    public void onNext(MateriaFriendsBean resourcesBean) {
                        if (resourcesBean == null) {//==
                            mRootView.showFriendsMateriaFail();
                            mRootView.hideLoading();
                            return;
                        }

                        mRootView.showFriendsMateriaSuccess(resourcesBean);
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRootView.hideLoading();
                    }
                });
    }


    public void getMateriaDataFromCircles() {
        mRootView.showLoading();
        mModel.getMateriaDataFromCircle(1, false)//false使用缓存
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mRootView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<MateriaCircleBean>(mErrorHandler) {
                    @Override
                    public void onNext(MateriaCircleBean resourcesBean) {
                        if (resourcesBean == null) {//==
                            mRootView.showCirclesMateriaFail();
                            mRootView.hideLoading();
                            return;
                        }

                        mRootView.showCirclesMateriaSuccess(resourcesBean);
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
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
