package com.mujirenben.android.common.datapackage.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by mac on 2018/8/26.
 */

public class JsDownloadInterceptor implements Interceptor{
    private JsDownloadListener downloadListener;

    public JsDownloadInterceptor(JsDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new JsResponseBody(response.body(), downloadListener)).build();
    }
}
