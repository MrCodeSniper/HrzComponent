package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.PermissionUtil;
import com.mujirenben.android.mine.mvp.contract.MineContract;
import com.mujirenben.android.mine.mvp.model.InviteCodeBean;
import com.mujirenben.android.mine.mvp.model.bean.UserInfo;
import com.mujirenben.android.mine.mvp.model.bean.UserRewardInfo;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.RequestBody;


@FragmentScope
public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View> {


    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public MinePresenter(MineContract.Model model, MineContract.View rootView) {
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

    public void requestStorage() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.requestPermissionSuccess(PermissionUtil.STORAGE);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("Request permissions failure");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showMessage("Need to go to the settings");
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }


    public void requestCamera() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.requestPermissionSuccess("camera");
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("Request permissions failure");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showMessage("Need to go to the settings");
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }

    public void getUserInfoFromLocal(Context context){
        LoginDataManager loginDataManager = LoginDataManager.getsInstance(context);
        if (loginDataManager.isLogin()) {
//            mRootView.showUserInfo();
        } else {
            mRootView.noUserInfoDB();
        }
    }

    public void getUserInfoByToken(Map<String,String> map){
        mModel.getUserInfo(map)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<UserInfo>(mErrorHandler) {
                    @Override
                    public void onNext(UserInfo resourcesBean) {
                        if(resourcesBean==null||resourcesBean.getData()==null){
                            mRootView.noUserInfoDB();
                            return;
                        }
                        mRootView.showUserInfo(resourcesBean);
                    }
                });
    }


    public void saveUserData(UserBean bean){

            mRootView.showLoading();
            mModel.saveUserInfo(bean)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> addDispose(disposable))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ErrorHandleSubscriber<Long>(mErrorHandler) {
                        @Override
                        public void onNext(Long itemId) {
                            if(itemId<0){
                                mRootView.saveFail(itemId);
                                return;
                            }
                            mRootView.saveSucess(itemId);
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
                        }
                    });
    }




    public void getAllProvince(){
        mModel.getAllProvince()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<Province>>(mErrorHandler) {
                    @Override
                    public void onNext(List<Province> provinces) {
                        if(provinces==null||provinces.size()==0){
                            mRootView.getLocalDbFail();
                            return;
                        }
                        mRootView.getProvinceSuccess(provinces);
                    }
                });
    }

    public void loadInviteCodeInfo() {
        mModel.getInviteCodeInfo()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<InviteCodeBean>(mErrorHandler) {
                    @Override
                    public void onNext(InviteCodeBean info) {
                        Log.i(TAG, "loadInviteCodeInfo >> info = " + info);
                        if (mRootView != null) {
                            mRootView.onLoadInviteCodeInfoCompleted(info);
                        }
                    }
                });
    }





    public void getCitiesByProvinceId(Long id){
        mModel.getAllCityByProvince(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<City>>(mErrorHandler) {
                    @Override
                    public void onNext(List<City> cities) {
                        if(cities==null||cities.size()==0){
                            mRootView.getLocalDbFail();
                            return;
                        }
                        mRootView.getCitiesSuccess(cities);
                    }
                });
    }

    public void getUserRewardInfo(Context context){
        mRootView.showLoading();
        mModel.getUserRewardInfo(context)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<UserRewardInfo>(mErrorHandler) {
                    @Override
                    public void onNext(UserRewardInfo userRewardInfo) {
                        if(userRewardInfo==null||userRewardInfo.getData()==null){
                            mRootView.getUserRewardError();
                            return;
                        }
                        mRootView.showUserRewardInfo(userRewardInfo);
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
                    }
                });


    }




}
