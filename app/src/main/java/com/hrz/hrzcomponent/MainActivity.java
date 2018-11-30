package com.hrz.hrzcomponent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mujirenben.liangchenbufu.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
