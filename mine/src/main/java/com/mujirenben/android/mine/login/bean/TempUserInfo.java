package com.mujirenben.android.mine.login.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.mujirenben.android.common.user.UserInfoBean;

import java.io.Serializable;

/**
 * @author: panrongfu
 * @date: 2018/8/24 16:25
 * @describe:
 */

public class TempUserInfo implements Serializable {

    private String accountSecToken;
    private String accountSecKey;
    private int phoneValidate;
    private UserInfoBean userInfoBean;

    private String wxAccessToken;
    private String wxOpenId;
    private String wxUnionid;

    public TempUserInfo() {
    }

    public String getAccountSecToken() {
        return accountSecToken;
    }

    public void setAccountSecToken(String accountSecToken) {
        this.accountSecToken = accountSecToken;
    }

    public String getAccountSecKey() {
        return accountSecKey;
    }

    public int getPhoneValidate() {
        return phoneValidate;
    }

    public void setPhoneValidate(int phoneValidate) {
        this.phoneValidate = phoneValidate;
    }

    public void setAccountSecKey(String accountSecKey) {
        this.accountSecKey = accountSecKey;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public String getWxAccessToken() {
        return wxAccessToken;
    }

    public void setWxAccessToken(String wxAccessToken) {
        this.wxAccessToken = wxAccessToken;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getWxUnionid() {
        return wxUnionid;
    }

    public void setWxUnionid(String wxUnionid) {
        this.wxUnionid = wxUnionid;
    }
}
