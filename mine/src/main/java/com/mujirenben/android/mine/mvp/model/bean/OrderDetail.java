package com.mujirenben.android.mine.mvp.model.bean;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/24.Best Wishes to You!  []~(~▽~)~* Cheers!


import java.util.List;

public class OrderDetail {


    /**
     * code : 00000
     * message : null
     * success : true
     * data : {"subOrderInfo":[{"orderStatus":1,"productId":1000000015,"productTitle":"百搭浅色小直筒牛仔裤","productSpec":"粉色,M码","productNum":4,"productPrice":"200","productImg":"http://192.168.5.175:8081/productImages/TB1tOQpEWSWBuNjSsrbSuu0mVXa.jpg_300x300q75.jpg"},{"orderStatus":2,"productId":1000000010,"productTitle":"休闲男士九分束脚裤子","productSpec":"粉色,M码","productNum":3,"productPrice":"300","productImg":"http://192.168.5.175:8081/productImages/TB1K6UPEL1TBuNjy0Fj1rmjyXXa.JPG_300x300q75.jpg"},{"orderStatus":1,"productId":1000000014,"productTitle":"迷彩破洞夏季牛仔短裤","productSpec":"粉色,M码","productNum":4,"productPrice":"300","productImg":"http://192.168.5.175:8081/productImages/TB1t6CDkFooBKNjSZPhSuw2CXXa.jpg_300x300q75.jpg"},{"orderStatus":1,"productId":1000000004,"productTitle":"阿茶与阿花黑花卉衬衫","productSpec":"粉色,M码","productNum":4,"productPrice":"300","productImg":"http://192.168.5.175:8081/productImages/TB1fmcSbWmWBuNjy1XaSutCbXXa.jpg_300x300q75.jpg"},{"orderStatus":1,"productId":1000000006,"productTitle":"阿茶与阿古的条纹衬衫","productSpec":"粉色,M码","productNum":4,"productPrice":"200","productImg":"http://192.168.5.175:8081/productImages/TB1Fq5XtwmTBuNjy1XbSuuMrVXa.jpg_300x300q75.jpg"},{"orderStatus":3,"productId":1000000014,"productTitle":"迷彩破洞夏季牛仔短裤","productSpec":"粉色,M码","productNum":4,"productPrice":"200","productImg":"http://192.168.5.175:8081/productImages/TB1t6CDkFooBKNjSZPhSuw2CXXa.jpg_300x300q75.jpg"}],"status":1,"consumerAward":"35.95","arriveTime":1532868182,"discounts":"10.0","count":19,"rawPrice":"4900","userId":102905671,"userName":"比尔盖茨2852","thirdpartId":4,"parentOrderId":1194026403309802,"createTime":1532858182,"earningTime":1532004254,"shopName":"店铺3","shopId":"3","payPrice":"4862.14","activityId":0,"activityName":"满减","orderStatus":3}
     */

    private String code;
    private String message;
    private boolean success;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * subOrderInfo : [{"orderStatus":1,"productId":1000000015,"productTitle":"百搭浅色小直筒牛仔裤","productSpec":"粉色,M码","productNum":4,"productPrice":"200","productImg":"http://192.168.5.175:8081/productImages/TB1tOQpEWSWBuNjSsrbSuu0mVXa.jpg_300x300q75.jpg"},{"orderStatus":2,"productId":1000000010,"productTitle":"休闲男士九分束脚裤子","productSpec":"粉色,M码","productNum":3,"productPrice":"300","productImg":"http://192.168.5.175:8081/productImages/TB1K6UPEL1TBuNjy0Fj1rmjyXXa.JPG_300x300q75.jpg"},{"orderStatus":1,"productId":1000000014,"productTitle":"迷彩破洞夏季牛仔短裤","productSpec":"粉色,M码","productNum":4,"productPrice":"300","productImg":"http://192.168.5.175:8081/productImages/TB1t6CDkFooBKNjSZPhSuw2CXXa.jpg_300x300q75.jpg"},{"orderStatus":1,"productId":1000000004,"productTitle":"阿茶与阿花黑花卉衬衫","productSpec":"粉色,M码","productNum":4,"productPrice":"300","productImg":"http://192.168.5.175:8081/productImages/TB1fmcSbWmWBuNjy1XaSutCbXXa.jpg_300x300q75.jpg"},{"orderStatus":1,"productId":1000000006,"productTitle":"阿茶与阿古的条纹衬衫","productSpec":"粉色,M码","productNum":4,"productPrice":"200","productImg":"http://192.168.5.175:8081/productImages/TB1Fq5XtwmTBuNjy1XbSuuMrVXa.jpg_300x300q75.jpg"},{"orderStatus":3,"productId":1000000014,"productTitle":"迷彩破洞夏季牛仔短裤","productSpec":"粉色,M码","productNum":4,"productPrice":"200","productImg":"http://192.168.5.175:8081/productImages/TB1t6CDkFooBKNjSZPhSuw2CXXa.jpg_300x300q75.jpg"}]
         * status : 1
         * consumerAward : 35.95
         * arriveTime : 1532868182
         * discounts : 10.0
         * count : 19
         * rawPrice : 4900
         * userId : 102905671
         * userName : 比尔盖茨2852
         * thirdpartId : 4
         * parentOrderId : 1194026403309802
         * createTime : 1532858182
         * earningTime : 1532004254
         * shopName : 店铺3
         * shopId : 3
         * payPrice : 4862.14
         * activityId : 0
         * activityName : 满减
         * orderStatus : 3
         */

        private List<SubOrderInfoBean> subOrderInfo;
        private int status;
        private String consumerAward;
        private long arriveTime;
        private String discounts;
        private int count;
        private String userName;
        private long userId;
        private int thirdpartId;
        private String parentOrderId;
        private long createTime;
        private long earningTime;
        private String shopName;
        private String shopId;
        private String payPrice;
        private int activityId;
        private String activityName;

        private String rawPrice;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<SubOrderInfoBean> getSubOrderInfo() {
            return subOrderInfo;
        }

        public void setSubOrderInfo(List<SubOrderInfoBean> subOrderInfo) {
            this.subOrderInfo = subOrderInfo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getConsumerAward() {
            return consumerAward;
        }

        public void setConsumerAward(String consumerAward) {
            this.consumerAward = consumerAward;
        }

        public long getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(long arriveTime) {
            this.arriveTime = arriveTime;
        }

        public String getDiscounts() {
            return discounts;
        }

        public void setDiscounts(String discounts) {
            this.discounts = discounts;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
        }

        public String getRawPrice() {
            return rawPrice == null ? "0.00" : rawPrice;
        }

        public void setRawPrice(String rawPrice) {
            this.rawPrice = rawPrice;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getThirdpartId() {
            return thirdpartId;
        }

        public void setThirdpartId(int thirdpartId) {
            this.thirdpartId = thirdpartId;
        }

        public String getParentOrderId() {
            return parentOrderId;
        }

        public void setParentOrderId(String parentOrderId) {
            this.parentOrderId = parentOrderId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getEarningTime() {
            return earningTime;
        }

        public void setEarningTime(long earningTime) {
            this.earningTime = earningTime;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "subOrderInfo=" + subOrderInfo +
                    ", status=" + status +
                    ", consumerAward='" + consumerAward + '\'' +
                    ", arriveTime=" + arriveTime +
                    ", discounts='" + discounts + '\'' +
                    ", count=" + count +
                    ", userName='" + userName + '\'' +
                    ", userId=" + userId +
                    ", thirdpartId=" + thirdpartId +
                    ", parentOrderId=" + parentOrderId +
                    ", createTime=" + createTime +
                    ", earningTime=" + earningTime +
                    ", shopName='" + shopName + '\'' +
                    ", shopId='" + shopId + '\'' +
                    ", payPrice='" + payPrice + '\'' +
                    ", activityId=" + activityId +
                    ", activityName='" + activityName + '\'' +
                    ", rawPrice='" + rawPrice + '\'' +
                    '}';
        }

        public static class SubOrderInfoBean {
            /**
             * orderStatus : 1
             * productId : 1000000015
             * productTitle : 百搭浅色小直筒牛仔裤
             * productSpec : 粉色,M码
             * productNum : 4
             * productPrice : 200
             * productImg : http://192.168.5.175:8081/productImages/TB1tOQpEWSWBuNjSsrbSuu0mVXa.jpg_300x300q75.jpg
             */

            private int orderStatus;
            private long productId;
            private String productTitle;
            private String productSpec;
            private int productNum;
            private String productPrice;
            private String productImg;

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public long getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductTitle() {
                return productTitle;
            }

            public void setProductTitle(String productTitle) {
                this.productTitle = productTitle;
            }

            public String getProductSpec() {
                return productSpec;
            }

            public void setProductSpec(String productSpec) {
                this.productSpec = productSpec;
            }

            public int getProductNum() {
                return productNum;
            }

            public void setProductNum(int productNum) {
                this.productNum = productNum;
            }

            public String getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(String productPrice) {
                this.productPrice = productPrice;
            }

            public String getProductImg() {
                return productImg;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }

            @Override
            public String toString() {
                return "SubOrderInfoBean{" +
                        "orderStatus=" + orderStatus +
                        ", productId=" + productId +
                        ", productTitle='" + productTitle + '\'' +
                        ", productSpec='" + productSpec + '\'' +
                        ", productNum=" + productNum +
                        ", productPrice='" + productPrice + '\'' +
                        ", productImg='" + productImg + '\'' +
                        '}';
            }
        }
    }


}
