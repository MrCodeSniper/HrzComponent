package com.mujirenben.android.xsh.entity;

import java.util.List;

/**
 * 最新上架result
 * Created by Administrator on 2017/6/27 0027.
 */

public class BestNewProSjResult {

    private int status;
    private String reason;
    private Data data;

    public class Data{

        private int pageAll;
        private List<Goods> goodslist;

        public int getPageAll() {
            return pageAll;
        }

        public void setPageAll(int pageAll) {
            this.pageAll = pageAll;
        }

        public List<Goods> getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(List<Goods> goodslist) {
            this.goodslist = goodslist;
        }
    }


    public class Goods{
        private String type;
        private String goodsid;
        private String thumb;
        private String price;
        private String shoufa;
        private String title;
        private String profile;
        private String tmall;

        public String getTmall() {
            return tmall;
        }

        public void setTmall(String tmall) {
            this.tmall = tmall;
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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getShoufa() {
            return shoufa;
        }

        public void setShoufa(String shoufa) {
            this.shoufa = shoufa;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
