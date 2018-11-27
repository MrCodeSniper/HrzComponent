package com.mujirenben.android.common.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

public class BundlerResourceLoader {

    private static AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            try {
                AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                        assetManager, apkPath);
            } catch (Throwable th) {
                System.out.println("debug:createAssetManager :"+th.getMessage());
                th.printStackTrace();
            }
            return assetManager;
        } catch (Throwable th) {
            System.out.println("debug:createAssetManager :"+th.getMessage());
            th.printStackTrace();
        }
        return null;
    }


    public static Resources getBundleResource(Context context, String apkPath){
        AssetManager assetManager = createAssetManager(apkPath);
        return new Resources(assetManager,
                context.getResources().getDisplayMetrics(),
                context.getResources().getConfiguration());
    }


}