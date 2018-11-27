package com.mujirenben.android.common.datapackage.resource;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.widget.dialog.CommonConfig;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *  简单封装sp处理
 * Created by admin on 2017/3/10.
 */
@Singleton
public class SharePreferenceHelper {
    private Context context;
    private static final String TAG = "SharePreferenceHelper";
    @Inject
    public SharePreferenceHelper(Context context) {
        this.context = context;
    }

    private SharedPreferences.Editor getSharePreferenceEditor(String fileName) {
        return getSharedPreferences(fileName).edit();
    }

    public SharedPreferences getSharedPreferences(String fileName){
        return context.getSharedPreferences(TextUtils.isEmpty(fileName) ? CommonConfig.SHARE_PREFERENCE_FILE_NAME : fileName, Context.MODE_PRIVATE);
    }

    /*
     * 存数据
     */
    public void saveData(Map<String ,String> maps){
        this.saveData(maps,null);
    }


    public void saveData(Map<String ,String> maps,String fileName){
        if(null == maps || maps.size() == 0)return;
        SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
        for(Map.Entry<String, String> map:maps.entrySet()){
            String key = map.getKey();
            String value = map.getValue();
            LogUtil.d(TAG, "存数据==key is "+key+",value is "+value);
            editor.putString(key, value);
        }
        editor.commit();
    }

    public void saveData(String key,String value){
        this.saveData(key,value,null);
    }

    public void saveData(String key,String value,String fileName){
        SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(String key){
        if(null==key)
            return null;
        return this.getValue(key ,null);
    }

    public String getValue(String key,String fileName){
        if(null==key)
            return null;
        return getSharedPreferences(fileName).getString(key, null);
    }

    public Boolean getBooleanValue(String fileName, String key, boolean defValue) {
        if (key == null) {
            return defValue;
        }
        return getSharedPreferences(fileName).getBoolean(key, defValue);
    }

    public Boolean getBooleanValue(String key, String fileName){
        if(null==key)
            return null;
        return getSharedPreferences(fileName).getBoolean(key, false);
    }

    public Boolean getBooleanValue(String key){
        if(null==key)
            return null;
        return this.getBooleanValue(key, null);
    }

    public void setBoolean(String key, String fileName, boolean value) {
        getSharedPreferences(fileName).edit().putBoolean(key, value).commit();
    }

    /*
     * 读数据，返回一个Map<String, String>
     */
    public Map<String, String> readData(){
        return this.readData(null);
    }

    public Map<String, String> readData(String fileName){
        return (Map<String, String>)getSharedPreferences(fileName).getAll();
    }

    /*
     * 根据文件名删除文件里的数据
     */
    public void deletePreference(){
        this.deletePreference(null);
    }

    public void deletePreference(String fileName){
        getSharedPreferences(fileName).getAll().clear();
        getSharePreferenceEditor(fileName).clear().commit();
    }
}
