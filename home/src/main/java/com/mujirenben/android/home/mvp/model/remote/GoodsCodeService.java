
package com.mujirenben.android.home.mvp.model.remote;



import com.mujirenben.android.home.mvp.model.entity.GoodsEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * ================================================
 */
public interface GoodsCodeService {


    @Headers({"Domain-Name: juhe"}) // 加上 Domain-Name header
    @GET("/jhbar/bar")
    Observable<GoodsEntity> getGoodsInfo(@Query("pkg") String packages, @Query("barcode") String qrcode, @Query("cityid") String city, @Query("appkey") String appkey);



}
