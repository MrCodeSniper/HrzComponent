package com.mujirenben.android.common.http;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.AppInfoUtils;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.util.MD5Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * http请求参数处理类
 **/
public class HttpParamUtil {




    /**
     * 无论是否登录添加了签名了
     *
     * @param context
     * @return
     */
    public static HashMap<String, String> getResultMap(Context context,HashMap<String, String> paramMap) {

        if(LoginDataManager.getsInstance(context).isLogin()){
            return HttpParamUtil.getCommonSignParamMap(context,paramMap);
        }else {
            if(paramMap!=null){
                paramMap.put(HttpParamName.PARAM_NONCE_STR, getNonceStr());
                paramMap.put(HttpParamName.PARAM_CLIENT_ID, getClientId(context));
                paramMap.put(HttpParamName.PARAM_CLIENT_VERSION, getClientVersionName(context));
                paramMap.put(HttpParamName.PARAM_TIME_STAMP, getTimeStamp());
            }
        }
        return paramMap;
    }





    /**
     * 获取通用请求参数Map
     *
     * @param context
     * @return
     */
    public static HashMap<String, String> getCommonParamMap(Context context) {
        HashMap<String, String> paramMap = new HashMap<>();

        paramMap.put(HttpParamName.PARAM_NONCE_STR, getNonceStr());
        paramMap.put(HttpParamName.PARAM_CLIENT_ID, getClientId(context));
        paramMap.put(HttpParamName.PARAM_CLIENT_VERSION, getClientVersionName(context));
        paramMap.put(HttpParamName.PARAM_TIME_STAMP, getTimeStamp());

        return paramMap;
    }

    /**
     * 获取认证接口通用参数Map
     *
     * @param context
     */
    private static HashMap<String, String> getSignParamMap(Context context) {
        HashMap<String, String> paramMap = HttpParamUtil.getCommonParamMap(context);

        LoginDataManager loginDataManager = LoginDataManager.getsInstance(context);
        paramMap.put(HttpParamName.PARAM_ACCOUNT_SEC_TOKEN, loginDataManager.getAccountSecToken());

        return paramMap;
    }

    /**
     * 获取认证接口的sign加密字符串
     *
     * @param context
     * @param paramMap
     * @return
     */
    public static String getSignParamStr(Context context, HashMap<String, String> paramMap) {
        Iterator<String> keys = paramMap.keySet().iterator();
        List<String> nameList = new ArrayList<>();
        while (keys.hasNext()) {
            String key = keys.next();
            if (!TextUtils.isEmpty(paramMap.get(key))) {
                nameList.add(key);
            }
        }

        Collections.sort(nameList, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                if (str1.compareTo(str2) < 0) {
                    return -1;
                } else if (str1.compareTo(str2) > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < nameList.size(); i++) {
            if (i != 0) {
                stringBuffer.append("&");
            }
            String name = nameList.get(i);
            stringBuffer.append(name);
            stringBuffer.append("=");
            stringBuffer.append(paramMap.get(name));
        }

        LoginDataManager loginDataManager = LoginDataManager.getsInstance(context);
        String accountSecKey = loginDataManager.getAccountSecKey();
        if (!TextUtils.isEmpty(accountSecKey)) {
            stringBuffer.append("&");
            stringBuffer.append(HttpParamName.PARAM_ACCOUNT_SEC_KEY);
            stringBuffer.append("=");
            stringBuffer.append(accountSecKey);
        }

        String strOri = stringBuffer.toString();
        String result = MD5Utils.getMD5String(strOri);

        LogUtil.d("login", "strOri =" + strOri);
        LogUtil.d("login", "strMD5 =" + result);
        return  result;
    }

    /**
     * 这里主要是在用户登录还没有成功之前调用，因为wx的token在用户还没确定登录成功之前，是不会保存在本地的
     * 所以需要手动传递过来
     * @param paramMap
     * @param accountSecKey
     * @return
     */
    public static String getSignParamStr(HashMap<String, String> paramMap,String accountSecKey){
        Iterator<String> keys = paramMap.keySet().iterator();
        List<String> nameList = new ArrayList<>();
        while (keys.hasNext()) {
            String key = keys.next();
            if (!TextUtils.isEmpty(paramMap.get(key))) {
                nameList.add(key);
            }
        }

        Collections.sort(nameList, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                if (str1.compareTo(str2) < 0) {
                    return -1;
                } else if (str1.compareTo(str2) > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < nameList.size(); i++) {
            if (i != 0) {
                stringBuffer.append("&");
            }
            String name = nameList.get(i);
            stringBuffer.append(name);
            stringBuffer.append("=");
            stringBuffer.append(paramMap.get(name));
        }
        if (!TextUtils.isEmpty(accountSecKey)) {
            stringBuffer.append("&");
            stringBuffer.append(HttpParamName.PARAM_ACCOUNT_SEC_KEY);
            stringBuffer.append("=");
            stringBuffer.append(accountSecKey);
        }

        String strOri = stringBuffer.toString();
        String result = MD5Utils.getMD5String(strOri);

        LogUtil.d("login", "strOri =" + strOri);
        LogUtil.d("login", "strMD5 =" + result);
        return  result;
    }

    /**
     * 获取需要签名认证接口的通用参数列表
     *
     * @param context
     * @param specParamMap 接口独有参数列表（除通用参数外的其它参数， 无则传null）
     * 通用参数：
     *                     1，clientId
     *                     2，clientVersion
     *                     3，timeStamp
     *                     4，nonceStr
     *                     5，sign
     *                     6，accountSecToken
     * @return 最终的请求参数Map
     */
    public static HashMap<String, String> getCommonSignParamMap(Context context, @Nullable HashMap<String, String> specParamMap) {
        HashMap<String, String> paramMap = getSignParamMap(context);
        if (specParamMap != null) {
            paramMap.putAll(specParamMap);
        }
        paramMap.put(HttpParamName.PARAM_SIGN, getSignParamStr(context, paramMap));

        return paramMap;
    }

    /**
     * 获取ClientId - 使用包名作为唯一标识
     *
     * @param context
     * @return
     */
    public static String getClientId(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取当前版本名
     *
     * @param context
     * @return
     */
    public static String getClientVersionName(Context context) {
        return AppInfoUtils.getVersionName(context);
    }

    /**
     * 获取时间戳秒数
     *
     * @return
     */
    public static String getTimeStamp() {
        long timeMills = System.currentTimeMillis();
        return timeMills / 1000 + "";
    }

    /**
     * 获取随机数
     *
     * @return
     */
    public static String getNonceStr() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.substring(0,8) + uuid.substring(9,13) + uuid.substring(14,18) + uuid.substring(19,23) + uuid.substring(24);
        return uuid;
    }
}
