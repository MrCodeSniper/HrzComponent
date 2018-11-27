package com.mujirenben.android.common.arounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.baichuan.trade.common.utils.JSONUtils;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.util.LogUtil;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 红人装自定义路由器
 *
 * 关于路由协议具体可以参考: http://192.168.0.248:10086/pages/viewpage.action?pageId=8880758
 */
public class HrzRouter {
    private static volatile HrzRouter sInstance;
    private static Object sLock = new Object();
    private Context mContext;

    public final static String SCHEME_HRZ = "hrzrouter://";
    private final static String SCHEME_HTTP = "http://";
    private final static String SCHEME_HTTPS = "https://";
    private final static String PATH_SLASH = "/";

    private final static String HOST_HRZ = "hrzapp";

    public final static String PARAM_URL = "param_url"; // 打开的url
    public final static String PARAM_INJECT_JS = "param_inject_js"; //是否需要注入jsbridge

    public final static String PATH_WEBVIEW_NORMAL = "/app/webSearch";
    public final static String PATH_WEBVIEW_ACTIVITY = "/h5/homepage/activity";

    private HrzRouter(Context mContext) {
        this.mContext = mContext;
    }

    public static HrzRouter getsInstance(Context context) {
        if (sInstance == null||sInstance.mContext==null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new HrzRouter(context);
                }
            }
        }
        return sInstance;
    }

    public void navigation(String url) {


        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (url.startsWith(SCHEME_HTTP) ||  url.startsWith(SCHEME_HTTPS)) {


            Logger.e("路由跳转http");
            Bundle bundle=new Bundle();
            bundle.putString(Consts.PARAM_URL, url);
            bundle.putBoolean(Consts.PARAM_INJECT_JS, true);
            ARouter.getInstance().build(ARouterPaths.WEB_VIEW)
                    .withBundle(Consts.HRZ_ROUTER_BUNDLE,bundle)
                    .navigation();


        } else if (url.startsWith(SCHEME_HRZ)) {
            String host = getHostFromUrl(url);
            String path = getPathFromUrl(url);
            HashMap<String, String> paramMap = getParamMapFromUrl(url);

            LogUtil.d("hrzrouter", "host = " + host);
            LogUtil.d("hrzrouter", "path = " + path);



            if (paramMap != null) {
                LogUtil.d("hrzrouter", "paramMap = " + paramMap.toString());
            }


            if (path.equals(PATH_WEBVIEW_NORMAL) || path.equals(PATH_WEBVIEW_ACTIVITY)) {
                boolean isInjectJs = false;
                if (path.equals(PATH_WEBVIEW_ACTIVITY)) {
                    isInjectJs = true;
                }
                // 跳转到webview页面


                navigationToWebView(paramMap, isInjectJs);
            } else {
                // 跳转到原生页面
                navigationToNativeActivity(path, paramMap);
            }
        }
    }

    /**
     * 跳到原生页面
     *
     * @param path Arouter路径
     * @param paramMap Intent参数列表
     */
    private void navigationToNativeActivity(String path, HashMap<String, String> paramMap) {
        Bundle bundle = new Bundle();
        Iterator<String> keys = paramMap.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Logger.e("key:"+key+"value:"+paramMap.get(key));
            bundle.putString(key, paramMap.get(key));
        }

        ARouter.getInstance().build(path).withBundle(Consts.HRZ_ROUTER_BUNDLE,bundle).navigation(mContext);
    }

    /**
     * 跳转到webivew页面
     *
     * @param paramMap 参数列表
     * @param isInjectJs 是否需要js注入
     */
    private void navigationToWebView(HashMap<String, String> paramMap, boolean isInjectJs) {
        Logger.e("跳转网页&map:"+ JSONUtils.toJson(paramMap));

        String routeUrl=paramMap.get("url");
        String urlTitleName=paramMap.get("titleName");
        String webType = paramMap.get(Consts.WEB_TYPE_KEY);
        String qrCodePrice = paramMap.get(Consts.QR_CODE_PRICE_KEY);
        String qrCodeContent = paramMap.get(Consts.QR_CODE_CONTENT_KEY);
        //Logger.e(urlTitleName);
        if(TextUtils.isEmpty(routeUrl)){
            return;
        }else {
            Bundle bundle = new Bundle();
            if(!TextUtils.isEmpty(urlTitleName)){
                bundle.putString(Consts.WEB_TITLE_TAG,urlTitleName);
            }else {
                bundle.putString(Consts.WEB_TITLE_TAG,"");
            }
            if(!TextUtils.isEmpty(webType)){
                bundle.putString(Consts.WEB_TYPE_KEY, webType);
            }
            if(!TextUtils.isEmpty(qrCodePrice)){
                bundle.putString(Consts.QR_CODE_PRICE_KEY,qrCodePrice);

            }
            if(!TextUtils.isEmpty(qrCodeContent)){
                bundle.putString(Consts.QR_CODE_CONTENT_KEY,qrCodeContent);
            }
            bundle.putString(Consts.PARAM_URL, routeUrl);
            bundle.putBoolean(Consts.PARAM_INJECT_JS, isInjectJs);
            ARouter.getInstance()
                    .build(ARouterPaths.WEB_VIEW)
                    .withBundle(Consts.HRZ_ROUTER_BUNDLE,bundle)
                    .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .navigation(mContext);
        }


    }

    /**
     * 获取自定义协议中的HOST配置
     *
     * @param url
     * @return
     */
    private String getHostFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        int startIndex = SCHEME_HRZ.length();
        int lastIndex = startIndex + url.substring(startIndex).indexOf(PATH_SLASH);
        if (lastIndex != -1) {
            String hostStr = url.substring(startIndex, lastIndex);
            return hostStr;
        }

        return null;
    }

    /**
     * 获取参数列表
     *
     * @param url
     * @return
     */
    private HashMap<String, String> getParamMapFromUrl(String url) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        if (TextUtils.isEmpty(url)) {
            return paramMap;
        }

        int startIndex = -1;
        if (url.contains("?")) {
            startIndex = url.indexOf("?") + 1;
        }

        if (url.length() <= startIndex) {
            return paramMap;
        }

        if (startIndex != -1) {
            String paramStr = url.substring(startIndex);
            String[] paramArray = paramStr.split("&");
// 如果url在运营后台使用了urlencode，则不需要对参数进行特别处理
//            if (paramStr.startsWith("url")) { // 如果是跳web的，url后边参数不再解析
//                paramArray = new String[1];
//                paramArray[0] = paramStr;
//            }

            for (int i = 0; i < paramArray.length; i++) {
                String param = paramArray[i];
                int index = param.indexOf("=");
                if (index != -1) {
                    String paramName = param.substring(0, index);
                    String paramValue = param.substring(index + 1);
                    try {
                        paramMap.put(paramName, URLDecoder.decode(paramValue, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Logger.e(e.getMessage());
                    }
                }
            }
        }

        return paramMap;
    }

    /**
     * 获取路由路径path
     *
     * @param url
     * @return
     */
    private String getPathFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        int hostIndex = url.indexOf(HOST_HRZ);
        int startIndex = hostIndex + HOST_HRZ.length();

        int lastIndex = -1;
        if (url.contains("?")) {
            lastIndex = url.indexOf("?");
        }
        if (lastIndex != -1) {
            return url.substring(startIndex, lastIndex);
        } else {
            return url.substring(startIndex);
        }
    }

}
