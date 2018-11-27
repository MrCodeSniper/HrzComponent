package com.mujirenben.android.xsh.entity;

import java.util.List;

public class ShopRecommedGoods extends BaseEntity {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * merchantId : 241
         * name : cpu
         * img : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=915733986,1360246095&fm=27&gp=0.jpg
         * price : 10
         * describe : 666
         * status : 1
         * examineStatus : 1
         */

        private int id;
        private int merchantId;
        private String name;
        private String img;
        private double price;
        private String describe;
        private int status;
        private int examineStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getExamineStatus() {
            return examineStatus;
        }

        public void setExamineStatus(int examineStatus) {
            this.examineStatus = examineStatus;
        }
    }
}
