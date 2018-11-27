package com.mujirenben.android.mine.login;

import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.login.bean.LogoutResultBean;
import com.mujirenben.android.mine.login.bean.MatchPhoneBean;
import com.mujirenben.android.mine.login.bean.PhoneVerifyCodeBean;
import com.mujirenben.android.mine.login.bean.WeixinAccessTokenResult;
import com.mujirenben.android.mine.login.bean.WeixinUserInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface LoginService {
    @Headers({"Domain-Name: wx_login"})
    @GET("/sns/oauth2/access_token")
    Observable<WeixinAccessTokenResult> getWeixinAccessToken(
            @Query("appid") String appid,
            @Query("secret") String secret,
            @Query("code") String code,
            @Query("grant_type") String grant_type);

    @Headers({
            "Domain-Name: wx_login_hrz",
            "Content-Type: application/json;charset=UTF-8"})
    @POST("/hrz/api/account/wx/login")
    Observable<LoginResultBean> loginWithWeixin(@Body RequestBody requestBody);

    @Headers({"Domain-Name: wx_login_hrz"})
    @GET("/hrz/api/account/info")
    Observable<WeixinUserInfo> getUserInfo(@QueryMap HashMap<String, String> paramMap);

    @Headers({"Domain-Name: wx_login_hrz"})
    @GET("/hrz/api/account/phone/code")
    Observable<PhoneVerifyCodeBean> getPhoneVerifyCode(@QueryMap HashMap<String, String> paramMap);

    @Headers({"Domain-Name: wx_login_hrz", "Content-Type: application/json;charset=UTF-8"})
    @POST("/hrz/api/account/phone/login")
    Observable<LoginResultBean> loginWithPhone(@Body RequestBody requestBody);

    @Headers({"Domain-Name: logout","Content-Type: application/json;charset=UTF-8"})
    @POST("/hrz/api/account/logout")
    Observable<LogoutResultBean> logOut(@Body RequestBody requestBody);

    @GET("/hrz/api/account/phone/match")
    @Headers({"Domain-Name: match_phone"})
    Observable<MatchPhoneBean> matchPhone(@QueryMap HashMap<String, String> hashMap);
}
