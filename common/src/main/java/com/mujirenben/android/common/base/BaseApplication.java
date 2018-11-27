package com.mujirenben.android.common.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;

import com.mujirenben.android.common.base.delegate.AppDelegate;
import com.mujirenben.android.common.base.delegate.AppLifecycles;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.AppInfoUtils;
import com.mujirenben.android.common.util.AppTaskUtil;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.util.PhonePlatformUtil;
import com.mujirenben.android.common.util.Preconditions;
import com.mujirenben.android.common.util.SpUtil;

import java.io.File;

/**
 * ================================================
 * 本框架由 MVP + Dagger2 + Retrofit + RxJava + AndroidEventBus + ButterKnife 组成
 * ================================================
 */
public class BaseApplication extends Application implements App {

    public final static String PROCESS_HRZ_MAIN = "com.mujirenben.liangchenbufu";
    public final static String PROCESS_GETUI_PUSH_SERVICE = ":pushservice";

    public static String SDIMGFILE = "";
    public static double latitude = 0;
    public static double longitude = 0;
    public static final String appKey = "85c191682c36444cac39c72567201e95";
    public static final String keySecret = "ba3dc750f6ee44eaba1bce8b4732383c";
    public static String UUID;                    // 设备ID
    public static String VER_CODE = "";
    public static int mScreenH;
    public static int mScreenW;
    public static boolean isLocation = false;
    public static String TOKEN_TAG = "";
    public static String CACHE_FILE;
    public static String LOCAL_FILE;
    public static String LOCALVIDEOPATH = "";
    public static String LOCALIMAGVIEWPATH = "";
    public static String FUWU_URL = "";
    public static  boolean isGoNextActivity = false;

    /**
     * 默认Video保存路径
     */
    public static String VIDEOPATH = "";





    private AppLifecycles mAppDelegate;//生命周期管理类

    private static BaseApplication sInstance;


    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);

        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);//代理类实现
        this.mAppDelegate.attachBaseContext(base);
    }


    public void init() {
        if (mAppDelegate != null && ((AppDelegate) mAppDelegate).getApplication() == null) {
            this.mAppDelegate.onCreate(this);//同步代理类实现
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        SpUtil.saveIsMIUI(this,PhonePlatformUtil.isMIUI());
        mekeDir();
        String prorcessName = AppTaskUtil.getProcessName(getApplication());
        LogUtil.e("hrz_process", "BaseApplication process name: " + prorcessName);

        if (PROCESS_HRZ_MAIN.equals(prorcessName)) {
            LogUtil.e("hrz_process", "BaseApplication init main process");

            //设计图标注的宽度
            int designWidth = 750;
            initScreenSize(this);
            // 初始化 JPush

           //  UUID = getMyUUID();
            VER_CODE = AppInfoUtils.getVersionName(getApplicationContext());
           // TOKEN_TAG = MD5Util.getMd5Value(BaseApplication.UUID + "xiaoyu") + ";" + BaseApplication.UUID + ";android";
            Log.i("chenhong", "Contant." + TOKEN_TAG);
        }

        init();





    }

    /***
     * 初始化屏幕的尺寸
     */
    private void initScreenSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mScreenW = metrics.widthPixels;
        mScreenH = metrics.heightPixels;
    }



    public static BaseApplication getApplication() {
        return sInstance;
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    /**
     * 将 {@link AppComponent} 返回出去, 供其它地方使用, {@link AppComponent} 接口中声明的方法所返回的实例, 在 {@link #getAppComponent()} 拿到对象后都可以直接使用
     *
     * @return AppComponent
     * @see ArmsUtils#obtainAppComponentFromContext(Context) 可直接获取 {@link AppComponent}
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", AppDelegate.class.getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }


    /**
     * 创建目录
     */
    private void mekeDir() {
        File SDIMGFILE = new File(Environment.getExternalStorageDirectory() + "/hongrenzhuang");
        if (!SDIMGFILE.exists()) {
            SDIMGFILE.mkdir();
        }
        BaseApplication.SDIMGFILE = SDIMGFILE.toString();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            try {
                LOCAL_FILE = this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath().toString();
                CACHE_FILE = this.getExternalCacheDir().toString();
                if (!new File(CACHE_FILE).exists()) {
                    new File(CACHE_FILE).mkdir();
                }
                if (!new File(LOCAL_FILE).exists()) {
                    new File(LOCAL_FILE).mkdir();
                }
            } catch (Exception e) {

            }
        } else {
            try {
                LOCAL_FILE = this.getFilesDir().getPath().toString();
                CACHE_FILE = this.getCacheDir().toString();
                if (!new File(CACHE_FILE).exists()) {
                    new File(CACHE_FILE).mkdir();
                }
                if (!new File(LOCAL_FILE).exists()) {
                    new File(LOCAL_FILE).mkdir();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            VIDEOPATH = CACHE_FILE;
            if (!new File(VIDEOPATH).exists()) {
                new File(VIDEOPATH).mkdir();
            }
            LOCALVIDEOPATH = LOCAL_FILE + "/video";
            if (!new File(LOCALVIDEOPATH).exists()) {
                new File(LOCALVIDEOPATH).mkdir();
            }
            LOCALIMAGVIEWPATH = LOCAL_FILE + "/thumb";
            if (!new File(LOCALIMAGVIEWPATH).exists()) {
                new File(LOCALIMAGVIEWPATH).mkdir();
            }
        } catch (Exception e) {

        }
    }



}
