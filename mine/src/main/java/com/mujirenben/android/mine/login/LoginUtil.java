package com.mujirenben.android.mine.login;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mujirenben.android.common.event.LoginStatusEvent;
import com.mujirenben.android.common.http.HttpParamName;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.mine.mvp.ui.activity.LoginHrzActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Iterator;

public class LoginUtil {
    /**
     * 获取微信登录请求参数json
     *
     * @param context
     * @return
     */
    public static JsonObject getLoginWeixinParamJson(Context context, String accessToken, String openId) {
        HashMap<String, String> paramMap = HttpParamUtil.getCommonParamMap(context);
        JsonObject paramJson = new JsonObject();
        Iterator<String> keys = paramMap.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            paramJson.addProperty(key, paramMap.get(key));
        }
        paramJson.addProperty(HttpParamName.PARAM_ACCESS_TOKEN, accessToken);
        paramJson.addProperty(HttpParamName.PARAM_OPEN_ID, openId);

        return paramJson;
    }

    /**
     * 获取短信验证码请求参数Map
     *
     * @param context
     * @param phone
     * @return
     */
    public static HashMap<String, String> getSmsVerifyCodeParamMap(Context context, String phone) {
        HashMap<String, String> paramMap = HttpParamUtil.getCommonParamMap(context);
        paramMap.put(HttpParamName.PARAM_PHONE_NUMBER, phone);
        paramMap.put(HttpParamName.PARAM_EVENT, "binding");

        return paramMap;
    }

    /**
     * 获取微信登录请求参数json
     *
     * @param context
     * @param needToken
     * @return
     */
    public static JsonObject getLoginPhoneParamJson(Context context, String phone, String verifyCode, boolean needToken) {
        HashMap<String, String> paramMap = HttpParamUtil.getCommonParamMap(context);
        JsonObject paramJson = new JsonObject();
        Iterator<String> keys = paramMap.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            paramJson.addProperty(key, paramMap.get(key));
        }
        paramJson.addProperty(HttpParamName.PARAM_PHONE_NUMBER, phone);
        paramJson.addProperty(HttpParamName.PARAM_VERIFY_CODE, verifyCode);
        if (needToken){
            //带上这个参数如果是首次手机登录就会绑定手机
            String accountSecToken;
            //如果是从登录页面跳转过来，必定有值，否则就是从更换绑定手机号页面跳转过来的
            if(LoginHrzActivity.tempUserInfo == null){
                accountSecToken = LoginDataManager.getsInstance(context).getAccountSecToken();
            }else {
                accountSecToken = LoginHrzActivity.tempUserInfo.getAccountSecToken();
            }
            paramJson.addProperty(HttpParamName.PARAM_ACCOUNT_SEC_TOKEN, accountSecToken);
        }
        return paramJson;
    }

    /**
     * 登录成功后发送到EventBus
     *
     * @param context
     */
    public static void postLoginSuccessEvent(Context context) {
        LoginStatusEvent loginStatusEvent = new LoginStatusEvent(true);

        EventBus.getDefault().post(loginStatusEvent);
    }

    /**
     * 登录成功后发送到EventBus
     *
     * @param context
     */
    public static void postLoginSuccessEventWithRequestCode(Context context, int requestCode) {
        LoginStatusEvent loginStatusEvent = new LoginStatusEvent(true, requestCode);

        EventBus.getDefault().post(loginStatusEvent);
    }

    /**
     * 退出登录后发送到EventBus
     *
     * @param context
     */
    public static void postLogoutEvent(Context context) {
        LoginStatusEvent loginStatusEvent = new LoginStatusEvent(false);
        EventBus.getDefault().post(loginStatusEvent);
    }
}
