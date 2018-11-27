package com.hrz.push;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/11/20.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Application;

import com.igexin.sdk.PushManager;

public  class PushAgent {

    public static void init(Application application, HrzPushIntentService hrzPushIntentService){
        PushManager.getInstance().initialize(application, HrzPushService.class);
        PushManager.getInstance().registerPushIntentService(application, hrzPushIntentService.getClass());
    }


}
