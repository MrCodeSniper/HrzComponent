package com.mujirenben.android.common.pay;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author: panrongfu
 * @date: 2018/8/18 10:44
 * @describe:
 */

public interface CommitOrderService {

    /**
     * 商品下单
     * @param body
     * @return
     */
    @POST("/hrz/api/wallet/order/add")
    @Headers({
            "Domain-Name: goodOrder",
            "Content-Type: application/json"})
    Observable<OrderResultBean> goodOrder(@Body RequestBody body);

    /**
     * 获取支付宝支付参数orderInfo
     * @param map
     * @return
     */
    @GET("/hrz/api/wallet/order/get/app/req")
    @Headers({ "Domain-Name: alipayReq"})
    Observable<PayRequestBean> alipayReq(@QueryMap HashMap<String, String> map);
}
