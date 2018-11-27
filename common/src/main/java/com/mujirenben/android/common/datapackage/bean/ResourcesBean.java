package com.mujirenben.android.common.datapackage.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2018/5/12.
 */

public class ResourcesBean {


    @Override
    public String toString() {
        return "ResourcesBean{" +
                "code='" + code + '\'' +
                ", resourceInfo=" + resourceInfo +
                '}';
    }

    /**
     * code : 0
     * resourceInfo : [{"resType":"drawable","resScope":"view","resContentList":[{"localId":"R.drawable.icon","imgUrl":"https://www.baidu.com/img/540-258pad_b4457124d4666cb02363fac26db23f68.png","effectTime_Start":"2018-05-12","effectTime_End":"2018-05-13","effectContext":"FlashActivity"}]},{"resType":"block","resScope":"home","resContentList":[{"templateId":"1","businessId":"wheelBanner","configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":0,"itemDataContent":[{"imageUrl":"https://m.360buyimg.com/babel/jfs/t19249/222/2308009498/68054/d1634987/5af10ff8Nf8c994f5.jpg","clickType":"webview","clickUrl":"https://www.jd.com/","mainTitle":"主标题0","subTitle":"子标题0","sourceFrom":"京东"},{"imageUrl":"https://img.alicdn.com/tfs/TB1E118qGmWBuNjy1XaXXXCbXXa-520-280.png_q90_.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"主标题1","subTitle":"子标题1","sourceFrom":"红人装"}]},{"templateId":"2","businessId":"enterIcon","configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":1,"itemDataContent":[{"imageUrl":"http://chuantu.biz/t6/309/1526090725x-1404793447.jpg","clickType":"webview","clickUrl":"https://www.vip.com/","mainTitle":"主标题0","subTitle":"子标题0","sourceFrom":"唯品会"},{"imageUrl":"http://chuantu.biz/t6/309/1526090787x-1404793447.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"主标题1","subTitle":"子标题1","sourceFrom":"红人装"}]}]},{"resType":"block","resScope":"goodsDetail","resContentList":[{"templateId":"1","businessId":"goods_banner","configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":1,"itemDataContent":[{"imageUrl":"http://chuantu.biz/t6/309/1526090725x-1404793447.jpg","clickType":"webview","clickUrl":"https://www.vip.com/","mainTitle":"主标题0","subTitle":"子标题0","sourceFrom":"唯品会"},{"imageUrl":"http://chuantu.biz/t6/309/1526090787x-1404793447.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"主标题1","subTitle":"子标题1","sourceFrom":"红人装"}]}]},{"resType":"controll","resScope":"global","resContentList":[{"isOpenPay":1},{"isOpenBuy":1},{"isOpenDebug":1}]}]
     */

    private String code;
    private List<ResourceInfoBean> resourceInfo;

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



        @Override
        public String toString() {
            return "ResourceInfoBean{" +
                    "resType='" + resType + '\'' +
                    ", resScope='" + resScope + '\'' +
                    ", resContentList=" + resContentList +
                    '}';
        }

        /**
         * resType : drawable
         * resScope : view
         * resContentList : [{"localId":"R.drawable.icon","imgUrl":"https://www.baidu.com/img/540-258pad_b4457124d4666cb02363fac26db23f68.png","effectTime_Start":"2018-05-12","effectTime_End":"2018-05-13","effectContext":"FlashActivity"}]
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



        public static class ResContentListBean implements MultiItemEntity,Serializable {



            @Override
            public int getItemType() {
                if(Const.TEMPLATE_BANNER==(templateId)){
                    return Const.TEMPLATE_BANNER;
                }
                else if(Const.TEMPLATE_ICONS==(templateId)){
                    return Const.TEMPLATE_ICONS;
                }else if(Const.TEMPLATE_TOUFU==templateId){
                    return Const.TEMPLATE_TOUFU;
                }else if(Const.TEMPLATE_NEWUSEREVENT==templateId){
                    return Const.TEMPLATE_NEWUSEREVENT;
                }else if(Const.TEMPLATE_ACTIVITY_RANK==templateId){
                    return Const.TEMPLATE_ACTIVITY_RANK;
                }else if(Const.TEMPLATE_ACTIVITY_TICKET==templateId){
                    return Const.TEMPLATE_ACTIVITY_TICKET;
                }
//                else if("newUser".equals(itemType)){
//                    return Constant.TYPE_NEW_USER;
//                }
                return Const.TEMPLATE_ICONS;
            }

            @Override
            public String toString() {
                return "ResContentListBean{" +
                        "localId='" + localId + '\'' +
                        ", imgUrl='" + imgUrl + '\'' +
                        ", effectTime_Start='" + effectTime_Start + '\'' +
                        ", effectTime_End='" + effectTime_End + '\'' +
                        ", effectContext='" + effectContext + '\'' +
                        ", isOpenPay=" + isOpenPay +
                        ", isOpenBuy=" + isOpenBuy +
                        ", isOpenDebug=" + isOpenDebug +
                        ", templateId='" + templateId + '\'' +
                        ", businessId='" + businessId + '\'' +
                        ", isShow=" + isShow +
                        ", position=" + position +
                        ", configuration=" + configuration +
                        ", itemDataContent=" + itemDataContent +
                        '}';
            }

            /**
             * localId : R.drawable.icon
             * imgUrl : https://www.baidu.com/img/540-258pad_b4457124d4666cb02363fac26db23f68.png
             * effectTime_Start : 2018-05-12
             * effectTime_End : 2018-05-13
             * effectContext : FlashActivity
             */



            private String localId;
            private String imgUrl;
            private String effectTime_Start;
            private String effectTime_End;
            private String effectContext;
            private int isOpenPay;
            private int isOpenBuy;
            private int isOpenDebug;
            private int templateId;
            private String businessId;
            private int isShow;
            private int position;
            private ConfigurationBean configuration;
            private List<ItemDataContentBean> itemDataContent;


            public int getTemplateId() {
                return templateId;
            }

            public void setTemplateId(int templateId) {
                this.templateId = templateId;
            }

            public int getIsOpenPay() {
                return isOpenPay;
            }

            public void setIsOpenPay(int isOpenPay) {
                this.isOpenPay = isOpenPay;
            }

            public int getIsOpenBuy() {
                return isOpenBuy;
            }

            public void setIsOpenBuy(int isOpenBuy) {
                this.isOpenBuy = isOpenBuy;
            }

            public int getIsOpenDebug() {
                return isOpenDebug;
            }

            public void setIsOpenDebug(int isOpenDebug) {
                this.isOpenDebug = isOpenDebug;
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

            public ConfigurationBean getConfiguration() {
                return configuration;
            }

            public void setConfiguration(ConfigurationBean configuration) {
                this.configuration = configuration;
            }

            public List<ItemDataContentBean> getItemDataContent() {
                return itemDataContent;
            }

            public void setItemDataContent(List<ItemDataContentBean> itemDataContent) {
                this.itemDataContent = itemDataContent;
            }

            public String getLocalId() {
                return localId;
            }

            public void setLocalId(String localId) {
                this.localId = localId;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getEffectTime_Start() {
                return effectTime_Start;
            }

            public void setEffectTime_Start(String effectTime_Start) {
                this.effectTime_Start = effectTime_Start;
            }

            public String getEffectTime_End() {
                return effectTime_End;
            }

            public void setEffectTime_End(String effectTime_End) {
                this.effectTime_End = effectTime_End;
            }

            public String getEffectContext() {
                return effectContext;
            }

            public void setEffectContext(String effectContext) {
                this.effectContext = effectContext;
            }




            public static class ConfigurationBean {


                @Override
                public String toString() {
                    return "ConfigurationBean{" +
                            "backgroundColor='" + backgroundColor + '\'' +
                            ", spanLine='" + spanLine + '\'' +
                            '}';
                }

                /**
                 * backgroundColor : #FFF
                 * spanLine : none
                 */

                private String backgroundColor;
                private String spanLine;

                public String getBackgroundColor() {
                    return backgroundColor;
                }

                public void setBackgroundColor(String backgroundColor) {
                    this.backgroundColor = backgroundColor;
                }

                public String getSpanLine() {
                    return spanLine;
                }

                public void setSpanLine(String spanLine) {
                    this.spanLine = spanLine;
                }
            }

            public static class ItemDataContentBean {

                @Override
                public String toString() {
                    return "ItemDataContentBean{" +
                            "imageUrl='" + imageUrl + '\'' +
                            ", clickType='" + clickType + '\'' +
                            ", clickUrl='" + clickUrl + '\'' +
                            ", mainTitle='" + mainTitle + '\'' +
                            ", subTitle='" + subTitle + '\'' +
                            ", sourceFrom='" + sourceFrom + '\'' +
                            '}';
                }

                /**
                 * imageUrl : https://m.360buyimg.com/babel/jfs/t19249/222/2308009498/68054/d1634987/5af10ff8Nf8c994f5.jpg
                 * clickType : webview
                 * clickUrl : https://www.jd.com/
                 * mainTitle : 主标题0
                 * subTitle : 子标题0
                 * sourceFrom : 京东
                 */



                private String imageUrl;
                private String clickType;
                private String clickUrl;
                private String mainTitle;
                private String subTitle;
                private String sourceFrom;

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
