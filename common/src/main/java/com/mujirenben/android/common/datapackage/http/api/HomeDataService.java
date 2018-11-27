package com.mujirenben.android.common.datapackage.http.api;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/9/6.Best Wishes to You!  []~(~▽~)~* Cheers!


import com.mujirenben.android.common.datapackage.bean.SearchHotWords;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HomeDataService {

    @Headers({"Domain-Name: global_search", "Content-Type: application/json"}) // 加上 Domain-Name header
    @POST("/hrz/api/heatSearch/hostSearchList")
    Observable<SearchHotWords> getSearchHotWords(@Body RequestBody body);

}
