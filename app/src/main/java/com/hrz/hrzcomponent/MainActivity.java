package com.hrz.hrzcomponent;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mujirenben.android.thirdsdk.JdSdk.JdCallback;
import com.mujirenben.android.thirdsdk.JdSdk.JdSdkRouter;
import com.mujirenben.liangchenbufu.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        JdSdkRouter.openUrlToJD("xxx", this, new JdCallback() {
//            @Override
//            public void onStatus(int i) {
//
//            }
//        });
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
        new HrzLoadingWidget(this).show();

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
