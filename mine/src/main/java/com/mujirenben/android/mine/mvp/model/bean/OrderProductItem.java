package com.mujirenben.android.mine.mvp.model.bean;

import java.io.Serializable;

/**
 * 订单详细信息 - 单个商品信息
 */
public class OrderProductItem implements Serializable {
	public String platform;
	public String storeName;
	public String activityType;
	public String iconUrl;
	public String title;
	public String color;
	public String size;
	public int count;
	public float priceOri;
	public float priceSell;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getPriceOri() {
		return priceOri;
	}

	public void setPriceOri(float priceOri) {
		this.priceOri = priceOri;
	}

	public float getPriceSell() {
		return priceSell;
	}

	public void setPriceSell(float priceSell) {
		this.priceSell = priceSell;
	}

	@Override
	public String toString() {
		return "OrderProductItem{" +
				"platform='" + platform + '\'' +
				", storeName='" + storeName + '\'' +
				", activityType='" + activityType + '\'' +
				", iconUrl='" + iconUrl + '\'' +
				", title='" + title + '\'' +
				", color='" + color + '\'' +
				", size='" + size + '\'' +
				", count=" + count +
				", priceOri=" + priceOri +
				", priceSell=" + priceSell +
				'}';
	}
}
