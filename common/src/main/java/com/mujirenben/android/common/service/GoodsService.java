
package com.mujirenben.android.common.service;

import com.mujirenben.android.common.entity.SearchResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;


/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * ================================================
 */
public interface GoodsService {

    @GET("/hrz/api/product/convertUrl")
    Call<GoodTaobaoLinkResult> getTaobaoLinkIntoCallQueue(@QueryMap HashMap<String, String> paramsMap);

    @Headers({"Domain-Name: server_search_result"})
    @GET("/hrz/api/product/list")
    Observable<SearchResult> getSearchGoods(@QueryMap Map<String, String> params);

    @Headers({"Domain-Name: server_goods_detail"})
    @GET("/hrz/api/product/convertUrl")
    Observable<GoodTaobaoLinkResult> getTaobaoLink(@QueryMap HashMap<String, String> paramsMap);
}
