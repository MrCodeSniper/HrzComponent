package com.mujirenben.android.mine.mvp.model.service;

import com.mujirenben.android.mine.mvp.model.bean.LockFansResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LockFansService {

    @Headers({"Domain-Name: normal_server", "Content-Type: application/json"})
    @POST("/hrz/api/accountMember/lockfanApp")
    Observable<LockFansResult> lockFans(@Body RequestBody body);
}
