
package com.mujirenben.android.mine.mvp.model.service;

import com.mujirenben.android.mine.mvp.model.bean.MateriaCircleBean;
import com.mujirenben.android.mine.mvp.model.bean.MateriaFriendsBean;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;


/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * ================================================
 */
public interface MateriaService {

    @Headers({"Domain-Name: category"}) // 加上 Domain-Name header
    @GET("/tools/mockapi/5592/search")
    Observable<MateriaFriendsBean> getSearchGoods();

    @Headers({"Domain-Name: mock_materia_circle"}) // 加上 Domain-Name header
    @GET("/tools/mockapi/5592/matria_circle")
    Observable<MateriaCircleBean> getCircleMateriaData();

}
