package com.mujirenben.android.thirdsdk.JdSdk;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/12.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Application;
import android.content.Context;
import android.util.Log;


import com.jd.jdsdk.R;
import com.mujirenben.android.thirdsdk.PackageUtils;

import com.kepler.jd.Listener.AsyncInitListener;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KelperTask;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;
import com.kepler.jd.sdk.exception.KeplerBufferOverflowException;

import org.json.JSONException;



/**
 * @Author CH
 */
public class JdSdkRouter {


    public static final int timeOut = 15;
    public final static String jdpgname="com.jingdong.app.mall";
    public final static String mhome = "http://m.jd.com";

    /**
     *  这个是即时性参数可以设置
     */
    public static KeplerAttachParameter mKeplerAttachParameter = new KeplerAttachParameter();
    public static KelperTask mKelperTask;



    public static void openJDHome(Context context) {
        try {
            if(PackageUtils.appIsInstalled(context,jdpgname)){
                mKelperTask = KeplerApiManager.getWebViewService().openJDUrlPage(mhome, mKeplerAttachParameter, context, null, timeOut);
            }else {
                KeplerApiManager.getWebViewService().openJDUrlWebViewPage(mhome, mKeplerAttachParameter);
            }
         } catch (KeplerBufferOverflowException e) {
              e.printStackTrace();
         } catch (JSONException e) {
              e.printStackTrace();
         }
    }

    public static void openJDGoodsDetail(Context context,String goodsId ){
        try {
            if(PackageUtils.appIsInstalled(context,jdpgname)){
                mKelperTask= KeplerApiManager.getWebViewService().openItemDetailsPage(goodsId, mKeplerAttachParameter,context,null,timeOut);
            }else {
                KeplerApiManager.getWebViewService().openItemDetailsWebViewPage(goodsId, mKeplerAttachParameter);
            }
        } catch (KeplerBufferOverflowException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开京东URL
     *
     * @param url
     */
    public static void openJDGoodsDetailUrl(String url) {
        try {
            KeplerApiManager.getWebViewService().openJDUrlWebViewPage(url, mKeplerAttachParameter);
        } catch (KeplerBufferOverflowException e) {
            e.printStackTrace();
        }
    }


    public static void init(final Application application,String appKey,String secretKey){
        KeplerApiManager.asyncInitSdk(application, appKey, secretKey,
                new AsyncInitListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("Kepler", application.getString(R.string.initjdsdksuccess));
                    }

                    @Override
                    public void onFailure() {
                        Log.e("Kepler", "Kepler asyncInitSdk 授权失败，请检查lib 工程资源引用；包名,签名证书是否和注册一致");
                    }
                });
    }

    public static void openUrlToJD(String url,Context context,OpenAppAction openAppAction){
           try {
               if(PackageUtils.appIsInstalled(context,jdpgname)) {
                   mKelperTask=KeplerApiManager.getWebViewService().openJDUrlPage(url,mKeplerAttachParameter,context,openAppAction,timeOut);
               }else {
                   KeplerApiManager.getWebViewService().openJDUrlWebViewPage(url,mKeplerAttachParameter);
               }
           } catch (KeplerBufferOverflowException e) {
               e.printStackTrace();
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }





}
