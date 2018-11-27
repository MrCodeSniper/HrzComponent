package com.mujirenben.android.mine.mvp.model.bean;

import java.io.Serializable;

/**
 * 订单详细信息 - 支付信息
 */
public class OrderPayInfo implements Serializable {
	public String serialNumber;
	public String orderTime;
	public String payType;
	public String payTime;
	public String transTime; // 成交时间
	public String rewardTime;

	public OrderPayInfo() {
	}

	public OrderPayInfo(String serialNumber, String orderTime, String payType, String payTime, String transTime, String rewardTime) {
		this.serialNumber = serialNumber;
		this.orderTime = orderTime;
		this.payType = payType;
		this.payTime = payTime;
		this.transTime = transTime;
		this.rewardTime = rewardTime;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(String rewardTime) {
		this.rewardTime = rewardTime;
	}

	@Override
	public String toString() {
		return "OrderPayInfo{" +
				"serialNumber='" + serialNumber + '\'' +
				", orderTime='" + orderTime + '\'' +
				", payType='" + payType + '\'' +
				", payTime='" + payTime + '\'' +
				", transTime='" + transTime + '\'' +
				", rewardTime='" + rewardTime + '\'' +
				'}';
	}
}
