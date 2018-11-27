package com.mujirenben.android.common.service;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/9.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.mujirenben.android.common.datapackage.bean.MessageEventGlobal;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.widget.LoadingDialog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LinkRouterService extends IntentService {

    public static final String BASE_URL = "http://47.106.21.23:8081/";
    private static final String PARAM_ID_TYPE="idType";
    private static final String PARAM_PLATFORM="platform";
    private static final String PARAM_PLATFORM_ID="platformId";
    private static final String PRODUCT_ID="productId";
    private static final String PARAM_COUPON_ID="couponId";


    /**
     * 是否正在运行
     */
    private boolean isRunning;
    private EventBus eventBus;


    @Override
    public void onCreate() {
        super.onCreate();
        eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public LinkRouterService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Logger.e("onHandleIntent");

        String json=intent.getStringExtra("couponParams");
        if(TextUtils.isEmpty(json)){
            return;
        }
        Map map_param = JSON.parseObject(json);
        Logger.e(map_param.get(PARAM_ID_TYPE).toString()+"&&"+map_param.get(PARAM_COUPON_ID));




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //获取接口实例
         GoodsService goodsService = retrofit.create(GoodsService.class);
        //调用方法得到一个Call
        HashMap<String,String> map=new HashMap<>();



        map.put(PARAM_ID_TYPE,map_param.get(PARAM_ID_TYPE).toString());
        map.put(PARAM_PLATFORM,map_param.get(PARAM_PLATFORM).toString());

        if(map_param.get(PARAM_PLATFORM_ID)!=null){
            map.put(PARAM_PLATFORM_ID,map_param.get(PARAM_PLATFORM_ID).toString());
        }

        if(map_param.get(PARAM_COUPON_ID)!=null){
            map.put(PARAM_COUPON_ID,map_param.get(PARAM_COUPON_ID).toString());
        }


        if(map_param.get(PRODUCT_ID)!=null){
            map.put(PRODUCT_ID, map_param.get(PRODUCT_ID).toString());
        }






        HashMap<String,String> signed_map=HttpParamUtil.getCommonSignParamMap(this, map);

        //调用方法得到一个Call
        Call<GoodTaobaoLinkResult> call = goodsService.getTaobaoLinkIntoCallQueue(signed_map);
        //进行网络请求
        call.enqueue(new Callback<GoodTaobaoLinkResult>() {
            @Override
            public void onResponse(Call<GoodTaobaoLinkResult> call, Response<GoodTaobaoLinkResult> response) {
              Logger.e(response.body().toString());
              if(response.body().isSuccess()){
                  response.body().setPlatform(Integer.parseInt(map.get(PARAM_PLATFORM)));
                  eventBus.post(response.body());
              }



            }
            @Override
            public void onFailure(Call<GoodTaobaoLinkResult> call, Throwable t) {
                Logger.e(t.getMessage());
                LoadingDialog.getInstance(LinkRouterService.this).hide();
            }
        });




    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveLinkRouterMessage(MessageEventGlobal msg){


    }


}
