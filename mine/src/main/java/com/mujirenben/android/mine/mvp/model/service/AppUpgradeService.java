package com.mujirenben.android.mine.mvp.model.service;

import com.mujirenben.android.mine.mvp.model.bean.AppUpgradeInfo;
import com.mujirenben.android.mine.mvp.model.bean.OrderInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface AppUpgradeService {
    @Headers({"Domain-Name: mock_app_upgrade"}) // 加上 Domain-Name header
    @GET("/tools/mockapi/5592/app_upgrade")
    Observable<AppUpgradeInfo> getAppUpgradeInfo();
}
