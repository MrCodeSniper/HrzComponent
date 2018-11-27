package com.mujirenben.android.mine.mvp.model.service;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/16.Best Wishes to You!  []~(~▽~)~* Cheers!

import com.mujirenben.android.common.entity.AddressBean;
import com.mujirenben.android.common.entity.EntityData;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 负责人：曾建文
 */
public interface AddressService {

    //收货地址列表
    @Headers({"Domain-Name: user_address", "Content-Type: application/json"})
    @POST("/hrz/api/address/list")
    Observable<AddressBean> getUserAddressList(@Body RequestBody body);


    //保存或者编辑
    @Headers({"Domain-Name: user_address", "Content-Type: application/json"})
    @POST("/hrz/api/address/saveOrUpdate")
    Observable<EntityData> saveUserShipAddress(@Body RequestBody body);

    //设置为默认地址
    @Headers({"Domain-Name: user_address", "Content-Type: application/json"})
    @POST("/hrz/api/address/addDefault")
    Observable<EntityData> setDefaultShipAddressById(@Body RequestBody body);

    //删除收货地址
    @Headers({"Domain-Name: user_address", "Content-Type: application/json"})
    @POST("/hrz/api/address/delete")
    Observable<EntityData> deleteAddressById(@Body RequestBody body);






}
