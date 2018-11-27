package com.mujirenben.android.common.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.resource.SharePreferenceHelper;
import com.mujirenben.android.common.entity.AddressBean;

public class LoginDataManager {
    private static volatile LoginDataManager sInstance;
    private SharedPreferences sharedPreferences;

    private final static String LOGIN_XML_FILE_NAME = "login_pref";
    private final static String PREF_KEY_WEIXIN_CODE = "pref_key_weixin_code";
    private final static String PREF_KEY_WEIXIN_ACCESS_TOKEN = "pref_key_weixin_accessToken";
    private final static String PREF_KEY_WEIXIN_OPEN_ID = "pref_key_weixin_openId";
    private final static String PERF_KEY_WEIXIN_UNION_ID = "pref_key_weixin_unionId";
    private final static String PREF_KEY_ACCOUNT_SEC_TOKEN = "pref_key_account_sec_token";
    private final static String PREF_KEY_ACCOUNT_SEC_KEY = "pref_key_account_sec_key";
    private final static String USER_MEDIA_ID = "USER_MEDIA_ID";
    private final static String USER_READ_JD_AGRREMENT="user_read_jd_aggreement";

    private final static String USER_REWARD_TODAY_INCOME = "USER_REWARD_TODAY_INCOME";
    private final static String USER_REWARD_TOTAL_INCOME = "USER_REWARD_TOTAL_INCOME";
    private final static String USER_REWARD_TODAY_RETURN = "USER_REWARD_TODAY_RETURN";
    private final static String USER_REWARD_PREDICT_INCOME = "USER_REWARD_PREDICT_INCOME";
    private final static String USER_REWARD_WITHDRAW_CASH = "USER_REWARD_WITHDRAW_CASH";

    private final static String IS_FIRST_PLAY = "IS_FIRST_PLAY";
    private final static String IS_NOWIFI_PLAY = "IS_NOWIFI_PLAY";

    private final static String USER_ID = "user_id";
    private final static String USER_INFO_PHONE = "user_phone";
    private final static String USER_INFO_AVATAR_URL = "user_avatar_url";
    private final static String USER_INFO_NICK_NAME = "user_nick_name";
    private final static String USER_INFO_LEVEL = "user_level";
    private final static String USER_INFO_STAR = "user_start";
    private final static String USER_PHONE_VALIDATE = "user_phone_validate";

    private final static String IS_FIRST_FULL = "IS_FIRST_FULL";
    private final static String USER_LATITUDE = "USER_LATITUDE";
    private final static String USER_LONGTITUDE = "USER_LONGTITUDE";
    private final static String IS_NONEED_UPGRADE = "IS_NONEED_UPGRADE";
    private final static String IS_SHOW_USER_INCOME="is_show_user_income";
    private final static String SHIP_DEFAULT_ADDRESS="ship_default_address";

    private final static String KE_FU_APP_KEY = "ke_fu_appKey";
    private final static String KE_FU_TENANTID_KEY = "ke_fu_tenantId";
    private final static String KE_FU_USERNAME_KEY = "ke_fu_username";
    private final static String KE_FU_PASSWORD_KEY = "ke_fu_password";
    private final static String KE_FU_TOKEN_KEY = "ke_fu_token";
    private final static String KE_FU_INIT_KEY = "ke_fu_init";
    private final static String KE_FU_IM_NUMBER_KEY = "ke_fu_im_number";

    private final static String GE_TUI_INIT_KEY = "ge_tui_init";
    public void setIsShowUserIncome(boolean isShowUserIncome) {
        sharedPreferences.edit().putBoolean(IS_SHOW_USER_INCOME, isShowUserIncome).commit();
    }

    public boolean getIsShowUserIncome() {
        return sharedPreferences.getBoolean(IS_SHOW_USER_INCOME, true);
    }

    public void setShipDefaultAddress(AddressBean.DataBean address){
        Gson gson=new Gson();
        String addressJson=gson.toJson(address);
        com.orhanobut.logger.Logger.e(addressJson);
        sharedPreferences.edit().putString(SHIP_DEFAULT_ADDRESS, addressJson);
    }

    public String  getShipDefaultAddress(){
        return sharedPreferences.getString(SHIP_DEFAULT_ADDRESS, "");
    }



    public void setIsNoNeedUpgrade(boolean isNoNeedUpgrade) {
        sharedPreferences.edit().putBoolean(IS_NONEED_UPGRADE, isNoNeedUpgrade).commit();
    }

    public void setUserReadJdAggrement(boolean isNoNeedJdAggrement){
        sharedPreferences.edit().putBoolean(USER_READ_JD_AGRREMENT, isNoNeedJdAggrement).commit();
    }


    public Boolean  getUserReadJdAggrement(){
        return sharedPreferences.getBoolean(USER_READ_JD_AGRREMENT, true);
    }




    public boolean getIsNoNeedUpgrade() {
        return sharedPreferences.getBoolean(IS_NONEED_UPGRADE, false);
    }


    public void setIsFirstPlay(boolean isFirstPlay) {
        sharedPreferences.edit().putBoolean(IS_FIRST_PLAY, isFirstPlay).commit();
    }


    public void setIsWifyPlay(boolean isWifyPlay) {
        sharedPreferences.edit().putBoolean(IS_NOWIFI_PLAY, isWifyPlay).commit();
    }


    public boolean isWifiPlay() {
        return sharedPreferences.getBoolean(IS_NOWIFI_PLAY, true);
    }

    public boolean isFirstPlay() {
        return sharedPreferences.getBoolean(IS_FIRST_PLAY, true);
    }

    private final static String USER_INFO_SHOPKEEPER_EXPIRATION = "user_shopkeeper_expiration";

    private static final String PREF_KEY_USER_QR_CODE_URL = "user_qr_code_url";

    public static LoginDataManager getsInstance(Context context) {
        if (sInstance == null ) {
            synchronized (LoginDataManager.class) {
                if (sInstance == null) {
                    sInstance = new LoginDataManager(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private LoginDataManager(Context context) {
        sharedPreferences = new SharePreferenceHelper(context).getSharedPreferences(LOGIN_XML_FILE_NAME);
    }

    public void setCanWithdrawCash(String canWithdraw){
        sharedPreferences.edit().putString(USER_REWARD_WITHDRAW_CASH,canWithdraw).commit();
    }

    public String getCanWithdrawCash(){
        return sharedPreferences.getString(USER_REWARD_WITHDRAW_CASH, "");
    }
    public void setUserTotalIncome(String income) {
        sharedPreferences.edit().putString(USER_REWARD_TODAY_INCOME, income).commit();
    }


    public String getUserTotalIncome() {
        return sharedPreferences.getString(USER_REWARD_TODAY_INCOME, "");
    }


    public void setIsFirstFull(boolean isFirstFull) {
        sharedPreferences.edit().putBoolean(IS_FIRST_FULL, isFirstFull).commit();
    }


    public boolean IsFirstFull() {
        return sharedPreferences.getBoolean(IS_FIRST_FULL, false);
    }


    public void setUserLatitude(String latitude) {
        sharedPreferences.edit().putString(USER_LATITUDE, latitude).commit();
    }

    public String getUserLatitude() {
        return sharedPreferences.getString(USER_LATITUDE, "");
    }

    public void setUserLongtitude(String longtitude) {
        sharedPreferences.edit().putString(USER_LONGTITUDE, longtitude).commit();
    }

    public String getUserLongtitude() {
        return sharedPreferences.getString(USER_LONGTITUDE, "");
    }


    public void saveWeixinCode(String code) {
        sharedPreferences.edit().putString(PREF_KEY_WEIXIN_CODE, code).commit();
    }

    public String getWeixinCode() {
        return sharedPreferences.getString(PREF_KEY_WEIXIN_CODE, "");
    }

    public void saveWeixinAccessToken(String accessToken) {
        sharedPreferences.edit().putString(PREF_KEY_WEIXIN_ACCESS_TOKEN, accessToken).commit();
    }

    public String getWeixinAccessToken() {
        return sharedPreferences.getString(PREF_KEY_WEIXIN_ACCESS_TOKEN, "");
    }

    public void saveWeixinOpenId(String openId) {
        sharedPreferences.edit().putString(PREF_KEY_WEIXIN_OPEN_ID, openId).commit();
    }

    public String getWeixinOpenId() {
        return sharedPreferences.getString(PREF_KEY_WEIXIN_OPEN_ID, "");
    }

    public void saveWeixinUnionid(String unionId) {
        sharedPreferences.edit().putString(PERF_KEY_WEIXIN_UNION_ID, unionId).commit();
    }

    public String getWeixinUnionId() {
        return sharedPreferences.getString(PERF_KEY_WEIXIN_UNION_ID, "");
    }

    public long getUserId() {
        return sharedPreferences.getLong(USER_ID, -1);
    }

    public void setAccountSecToken(String accountSecToken) {
        sharedPreferences.edit().putString(PREF_KEY_ACCOUNT_SEC_TOKEN, accountSecToken).commit();
    }

    public String getAccountSecToken() {
        return sharedPreferences.getString(PREF_KEY_ACCOUNT_SEC_TOKEN, "");
    }

    public void setAccountSecKey(String accountSecKey) {
        sharedPreferences.edit().putString(PREF_KEY_ACCOUNT_SEC_KEY, accountSecKey).commit();
    }

    public void setMediaIdForAlliance(String mediaId) {
        sharedPreferences.edit().putString(USER_MEDIA_ID, mediaId).commit();
    }

    public String getMediaId() {
        return sharedPreferences.getString(USER_MEDIA_ID, "");
    }


    public String getAccountSecKey() {
        return sharedPreferences.getString(PREF_KEY_ACCOUNT_SEC_KEY, "");
    }

    public int getPhoneValidate() {
        return sharedPreferences.getInt(USER_PHONE_VALIDATE, 0);
    }

    public void setAvatarUrl(String url) {
        sharedPreferences.edit().putString(USER_INFO_AVATAR_URL, url).commit();
    }

    public String getPhone() {
        return sharedPreferences.getString(USER_INFO_PHONE, "");
    }

    public String getAvatarUrl() {
        return sharedPreferences.getString(USER_INFO_AVATAR_URL, "");
    }

    public String getNickName() {
        return sharedPreferences.getString(USER_INFO_NICK_NAME, "");
    }

    /**
     * 获取需要展示的用户名
     *
     * @return
     */


    public String getDisplayName() {
        String displayName = getNickName();
        if (TextUtils.isEmpty(displayName)) {
            displayName = getPhone();
        }

        return displayName;
    }

    /**
     * 保存用户信息
     *
     * @param userInfo
     */
    public void setUserInfo(UserInfoBean userInfo) {
        if (userInfo == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(USER_ID, userInfo.getId());
        editor.putInt(USER_PHONE_VALIDATE, userInfo.getPhoneValidate());
        editor.putString(USER_INFO_NICK_NAME, userInfo.getNikeName());
        editor.putString(USER_INFO_PHONE, userInfo.getPhone());
        editor.putString(USER_INFO_AVATAR_URL, userInfo.getAvatarUrl());
        editor.putInt(USER_INFO_LEVEL, userInfo.getLv());
        editor.putInt(USER_INFO_STAR, userInfo.getStar());
        editor.putLong(USER_INFO_SHOPKEEPER_EXPIRATION, userInfo.getExpiredDay());

        Log.i("jan3", "ddd = " + userInfo.getExpiredDay());

        editor.commit();
    }

    /**
     * 判断用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        boolean isWeixinRegistered = isWeixinRegistered();
        int phoneValidate = getPhoneValidate();
        // 强制绑定手机
        if (isWeixinRegistered && phoneValidate == 1) {
            return true;
        }

        return isWeixinRegistered();
    }

    /**
     * 判断是否已经登录完微信，用来判断用户是否已经登录完微信，但是还没有完成手机绑定
     *
     * @return
     */
    public boolean isWeixinRegistered() {
        String accountSecToken = getAccountSecToken();
        String accountSecKey = getAccountSecKey();
        if (!TextUtils.isEmpty(accountSecToken)&& !TextUtils.isEmpty(accountSecKey)) {
            return true;
        }

        return false;
    }

    /**
     * 获取用户等级
     *
     * @return
     */
    public int getLevel() {
        return sharedPreferences.getInt(USER_INFO_LEVEL, Const.UserLevel.LEVEL_FANS);
    }

    public String getLevelString() {
        String levelString = "粉丝";
        switch (getLevel()) {
            case 0:
                levelString = "粉丝";
                break;
            case 1:
                levelString = "皇冠";
                break;
            case 2:
                levelString = "店主";
                break;
        }
        return levelString;
    }

    /**
     * 保存用户等级
     * @param lv
     */
    public void setLevel(int lv){
      sharedPreferences.edit().putInt(USER_INFO_LEVEL, lv).commit();
    }


    /**
     * 用户登出
     */
    public void logout() {
        sharedPreferences.edit().remove(PREF_KEY_ACCOUNT_SEC_TOKEN)
                .remove(PREF_KEY_ACCOUNT_SEC_KEY)
                .remove(USER_ID)
                .remove(USER_PHONE_VALIDATE)
                .remove(USER_INFO_NICK_NAME)
                .remove(USER_INFO_PHONE)
                .remove(USER_INFO_AVATAR_URL)
                .remove(USER_INFO_LEVEL)
                .remove(USER_INFO_STAR)
                .remove(USER_INFO_SHOPKEEPER_EXPIRATION)
                .remove(KE_FU_INIT_KEY)
                .remove(KE_FU_IM_NUMBER_KEY)
                .remove(KE_FU_APP_KEY)
                .remove(KE_FU_TOKEN_KEY)
                .remove(KE_FU_USERNAME_KEY)
                .remove(KE_FU_TENANTID_KEY)
                .remove(KE_FU_PASSWORD_KEY)
                .remove(GE_TUI_INIT_KEY)
                .commit();
    }

    /**
     * 获取我的二维码链接
     */
    public String getUserQrCodeUrl() {
        return sharedPreferences.getString(PREF_KEY_USER_QR_CODE_URL, "");
    }

    /**
     * 获取店主等级过期时间
     */
    public long getShopKeeperExpirationTimeMillis() {
        return sharedPreferences.getLong(USER_INFO_SHOPKEEPER_EXPIRATION, 0) * 1000L;
    }

    /**
     * 设置店主过期时间
     * @param expiredDay
     */
    public void setShopKeeperExpirationTimeMillis(long expiredDay){
        sharedPreferences.edit().putLong(USER_INFO_SHOPKEEPER_EXPIRATION,expiredDay).commit();
    }

    /**
     * 如果需要，更新我的二维码链接
     *
     * @param newUrl
     */
    public void updateUserQrCodeUrlIfNeeded(String newUrl) {
        if (newUrl.equals(getUserQrCodeUrl())) {
            return;
        }

        sharedPreferences.edit().putString(PREF_KEY_USER_QR_CODE_URL, newUrl).apply();
    }


    public void setKeFuAppKey(String appKey){
        sharedPreferences.edit().putString(KE_FU_APP_KEY,appKey).commit();
    }

    public void setKeFuTenantId(String tenantId){
        sharedPreferences.edit().putString(KE_FU_TENANTID_KEY,tenantId).commit();
    }

    public void setKeFuUsername(String username){
        sharedPreferences.edit().putString(KE_FU_USERNAME_KEY,username).commit();
    }

    public void setKeFuPassword(String password){
        sharedPreferences.edit().putString(KE_FU_PASSWORD_KEY,password).commit();
    }

    public void setKeFuToken(String token){
        sharedPreferences.edit().putString(KE_FU_TOKEN_KEY,token).commit();
    }

    public void setImNumber(String imNumber){
        sharedPreferences.edit().putString(KE_FU_IM_NUMBER_KEY,imNumber).commit();
    }
    public void setGeTuiInitState(boolean state){
        sharedPreferences.edit().putBoolean(GE_TUI_INIT_KEY,state).commit();
    }

    public String getKeFuAppKey(){
        return sharedPreferences.getString(KE_FU_APP_KEY,"");
    }
    public String getKeFuTenantId(){
        return sharedPreferences.getString(KE_FU_TENANTID_KEY,"");
    }
    public String getKeFuUserName(){
        return sharedPreferences.getString(KE_FU_USERNAME_KEY,"");
    }
    public String getKeFuPassword(){
        return sharedPreferences.getString(KE_FU_PASSWORD_KEY,"");
    }
    public String getKeFuToken(){
        return sharedPreferences.getString(KE_FU_TOKEN_KEY,"");
    }
    public boolean getKeFuInitState(){
        return sharedPreferences.getBoolean(KE_FU_INIT_KEY,false);
    }
    public boolean getGeTuiInitState(){ return sharedPreferences.getBoolean(GE_TUI_INIT_KEY,false);}
    public String getKeFuImNumber(){
        return sharedPreferences.getString(KE_FU_IM_NUMBER_KEY,"");
    }
    public void setKeFuInit(boolean init){
        sharedPreferences.edit().putBoolean(KE_FU_INIT_KEY,init).commit();
    }
}
