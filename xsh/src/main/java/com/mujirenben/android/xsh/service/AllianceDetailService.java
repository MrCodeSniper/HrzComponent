
package com.mujirenben.android.xsh.service;


import com.mujirenben.android.xsh.entity.GetTicketInfo;
import com.mujirenben.android.xsh.entity.ShareEntity;
import com.mujirenben.android.xsh.entity.ShopMerchantBean;
import com.mujirenben.android.xsh.entity.ShopRecommedGoods;
import com.mujirenben.android.xsh.entity.ShopTicketEntity;
import com.mujirenben.android.common.entity.AllianceDetailBeans;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * ================================================
 */
public interface AllianceDetailService {


    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("/xdz/coupon/receiveMerchant")
    Call<GetTicketInfo> getCommonTicket(@Body RequestBody body);


    @POST("/xdz/coupon/nearbyCoupons")
    Call<ShopTicketEntity> getShopTicket(@Body RequestBody body);


    @GET("/xdz/admin/share/list")
    Call<ShareEntity> getShareData(@Query("page") int page, @Query("pageSize") int pageSize, @Query("clientVersion") String version, @Query("clientId") String clientId, @Query("nonceStr") String nonceStr,
                                   @Query("timeStamp") String timeStamp);


    @GET("/xdz/shop/{v}/{id}")
    Call<AllianceDetailBeans> getShopDetailBeanData(@Path("id") int id);

    @GET("/xdz/product/{v}/list/{id}")
    Call<ShopRecommedGoods> getShopRecommendGoods(@Path("id") int id);

    @GET("/xdz/coupon/merchantCoupons")
    Call<ShopMerchantBean> getShopMerchantBean(@Query("mediaUserId") String mediaUserId,
                                               @Query("mediaId") String mediaId,
                                               @Query("merchantId") Long merchantId,
                                               @Query("timeStamp") String timeStamp,
                                               @Query("nonceStr") String nonceStr,
                                               @Query("clientId") String clientId,
                                               @Query("clientVersion") String clientVersion);
}
