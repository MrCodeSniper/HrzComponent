package com.mujirenben.android.vip.mvp.model.service;

import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.FanListBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Author: panrongfu
 * Date:2018/6/30 16:14
 * Description:存放关于粉丝的API
 */

public interface FanService{
    /**
     * 获取粉丝列表
     * @return
     */
    @POST("/hrz/api/accountMember/fans")
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json",
            "Domain-Name:mock_app_fans_list"})// 加上 Domain-Name header
    Observable<FanListBean> getMyFanList(@Body RequestBody requestBody);

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
}
