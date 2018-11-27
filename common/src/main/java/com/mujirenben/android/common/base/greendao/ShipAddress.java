package com.mujirenben.android.common.base.greendao;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import java.util.Date;



@Entity(
        indexes = {
        @Index(value = "addressId", unique = true) //user_id為唯一索引
}
)
public class ShipAddress implements Serializable{

    private static final long serialVersionUID = 10L;


    @Id(autoincrement = true)
    private Long addressId;

    private Long userId;

    private String ship_user_name;

    private String ship_user_phone;

    private String areaId1;//省

    private String areaId2;//市

    private String areaId3;//區/縣

    private String communityId;//

    private String address; //詳細地址

    private String postCode;//郵編

    private Long isDefault;//是否為默認 0否1是

    private Long addressFlag;// -1 刪除狀態 1 有效地址

    private Date createTime;


    @Override
    public String toString() {
        return "ShipAddress{" +
                "addressId=" + addressId +
                ", userId=" + userId +
                ", ship_user_name='" + ship_user_name + '\'' +
                ", ship_user_phone='" + ship_user_phone + '\'' +
                ", areaId1='" + areaId1 + '\'' +
                ", areaId2='" + areaId2 + '\'' +
                ", areaId3='" + areaId3 + '\'' +
                ", communityId='" + communityId + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", isDefault=" + isDefault +
                ", addressFlag=" + addressFlag +
                ", createTime=" + createTime +
                '}';
    }

    @Generated(hash = 2028501420)
public ShipAddress(Long addressId, Long userId, String ship_user_name,
        String ship_user_phone, String areaId1, String areaId2, String areaId3,
        String communityId, String address, String postCode, Long isDefault,
        Long addressFlag, Date createTime) {
    this.addressId = addressId;
    this.userId = userId;
    this.ship_user_name = ship_user_name;
    this.ship_user_phone = ship_user_phone;
    this.areaId1 = areaId1;
    this.areaId2 = areaId2;
    this.areaId3 = areaId3;
    this.communityId = communityId;
    this.address = address;
    this.postCode = postCode;
    this.isDefault = isDefault;
    this.addressFlag = addressFlag;
    this.createTime = createTime;
}

@Generated(hash = 733573968)
public ShipAddress() {
}

public Long getAddressId() {
    return this.addressId;
}

public void setAddressId(Long addressId) {
    this.addressId = addressId;
}

public Long getUserId() {
    return this.userId;
}

public void setUserId(Long userId) {
    this.userId = userId;
}

public String getShip_user_name() {
    return this.ship_user_name;
}

public void setShip_user_name(String ship_user_name) {
    this.ship_user_name = ship_user_name;
}

public String getShip_user_phone() {
    return this.ship_user_phone;
}

public void setShip_user_phone(String ship_user_phone) {
    this.ship_user_phone = ship_user_phone;
}

public String getAreaId1() {
    return this.areaId1;
}

public void setAreaId1(String areaId1) {
    this.areaId1 = areaId1;
}

public String getAreaId2() {
    return this.areaId2;
}

public void setAreaId2(String areaId2) {
    this.areaId2 = areaId2;
}

public String getAreaId3() {
    return this.areaId3;
}

public void setAreaId3(String areaId3) {
    this.areaId3 = areaId3;
}

public String getCommunityId() {
    return this.communityId;
}

public void setCommunityId(String communityId) {
    this.communityId = communityId;
}

public String getAddress() {
    return this.address;
}

public void setAddress(String address) {
    this.address = address;
}

public String getPostCode() {
    return this.postCode;
}

public void setPostCode(String postCode) {
    this.postCode = postCode;
}

public Long getIsDefault() {
    return this.isDefault;
}

public void setIsDefault(Long isDefault) {
    this.isDefault = isDefault;
}

public Long getAddressFlag() {
    return this.addressFlag;
}

public void setAddressFlag(Long addressFlag) {
    this.addressFlag = addressFlag;
}

public Date getCreateTime() {
    return this.createTime;
}

public void setCreateTime(Date createTime) {
    this.createTime = createTime;
}



}
