package com.hrz.hrzcomponent;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/11/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Application;
import android.content.Context;
import android.util.Log;


import com.hrz.push.HrzPushIntentService;
import com.hrz.push.PushAgent;
import com.igexin.sdk.message.GTTransmitMessage;
import com.mujirenben.android.common.pay.PayHelper;

import java.io.UnsupportedEncodingException;

public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        AlibabaSDK.initSDK(this);
        PushAgent.init(this, new HrzPushIntentService() {
            @Override
            public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
                try {
                    String msg=new String(gtTransmitMessage.getPayload(),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}
