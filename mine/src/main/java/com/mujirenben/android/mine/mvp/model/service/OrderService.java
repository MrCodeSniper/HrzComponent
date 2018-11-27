package com.mujirenben.android.mine.mvp.model.service;

import com.mujirenben.android.mine.mvp.model.bean.OrderBean;
import com.mujirenben.android.mine.mvp.model.bean.OrderDetail;
import com.mujirenben.android.mine.mvp.model.bean.OrderInfo;
import com.mujirenben.android.mine.mvp.model.bean.SelfRunOrderDetail;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface OrderService {
	@Headers({"Domain-Name: mock_order_list"})
	@GET("/tools/mockapi/5592/order_list")
	Observable<OrderInfo> getOrderList(@Query("userId") String userId);

	@POST("/hrz/api/order/findList")
	@Headers({
            "Content-Type: application/json",
            "Accept: application/json",
            "Domain-Name:order_findList"})
	Observable<OrderBean> getOrderByType(@Body RequestBody requestBody);


	@Headers({"Domain-Name: server_order_detail"})
	@GET("/hrz/api/order/findOne")
	Observable<OrderDetail> getOrderDetailById(@Query("orderId") String orderId);


	@Headers({"Domain-Name: server_order_detail"})
	@GET("/hrz/api/wallet/order/self/order/detail")
	Observable<SelfRunOrderDetail> getSelfRunOrderDetail(@QueryMap HashMap<String, String> params);
}
