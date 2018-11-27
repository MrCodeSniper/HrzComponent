package com.mujirenben.android.mine.mvp.model.bean;

import java.util.List;

/**
 * 订单详细信息页面，一个订单主要包含以下信息
 *
 * 1， 商品列表
 * 2， 订单汇总信息：数量，金额，优惠。。。
 * 3， 交易信息：日期，支付方式，流水号。。
 * 4， 手机地址信息：收件人，手机号，地址。。
 */
public class OrderInfo {

	private int statusCode;
	private List<OrderItem> data;

	public OrderInfo(int statusCode, List<OrderItem> data) {
		this.statusCode = statusCode;
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public List<OrderItem> getData() {
		return data;
	}

	public void setData(List<OrderItem> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "OrderInfo{" +
				"statusCode=" + statusCode +
				", data=" + data +
				'}';
	}
}
