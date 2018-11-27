package com.mujirenben.android.common.base.greendao;

/**
 * Created by mac on 2018/5/29.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity(
        indexes = {
                @Index(value = "user_id", unique = true) //user_id為唯一索引
        }
)
public class UserBean implements Serializable{

    private static final long serialVersionUID = 2L;



    @Id(autoincrement = true)
    private Long id;

    private String user_name;
    private String user_phone;
    private Long user_id;
    private Boolean LoginSta;
    private Long preTime;
    private Boolean sex;
    private String nickLocalUri;
    private String fromCity;
    private String fromProvince;
    private String fromZone;
    private String nickUrl;

@Generated(hash = 841287542)
public UserBean(Long id, String user_name, String user_phone, Long user_id,
        Boolean LoginSta, Long preTime, Boolean sex, String nickLocalUri,
        String fromCity, String fromProvince, String fromZone, String nickUrl) {
    this.id = id;
    this.user_name = user_name;
    this.user_phone = user_phone;
    this.user_id = user_id;
    this.LoginSta = LoginSta;
    this.preTime = preTime;
    this.sex = sex;
    this.nickLocalUri = nickLocalUri;
    this.fromCity = fromCity;
    this.fromProvince = fromProvince;
    this.fromZone = fromZone;
    this.nickUrl = nickUrl;
}
@Generated(hash = 1203313951)
public UserBean() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public String getUser_name() {
    return this.user_name;
}
public void setUser_name(String user_name) {
    this.user_name = user_name;
}
public String getUser_phone() {
    return this.user_phone;
}
public void setUser_phone(String user_phone) {
    this.user_phone = user_phone;
}
public Long getUser_id() {
    return this.user_id;
}
public void setUser_id(Long user_id) {
    this.user_id = user_id;
}

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_id=" + user_id +
                ", LoginSta=" + LoginSta +
                ", preTime=" + preTime +
                ", sex=" + sex +
                ", nickLocalUri='" + nickLocalUri + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", fromProvince='" + fromProvince + '\'' +
                ", fromZone='" + fromZone + '\'' +
                ", nickUrl='" + nickUrl + '\'' +
                '}';
    }

    public Boolean getLoginSta() {
    return this.LoginSta;
}
public void setLoginSta(Boolean LoginSta) {
    this.LoginSta = LoginSta;
}
public Long getPreTime() {
    return this.preTime;
}
public void setPreTime(Long preTime) {
    this.preTime = preTime;
}
public Boolean getSex() {
    return this.sex;
}
public void setSex(Boolean sex) {
    this.sex = sex;
}
public String getNickLocalUri() {
    return this.nickLocalUri;
}
public void setNickLocalUri(String nickLocalUri) {
    this.nickLocalUri = nickLocalUri;
}
public String getFromCity() {
    return this.fromCity;
}
public void setFromCity(String fromCity) {
    this.fromCity = fromCity;
}
public String getNickUrl() {
    return this.nickUrl;
}
public void setNickUrl(String nickUrl) {
    this.nickUrl = nickUrl;
}
public String getFromProvince() {
    return this.fromProvince;
}
public void setFromProvince(String fromProvince) {
    this.fromProvince = fromProvince;
}
public String getFromZone() {
    return this.fromZone;
}
public void setFromZone(String fromZone) {
    this.fromZone = fromZone;
}


}
