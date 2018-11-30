package com.mujirenben.kefu.service;

import com.mujirenben.kefu.bean.KeFuLoginBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * @author: panrongfu
 * @date: 2018/8/25 19:17
 * @describe:
 */

public interface KeFuService {

    @GET("/hrz/api/account/custom/service/login")
    @Headers({"Domain-Name: kefu_login_info"})
    Observable<KeFuLoginBean> getKeFuLoginInfo(@QueryMap HashMap<String, String> map);
}
