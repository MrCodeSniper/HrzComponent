package com.mujirenben.android.xsh.service;

import com.google.gson.JsonObject;
import com.mujirenben.android.xsh.entity.LinkDataBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LinkListService {
	//  @Headers({"Domain-Name: mock_alliance_welfare"})
	@GET("/merchant/myMerchant.htm")
    Call<LinkDataBean> getLinkList(@Query("userid") String userId, @Query("page") int page, @Query("pageSize") int pageSize);

	@Headers({"Content-Type: application/json"})
	@POST("/merchant/{v}/myMerchant/referreStatus.htm")
    Call<JsonObject> refreshStatus(@Path("v") String version, @Body RequestBody requestBody);
}
