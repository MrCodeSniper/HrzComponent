package com.hrz.hrzcomponent;

import java.util.Map;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TestService {


    @POST("/getVerificationCode")
    io.reactivex.Observable<ResponseEntity> getSmsCode(@Body Map<String, String> params);


    @POST("/register")
    io.reactivex.Observable<ResponseEntity> register(@Body Map<String, String> params);


}
