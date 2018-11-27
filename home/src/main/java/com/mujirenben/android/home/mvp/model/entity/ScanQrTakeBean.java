package com.mujirenben.android.home.mvp.model.entity;

/**
 * @author: panrongfu
 * @date: 2018/9/11 16:39
 * @describe:
 */

public class ScanQrTakeBean {

    /**
     * status : 201
     * data : {"labelid":"13333","type":"kucun","goodsid":"19676","price":"0.01","profile":"0","profileHeight":"0","taobaourl":"http://apiv2.tlgn365.com//v1/share/goodsOffline?goodsid=19676","read":"http://apiv2.tlgn365.com/v1/jiaocheng/kucunfanxian10"}
     * reason : 操作成功
     */

    private String status;
    private DataBean data;
    private String reason;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static class DataBean {
        /**
         * labelid : 13333
         * type : kucun
         * goodsid : 19676
         * price : 0.01
         * profile : 0
         * profileHeight : 0
         * taobaourl : http://apiv2.tlgn365.com//v1/share/goodsOffline?goodsid=19676
         * read : http://apiv2.tlgn365.com/v1/jiaocheng/kucunfanxian10
         */

        private String labelid;
        private String type;
        private String goodsid;
        private String price;
        private String profile;
        private String profileHeight;
        private String taobaourl;
        private String read;

        public String getLabelid() {
            return labelid;
        }

        public void setLabelid(String labelid) {
            this.labelid = labelid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getProfileHeight() {
            return profileHeight;
        }

        public void setProfileHeight(String profileHeight) {
            this.profileHeight = profileHeight;
        }

        public String getTaobaourl() {
            return taobaourl;
        }

        public void setTaobaourl(String taobaourl) {
            this.taobaourl = taobaourl;
        }

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }
    }
}
