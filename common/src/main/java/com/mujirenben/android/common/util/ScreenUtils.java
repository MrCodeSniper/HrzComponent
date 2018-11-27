package com.mujirenben.android.common.util;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/2.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebView;

public class ScreenUtils {
    public static final int MODE_ADAPT_TWO_SIDE = 1;
    public static final int MODE_FORCE_ADAPT_SHORT_SIDE = 2;
    public static final int MODE_FORCE_ADAPT_LONG_SIDE = 3;
    private static int screenHeightWithPx = 0;
    private static int screenWidthWithPx = 0;
    private static float systemDensityRatio = 0;
    private static float adaptDensityRatio = 0;
    private static DisplayMetrics displayMetrics;

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getWidth() {
        return screenWidthWithPx;
    }


    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getHeight() {
        return screenHeightWithPx;
    }









    public static void adaptScreen(final Activity activity,
                                    final float sizeInDp,
                                    final boolean isVerticalSlide) {
        final DisplayMetrics appDm = activity.getApplication().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / sizeInDp;
        } else {
            activityDm.density = activityDm.heightPixels / sizeInDp;
        }
        activityDm.scaledDensity = activityDm.density * (appDm.scaledDensity / appDm.density);
        activityDm.densityDpi = (int) (160 * activityDm.density);
    }











    private static boolean webViewHasDestroyed = false;

    /**
     * 最小宽度适配 phone、tablet、tv；
     * 1.实现了与设计稿不同比例设备的兼容; e.g. 设计比例：16：9  设备比例 4：3  按照16:9显示 但是直接造成缺陷：不能充分使用屏幕像素;
     * 2.支持minSdkVersion>=17;
     *
     * @param application {@link Application or it's subClass}
     */
    public static void adaptDensity(final Application application, final int shortSideLengthWidthDp, final int longSideLengthWithDp, final boolean isSetFontSizeToDefault, final int adaptMode) {
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration config) {
                Resources resources = application.getResources();
                updateConfig(application, resources, isSetFontSizeToDefault);
            }

            @Override
            public void onLowMemory() {

            }
        });
        if (Build.VERSION.SDK_INT >= 26) {

            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    Resources resources = activity.getResources();
                    updateConfig(activity, resources, isSetFontSizeToDefault);
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            });
        }

        //设计稿宽高比
        float aspectRatio = longSideLengthWithDp / (float) shortSideLengthWidthDp;

        Resources resources = application.getResources();
        Configuration configuration = resources.getConfiguration();

        displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);

        //屏幕物理高 px
        screenHeightWithPx = displayMetrics.heightPixels;
        //屏幕物理宽 px
        screenWidthWithPx = displayMetrics.widthPixels;
        com.orhanobut.logger.Logger.d("adaptDensity", "screenHeightWithPx =" + screenHeightWithPx);
        com.orhanobut.logger.Logger.d("adaptDensity", "screenWidthWithPx =" + screenWidthWithPx);

        //屏幕最小宽度 px
        int screenSwWithPx;
        //屏幕长边 px
        int screenLongSideLengthWithPx;

        if (screenHeightWithPx > screenWidthWithPx) {
            screenSwWithPx = screenWidthWithPx;
            screenLongSideLengthWithPx = screenHeightWithPx;
        } else {
            screenSwWithPx = screenHeightWithPx;
            screenLongSideLengthWithPx = screenWidthWithPx;
        }

        com.orhanobut.logger.Logger.d("adaptDensity", "screenSwWithPx =" + screenSwWithPx);
        com.orhanobut.logger.Logger.d("adaptDensity", "screenLongSideLengthWithPx =" + screenLongSideLengthWithPx);

        //屏幕最小宽度 dp
        int screenSwWithDp = configuration.smallestScreenWidthDp;
        //屏幕密度比
        systemDensityRatio = displayMetrics.density;

        com.orhanobut.logger.Logger.d("adaptDensity", "screenSwWithDp =" + screenSwWithDp);
        com.orhanobut.logger.Logger.d("adaptDensity", "systemDensityRatio =" + systemDensityRatio);

        int adaptSwWithDp;
        int adaptLongSideLengthWithDp;

        adaptSwWithDp = screenSwWithDp;
        adaptLongSideLengthWithDp = (int) (adaptSwWithDp * aspectRatio);

        com.orhanobut.logger.Logger.d("adaptDensity", "adaptSwWithDp =" + adaptSwWithDp);
        com.orhanobut.logger.Logger.d("adaptDensity", "adaptLongSideLengthWithDp =" + adaptLongSideLengthWithDp);


        if (adaptMode == MODE_ADAPT_TWO_SIDE) {
            float ratio1 = systemDensityRatio;
            float ratio2 = screenLongSideLengthWithPx / (float) adaptLongSideLengthWithDp;
            adaptDensityRatio = ratio1 > ratio2 ? ratio2 : ratio1;
        } else if (adaptMode == MODE_FORCE_ADAPT_SHORT_SIDE) {
            adaptDensityRatio = systemDensityRatio;
        } else if (adaptMode == MODE_FORCE_ADAPT_LONG_SIDE) {
            adaptDensityRatio = screenLongSideLengthWithPx / (float) adaptLongSideLengthWithDp;
        }

        adaptDensityRatio *= screenSwWithDp / (float) shortSideLengthWidthDp;

        com.orhanobut.logger.Logger.e("adaptDensity", "update systemDensityRatio " + systemDensityRatio + " ==> " + adaptDensityRatio);

        updateConfig(application, resources, isSetFontSizeToDefault);
    }

    private static void destroyWebView(Context context) {
        try {//Caused by android.webkit.WebViewFactory$MissingWebViewPackageException
            new WebView(context).destroy();//see https://stackoverflow.com/questions/40398528/android-webview-language-changes-abruptly-on-android-n
            webViewHasDestroyed = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateConfig(Context context, Resources resources, boolean isSetFontSizeToDefault) {
        if (!webViewHasDestroyed) {
            destroyWebView(context);
        }
        Configuration newConfig = resources.getConfiguration();
        newConfig.densityDpi = (int) (adaptDensityRatio * DisplayMetrics.DENSITY_DEFAULT);
        if (isSetFontSizeToDefault) {
            newConfig.fontScale = 1;
        }
        resources.updateConfiguration(newConfig, displayMetrics);
    }


    /**
     * dip2px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px2dip
     *
     * @param context
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px2sp
     *
     * @param context
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp2px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(null).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
            statusHeight = recoverToSystemValueIfNeed(statusHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private static int recoverToSystemValueIfNeed(int valueWithPx) {
        if (systemDensityRatio != adaptDensityRatio) {
            valueWithPx = (int) (valueWithPx / adaptDensityRatio * systemDensityRatio);
        }
        return valueWithPx;
    }

}
