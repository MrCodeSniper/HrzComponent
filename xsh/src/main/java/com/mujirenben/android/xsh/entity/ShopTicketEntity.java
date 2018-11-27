package com.mujirenben.android.xsh.entity;

import java.util.List;

/**
 * Created by Cyj on 2018/8/3.
 */
public class ShopTicketEntity extends BaseEntity {

    @Override
    public String toString() {
        return "ShopTicketEntity{" +
                "data=" + data +
                '}';
    }

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public  class DataBean {


        private int id;
        private int merchantId;
        private Object couponId;
        private Object name;
        private Object beginTime;
        private long endTime;
        private Object minPrice;
        private double discounted;
        private Object type;
        private Object useType;
        private Object receType;
        private String storeName;
        private int distance;
        private Object storefrontImg;
        private int status;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", merchantId=" + merchantId +
                    ", couponId=" + couponId +
                    ", name=" + name +
                    ", beginTime=" + beginTime +
                    ", endTime=" + endTime +
                    ", minPrice=" + minPrice +
                    ", discounted=" + discounted +
                    ", type=" + type +
                    ", useType=" + useType +
                    ", receType=" + receType +
                    ", storeName='" + storeName + '\'' +
                    ", distance=" + distance +
                    ", storefrontImg=" + storefrontImg +
                    ", status=" + status +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public Object getCouponId() {
            return couponId;
        }

        public void setCouponId(Object couponId) {
            this.couponId = couponId;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public Object getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Object minPrice) {
            this.minPrice = minPrice;
        }

        public double getDiscounted() {
            return discounted;
        }

        public void setDiscounted(double discounted) {
            this.discounted = discounted;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getUseType() {
            return useType;
        }

        public void setUseType(Object useType) {
            this.useType = useType;
        }

        public Object getReceType() {
            return receType;
        }

        public void setReceType(Object receType) {
            this.receType = receType;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public Object getStorefrontImg() {
            return storefrontImg;
        }

        public void setStorefrontImg(Object storefrontImg) {
            this.storefrontImg = storefrontImg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
