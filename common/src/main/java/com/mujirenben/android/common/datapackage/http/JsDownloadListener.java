package com.mujirenben.android.common.datapackage.http;

/**
 * Created by mac on 2018/8/26.
 */

public interface JsDownloadListener {
    void onStartDownload(long length);
    void onProgress(int progress);
    void onFail(String errorInfo);
}
