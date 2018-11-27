package com.mujirenben.android.vip.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Author: panrongfu
 * Time:2018/6/21 16:04
 * Description: vip 信息页面
 */

public class VipHeaderInfo {


    /**
     * code : 0
     * vipInfo : [{"itemType":"banner","module":"banner","resContentList":[{"imageUrl":"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1529475102&di=8b1ca8f9d4e0ee09627e55d31f5140bc&src=http://pic.58pic.com/58pic/12/97/77/88J58PICwqY.jpg","subTitle":"轮播图片","sourceFrom":"红人装"}]},{"itemType":"share","module":"share","resContentList":[{"imageUrl":"","subTitle":"分享赚","describe":"购物返佣20%"},{"imageUrl":"","subTitle":"购物赚","describe":"购物返佣20%"},{"imageUrl":"","subTitle":"分享赚","describe":"分享得高佣金%"},{"imageUrl":"","subTitle":"粉丝赚","describe":"好友购物也赚"}]},{"itemType":"notice","module":"notice","resContentList":[{"subTitle":"公告文案","imageUrl":""}]},{"itemType":"shopList","module":"shopList","resContentList":[]}]
     */

    private String code;
    private int roleType;
    private List<VipInfoBean> vipInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<VipInfoBean> getVipInfo() {
        return vipInfo;
    }

    public void setVipInfo(List<VipInfoBean> vipInfo) {
        this.vipInfo = vipInfo;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public static class VipInfoBean implements MultiItemEntity{
        /**
         * itemType : banner
         * module : banner
         * resContentList : [{"imageUrl":"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1529475102&di=8b1ca8f9d4e0ee09627e55d31f5140bc&src=http://pic.58pic.com/58pic/12/97/77/88J58PICwqY.jpg","subTitle":"轮播图片","sourceFrom":"红人装"}]
         */

        private String itemType;
        private String module;
        private List<ResContentListBean> resContentList;

        @Override
        public int getItemType() {
            if("banner".equals(itemType)){
                return Constant.VIP_TYPE_BANNER;
            }else if("share".equals(itemType)){
                return Constant.VIP_TYPE_SHARE;
            }else if("notice".equals(itemType)){
                return Constant.VIP_TYPE_NOTICE;
            }
            return Constant.VIP_TYPE_BANNER;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public List<ResContentListBean> getResContentList() {
            return resContentList;
        }

        public void setResContentList(List<ResContentListBean> resContentList) {
            this.resContentList = resContentList;
        }

        public static class ResContentListBean {
            /**
             * imageUrl : https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1529475102&di=8b1ca8f9d4e0ee09627e55d31f5140bc&src=http://pic.58pic.com/58pic/12/97/77/88J58PICwqY.jpg
             * subTitle : 轮播图片
             * sourceFrom : 红人装
             */

            private String imageUrl;
            private String subTitle;
            private String sourceFrom;
            private String describe;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
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

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }
        }
    }
}
