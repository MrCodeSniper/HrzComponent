package com.mujirenben.android.mine.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import javax.inject.Inject;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.mine.mvp.contract.WithdrawDetailContract;
import com.mujirenben.android.mine.mvp.model.bean.AccountInfoBean;
import com.mujirenben.android.mine.mvp.model.bean.IdCardUploadBean;
import com.mujirenben.android.mine.mvp.model.bean.UpdateAccountBean;
import com.mujirenben.android.mine.mvp.model.service.WithdrawService;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@ActivityScope
public class WithdrawDetailModel extends BaseModel implements WithdrawDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WithdrawDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<UpdateAccountBean> updateWithDrawInfo(RequestBody requestBody) {
        return mRepositoryManager.obtainRetrofitService(WithdrawService.class).updateWithDrawInfo(requestBody);
    }

    @Override
    public Observable<IdCardUploadBean> uploadIdCardPic(MultipartBody.Part part) {
        return mRepositoryManager.obtainRetrofitService(WithdrawService.class).uploadIdCardPic(part);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}