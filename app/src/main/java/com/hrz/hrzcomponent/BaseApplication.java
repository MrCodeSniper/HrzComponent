package com.hrz.hrzcomponent;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/11/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Application;
import android.content.Context;
import android.util.Log;




import java.io.UnsupportedEncodingException;

public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
//            @Override
//            public void onSuccess() {
//                //初始化成功，设置相关的全局配置参数
//                Log.e("xxx","初始化成功");
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
//                Log.e("xxx","初始化失败,错误码="+code+" / 错误消息="+msg);
//            }
//        });
//        AlibabaSDK.initSDK(this);
//        PushAgent.init(this, new HrzPushIntentService() {
//            @Override
//            public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
//                try {
//                    String msg=new String(gtTransmitMessage.getPayload(),"utf-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });



    }
}
