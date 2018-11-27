package com.mujirenben.android.common.misc;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface KoulingService {

    @Headers({"Domain-Name: kouling"})
    @GET("/hrz/api/product/tpwd/convert")
    Observable<KoulingBean> loadKoulingInfo(@QueryMap Map<String, String> params);

    @Headers({"Domain-Name: kouling"})
    @GET("/hrz/api/product/tpwd/get")
    Observable<KoulingGenerateResult> generateKouling(@QueryMap Map<String, String> params);

    @Headers({"Domain-Name: kouling"})
    @GET("/hrz/api/product/tpwd/convert/simple")
    Observable<SimplifiedKoulingBean> loadSimplifiedKoulingInfo(@QueryMap Map<String, String> params);
}
