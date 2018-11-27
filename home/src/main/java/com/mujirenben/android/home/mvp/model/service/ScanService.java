package com.mujirenben.android.home.mvp.model.service;

import com.mujirenben.android.home.mvp.model.entity.ScanQrOrderBean;
import com.mujirenben.android.home.mvp.model.entity.ScanQrTakeBean;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author: panrongfu
 * @date: 2018/9/11 16:45
 * @describe:
 */

public interface ScanService {
    /**
     *  扫码下单
     * @param url
     * @param body
     * @return
     */
    @POST
    Observable<ScanQrOrderBean> scanQrOrder(@Url String url, @Body RequestBody body);

    /**
     *  扫码带走
     * @param url
     * @param body
     * @return
     */
    @POST
    Observable<ScanQrTakeBean> scanQrTake(@Url String url, @Body RequestBody body);
}
