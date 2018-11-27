package com.mujirenben.android.xsh.service;

import com.mujirenben.android.xsh.entity.OfficeResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public interface OfficeApi {

    @GET("/hrz/api/merchant/broker/commision")
    Call<OfficeResult> office(@QueryMap Map<String, String> params);
}
