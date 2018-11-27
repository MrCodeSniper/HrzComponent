package com.mujirenben.android.common.util.throwable;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.mujirenben.android.common.constants.Consts;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import timber.log.Timber;

/**
 * @author: panrongfu
 * @date: 2018/7/20 10:17
 * @describe: 统一处理请求异常工具类
 */

public class ThrowableUtil {

    public static void handleThrowable(Throwable t,HandlerCallback callback){
        Log.e("ThrowableUtil",t.getMessage()+"");
        if (t instanceof HttpException){
            handleDataThrowable(t,callback);
        }else if (t instanceof JsonParseException|| t instanceof ParseException || t instanceof JSONException|| t instanceof JsonIOException) {
            String msg = Consts.ERROR_STR.DATA_PARSE_ERROR;
            callback.dataException(msg);
        }else {
            handleNetworkThrowable(t,callback);
        }
    }

    /**
     * 网络异常处理
     * @param t
     * @param callback
     */
    private static void handleNetworkThrowable(Throwable t, HandlerCallback callback){
        Timber.tag("Catch-Error").w(t.getMessage());
        //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
        String msg = Consts.ERROR_STR.NETWORK_PROBLEM;
        if (t instanceof UnknownHostException) {
            msg = Consts.ERROR_STR.NETWORK_UNENABLE;
        } else if (t instanceof SocketTimeoutException) {
            msg = Consts.ERROR_STR.NETWORK_TIMEOUT;
        } else if(t instanceof ConnectException){
            msg = Consts.ERROR_STR.NETWORK_CONNECTION_EXCEPTION;
        }
        if(callback != null){
            callback.networkException(msg);
        }
    }

    /**
     * 数据异常
     * @param t
     * @param callback
     */
    private static void handleDataThrowable(Throwable t, HandlerCallback callback) {
        HttpException httpException = (HttpException) t;
        Logger.e("处理错误中2");
        String msg = Consts.ERROR_STR.DATA_GET_ERROR;
        if (httpException.code() == 500) {
            msg = Consts.ERROR_STR.SERVER_ERROR;
        } else if (httpException.code() == 404) {
            msg = Consts.ERROR_STR.REQUEST_ADDRESS_NOTEXIST;
        } else if (httpException.code() == 403) {
            msg = Consts.ERROR_STR.REQUEST_REJECT_BY_SERVER;
        } else if (httpException.code() == 307) {
            msg = Consts.ERROR_STR.REQUEST_REDIRECT;
        }
        if(callback != null){
            callback.networkException(msg);
        }
    }
}


