package com.mujirenben.android.mine.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.mine.mvp.contract.WithDrawContract;
import com.mujirenben.android.mine.mvp.model.bean.AccountInfoBean;
import com.mujirenben.android.mine.mvp.model.bean.LockFanBean;
import com.mujirenben.android.mine.mvp.model.bean.UpdateAccountBean;
import com.mujirenben.android.mine.mvp.model.bean.VerifyCodeBean;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawResultBean;
import com.mujirenben.android.mine.mvp.model.service.WithdrawService;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author: panrongfu
 * @date: 2018/8/6 16:14
 * @describe:
 */

public class WithdrawModel extends BaseModel implements WithDrawContract.Model {

    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WithdrawModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<AccountInfoBean> getWithdrawInfo(HashMap<String,String> map) {
        return mRepositoryManager.obtainRetrofitService(WithdrawService.class).getWithdrawInfo(map);
    }

    @Override
    public Observable<WithdrawResultBean> applyWithdraw(RequestBody requestBody) {
        return mRepositoryManager.obtainRetrofitService(WithdrawService.class).applyWithdraw(requestBody);
    }

    /**
     * 锁粉
     * @param requestBody
     * @return
     */
    @Override
    public Observable<LockFanBean> applyLockFan(RequestBody requestBody){
        return mRepositoryManager.obtainRetrofitService(WithdrawService.class).applyLockFan(requestBody);
    }

    /**
     * 发送手机验证码
     * @param map
     * @return
     */
    @Override
    public Observable<VerifyCodeBean> sendPhoneVerify(HashMap<String,String> map){
        return mRepositoryManager.obtainRetrofitService(WithdrawService.class).sendPhoneVerify(map);
    }

    /**
     * 验证手机验证码
     * @param requestBody
     * @return
     */
    @Override
    public Observable<Object> verifyVcode(RequestBody requestBody){
        return mRepositoryManager.obtainRetrofitService(WithdrawService.class).verifyVcode(requestBody);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}
