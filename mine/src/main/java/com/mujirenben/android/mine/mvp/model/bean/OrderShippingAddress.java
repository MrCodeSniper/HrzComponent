package com.mujirenben.android.mine.mvp.model.bean;

import java.io.Serializable;

/**
 * 订单详细信息 - 收件人信息
 */
public class OrderShippingAddress implements Serializable{
	public String userName;
	public String userPhone;
	public String userAddress;
	public String deliverTime;

	public OrderShippingAddress() {

	}

	public OrderShippingAddress(String userName, String userPhone, String userAddress, String deliverTime) {
		this.userName = userName;
		this.userPhone = userPhone;
		this.userAddress = userAddress;
		this.deliverTime = deliverTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	@Override
	public String toString() {
		return "OrderShippingAddress{" +
				"userName='" + userName + '\'' +
				", userPhone='" + userPhone + '\'' +
				", userAddress='" + userAddress + '\'' +
				", deliverTime='" + deliverTime + '\'' +
				'}';
	}
}
