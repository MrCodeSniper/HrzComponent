package com.mujirenben.android.common.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class UserInfoBean implements Serializable{
    /**
     * id : 1
     * sex : null
     * phone : 1599123123
     * phoneValidate : 1
     * email :
     * emailValidate : 0
     * nikeName :
     * unionId :
     * openId :
     * avatarUrl :
     * status : null
     */

    private long id;
    private String sex;
    private String phone;
    private int phoneValidate;
    private String email;
    private String emailValidate;
    private String nikeName;
    private String unionId;
    private String openId;
    private String avatarUrl;
    private String status;
    private int lv;
    private int star;
    private long expiredDay;

    protected UserInfoBean(Parcel in) {
        id = in.readLong();
        sex = in.readString();
        phone = in.readString();
        phoneValidate = in.readInt();
        email = in.readString();
        emailValidate = in.readString();
        nikeName = in.readString();
        unionId = in.readString();
        openId = in.readString();
        avatarUrl = in.readString();
        status = in.readString();
        lv = in.readInt();
        star = in.readInt();
        expiredDay = in.readLong();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhoneValidate() {
        return phoneValidate;
    }

    public void setPhoneValidate(int phoneValidate) {
        this.phoneValidate = phoneValidate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailValidate() {
        return emailValidate;
    }

    public void setEmailValidate(String emailValidate) {
        this.emailValidate = emailValidate;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public long getExpiredDay() {
        return expiredDay;
    }

    public void setExpiredDay(long expiredDay) {
        this.expiredDay = expiredDay;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "id=" + id +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneValidate='" + phoneValidate + '\'' +
                ", email='" + email + '\'' +
                ", emailValidate='" + emailValidate + '\'' +
                ", nikeName='" + nikeName + '\'' +
                ", unionId='" + unionId + '\'' +
                ", openId='" + openId + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", status='" + status + '\'' +
                ", lv=" + lv +
                ", star=" + star +
                ", expiredDay=" + expiredDay +
                '}';
    }
}
