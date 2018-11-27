package com.mujirenben.android.discovery.mvp.model.service;

import com.mujirenben.android.discovery.mvp.model.entity.DiscoveryBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DiscoveryService {

    @Headers({"Domain-Name: normal_server", "Content-Type: application/json"})
    @POST("/hrz/api/burstingBetirCommon/list")
    Observable<DiscoveryBean> loadDiscoveryBean(@Body RequestBody body);

    @Headers({"Domain-Name: normal_server", "Content-Type: application/json"})
    @POST("/hrz/api/burstingBetirCommon/burstingBetirClick")
    Observable<DiscoveryBean> clickShare(@Body RequestBody body);
}
