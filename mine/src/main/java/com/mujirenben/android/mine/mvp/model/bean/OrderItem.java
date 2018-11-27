package com.mujirenben.android.mine.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

public class OrderItem implements Serializable {
	public String  orderStatus;
	public float productTotal;
	public float reward;
	public float discount;
	public float freight;
	public float realTotal;
	public String attachInfo;

	public List<OrderProductItem> orderProductList;
	public OrderPayInfo orderPayInfo;
	public OrderShippingAddress orderShippingAddress;

	public OrderItem() {

	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public float getProductTotal() {
		return productTotal;
	}

	public void setProductTotal(float productTotal) {
		this.productTotal = productTotal;
	}

	public float getReward() {
		return reward;
	}

	public void setReward(float reward) {
		this.reward = reward;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getFreight() {
		return freight;
	}

	public void setFreight(float freight) {
		this.freight = freight;
	}

	public float getRealTotal() {
		return realTotal;
	}

	public void setRealTotal(float realTotal) {
		this.realTotal = realTotal;
	}

	public String getAttachInfo() {
		return attachInfo;
	}

	public void setAttachInfo(String attachInfo) {
		this.attachInfo = attachInfo;
	}

	public List<OrderProductItem> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<OrderProductItem> orderProductList) {
		this.orderProductList = orderProductList;
	}

	public OrderPayInfo getOrderPayInfo() {
		return orderPayInfo;
	}

	public void setOrderPayInfo(OrderPayInfo orderPayInfo) {
		this.orderPayInfo = orderPayInfo;
	}

	public OrderShippingAddress getOrderShippingAddress() {
		return orderShippingAddress;
	}

	public void setOrderShippingAddress(OrderShippingAddress orderShippingAddress) {
		this.orderShippingAddress = orderShippingAddress;
	}

	@Override
	public String toString() {
		return "OrderItem{" +
				"orderStatus='" + orderStatus + '\'' +
				", productTotal=" + productTotal +
				", reward=" + reward +
				", discount=" + discount +
				", freight=" + freight +
				", realTotal=" + realTotal +
				", attachInfo='" + attachInfo + '\'' +
				", orderProductList=" + orderProductList +
				", orderPayInfo=" + orderPayInfo +
				", orderShippingAddress=" + orderShippingAddress +
				'}';
	}
}
