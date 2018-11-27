package com.mujirenben.android.vip.mvp.model.service;


import com.mujirenben.android.common.entity.AddressBean;
import com.mujirenben.android.common.entity.SearchResult;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.IndustryBean;
import com.mujirenben.android.vip.mvp.model.bean.InvitationCodeBean;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;
import com.mujirenben.android.vip.mvp.model.bean.ShopBean;
import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;
import com.mujirenben.android.vip.mvp.model.bean.VipUpgradeInfo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Author: panrongfu
 * Time:2018/6/21 15:10
 * Description:vip_module API
 */

public interface VipService{

    /**
     * 获取粉丝总览
     * @return
     */
    @POST("/hrz/api/accountMember/fansReport")
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json",
            "Domain-Name:mock_app_fan_info"})// 加上 Domain-Name header
    Observable<FanHeaderInfo> getMyFanInfo(@Body RequestBody requestBody);

    /**
     * 获取店铺列表
     * @return
     */
    @Headers({"Domain-Name: mock_app_vip_shop"}) // 加上 Domain-Name header
    @GET("/tools/mockapi/5592/vip_shops")
    Observable<ShopBean> getShopList();


    @Headers({"Domain-Name: normal_server", "Content-Type: application/json"})
    @POST("/hrz/api/accountMember/inviteCode")
    Observable<InvitationCodeBean> loadInvitationCode(@Body RequestBody body);

    @Headers({"Domain-Name: industry_list", "Content-Type: application/json"})
    @GET("/xdz/industry/{v}/list")
    Observable<IndustryBean> getAllianceOpenIndustry();

    /**
     * 获取会员升级信息
     * @return
     */
    @Headers({"Domain-Name: normal_server"})
    @GET("/hrz/api/accountMember/upgradeInfo")
    Observable<VipUpgradeInfo> loadVipUpgradeInfo(@QueryMap Map<String, String> params);

    /**
     * 获取公告信息
     * @param map
     * @return
     */
    @GET("hrz/api/accountMember/notice")
    @Headers({"Domain-Name: notice_list"})
    Observable<NoticeBean> getNoticeList(@QueryMap HashMap<String, String> map);

    /**
     * 获取会员页面banner
     * @param body
     * @return
     */
    @POST("/hrz/api/bannerEntrance/list")
    @Headers({"Domain-Name: banner_list", "Content-Type: application/json"})
    Observable<VipBannerBean> getVipBanner(@Body RequestBody body);


    @Headers({"Domain-Name: alliance_city", "Content-Type: application/json"})
    @GET("/xdz/shop/city/{v}/list")
    Observable<AddressBean> getAllianceOpenCities();


    @Headers({"Domain-Name: self_goods", "Content-Type: application/json"})
    @GET("/hrz/api/product/self/list")
    Observable<SearchResult> getSelfGoods(@QueryMap Map<String, String> map);
}
