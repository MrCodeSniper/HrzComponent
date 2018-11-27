package com.mujirenben.android.mine.mvp.model;

import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.mine.login.LoginService;
import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.login.bean.MatchPhoneBean;
import com.mujirenben.android.mine.login.bean.PhoneVerifyCodeBean;
import com.mujirenben.android.mine.login.bean.WeixinAccessTokenResult;
import com.mujirenben.android.mine.login.bean.WeixinUserInfo;
import com.mujirenben.android.mine.mvp.contract.LoginContract;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

public class LoginModel extends BaseModel implements LoginContract.Model {

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<WeixinAccessTokenResult> getWeixinAccessToken(String appid, String secret, String code, String grant_type) {
        return Observable.just(mRepositoryManager.obtainRetrofitService(LoginService.class).getWeixinAccessToken(appid, secret, code, grant_type))
                .flatMap(new Function<Observable<WeixinAccessTokenResult>, ObservableSource<WeixinAccessTokenResult>>() {
                    @Override
                    public ObservableSource<WeixinAccessTokenResult> apply(@NonNull Observable<WeixinAccessTokenResult> listObservable) throws Exception {
                        return  listObservable;
                    }
                });
    }

    @Override
    public Observable<LoginResultBean> loginWithWeixin(RequestBody requestBody) {
        return Observable.just(mRepositoryManager.obtainRetrofitService(LoginService.class).loginWithWeixin(requestBody))
                .flatMap(new Function<Observable<LoginResultBean>, ObservableSource<LoginResultBean>>() {
                    @Override
                    public ObservableSource<LoginResultBean> apply(@NonNull Observable<LoginResultBean> listObservable) throws Exception {
                        return  listObservable;
                    }
                });
    }

    /**
     * 匹配手机号码
     * @param paramMap
     * @return
     */
    @Override
    public Observable<MatchPhoneBean> matchPhone(HashMap<String, String> paramMap) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).matchPhone(paramMap);
    }
}
