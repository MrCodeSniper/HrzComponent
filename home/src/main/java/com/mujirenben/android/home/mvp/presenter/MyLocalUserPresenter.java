package com.mujirenben.android.home.mvp.presenter;

import android.app.Application;

import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.home.mvp.contract.MyLocalUserContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class MyLocalUserPresenter extends BasePresenter<MyLocalUserContract.Model, MyLocalUserContract.View> {


    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyLocalUserPresenter(MyLocalUserContract.Model model, MyLocalUserContract.View rootView) {
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




    public void getUserInfofromDB(){
        mModel.getUserInfo()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<UserBean>(mErrorHandler) {
                    @Override
                    public void onNext(UserBean resourcesBean) {
                        if(resourcesBean==null){
                            mRootView.noUserInfoDB();
                            return;
                        }
                        mRootView.showUserInfo(resourcesBean);
                    }
                });
    }


  
    



}
