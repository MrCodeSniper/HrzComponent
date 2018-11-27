package com.mujirenben.android.home.mvp.model.entity;

import com.ch.android.resource.Configuration;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mujirenben.android.common.util.NumberUtils;

import java.io.Serializable;
import java.util.List;

public class HomeModuleBean implements MultiItemEntity, Serializable {

    private String itemType;

    private String code;
    private String message;
    private boolean success;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "HomeModuleBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    @Override
    public int getItemType() {
        if ("topBanner".equals(itemType)) {
            return Configuration.Constant.TYPE_TOP_BANNER;
        } else if ("iconList".equals(itemType)) {
            return Configuration.Constant.TYPE_ICON_LIST;
        } else if ("newUser".equals(itemType)) {
            return Configuration.Constant.TYPE_NEW_USER;
        } else if ("showEvent".equals(itemType)) {
            return Configuration.Constant.TYPE_SHOW_EVENT_3;
        } else if ("findGoodShop".equals(itemType)) {
            return Configuration.Constant.TYPE_FIND_GOOD_SHOP;
        }

        return Configuration.Constant.TYPE_TOP_BANNER;
    }

    public static class DataBean implements MultiItemEntity, Serializable {

        private String itemType;

        private long id;
        private int sequence;
        private String title;
        private String url;
        private String startTime;
        private String endTime;
        private String img;
        private String subtitle;
        private int flag;

        // 瀑布流
        private List<Product> products;
        private List<Shop> shops;
        private List<Advertise> advertises;

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public List<Shop> getShops() {
            return shops;
        }

        public void setShops(List<Shop> shops) {
            this.shops = shops;
        }

        public List<Advertise> getAdvertises() {
            return advertises;
        }

        public void setAdvertises(List<Advertise> advertises) {
            this.advertises = advertises;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "itemType='" + itemType + '\'' +
                    ", id=" + id +
                    ", sequence=" + sequence +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", img='" + img + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", flag=" + flag +
                    ", products=" + products +
                    ", shops=" + shops +
                    ", advertises=" + advertises +
                    '}';
        }

        @Override
        public int getItemType() {
            if ("活动项目".equals(itemType)) {
                return Configuration.Constant.TYPE_WATERFALL_ACTIVITY;
            }
            return Configuration.Constant.TYPE_WATERFALL_GOODS;
        }

        public static class Product {
            private long id;
            private String productName;
            private String img;
            private float orgPrice;
            private float coupon;
            private float maxCommission;
            private int platform;
            private int saleVolume;
            private float afterPrice;

            @Override
            public String toString() {
                return "Product{" +
                        "id=" + id +
                        ", productName='" + productName + '\'' +
                        ", img='" + img + '\'' +
                        ", orgPrice=" + NumberUtils.floatToString(orgPrice) +
                        ", coupon=" + NumberUtils.floatToString(coupon) +
                        ", maxCommission=" + NumberUtils.floatToString(maxCommission) +
                        ", platform=" + platform +
                        ", saleVolume=" + saleVolume +
                        ", afterPrice=" + NumberUtils.floatToString(afterPrice) +
                        '}';
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
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

            public float getMaxCommission() {
                return maxCommission;
            }

            public void setMaxCommission(float maxCommission) {
                this.maxCommission = maxCommission;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public int getSaleVolume() {
                return saleVolume;
            }

            public void setSaleVolume(int saleVolume) {
                this.saleVolume = saleVolume;
            }

            public float getAfterPrice() {
                return afterPrice;
            }

            public void setAfterPrice(float afterPrice) {
                this.afterPrice = afterPrice;
            }

        }

        public static class Shop {
            private String shopName;
            private String shopUrl;
            private String shopImg;

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopUrl() {
                return shopUrl;
            }

            public void setShopUrl(String shopUrl) {
                this.shopUrl = shopUrl;
            }

            public String getShopImg() {
                return shopImg;
            }

            public void setShopImg(String shopImg) {
                this.shopImg = shopImg;
            }

            @Override
            public String toString() {
                return "Shop{" +
                        "shopName='" + shopName + '\'' +
                        ", shopUrl='" + shopUrl + '\'' +
                        ", shopImg='" + shopImg + '\'' +
                        '}';
            }
        }

        public static class Advertise {
            private String url;
            private String advertiseName;
            private String img;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getAdvertiseName() {
                return advertiseName;
            }

            public void setAdvertiseName(String advertiseName) {
                this.advertiseName = advertiseName;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            @Override
            public String toString() {
                return "Advertise{" +
                        "url='" + url + '\'' +
                        ", advertiseName='" + advertiseName + '\'' +
                        ", img='" + img + '\'' +
                        '}';
            }
        }
    }
}
