package com.hrz.poplayer.impl;

import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hrz.poplayer.config.HrzWebViewSecurity;
import com.hrz.poplayer.interfaces.WebViewConfig;
import com.hrz.poplayer.log.Environment;
import com.hrz.poplayer.log.Logs;
import com.hrz.poplayer.log.Order;


/**
 * Created by mac on 2018/7/11.
 */

public  class BaseWebConfigImpl implements WebViewConfig,Order {
    static final String TAG="BaseWebConfigImpl";

    private Logs logs;

     public BaseWebConfigImpl(Logs logs) {
        this.logs = logs;
    }

    public BaseWebConfigImpl() {
    }

    @Override
    public void execute(Environment environment) {
        logs.logE("BaseWebConfigImpl");
        environment.setOrder(this);
    }

    ///////////////////////////////////////配置WEBVIEW 待后续继续优化 参考网易考拉团队webview优化////////////////////////////////

    @Override
    public void setUpWebConfig(WebView webView, String showScheme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        webView.setBackgroundColor(0);
        webView.getBackground().setAlpha(0);
        //允许js代码
        //在Android 4.3版本调用WebSettings.setJavaScriptEnabled()方法时会调用一下reload方法，同时会回调多次WebChromeClient.onJsPrompt()
        settings.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        settings.setDomStorageEnabled(true);
        //禁用放缩
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(false);
        //禁用文字缩放
        settings.setTextZoom(100);
        //10M缓存，api 18后，系统自动管理。
        settings.setAppCacheMaxSize(10 * 1024 * 1024);
        //允许缓存，设置缓存位置
        settings.setAppCacheEnabled(true);
//        settings.setAppCachePath(context.getDir("appcache", 0).getPath());
        //允许WebView使用File协议
        settings.setAllowFileAccess(true);
        //不保存密码
        settings.setSavePassword(false);
        //移除部分系统JavaScript接口 涉及安全问题
        HrzWebViewSecurity.removeJavascriptInterfaces(webView);
        //自动加载图片
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.loadUrl(showScheme);
//        webView.addJavascriptInterface(new Object() {
//            @JavascriptInterface
//            public void hidePopLayer() {
//                webView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e("chnehong","xxx");
//                        webView.setVisibility(GONE);
//                    }
//                });
//            }
//        }, "poplayer");
        setUpJsBridge(webView);
    }


   public  void setUpJsBridge(WebView webView){

   };

    @Override
    public String toString() {
        return "WebConfig State";
    }





}
