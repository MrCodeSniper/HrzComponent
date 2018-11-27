package com.mujirenben.android.home.mvp.model.remote;

import com.mujirenben.android.home.mvp.model.entity.HomeModuleBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HomeModuleService {

    @Headers({"Domain-Name: normal_server", "Content-Type: application/json"})
    @POST("/hrz/api/bannerEntrance/list")
    Observable<HomeModuleBean> loadBannerData(@Body RequestBody body);

    @Headers({"Domain-Name: normal_server", "Content-Type: application/json"})
    @POST("/hrz/api/bannerEntrance/list")
    Observable<HomeModuleBean> loadIconListData(@Body RequestBody body);

    @Headers({"Domain-Name: normal_server", "Content-Type: application/json"})
    @POST("/hrz/api/beanCurd/list")
    Observable<HomeModuleBean> loadToufuData(@Body RequestBody body);

    @Headers({"Domain-Name: normal_server", "Content-Type: application/json"})
    @POST("/hrz/api/rewardActivity/list")
    Observable<HomeModuleBean> loadRewardData(@Body RequestBody body);

    @Headers({"Domain-Name: normal_server", "Content-Type: application/json"})
    @POST("/hrz/api/fallsFlow/list")
    Observable<HomeModuleBean> loadWaterfallData(@Body RequestBody body);
}
