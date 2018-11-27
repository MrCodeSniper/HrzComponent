package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.mine.mvp.contract.WithdrawDetailContract;
import com.mujirenben.android.mine.mvp.model.bean.IdCardUploadBean;
import com.mujirenben.android.mine.mvp.model.bean.UpdateAccountBean;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;


@ActivityScope
public class WithdrawDetailPresenter extends BasePresenter<WithdrawDetailContract.Model, WithdrawDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WithdrawDetailPresenter(WithdrawDetailContract.Model model, WithdrawDetailContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 更新账户信息
     * @param map
     */
    public void updateWithDrawInfo(HashMap<String,String> map) {
        mRootView.showLoading();
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mModel.updateWithDrawInfo(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<UpdateAccountBean>(mErrorHandler) {
                    @Override
                    public void onNext(UpdateAccountBean bean) {
                        if(bean != null && bean.isSuccess() && bean.getData() != null){
                            String resultCode = bean.getData().getResult();
                            //校验结果：0、匹配，1、不匹配，2、身份证号不存在
                            if(!TextUtils.isEmpty(resultCode) && resultCode.equals("0")){
                                mRootView.updateAccountOnSuccess();
                            }else {
                                String errorMsg = bean.getMessage();
                                mRootView.updateAccountOnFail(errorMsg);
                            }
                        }else {
                            String errorMsg = bean.getMessage();
                            if(TextUtils.isEmpty(errorMsg)){
                                errorMsg = "未知错误,请重试";
                            }
                            mRootView.updateAccountOnFail(errorMsg);
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
     * 图片上传
     * @param uploadFile
     */
    public void uploadIdCardPic(File uploadFile){
        mRootView.showLoading();
        RetrofitUrlManager.getInstance().putDomain("mock_upload_file", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), uploadFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", uploadFile.getName(), requestFile);
        mModel.uploadIdCardPic(part)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<IdCardUploadBean>(mErrorHandler) {
                    @Override
                    public void onNext(IdCardUploadBean idCardUploadBean) {
                        if(idCardUploadBean != null && idCardUploadBean.isSuccess()){
                            mRootView.uploadIdPicOnSuccess(idCardUploadBean.getData());
                        }else if(idCardUploadBean != null && !idCardUploadBean.isSuccess()) {
                            String errorMsg = idCardUploadBean.getMsg();
                            mRootView.uploadIdPicOnFail(errorMsg);
                        }else {
                            String errorMsg = "未知错误,请重试";
                            mRootView.updateAccountOnFail(errorMsg);
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
                                mRootView.updateAccountOnFail(msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                mRootView.updateAccountOnFail(msg);
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
    }
}
