package com.mujirenben.android.mine.mvp.model;

import com.mujirenben.android.common.base.greendao.DaoSession;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.datapackage.resource.DataBaseHelper;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.mine.login.LoginService;
import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.login.bean.PhoneVerifyCodeBean;
import com.mujirenben.android.mine.mvp.contract.SmsCodeVerifyContract;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

public class SmsCodeVerifyModel extends BaseModel implements SmsCodeVerifyContract.Model {
    @Inject
    public SmsCodeVerifyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<PhoneVerifyCodeBean> getPhoneVerifyCode(HashMap<String, String> paramMap) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).getPhoneVerifyCode(paramMap);
    }

    @Override
    public Observable<LoginResultBean> loginWithPhone(RequestBody requestBody) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).loginWithPhone(requestBody);
    }

    @Override
    public void saveUserInfo(UserBean userBean) {
        DaoSession daoSession = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN);
        long itemId = daoSession.getUserBeanDao().insertOrReplace(userBean);
    }
}
