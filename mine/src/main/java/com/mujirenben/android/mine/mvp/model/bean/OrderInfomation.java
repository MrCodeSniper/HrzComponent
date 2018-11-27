package com.mujirenben.android.mine.mvp.model.bean;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/9/8.Best Wishes to You!  []~(~▽~)~* Cheers!


import java.util.List;

public class OrderInfomation {


    /**
     * code : 00000
     * message : null
     * success : true
     * data : [{"subOrderInfo":[{"orderId":"1031869704999931904","productId":25,"productTitle":"物格夏装新款大码女装文艺宽松水洗做旧印花翻领薄牛仔休闲套装女","productSpec":"","productNum":1,"productPrice":"189.2","productImg":"http://img.alicdn.com/bao/uploaded/i3/48779051/TB2tI9XdN9YBuNjy0FfXXXIsVXa_!!48779051.jpg","orderStatus":1}],"parentOrderId":"188022522791019022","status":1,"consumerAward":"50.00","arriveTime":1536734048,"discounts":"0","count":1,"payPrice":"90.00","thirdpartId":2,"shopId":"9866022983","shopName":"安少旗舰店"},{"subOrderInfo":[{"orderId":"1031869882494488576","productId":53,"productTitle":"洛可可2018夏装新款碎花阔腿裤女撞色条纹腰边休闲裤度假风直筒裤 白花 M","productSpec":"","productNum":1,"productPrice":"199.0","productImg":"https://img11.360buyimg.com/evalpic/jfs/t19501/295/1302649543/228623/82dcd9/5ac499c0N14edc26c.jpg","orderStatus":1}],"parentOrderId":"1031869882494488576","status":1,"consumerAward":"52.91","arriveTime":26886810719,"discounts":"0","count":1,"payPrice":"0","thirdpartId":3,"shopId":"123123","shopName":"京东店铺"},{"subOrderInfo":[{"orderId":"1032243541134413824","productId":29,"productTitle":"丽丽lily2018秋新款女装莉莉印花衬衣长袖宽松衬衫118130C4612","productSpec":"","productNum":1,"productPrice":"229.0","productImg":"http://img.alicdn.com/bao/uploaded/i4/1031105204/TB1wytodZic_eJjSZFnXXXVwVXa_!!0-item_pic.jpg","orderStatus":1}],"parentOrderId":"198022522791019027","status":1,"consumerAward":"0","arriveTime":1533107213,"discounts":"0","count":1,"payPrice":"100.00","thirdpartId":2,"shopId":"8279867984","shopName":"安少旗舰店"},{"subOrderInfo":[{"orderId":"1032235625090256896","productId":29,"productTitle":"丽丽lily2018秋新款女装莉莉印花衬衣长袖宽松衬衫118130C4612","productSpec":"","productNum":2,"productPrice":"229.0","productImg":"http://img.alicdn.com/bao/uploaded/i4/1031105204/TB1wytodZic_eJjSZFnXXXVwVXa_!!0-item_pic.jpg","orderStatus":1}],"parentOrderId":"198022522791019026","status":1,"consumerAward":"5.00","arriveTime":1536734048,"discounts":"0","count":2,"payPrice":"100.00","thirdpartId":2,"shopId":"1247875278","shopName":"安少旗舰店"},{"subOrderInfo":[{"orderId":"1031871002805669888","productId":53,"productTitle":"洛可可2018夏装新款碎花阔腿裤女撞色条纹腰边休闲裤度假风直筒裤 白花 M","productSpec":"","productNum":1,"productPrice":"199.0","productImg":"https://img11.360buyimg.com/evalpic/jfs/t19501/295/1302649543/228623/82dcd9/5ac499c0N14edc26c.jpg","orderStatus":1}],"parentOrderId":"1031871002805669888","status":1,"consumerAward":"52.91","arriveTime":26886810719,"discounts":"0","count":1,"payPrice":"0","thirdpartId":3,"shopId":"123123","shopName":"京东店铺"}]
     */

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

    public static class DataBean {
        /**
         * subOrderInfo : [{"orderId":"1031869704999931904","productId":25,"productTitle":"物格夏装新款大码女装文艺宽松水洗做旧印花翻领薄牛仔休闲套装女","productSpec":"","productNum":1,"productPrice":"189.2","productImg":"http://img.alicdn.com/bao/uploaded/i3/48779051/TB2tI9XdN9YBuNjy0FfXXXIsVXa_!!48779051.jpg","orderStatus":1}]
         * parentOrderId : 188022522791019022
         * status : 1
         * consumerAward : 50.00
         * arriveTime : 1536734048
         * discounts : 0
         * count : 1
         * payPrice : 90.00
         * thirdpartId : 2
         * shopId : 9866022983
         * shopName : 安少旗舰店
         */

        private String parentOrderId;
        private int status;
        private String consumerAward;
        private int arriveTime;
        private String discounts;
        private int count;
        private String payPrice;
        private int thirdpartId;
        private String shopId;
        private String shopName;
        private List<SubOrderInfoBean> subOrderInfo;

        public String getParentOrderId() {
            return parentOrderId;
        }

        public void setParentOrderId(String parentOrderId) {
            this.parentOrderId = parentOrderId;
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

        public int getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(int arriveTime) {
            this.arriveTime = arriveTime;
        }

        public String getDiscounts() {
            return discounts;
        }

        public void setDiscounts(String discounts) {
            this.discounts = discounts;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
        }

        public int getThirdpartId() {
            return thirdpartId;
        }

        public void setThirdpartId(int thirdpartId) {
            this.thirdpartId = thirdpartId;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public List<SubOrderInfoBean> getSubOrderInfo() {
            return subOrderInfo;
        }

        public void setSubOrderInfo(List<SubOrderInfoBean> subOrderInfo) {
            this.subOrderInfo = subOrderInfo;
        }

        public static class SubOrderInfoBean {
            /**
             * orderId : 1031869704999931904
             * productId : 25
             * productTitle : 物格夏装新款大码女装文艺宽松水洗做旧印花翻领薄牛仔休闲套装女
             * productSpec :
             * productNum : 1
             * productPrice : 189.2
             * productImg : http://img.alicdn.com/bao/uploaded/i3/48779051/TB2tI9XdN9YBuNjy0FfXXXIsVXa_!!48779051.jpg
             * orderStatus : 1
             */

            private String orderId;
            private int productId;
            private String productTitle;
            private String productSpec;
            private int productNum;
            private String productPrice;
            private String productImg;
            private int orderStatus;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getProductId() {
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

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }
        }
    }
}
