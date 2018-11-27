package com.mujirenben.android.xsh.entity;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mujirenben.android.xsh.constant.Constants;

import java.util.List;

public class ShopDetailBean {


    /**
     * code : 0
     * resourceInfo : [{"resType":"shopsDetail","resScope":"Alliance","resContentList":[{"templateId":2,"businessId":"shopName","isShow":0,"position":1,"itemDataContent":[{"mainTitle":"店铺名称店铺名称店铺名称店","subTitle":"子店铺名","sourceFrom":"红人装"}]},{"templateId":1,"businessId":"Banner","isShow":1,"position":0,"itemDataContent":[{"imageUrl":"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1529475102&di=8b1ca8f9d4e0ee09627e55d31f5140bc&src=http://pic.58pic.com/58pic/12/97/77/88J58PICwqY.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"}]},{"templateId":3,"businessId":"shopDetailParam","isShow":1,"position":2,"itemDataContent":[{"bussinesType":"西餐","city":"广州","averagePrice":120}]},{"templateId":4,"businessId":"shopPics","isShow":1,"position":3,"itemDataContent":[{"imageUrl":"https://img13.360buyimg.com/n7/jfs/t19585/182/2579614246/216906/b669638f/5afced42N397104cb.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n7/jfs/t17599/148/1789034295/271289/8bdbd5bd/5ad875d4N23049c2c.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/n7/jfs/t17137/134/1253764714/149950/acf159b1/5ac1bf58Ndefaac16.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/n7/jfs/t17773/222/1772357349/201925/627b888c/5ad8614bN7b9187f7.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"}]},{"templateId":5,"businessId":"shopBussinessSituation","isShow":1,"position":4,"itemDataContent":[{"bussinessTime":"11:00-21:00","WifiEnable":1,"ParkEnable":1}]},{"templateId":6,"businessId":"location","isShow":1,"position":5,"itemDataContent":[{"mainTitle":"广州市天河区巴拉拉拉的地址地址地址地地址地址地址地址地址地址地址","subTitle":"banner_rank","sourceFrom":"红人装"}]},{"templateId":7,"businessId":"commonTicket","isShow":1,"position":6,"itemDataContent":[{"ticketUperLimit":120,"ticketType":1,"ticketAmount":10,"mainTitle":"可使用优惠券规则","subTitle":"满120减10","sourceFrom":"红人装"},{"ticketUperLimit":198,"ticketType":2,"ticketAmount":20,"mainTitle":"可使用优惠券规则","subTitle":"满198减20","sourceFrom":"红人装"},{"ticketUperLimit":258,"ticketType":3,"ticketAmount":30,"mainTitle":"可使用优惠券规则","subTitle":"满258减30","sourceFrom":"红人装"}]},{"templateId":8,"businessId":"redPocket","isShow":1,"position":7,"itemDataContent":[{"redPocketTimeLimit":"2018.06.01~2018.06.20","redPocketType":1,"redPocketAmount":3,"mainTitle":"无门槛红包","subTitle":"满258减30","sourceFrom":"红人装"},{"redPocketTimeLimit":"2018.06.01~2018.06.20","redPocketType":2,"redPocketAmount":10,"mainTitle":"无门槛红包","subTitle":"满258减30","sourceFrom":"红人装"},{"redPocketTimeLimit":"2018.06.01~2018.06.20","redPocketType":3,"redPocketAmount":30,"mainTitle":"无门槛红包","subTitle":"满258减30","sourceFrom":"红人装"}]},{"templateId":9,"businessId":"shopMenu","isShow":1,"position":8,"itemDataContent":[{"imageUrl":"https://img10.360buyimg.com/n7/jfs/t277/193/1005339798/768456/29136988/542d0798N19d42ce3.jpg","menuType":"招牌","clickType":"webview","clickUrl":"http://www.itmfb.com/","mainTitle":"辣味奶油番茄面","subTitle":"满100减10","sourceFrom":"红人装"},{"imageUrl":"https://img12.360buyimg.com/n7/jfs/t21415/332/642302956/189613/778f2021/5b13cd6cN8e12d4aa.jpg","menuType":"新品","clickType":"webview","clickUrl":"http://www.itmfb.com/","mainTitle":"甜醋玉米排骨","subTitle":"满100减10","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/n7/jfs/t13345/152/782244800/176860/f8b54c8b/5a13d2e3N6b6311f5.jpg","menuType":"招牌","clickType":"webview","clickUrl":"http://www.itmfb.com/","mainTitle":"鲜虾堡","subTitle":"满100减10","sourceFrom":"红人装"}]}]}]
     */

    private String code;
    private List<ResourceInfoBean> resourceInfo;

    @Override
    public String toString() {
        return "ShopDetailBean{" +
                "code='" + code + '\'' +
                ", resourceInfo=" + resourceInfo +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResourceInfoBean> getResourceInfo() {
        return resourceInfo;
    }

    public void setResourceInfo(List<ResourceInfoBean> resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

    public static class ResourceInfoBean {
        /**
         * resType : shopsDetail
         * resScope : Alliance
         * resContentList : [{"templateId":2,"businessId":"shopName","isShow":0,"position":1,"itemDataContent":[{"mainTitle":"店铺名称店铺名称店铺名称店","subTitle":"子店铺名","sourceFrom":"红人装"}]},{"templateId":1,"businessId":"Banner","isShow":1,"position":0,"itemDataContent":[{"imageUrl":"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1529475102&di=8b1ca8f9d4e0ee09627e55d31f5140bc&src=http://pic.58pic.com/58pic/12/97/77/88J58PICwqY.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"}]},{"templateId":3,"businessId":"shopDetailParam","isShow":1,"position":2,"itemDataContent":[{"bussinesType":"西餐","city":"广州","averagePrice":120}]},{"templateId":4,"businessId":"shopPics","isShow":1,"position":3,"itemDataContent":[{"imageUrl":"https://img13.360buyimg.com/n7/jfs/t19585/182/2579614246/216906/b669638f/5afced42N397104cb.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n7/jfs/t17599/148/1789034295/271289/8bdbd5bd/5ad875d4N23049c2c.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/n7/jfs/t17137/134/1253764714/149950/acf159b1/5ac1bf58Ndefaac16.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/n7/jfs/t17773/222/1772357349/201925/627b888c/5ad8614bN7b9187f7.jpg","clickType":"photo","clickUrl":"https://www.hrz.com/","mainTitle":"显示banner图片1","subTitle":"子标题0","sourceFrom":"红人装"}]},{"templateId":5,"businessId":"shopBussinessSituation","isShow":1,"position":4,"itemDataContent":[{"bussinessTime":"11:00-21:00","WifiEnable":1,"ParkEnable":1}]},{"templateId":6,"businessId":"location","isShow":1,"position":5,"itemDataContent":[{"mainTitle":"广州市天河区巴拉拉拉的地址地址地址地地址地址地址地址地址地址地址","subTitle":"banner_rank","sourceFrom":"红人装"}]},{"templateId":7,"businessId":"commonTicket","isShow":1,"position":6,"itemDataContent":[{"ticketUperLimit":120,"ticketType":1,"ticketAmount":10,"mainTitle":"可使用优惠券规则","subTitle":"满120减10","sourceFrom":"红人装"},{"ticketUperLimit":198,"ticketType":2,"ticketAmount":20,"mainTitle":"可使用优惠券规则","subTitle":"满198减20","sourceFrom":"红人装"},{"ticketUperLimit":258,"ticketType":3,"ticketAmount":30,"mainTitle":"可使用优惠券规则","subTitle":"满258减30","sourceFrom":"红人装"}]},{"templateId":8,"businessId":"redPocket","isShow":1,"position":7,"itemDataContent":[{"redPocketTimeLimit":"2018.06.01~2018.06.20","redPocketType":1,"redPocketAmount":3,"mainTitle":"无门槛红包","subTitle":"满258减30","sourceFrom":"红人装"},{"redPocketTimeLimit":"2018.06.01~2018.06.20","redPocketType":2,"redPocketAmount":10,"mainTitle":"无门槛红包","subTitle":"满258减30","sourceFrom":"红人装"},{"redPocketTimeLimit":"2018.06.01~2018.06.20","redPocketType":3,"redPocketAmount":30,"mainTitle":"无门槛红包","subTitle":"满258减30","sourceFrom":"红人装"}]},{"templateId":9,"businessId":"shopMenu","isShow":1,"position":8,"itemDataContent":[{"imageUrl":"https://img10.360buyimg.com/n7/jfs/t277/193/1005339798/768456/29136988/542d0798N19d42ce3.jpg","menuType":"招牌","clickType":"webview","clickUrl":"http://www.itmfb.com/","mainTitle":"辣味奶油番茄面","subTitle":"满100减10","sourceFrom":"红人装"},{"imageUrl":"https://img12.360buyimg.com/n7/jfs/t21415/332/642302956/189613/778f2021/5b13cd6cN8e12d4aa.jpg","menuType":"新品","clickType":"webview","clickUrl":"http://www.itmfb.com/","mainTitle":"甜醋玉米排骨","subTitle":"满100减10","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/n7/jfs/t13345/152/782244800/176860/f8b54c8b/5a13d2e3N6b6311f5.jpg","menuType":"招牌","clickType":"webview","clickUrl":"http://www.itmfb.com/","mainTitle":"鲜虾堡","subTitle":"满100减10","sourceFrom":"红人装"}]}]
         */

        private String resType;
        private String resScope;
        private List<ResContentListBean> resContentList;

        public String getResType() {
            return resType;
        }

        public void setResType(String resType) {
            this.resType = resType;
        }

        public String getResScope() {
            return resScope;
        }

        public void setResScope(String resScope) {
            this.resScope = resScope;
        }

        public List<ResContentListBean> getResContentList() {
            return resContentList;
        }

        public void setResContentList(List<ResContentListBean> resContentList) {
            this.resContentList = resContentList;
        }


        @Override
        public String toString() {
            return "ResourceInfoBean{" +
                    "resType='" + resType + '\'' +
                    ", resScope='" + resScope + '\'' +
                    ", resContentList=" + resContentList +
                    '}';
        }

        public static class ResContentListBean implements MultiItemEntity {
            /**
             * templateId : 2
             * businessId : shopName
             * isShow : 0
             * position : 1
             * itemDataContent : [{"mainTitle":"店铺名称店铺名称店铺名称店","subTitle":"子店铺名","sourceFrom":"红人装"}]
             */

            private int templateId;
            private String businessId;
            private int isShow;
            private int position;
            private List<ItemDataContentBean> itemDataContent;


            @Override
            public String toString() {
                return "ResContentListBean{" +
                        "templateId=" + templateId +
                        ", businessId='" + businessId + '\'' +
                        ", isShow=" + isShow +
                        ", position=" + position +
                        ", itemDataContent=" + itemDataContent +
                        '}';
            }

            public int getTemplateId() {
                return templateId;
            }

            public void setTemplateId(int templateId) {
                this.templateId = templateId;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public List<ItemDataContentBean> getItemDataContent() {
                return itemDataContent;
            }

            public void setItemDataContent(List<ItemDataContentBean> itemDataContent) {
                this.itemDataContent = itemDataContent;
            }

            @Override
            public int getItemType() {
                if ("Banner".equals(businessId)) {
                    return Constants.TYPE_ALLIANCE_SHOPDETAIL_BANNER;
                } else if ("shopName".equals(businessId)) {
                    return Constants.TYPE_ALLIANCE_SHOPDETAIL_SHOPNAME;
                }else if("shopDetailParam".equals(businessId)){
                    return Constants.TYPE_ALLIANCE_SHOPDETAIL_SHOPDETAILPARAM;
                }else if("shopPics".equals(businessId)){
                    return Constants.TYPE_ALLIANCE_SHOPDETAIL_SHOPPICS;
                }else if("shopBussinessSituation".equals(businessId)){
                    return Constants.TYPE_ALLIANCE_SHOPDETAIL_BUSSINESS_SITUATION;
                }else if("location".equals(businessId)){
                    return Constants.TYPE_ALLIANCE_SHOPDETAIL_LOCATION;
                }else if("commonTicket".equals(businessId)){
                    return Constants.TYPE_ALLIANCE_SHOPDETAIL_COMMONTICKET;
                }else if("redPocket".equals(businessId)){
                    return Constants.TYPE_ALLIANCE_SHOPDETAIL_REDPOCKET;
                }else if("shopMenu".equals(businessId)){
                    return Constants.TYPE_ALLIANCE_SHOPDETAIL_SHOPMENU;
                }else if("shopDiscount".equals(businessId)){
                    return Constants.TYPE_ALLIANCEE_SHOPDETAIL_DISCOUNT_SHOPS;
                }
                return Constants.TYPE_ALLIANCE_SHOPDETAIL_EMPTY;
            }




            public static class ItemDataContentBean {
                /**
                 * mainTitle : 店铺名称店铺名称店铺名称店
                 * subTitle : 子店铺名
                 * sourceFrom : 红人装
                 */

                private String mainTitle;
                private String subTitle;
                private String sourceFrom;
                private String imageUrl;
                private String clickType;
                private String clickUrl;
                private String bussinesType;
                private String city;
                private String bussinessTime;
                private double averagePrice;
                private int WifiEnable;
                private int ParkEnable;
                private int ticketType;
                private double ticketUperLimit;
                private Double ticketAmount;
                private String redPocketTimeLimit;
                private int redPocketType;
                private double redPocketAmount;
                private String menuType;
                private String shopLocation;
                private String distance;

                @Override
                public String toString() {
                    return "ItemDataContentBean{" +
                            "mainTitle='" + mainTitle + '\'' +
                            ", subTitle='" + subTitle + '\'' +
                            ", sourceFrom='" + sourceFrom + '\'' +
                            ", imageUrl='" + imageUrl + '\'' +
                            ", clickType='" + clickType + '\'' +
                            ", clickUrl='" + clickUrl + '\'' +
                            ", bussinesType='" + bussinesType + '\'' +
                            ", city='" + city + '\'' +
                            ", bussinessTime='" + bussinessTime + '\'' +
                            ", averagePrice=" + averagePrice +
                            ", WifiEnable=" + WifiEnable +
                            ", ParkEnable=" + ParkEnable +
                            ", ticketType=" + ticketType +
                            ", ticketUperLimit=" + ticketUperLimit +
                            ", ticketAmount=" + ticketAmount +
                            ", redPocketTimeLimit='" + redPocketTimeLimit + '\'' +
                            ", redPocketType=" + redPocketType +
                            ", redPocketAmount=" + redPocketAmount +
                            ", menuType='" + menuType + '\'' +
                            ", shopLocation='" + shopLocation + '\'' +
                            ", distance='" + distance + '\'' +
                            '}';
                }

                public String getShopLocation() {
                    return shopLocation;
                }

                public void setShopLocation(String shopLocation) {
                    this.shopLocation = shopLocation;
                }

                public String getDistance() {
                    return distance;
                }

                public void setDistance(String distance) {
                    this.distance = distance;
                }

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getClickType() {
                    return clickType;
                }

                public void setClickType(String clickType) {
                    this.clickType = clickType;
                }

                public String getClickUrl() {
                    return clickUrl;
                }

                public void setClickUrl(String clickUrl) {
                    this.clickUrl = clickUrl;
                }

                public String getBussinesType() {
                    return bussinesType;
                }

                public void setBussinesType(String bussinesType) {
                    this.bussinesType = bussinesType;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getBussinessTime() {
                    return bussinessTime;
                }

                public void setBussinessTime(String bussinessTime) {
                    this.bussinessTime = bussinessTime;
                }

                public double getAveragePrice() {
                    return averagePrice;
                }

                public void setAveragePrice(double averagePrice) {
                    this.averagePrice = averagePrice;
                }

                public int getWifiEnable() {
                    return WifiEnable;
                }

                public void setWifiEnable(int wifiEnable) {
                    WifiEnable = wifiEnable;
                }

                public int getParkEnable() {
                    return ParkEnable;
                }

                public void setParkEnable(int parkEnable) {
                    ParkEnable = parkEnable;
                }

                public int getTicketType() {
                    return ticketType;
                }

                public void setTicketType(int ticketType) {
                    this.ticketType = ticketType;
                }

                public double getTicketUperLimit() {
                    return ticketUperLimit;
                }

                public void setTicketUperLimit(double ticketUperLimit) {
                    this.ticketUperLimit = ticketUperLimit;
                }

                public Double getTicketAmount() {
                    return ticketAmount;
                }

                public void setTicketAmount(Double ticketAmount) {
                    this.ticketAmount = ticketAmount;
                }

                public String getRedPocketTimeLimit() {
                    return redPocketTimeLimit;
                }

                public void setRedPocketTimeLimit(String redPocketTimeLimit) {
                    this.redPocketTimeLimit = redPocketTimeLimit;
                }

                public int getRedPocketType() {
                    return redPocketType;
                }

                public void setRedPocketType(int redPocketType) {
                    this.redPocketType = redPocketType;
                }

                public double getRedPocketAmount() {
                    return redPocketAmount;
                }

                public void setRedPocketAmount(double redPocketAmount) {
                    this.redPocketAmount = redPocketAmount;
                }

                public String getMenuType() {
                    return menuType;
                }

                public void setMenuType(String menuType) {
                    this.menuType = menuType;
                }

                public String getMainTitle() {
                    return mainTitle;
                }

                public void setMainTitle(String mainTitle) {
                    this.mainTitle = mainTitle;
                }

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public String getSourceFrom() {
                    return sourceFrom;
                }

                public void setSourceFrom(String sourceFrom) {
                    this.sourceFrom = sourceFrom;
                }


            }
        }
    }
}

