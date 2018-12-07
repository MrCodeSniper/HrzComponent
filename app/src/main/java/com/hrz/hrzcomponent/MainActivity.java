package com.hrz.hrzcomponent;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.liangchenbufu.R;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;


public class MainActivity extends AppCompatActivity {

    private static final String API_BASE_URL = "http://39.104.138.184:8080/";
    private HrzLoadingWidget hrzLoadingWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hrzLoadingWidget=new HrzLoadingWidget(this);

        RetrofitUrlManager.getInstance().setGlobalDomain(API_BASE_URL);
        
    }

    public void goMain() {
//        ComponentName componentName = new ComponentName("com.mujirenben.liangchenbufu",
//                "com.mujirenben.liangchenbufu.activity.MainActivity");
        Intent intent = new Intent();
        intent.setClassName("com.mujirenben.liangchenbufu",
                "com.mujirenben.liangchenbufu.MainActivity");
        intent.setAction("android.intent.action.MAIN");
        intent.putExtra("FromXDZ",true);
//        intent.setAction("android.intent.action.MAIN");
//        intent.putExtra("FromXDZ", true);
        // intent.putExtra("com.mujirenben.videoDetailActivity.linkurl",
        // "http://yangjie.tlgn365.com/hrz_zhifubao/index.html");
        // intent.putExtra("IsActivity", true);
//        intent.setComponent(componentName);
        startActivity(intent);
    }

    public void test(View view){
//        goMain();
//        new HrzLoadingWidget(this).show();

        hrzLoadingWidget.show();
        Map smsMap=new HashMap();
        smsMap.put("telephone","17620752830");
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager()
                .obtainRetrofitService(TestService.class)
                .getSmsCode(smsMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity o) throws Exception {
                        Log.e("chenhong",o.toString());
                        hrzLoadingWidget.cancel();
                    }
                });

    }

//    public void testTbRoute(View view){
//        //测试阿里
//        String goodsUrl="https://item.taobao.com/item.htm?spm=a1z10.1-c-s.w5003-18776842796.2.7dee96f95PenTf&id=574499704385&scene=taobao_shop";
//        AlibabaSDK.routeUrl(this, goodsUrl, new HrzTradeCallback() {
//            @Override
//            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
//    }
//
//
//    public void  testTbGoodsDetail (View view){
//        AlibabaSDK.showGoodsDetail(this, "522166121586", new HrzTradeCallback() {
//            @Override
//            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
//    }
//
//
//    public void  testTbShop (View view){
//        AlibabaSDK.showShop(this, "60552065", new HrzTradeCallback() {
//            @Override
//            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        AlibabaSDK.destroy();
//        super.onDestroy();
//    }
}
