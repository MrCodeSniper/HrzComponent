package com.mujirenben.android.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ch.android.common.R;

import java.lang.reflect.Field;

import cn.albert.autosystembar.SystemBarHelper;


public class StatusBarUtil {

    public static void setStatusBarWhite(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(context.getResources().getColor(R.color.white));
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    public static SystemBarHelper.Builder getStatusBarHelper(Context context, int color){
       return  new SystemBarHelper.Builder()
                .statusBarColor(ArmsUtils.getColor(context,color))    // 设置状态栏颜色
                .statusBarFontStyle(SystemBarHelper.STATUS_BAR_DARK_FONT_STYLE)  // 设置状态栏时间,电量的风格, 6.0以上, 部分国产机可以不用6.0以上.
                .navigationBarColor(ArmsUtils.getColor(context,color))  // 设置导航栏颜色
                .enableImmersedStatusBar(false)  // 布局嵌入状态栏，例如图片嵌入状态栏
                .enableImmersedNavigationBar(false)  // 布局嵌入导航栏，例如图片嵌入导航栏
                .enableAutoSystemBar(true);// 根据状态栏下面的背景颜色自动调整状态栏的颜色, 自动调整状态栏时间,电量的风格, 默认是开启的
    }


    public static void setStatusBarColor(Activity context,int color){
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            window.setStatusBarColor(context.getResources().getColor(color));
        }
    }



    public static void setStatusBarTransparent(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }


    public static int getStatusBarHeight(Context context){

        Class<?> c = null;
        Object obj = null;
        Field field = null;

        int normalSize = (int)(24 * context.getResources().getDisplayMetrics().density);
        int x = 0, statusBarHeight = normalSize;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            if (statusBarHeight < normalSize) {
                statusBarHeight = normalSize;
            }
            Log.i("jan4", "statusBarHeight = " + statusBarHeight);
        } catch (Exception e1) {
            e1.printStackTrace();
            Log.i("jan4", "error");
        }
        return statusBarHeight;
    }


}
