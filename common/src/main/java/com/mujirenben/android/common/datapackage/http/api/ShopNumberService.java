package com.mujirenben.android.common.datapackage.http.api;


import com.mujirenben.android.common.entity.AllianceDetailBeans;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ShopNumberService {

	@GET("/xdz/shop/{v}/shopNo/{sid}")
    Call<AllianceDetailBeans> requestShopInfoBySid(@Path("sid") String sid);
}
