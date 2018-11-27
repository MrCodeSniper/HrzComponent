package com.mujirenben.android.xsh.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.mujirenben.android.xsh.entity.BaseData;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.entity.NewBaseDate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class NetServiceUtils {

    public static final String CONNECTION_URI = "https://dev-api.hongrenzhuang.com/v1.1/";
    public static String REQUEST_URL = "http://apiv2.tlgn365.com/v1/";
    public static String TestREQUEST_URL = "http://xiao.tlgn365.com/applet.php/joint/";
    public static String TestREQUEST_URL_NEW = "http://result.eolinker.com/DpiwlLD54dae702cd8d6b24fe66a83025e597ccdcb28855?uri=host:port/v1/";
    public static final String REQUEST_NEW_URL = "http://47.106.21.23:8081";

    public static NetServiceUtils getInstance() {
        return new NetServiceUtils();
    }

    /**
     * 获取Observable<T></>
     *
     * @param serviceCls service class</>
     * @param <T>        Service实体
     * @return
     */
    public static <T> T getService(@NonNull final Class<T> serviceCls) {
        return getRetrofit().create(serviceCls);
    }

    /**
     * 老接口获取observable
     *
     * @return
     */
    public static <T> T getOldService(@NonNull final Class<T> serviceCls) {
        return getRetrofitold().create(serviceCls);
    }

    /**
     * 测试获取observable
     *
     * @return
     */
    public static <T> T getTestService(@NonNull final Class<T> serviceCls) {
        return getTestRetrofitold().create(serviceCls);
    }

    /**
     * 测试获取observable
     *
     * @return
     */
    public static <T> T getTestServiceNew(@NonNull final Class<T> serviceCls) {
        return getTestRetrofitoldNew(TestREQUEST_URL_NEW).create(serviceCls);
    }

    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)       //连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)           //读超时时间
                .writeTimeout(20, TimeUnit.SECONDS)           //写超时时间
        .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json;charset=UTF-8").build();

                return chain.proceed(request);
            }
        }).addInterceptor(new MyIntercepter()).build();
        return httpClient;
    }

    public static OkHttpClient genericClientOld() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)       //连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)           //读超时时间
                .writeTimeout(20, TimeUnit.SECONDS)           //写超时时间
                .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                final Matcher m = p.matcher(BaseApplication.VER_CODE);
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json;charset=UTF-8")
                        .addHeader("token", "77cbc257e66302866cf6191754c0c8e3")
                        .addHeader("uuid", "42DAD247-B11B-44D5-92ED-E93FA4F1E002")
                        .header("version", m.replaceAll("").trim()).build();

               // Logger.e("token"+BaseApplication.TOKEN_TAG+"uuid"+BaseApplication.UUID+"version"+ m.replaceAll("").trim());
                return chain.proceed(request);
            }
        }).addInterceptor(new MyIntercepter()).build();
        return httpClient;
    }

    /**
     * 配置Retrofit并获取Retrofit对象
     *
     * @return
     */
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(CONNECTION_URI)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))// 解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(genericClient()).build();
    }



    public static Retrofit getRetrofitNew() {
        return new Retrofit.Builder()
                .baseUrl(Consts.PROTOCROL + Consts.OFFICIAL_SERVER)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))// 解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(genericClient()).build();
    }

    public static Retrofit getRetrofitold() {
        return new Retrofit.Builder().baseUrl(REQUEST_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))// 解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(genericClientOld()).build();
    }




    public static Retrofit getTestRetrofitold() {
        return new Retrofit.Builder().baseUrl(TestREQUEST_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))// 测试解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(genericClientOld()).build();
    }

    public static Retrofit getTestRetrofitoldNew(@Url String url) {
        return new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(new Gson()))// 测试解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(genericClientOld()).build();
    }

    public static Retrofit getRetrofit(@NonNull String Url) {
        return new Retrofit.Builder().baseUrl(Url).addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    /**
     * @param observable
     * @param dataHandlers
     * @param subscribe
     */
    public void invoke(@NonNull final Observable<BaseData> observable, @NonNull final Action1<Object> dataHandlers,
                       @NonNull final Subscriber<Object> subscribe, @NonNull final TypeReference callCls) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).filter(baseData -> {
            LogUtils.e("返回值： " + JSON.toJSONString(baseData));
            if (baseData.getStatus() != 200) {// 如果返回状态为false，则输出失败原因并结束
                subscribe.onError(new Exception(baseData.getReason() + ""));
                LogUtils.e(baseData.getStatus() + "invoke");
                subscribe.onCompleted();
                return false;
            }
            return true;
        }).map(new Func1<BaseData, Object>() {
            @Override
            public Object call(BaseData baseData) {
                String data = JSON.toJSONString(baseData.getData());
                Object object = null;
                if (!TextUtils.isEmpty(data)) {
                    try {
                        object = String.class.getSimpleName().equals(callCls.getClass().getSimpleName()) ? data
                                : JSON.parseObject(data, callCls);
                    } catch (Exception e) {
                        LogUtils.e("解析错误===================Exception=========================" + e.getMessage());
                    }
                }
                return object;
            }
        }).doOnNext(dataHandlers)// 数据二次加工处理
                .subscribe(subscribe);// 请求回调
    }

    public void invokeNew(@NonNull final Observable<NewBaseDate> observable,
                          @NonNull final Action1<Object> dataHandlers, @NonNull final Subscriber<Object> subscribe,
                          @NonNull final TypeReference callCls) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).filter(baseData -> {
            LogUtils.e("返回值： " + JSON.toJSONString(baseData));
            if (baseData.getServerNo() != 200) {// 如果返回状态为false，则输出失败原因并结束
                subscribe.onError(new Exception(baseData.getServerMsg() + ""));
                LogUtils.e(baseData.getServerNo() + "invoke");
                subscribe.onCompleted();
                return false;
            }
            return true;
        }).map(new Func1<NewBaseDate, Object>() {
            @Override
            public Object call(NewBaseDate baseData) {
                String data = JSON.toJSONString(baseData.getServerData());
                Object object = null;
                if (!TextUtils.isEmpty(data)) {
                    try {
                        object = String.class.getSimpleName().equals(callCls.getClass().getSimpleName()) ? data
                                : JSON.parseObject(data, callCls);
                    } catch (Exception e) {
                        LogUtils.e("解析错误===================Exception=========================" + e.getMessage());
                    }
                }
                return object;
            }
        }).doOnNext(dataHandlers)// 数据二次加工处理
                .subscribe(subscribe);// 请求回调
    }
}
