package com.mujirenben.android.mine.mvp.model.service;

import com.alibaba.android.arouter.facade.Postcard;
import com.mujirenben.android.mine.mvp.model.bean.ProfitListDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMainDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMonthDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitNoticeBean;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ProfitDetailsService {
    @Headers({"Domain-Name: profit_details","Content-Type: application/json"}) // 加上 Domain-Name header
    @POST("/hrz/api/income/findIncomeInfoByUser")
    Observable<ProfitMainDetailsBean> getProfitMainDetails(@Body RequestBody body);

    @Headers({"Domain-Name: profit_details"}) // 加上 Domain-Name header
    @GET("/hrz/api/income/findIncomeInfoDesc")
    Observable<ProfitNoticeBean> getProfitNotice();

    @Headers({"Domain-Name: profit_details","Content-Type: application/json"})
    @POST("/hrz/api/income/findIncomeWithMonth")
    Observable<ProfitMonthDetailsBean> getProfitMonthDetails(@Body RequestBody body);

    @Headers({"Domain-Name: profit_details","Content-Type: application/json"})
    @POST("/hrz/api/income/findIncomeInfoByType")
    Observable<ProfitListDetailsBean> getProfitListByType(@Body RequestBody body);
}