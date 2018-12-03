package com.mujirenben.android.thirdsdk.alibabaSDK;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.Keep;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcAddCartPage;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: panrongfu
 * @date: 2018/8/27 20:02
 * @describe:
 */
@Keep
public class AlibabaSDK {

    private static AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native

    private static AlibcTaokeParams alibcTaokeParams = null;//淘客参数，包括pid，unionid，subPid

    private static Map<String, String> exParams;//yhhpass参数

    /**
     * 百川初始化入口
     */
    public static void initSDK(Application application) {
        AlibcTradeSDK.asyncInit(application, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                Log.d(getClass().getSimpleName(),"初始化成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                Log.d(getClass().getSimpleName(),"初始化失败,错误码="+code+" / 错误消息="+msg);
            }
        });

        alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
        alibcTaokeParams=new AlibcTaokeParams(AlibabaConstant.TAOKE_PID,null,null);
        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
//        exParams.put("alibaba", "阿里巴巴");
        //自定义参数部分，可任意增删改
    }




    /**
     * 显示店铺
     */
    public static void showShop(Activity activity,String shopId,AlibcTradeCallback hrzTradeCallback) {
        AlibcBasePage alibcBasePage=new AlibcShopPage(shopId);
        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams , hrzTradeCallback);
    }


    /**
     * 商品详情页
     */
    public static void showGoodsDetail(Activity activity,String goodsId,AlibcTradeCallback hrzTradeCallback){
        AlibcBasePage alibcBasePage=new AlibcDetailPage(goodsId);
        AlibcTrade.show(activity,alibcBasePage, alibcShowParams, alibcTaokeParams, exParams , hrzTradeCallback);
    }

    /**
     * 添加到购物车(可行)
     */
    public static void addToCart(Activity activity,String goodsId,AlibcTradeCallback hrzTradeCallback){
        AlibcBasePage alibcBasePage=new AlibcAddCartPage(goodsId);
        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams , hrzTradeCallback);
    }



    /**
     * 打开指定链接  打开web页面
     */
    public static void routeUrl(Activity activity,String url,AlibcTradeCallback hrzTradeCallback){
        AlibcTrade.show(activity, new AlibcPage(url), alibcShowParams, null, exParams ,hrzTradeCallback);
    }


    /**
     * 回收资源
     */
    public static void destroy() {
        //调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
        AlibcTradeSDK.destory();
    }






}
