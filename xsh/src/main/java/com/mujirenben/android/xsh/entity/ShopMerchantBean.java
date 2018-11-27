package com.mujirenben.android.xsh.entity;

import java.util.List;

public class ShopMerchantBean extends BaseEntity {

    @Override
    public String toString() {
        return "ShopMerchantBean{" +
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

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", merchantId=" + merchantId +
                    ", couponId=" + couponId +
                    ", beginTime=" + beginTime +
                    ", endTime=" + endTime +
                    ", minPrice=" + minPrice +
                    ", discounted=" + discounted +
                    ", type=" + type +
                    ", useType=" + useType +
                    ", receType=" + receType +
                    '}';
        }

        /**
         * id : 15
         * merchantId : 240
         * couponId : 5
         * beginTime : 1234334000
         * endTime : 1531464734000
         * minPrice : 32.12
         * discounted : 25.12
         * type : 1
         * useType : 1
         * receType : null
         */





        private int id;
        private int merchantId;
        private int couponId;
        private long beginTime;
        private long endTime;
        private float minPrice;
        private Float discounted;
        private int type;
        private int useType;
        private int receType;

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

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public float getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(float minPrice) {
            this.minPrice = minPrice;
        }

        public Float getDiscounted() {
            return discounted;
        }

        public void setDiscounted(float discounted) {
            this.discounted = discounted;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUseType() {
            return useType;
        }

        public void setUseType(int useType) {
            this.useType = useType;
        }

        public int getReceType() {
            return receType;
        }

        public void setReceType(int receType) {
            this.receType = receType;
        }
    }




}
