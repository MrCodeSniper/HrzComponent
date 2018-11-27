package com.mujirenben.android.mine.mvp.ui.fragment;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


import com.hrz.poplayer.impl.BaseWebConfigImpl;

import static android.view.View.GONE;

/**
 * 可以自己实现一个WebConfigImpl 也可以重写base的
 * Created by mac on 2018/7/11.
 */

public class WebConfigImplByUser extends BaseWebConfigImpl {
    @Override
    public void setUpJsBridge(WebView webView) {
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void hidePopLayer() {
                webView.post(() -> {
                    Log.e("chnehong","xxx");
                    webView.setVisibility(GONE);
                });
            }
        }, "poplayer");
    }
}
