
package com.mujirenben.android.mine.mvp.model.service;

import com.mujirenben.android.mine.mvp.model.InviteCodeBean;
import com.mujirenben.android.mine.mvp.model.bean.UserInfo;
import com.mujirenben.android.mine.mvp.model.bean.UserRewardInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * ================================================
 */
public interface MineService {

    @Headers({"Domain-Name: api_server","Content-Type: application/json"})
    @POST("/hrz/api/income/findIncomeByUser")
    Observable<UserRewardInfo> getUserRewardInfo(@Body RequestBody body);

    @Headers({"Domain-Name: api_server","Content-Type: application/json"})
    @GET("/hrz/api/account/info")
    Observable<UserInfo> getUserInfo(@QueryMap Map<String, String> body);


    @Headers({"Domain-Name: api_server","Content-Type: application/json"})
    @GET("/hrz/api/account/has/rel")
    Observable<InviteCodeBean> getInviteCodeInfo(@QueryMap Map<String, String> body);

}
