package com.mujirenben.android.discovery.mvp.model.entity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryBean {

    private String code;
    private String msg;
    private boolean success;
    private List<Data> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DiscoveryBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public class Data {

        private long id;
        private String content;
        private long createTime;
        private String type;
        private List<String> imgs;

        private Product product;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", content='" + content + '\'' +
                    ", createTime=" + createTime +
                    ", type='" + type + '\'' +
                    ", imgs=" + imgs +
                    ", product=" + product +
                    '}';
        }
    }

    public class Product {
        private long productId;
        private String platformId;
        private int idType;
        private String name;
        private String img;
        private float orgPrice;
        private float coupon;
        private int platform;
        private float afterPrice;
        private float shareProfit;

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public String getPlatformId() {
            return platformId;
        }

        public void setPlatformId(String platformId) {
            this.platformId = platformId;
        }

        public int getIdType() {
            return idType;
        }

        public void setIdType(int idType) {
            this.idType = idType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public float getOrgPrice() {
            return orgPrice;
        }

        public void setOrgPrice(float orgPrice) {
            this.orgPrice = orgPrice;
        }

        public float getCoupon() {
            return coupon;
        }

        public void setCoupon(float coupon) {
            this.coupon = coupon;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public float getAfterPrice() {
            return afterPrice;
        }

        public void setAfterPrice(float afterPrice) {
            this.afterPrice = afterPrice;
        }

        public float getShareProfit() {
            return shareProfit;
        }

        public void setShareProfit(float shareProfit) {
            this.shareProfit = shareProfit;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "productId=" + productId +
                    ", platformId='" + platformId + '\'' +
                    ", idType=" + idType +
                    ", name='" + name + '\'' +
                    ", img='" + img + '\'' +
                    ", orgPrice=" + orgPrice +
                    ", coupon=" + coupon +
                    ", platform=" + platform +
                    ", afterPrice=" + afterPrice +
                    ", shareProfit=" + shareProfit +
                    '}';
        }
    }
}
