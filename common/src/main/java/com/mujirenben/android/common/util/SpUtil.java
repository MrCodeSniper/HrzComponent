package com.mujirenben.android.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;



import java.util.Map;

/**
 * author：panRongFu on 2016/9/20
 * email: love_gec@163.com
 * describe:sp存储工具类
 */
public class SpUtil {


    public static final String NAME="HRZ_SP";
    public static final String MIUI="MIUI";
//    public static final String LOGIN_STATE="isUserLogin";


    public static void saveIsMIUI(Context context,boolean isMIUI){
       SharedPreferences.Editor editor =  context.getSharedPreferences(NAME,Activity.MODE_PRIVATE).edit();
       editor.putBoolean(MIUI,isMIUI).apply();
    }

    public static boolean getIsMIUI(Context context){
       return context.getSharedPreferences(NAME,Context.MODE_PRIVATE).getBoolean(MIUI,false);
    }


    /**
     * 指定sp保存
     * @param name
     * @param data
     */
    public static void save(String name, Map<String, Object> data,Context context) {
        String sputilName = NAME;//默认的名字是BIDE_NAME
        if(!TextUtils.isEmpty(name)){//如果传的name不为空，就是用name
            sputilName = name;
        }
        //实例化SharedPreferences对象（第一步）
        SharedPreferences sharedPreferences = context.getSharedPreferences(sputilName, Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //用putString的方法保存数据

        for (String key : data.keySet()) {
            Object value = data.get(key);
            if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof String) {
                editor.putString(key,(String) value);
            } else if (value instanceof Float) {
                editor.putFloat(key,(Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key,(Long) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if(null == value){
                editor.putString(key,null);
            }
        }
        //提交当前数据
        editor.commit();

    }



    /**
     * 指定sp文件获取
     * @param key
     * @return
     */
    public static String getStringValue(String name,String key,Context context) {
        String sputilName = NAME;//默认的名字是BIDE_NAME
        if(!TextUtils.isEmpty(name)){//如果传的name不为空，就是用name 去查询
            sputilName = name;
        }
        SharedPreferences sp = context.getSharedPreferences(sputilName, Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String value = sp.getString(key, "");
        return value;
    }


    /**
     * 指定sp获取整型数据
     * @param name
     * @param key
     * @return
     */
    public static int getIntValue(String name,String key,Context context){
        String sputilName = NAME;//默认的名字是BIDE_NAME
        if(!TextUtils.isEmpty(name)){//如果传的name不为空，就是用name 去查询
            sputilName = name;
        }
        SharedPreferences sp = context.getSharedPreferences(sputilName, Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        int value = sp.getInt(key, 0);
        return value;
    }


    /**
     * 指定sp获取长整型数据
     * @param name
     * @param key
     * @return
     */
    public static long getLongValue(String name,String key,Context context){

        String sputilName = NAME;//默认的名字是BIDE_NAME
        if(!TextUtils.isEmpty(name)){//如果传的name不为空，就是用name 去查询
            sputilName = name;
        }
        SharedPreferences sp = context.getSharedPreferences(sputilName, Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        long value = sp.getLong(key, 0);
        return value;
    }


    /**
     * 指定sp获取boolean型数据
     * @param name
     * @param key
     * @return
     */
    public static boolean getBoolenValue(String name,String key,Context context){
        String sputilName =NAME;//默认的名字是BIDE_NAME
        if(!TextUtils.isEmpty(name)){//如果传的name不为空，就是用name 去查询
            sputilName = name;
        }
        SharedPreferences sp = context.getSharedPreferences(sputilName, Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        boolean value = sp.getBoolean(key, false);
        return value;
    }


    /**
     * 指定sp获取浮点型数据
     * @param name
     * @param key
     * @return
     */
    public static float getFloatValue(String name,String key,Context context){
        String sputilName = NAME;//默认的名字是BIDE_NAME
        if(!TextUtils.isEmpty(name)){//如果传的name不为空，就是用name 去查询
            sputilName = name;
        }
        SharedPreferences sp = context.getSharedPreferences(sputilName, Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        float value = sp.getFloat(key, 0.0f);
        return value;
    }





    /**
     * 清除指定sp数据
     * @param fileName
     */
    public void clearXmlDatas(String fileName,Context context){
        String sputilName = NAME;//默认的名字是BIDE_NAME
        if(!TextUtils.isEmpty(fileName)){//如果传的name不为空，就是用name 去查询
            sputilName = fileName;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(sputilName, Context.MODE_WORLD_WRITEABLE);
        sharedPreferences.edit().clear().commit();
    }
}
