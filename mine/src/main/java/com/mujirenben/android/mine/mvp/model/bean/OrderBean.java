package com.mujirenben.android.mine.mvp.model.bean;

import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.mine.mvp.ui.activity.OrderListActivity;

import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/7/19 20:15
 * @describe:
 */

public class OrderBean {


//    @Override
//    public String toString() {
//        return "OrderBean{" +
//                "code='" + code + '\'' +
//                ", message='" + message + '\'' +
//                ", success=" + success +
//                ", data=" + data +
//                '}';
//    }
//
//    /**
//     * code : 00000
//     * message : null
//     * success : true
//     * data : {"parentOrderInfo":[{"subOrderInfo":[{"orderId":1222515370076629,"parentOrderId":5341801535656491,"productId":1000000011,"productTitle":"国潮反光束脚裤休闲裤","productSpec":"粉色,M码","productNum":1,"productPrice":"105.0","productImg":"http://xiaoding-pc:8081/productImages/TB1KM7BFDJYBeNjy1zeSuuhzVXa.jpg_300x300q75.jpg"}],"status":0,"consumerAward":"584.44","arriveTime":1532862802,"discounts":"2.0","count":1,"totalPrice":"756.9","thirdpartId":1,"shopName":"店铺3"},{"subOrderInfo":[{"orderId":4707871990778899,"parentOrderId":6033098396875987,"productId":1000000008,"productTitle":"纯亚麻宽松男士短裤","productSpec":"粉色,M码","productNum":1,"productPrice":"78.0","productImg":"http://xiaoding-pc:8081/productImages/TB1ikWtFr1YBuNjSszeSuublFXa.jpg_300x300q75.jpg"}],"status":0,"consumerAward":"640.71","arriveTime":1533012013,"discounts":"2.0","count":1,"totalPrice":"556.66","thirdpartId":1,"shopName":"店铺1"},{"subOrderInfo":[{"orderId":4497034411884237,"parentOrderId":8054459712518807,"productId":1000000010,"productTitle":"休闲男士九分束脚裤子","productSpec":"粉色,M码","productNum":2,"productPrice":"125.0","productImg":"http://xiaoding-pc:8081/productImages/TB1K6UPEL1TBuNjy0Fj1rmjyXXa.JPG_300x300q75.jpg"}],"status":0,"consumerAward":"642.84","arriveTime":1532852454,"discounts":"2.0","count":1,"totalPrice":"466.58","thirdpartId":1,"shopName":"店铺3"},{"subOrderInfo":[{"orderId":5185032532905504,"parentOrderId":1194026403309802,"productId":1000000015,"productTitle":"百搭浅色小直筒牛仔裤","productSpec":"粉色,M码","productNum":4,"productPrice":"201","productImg":"http://xiaoding-pc:8081/productImages/TB1tOQpEWSWBuNjSsrbSuu0mVXa.jpg_300x300q75.jpg"},{"orderId":5938335422225915,"parentOrderId":1194026403309802,"productId":1000000010,"productTitle":"休闲男士九分束脚裤子","productSpec":"粉色,M码","productNum":3,"productPrice":"125.0","productImg":"http://xiaoding-pc:8081/productImages/TB1K6UPEL1TBuNjy0Fj1rmjyXXa.JPG_300x300q75.jpg"},{"orderId":4227537353505642,"parentOrderId":1194026403309802,"productId":1000000014,"productTitle":"迷彩破洞夏季牛仔短裤","productSpec":"粉色,M码","productNum":4,"productPrice":"65.0","productImg":"http://xiaoding-pc:8081/productImages/TB1t6CDkFooBKNjSZPhSuw2CXXa.jpg_300x300q75.jpg"},{"orderId":2231366838851921,"parentOrderId":1194026403309802,"productId":1000000004,"productTitle":"阿茶与阿花黑花卉衬衫","productSpec":"粉色,M码","productNum":4,"productPrice":"129.0","productImg":"http://xiaoding-pc:8081/productImages/TB1fmcSbWmWBuNjy1XaSutCbXXa.jpg_300x300q75.jpg"},{"orderId":7920817747015033,"parentOrderId":1194026403309802,"productId":1000000006,"productTitle":"阿茶与阿古的条纹衬衫","productSpec":"粉色,M码","productNum":4,"productPrice":"129.0","productImg":"http://xiaoding-pc:8081/productImages/TB1Fq5XtwmTBuNjy1XbSuuMrVXa.jpg_300x300q75.jpg"},{"orderId":9392280818345526,"parentOrderId":1194026403309802,"productId":1000000014,"productTitle":"迷彩破洞夏季牛仔短裤","productSpec":"粉色,M码","productNum":4,"productPrice":"65.0","productImg":"http://xiaoding-pc:8081/productImages/TB1t6CDkFooBKNjSZPhSuw2CXXa.jpg_300x300q75.jpg"}],"status":0,"consumerAward":"3115.99","arriveTime":1532868182,"discounts":"12.0","count":6,"totalPrice":"3703.52","thirdpartId":4,"shopName":"店铺3"},{"subOrderInfo":[{"orderId":6578232853995493,"parentOrderId":6249378119827600,"productId":1000000005,"productTitle":"夏威夷印花衬衫短袖","productSpec":"粉色,M码","productNum":4,"productPrice":"238.0","productImg":"http://xiaoding-pc:8081/productImages/TB1Fn2LldcnBKNjSZR0SuwFqFXa.jpg_300x300q75.jpg"}],"status":0,"consumerAward":"218.59","arriveTime":1533041797,"discounts":"2.0","count":1,"totalPrice":"155.77","thirdpartId":2,"shopName":"淘宝店铺"},{"subOrderInfo":[{"orderId":7074553730652081,"parentOrderId":8858129665123419,"productId":1000000006,"productTitle":"阿茶与阿古的条纹衬衫","productSpec":"粉色,M码","productNum":4,"productPrice":"129.0","productImg":"http://xiaoding-pc:8081/productImages/TB1Fq5XtwmTBuNjy1XbSuuMrVXa.jpg_300x300q75.jpg"}],"status":0,"consumerAward":"214.61","arriveTime":1532925526,"discounts":"2.0","count":1,"totalPrice":"135.91","thirdpartId":2,"shopName":"店铺3"},{"subOrderInfo":[{"orderId":6146058923132811,"parentOrderId":5971562285881082,"productId":1000000000,"productTitle":"刺绣印花棉麻拼接T恤","productSpec":"粉色,M码","productNum":3,"productPrice":"75.0","productImg":"http://xiaoding-pc:8081/productImages/TB1acYjxXmWBuNjSspdSuvugXXa.jpg_300x300q75.jpg"}],"status":0,"consumerAward":"333.96","arriveTime":1532809594,"discounts":"2.0","count":1,"totalPrice":"246.59","thirdpartId":3,"shopName":"店铺1"},{"subOrderInfo":[{"orderId":6124085040883573,"parentOrderId":5543089505790816,"productId":1000000009,"productTitle":"快手红人格子九分裤男","productSpec":"粉色,M码","productNum":3,"productPrice":"55.0","productImg":"http://xiaoding-pc:8081/productImages/TB1jHQVpNSYBuNjSspjSut73VXa.jpg_300x300q75.jpg"}],"status":0,"consumerAward":"446.8","arriveTime":1532895159,"discounts":"2.0","count":1,"totalPrice":"822.15","thirdpartId":1,"shopName":"店铺1"},{"subOrderInfo":[{"orderId":2184841212791782,"parentOrderId":2796217189911955,"productId":1000000012,"productTitle":"水洗牛仔男士五分短裤","productSpec":"粉色,M码","productNum":4,"productPrice":"114.0","productImg":"http://xiaoding-pc:8081/productImages/TB1pnQZiYZnBKNjSZFKSuwGOVXa.jpg_300x300q75.jpg"}],"status":0,"consumerAward":"714.43","arriveTime":1532843146,"discounts":"2.0","count":1,"totalPrice":"631.27","thirdpartId":3,"shopName":"店铺2"},{"subOrderInfo":[{"orderId":3152854410003099,"parentOrderId":5165215019290536,"productId":1000000013,"productTitle":"韩版直筒水洗牛仔短裤","productSpec":"粉色,M码","productNum":3,"productPrice":"56.84","productImg":"http://xiaoding-pc:8081/productImages/TB1R_BCwOOYBuNjSsD4SuvSkFXa.jpg_300x300q75.jpg"}],"status":0,"consumerAward":"922.23","arriveTime":1533032430,"discounts":"2.0","count":1,"totalPrice":"996.51","thirdpartId":4,"shopName":"店铺2"}]}
//     */
//
//    private String code;
//    private String message;
//    private boolean success;
//    private OrderListBean data;
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public OrderListBean getData() {
//        return data;
//    }
//
//    public void setData(OrderListBean data) {
//        this.data = data;
//    }
//
//    public static class OrderListBean {
//
//
//        @Override
//        public String toString() {
//            return "OrderListBean{" +
//                    "parentOrderInfo=" + parentOrderInfo +
//                    '}';
//        }
//
//        private List<ParentOrderInfoBean> parentOrderInfo;
//
//        public List<ParentOrderInfoBean> getParentOrderInfo() {
//            return parentOrderInfo;
//        }
//
//        public void setParentOrderInfo(List<ParentOrderInfoBean> parentOrderInfo) {
//            this.parentOrderInfo = parentOrderInfo;
//        }
//
//        public static class ParentOrderInfoBean {
//            /**
//             * subOrderInfo : [{"orderId":1222515370076629,"parentOrderId":5341801535656491,"productId":1000000011,"productTitle":"国潮反光束脚裤休闲裤","productSpec":"粉色,M码","productNum":1,"productPrice":"105.0","productImg":"http://xiaoding-pc:8081/productImages/TB1KM7BFDJYBeNjy1zeSuuhzVXa.jpg_300x300q75.jpg"}]
//             * status : 0
//             * consumerAward : 584.44
//             * arriveTime : 1532862802
//             * discounts : 2.0
//             * count : 1
//             * totalPrice : 756.9
//             * thirdpartId : 1
//             * shopName : 店铺3
//             */
//
//

//
//
//            @Override
//            public String toString() {
//                return "ParentOrderInfoBean{" +
//                        "status=" + status +
//                        ", consumerAward='" + consumerAward + '\'' +
//                        ", arriveTime=" + arriveTime +
//                        ", discounts='" + discounts + '\'' +
//                        ", count=" + count +
//                        ", totalPrice='" + totalPrice + '\'' +
//                        ", thirdpartId=" + thirdpartId +
//                        ", shopName='" + shopName + '\'' +
//                        ", subOrderInfo=" + subOrderInfo +
//                        '}';
//            }
//            private String parentOrderId;
//            private int status;
//            private String consumerAward;
//            private long arriveTime;
//            private String discounts;
//            private int count;
//            private String payPrice;
//            private String totalPrice;
//            private int thirdpartId;
//            private String shopName;
//            private List<SubOrderInfoBean> subOrderInfo;
//            private String shopId;
//
//
//            public String getShopId() {
//                return shopId;
//            }
//
//            public void setShopId(String shopId) {
//                this.shopId = shopId;
//            }
//
//            public String getParentOrderId() {
//                return parentOrderId;
//            }
//
//            public void setParentOrderId(String parentOrderId) {
//                this.parentOrderId = parentOrderId;
//            }
//
//            public String getPayPrice() {
//                return payPrice;
//            }
//
//            public void setPayPrice(String payPrice) {
//                this.payPrice = payPrice;
//            }
//
//            public int getStatus() {
//                return status;
//            }
//
//            public void setStatus(int status) {
//                this.status = status;
//            }
//
//            public String getConsumerAward() {
//                return consumerAward;
//            }
//
//            public void setConsumerAward(String consumerAward) {
//                this.consumerAward = consumerAward;
//            }
//
//            public long getArriveTime() {
//                return arriveTime;
//            }
//
//            public void setArriveTime(long arriveTime) {
//                this.arriveTime = arriveTime;
//            }
//
//            public String getDiscounts() {
//                return discounts;
//            }
//
//            public void setDiscounts(String discounts) {
//                this.discounts = discounts;
//            }
//
//            public int getCount() {
//                return count;
//            }
//
//            public void setCount(int count) {
//                this.count = count;
//            }
//
//            public String getTotalPrice() {
//                return totalPrice;
//            }
//
//            public void setTotalPrice(String totalPrice) {
//                this.totalPrice = totalPrice;
//            }
//
//            public int getThirdpartId() {
//                return thirdpartId;
//            }
//
//            public void setThirdpartId(int thirdpartId) {
//                this.thirdpartId = thirdpartId;
//            }
//
//            public String getShopName() {
//                return shopName;
//            }
//
//            public void setShopName(String shopName) {
//                this.shopName = shopName;
//            }
//
//            public List<SubOrderInfoBean> getSubOrderInfo() {
//                return subOrderInfo;
//            }
//
//            public void setSubOrderInfo(List<SubOrderInfoBean> subOrderInfo) {
//                this.subOrderInfo = subOrderInfo;
//            }
//
//            public static class SubOrderInfoBean {
//                /**
//                 * orderId : 1222515370076629
//                 * parentOrderId : 5341801535656491
//                 * productId : 1000000011
//                 * productTitle : 国潮反光束脚裤休闲裤
//                 * productSpec : 粉色,M码
//                 * productNum : 1
//                 * productPrice : 105.0
//                 * productImg : http://xiaoding-pc:8081/productImages/TB1KM7BFDJYBeNjy1zeSuuhzVXa.jpg_300x300q75.jpg
//                 */
//
//                private String orderId;
//                private String productId;
//                private String productTitle;
//                private String productSpec;
//                private int productNum;
//                private String productPrice;
//                private String productImg;
//                private int orderStatus;
//
//
//                public int getOrderStatus() {
//                    return orderStatus;
//                }
//
//                public void setOrderStatus(int orderStatus) {
//                    this.orderStatus = orderStatus;
//                }
//
//                public String getOrderId() {
//                    return orderId;
//                }
//
//                public void setOrderId(String orderId) {
//                    this.orderId = orderId;
//                }
//
//
//
//                public String getProductId() {
//                    return productId;
//                }
//
//                public void setProductId(String productId) {
//                    this.productId = productId;
//                }
//
//                public String getProductTitle() {
//                    return productTitle;
//                }
//
//                public void setProductTitle(String productTitle) {
//                    this.productTitle = productTitle;
//                }
//
//                public String getProductSpec() {
//                    return productSpec;
//                }
//
//                public void setProductSpec(String productSpec) {
//                    this.productSpec = productSpec;
//                }
//
//                public int getProductNum() {
//                    return productNum;
//                }
//
//                public void setProductNum(int productNum) {
//                    this.productNum = productNum;
//                }
//
//                public String getProductPrice() {
//                    return productPrice;
//                }
//
//                public void setProductPrice(String productPrice) {
//                    this.productPrice = productPrice;
//                }
//
//                public String getProductImg() {
//                    return productImg;
//                }
//
//                public void setProductImg(String productImg) {
//                    this.productImg = productImg;
//                }
//
//
//                @Override
//                public String toString() {
//                    return "SubOrderInfoBean{" +
//                            "orderId=" + orderId +
//                            ", productId=" + productId +
//                            ", productTitle='" + productTitle + '\'' +
//                            ", productSpec='" + productSpec + '\'' +
//                            ", productNum=" + productNum +
//                            ", productPrice='" + productPrice + '\'' +
//                            ", productImg='" + productImg + '\'' +
//                            '}';
//                }
//            }
//        }
//    }


    @Override
    public String toString() {
        return "OrderBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

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

        @Override
        public String toString() {
            return "DataBean{" +
                    "parentOrderId='" + parentOrderId + '\'' +
                    ", status=" + status +
                    ", consumerAward='" + consumerAward + '\'' +
                    ", arriveTime=" + arriveTime +
                    ", discounts='" + discounts + '\'' +
                    ", count=" + count +
                    ", payPrice='" + payPrice + '\'' +
                    ", thirdpartId=" + thirdpartId +
                    ", shopId='" + shopId + '\'' +
                    ", shopName='" + shopName + '\'' +
                    ", subOrderInfo=" + subOrderInfo +
                    '}';
        }

        private String parentOrderId;
        private int status;
        private String consumerAward;
        private long arriveTime;
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

        public long getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(long  arriveTime) {
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
            @Override
            public String toString() {
                return "SubOrderInfoBean{" +
                        "orderId='" + orderId + '\'' +
                        ", productId=" + productId +
                        ", productTitle='" + productTitle + '\'' +
                        ", productSpec='" + productSpec + '\'' +
                        ", productNum=" + productNum +
                        ", productPrice='" + productPrice + '\'' +
                        ", productImg='" + productImg + '\'' +
                        ", orderStatus=" + orderStatus +
                        '}';
            }



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

    public static String getOrderStatusStr(int status,int platform){

        String status_str="--";
        if(platform== Const.Platform.HRZ){
            //自营
            switch (status){
                case Consts.HRZ_SELF_ORDER_STATUS.PAY_ING:
                    status_str= Consts.HRZ_SELF_ORDER_STATUS_STR.PAY_ING;
                    break;
                case Consts.HRZ_SELF_ORDER_STATUS.ALREADY_PAY:
                    status_str=Consts.HRZ_SELF_ORDER_STATUS_STR.ALREADY_PAY;
                    break;
                case Consts.HRZ_SELF_ORDER_STATUS.PAY_FAIL:
                    status_str=Consts.HRZ_SELF_ORDER_STATUS_STR.PAY_FAIL;
                    break;
            }
        }else {
            switch (status){
                case Consts.HRZ_ORDER_STATUS.ORDER_SUCCESS:
                    status_str= Consts.HRZ_ORDER_STATUS_STR.ORDER_SUCCESS;
                    break;
                case Consts.HRZ_ORDER_STATUS.ORDER_FINISH:
                    status_str=Consts.HRZ_ORDER_STATUS_STR.ORDER_FINISH;
                    break;
                case Consts.HRZ_ORDER_STATUS.ORDER_INVALID:
                    status_str=Consts.HRZ_ORDER_STATUS_STR.ORDER_INVALID;
                    break;
            }
        }

        return status_str;
    }




    public static String getOrderStatus(int status){
        String status_str= OrderListActivity.OrderType.ALL.type;
        if(status==1){
            status_str= OrderListActivity.OrderType.SUCCESS.type;
        }else if(status==2){
            status_str= OrderListActivity.OrderType.FINISH.type;
        }else if(status==3){
            status_str=OrderListActivity.OrderType.INVALID.type;
        }else {
            status_str="返回的订单状态码不是1,2,3";
        }
        return status_str;
    }


    public static String getThirdPartyName(int thirdpartId){
        String thirdParty= Const.PlatformName.ZIYING;
        if(thirdpartId==1){
            thirdParty= Const.PlatformName.ZIYING;
        }else if(thirdpartId==2){
            thirdParty= Const.PlatformName.TAOBAO;
        }else if(thirdpartId==3){
            thirdParty=Const.PlatformName.JINGDONG;
        }else if(thirdpartId==4){
            thirdParty=Const.PlatformName.WEIPINHUI;
        }else if(thirdpartId==5){
            thirdParty=Const.PlatformName.OFFLINE;
        }
        return thirdParty;
    }

}
