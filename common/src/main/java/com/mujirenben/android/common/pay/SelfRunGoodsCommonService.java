package com.mujirenben.android.common.pay;

import com.mujirenben.android.common.datapackage.bean.SelfRunGoodsBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface SelfRunGoodsCommonService {

    @Headers({"Domain-Name: self_run_goods"})
    @GET("/hrz/api/product/self/detail")
    Observable<SelfRunGoodsBean> loadSelfRunGoodsInfo(@QueryMap HashMap<String, String> params);
}
