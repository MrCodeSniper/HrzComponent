package com.mujirenben.android.xsh.service;

import android.content.Context;

import com.mujirenben.android.common.constants.Consts;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Retrofit单例
 * 对于网络返回状态需要再次封装一次，这里暂时没做，开始没有约定好移动端和后台之间的错误码。
 * 这里只对调用封装下
 * Created by Administrator on 2017/6/20 0020.
 */
public class RetrofitSingle {

    private static RetrofitSingle retrofitSingle;
    public Retrofit retrofit;
    private static Context mContext;

    private RetrofitSingle(Context mContext){
        this.mContext=mContext;
        OkHttpClient okHttpClient=null;

        try {
            Cache cache = new Cache(mContext.getCacheDir(), 10 * 1024 * 1024);//设置OKHTTP缓存的大小
             okHttpClient= new OkHttpClient.Builder()
                    .addInterceptor(setRequestHeader())         //添加头拦截器
                    .connectTimeout(20, TimeUnit.SECONDS)       //连接超时时间
                    .readTimeout(20, TimeUnit.SECONDS)           //读超时时间
                    .writeTimeout(20, TimeUnit.SECONDS)           //写超时时间
                    .cache(cache)
                    .build();

            retrofit=new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(Consts.PROTOCROL+Consts.OFFICIAL_SERVER)
                    .build();
        } catch (Exception e) {
            okHttpClient.cache();
        }
    }

    public static RetrofitSingle getInstance(Context mContext){
        if(retrofitSingle==null){
            //使用全局的上下文
            retrofitSingle=new RetrofitSingle(mContext.getApplicationContext());
        }

        return retrofitSingle;
    }


    /**
     * 设置请求头拦截器
     * @return
     */
    private Interceptor setRequestHeader(){

        Interceptor interceptor= null;
        try {
            interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()

                            .addHeader("Content-Type", "application/json;charset=UTF-8").build();

                    return chain.proceed(request);
                }
            };
        } catch (Exception e) {

        }
        return interceptor;
    }


//    /**
//     * 打印日志
//     * @return
//     */
//    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        return loggingInterceptor;
//    }

    /**
     * 缓存策略   缓存只缓存GET请求，目前都为Post,暂时不考虑
     * @return
     */
//    public static Interceptor getCacheInterceptor() {
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                if (!NetUtil.isNetworkConnected(mContext)) {
//                    //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。
//                    request=request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
//                            .build();
//                }
//
//                Response response = chain.proceed(request);
//                if (NetUtil.isNetworkConnected(mContext)) {//有网络情况下，根据请求接口的设置，配置缓存。
//                    //这样在下次请求时，根据缓存决定是否真正发出请求。
//                    String cacheControl = request.cacheControl().toString();
//                    //当然如果你想在有网络的情况下都直接走网络，那么只需要
//                    //将其超时时间这是为0即可:String cacheControl="Cache-Control:public,max-age=0"
//                    return response.newBuilder().header("Cache-Control", cacheControl)
//                            .removeHeader("Pragma")
//                            .build();
//                }else{//无网络
//                    return response.newBuilder().header("Cache-Control", "public,only-if-cached,max-stale=360000")
//                            .removeHeader("Pragma")
//                            .build();
//                }
//            }
//
//        };
//    }






}
