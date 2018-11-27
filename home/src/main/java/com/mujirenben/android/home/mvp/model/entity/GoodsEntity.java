package com.mujirenben.android.home.mvp.model.entity;

import java.util.List;

/**
 * Created by mac on 2018/5/9.
 */

public class GoodsEntity {

    /**
     * error_code : 0
     * reason : 请求成功！
     * result : {"summary":{"barcode":"4891028164456","name":"维他奶柠檬茶","imgurl":"http://d6.yihaodianimg.com/N02/M0A/3D/5F/CgQCsVFSmqKAHccSAANDJLGgAPQ37800_200x200.jpg","shopNum":0,"eshopNum":1,"interval":"￥:2.5"},"eshop":[{"price":2.5,"shopname":"1号店","dsid":5}]}
     */

    private int error_code;
    private String reason;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "GoodsEntity{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }

    public static class ResultBean {
        /**
         * summary : {"barcode":"4891028164456","name":"维他奶柠檬茶","imgurl":"http://d6.yihaodianimg.com/N02/M0A/3D/5F/CgQCsVFSmqKAHccSAANDJLGgAPQ37800_200x200.jpg","shopNum":0,"eshopNum":1,"interval":"￥:2.5"}
         * eshop : [{"price":2.5,"shopname":"1号店","dsid":5}]
         */

        private SummaryBean summary;
        private List<EshopBean> eshop;

        public SummaryBean getSummary() {
            return summary;
        }

        public void setSummary(SummaryBean summary) {
            this.summary = summary;
        }

        public List<EshopBean> getEshop() {
            return eshop;
        }

        public void setEshop(List<EshopBean> eshop) {
            this.eshop = eshop;
        }


        @Override
        public String toString() {
            return "ResultBean{" +
                    "summary=" + summary +
                    ", eshop=" + eshop +
                    '}';
        }

        public static class SummaryBean {
            /**
             * barcode : 4891028164456
             * name : 维他奶柠檬茶
             * imgurl : http://d6.yihaodianimg.com/N02/M0A/3D/5F/CgQCsVFSmqKAHccSAANDJLGgAPQ37800_200x200.jpg
             * shopNum : 0
             * eshopNum : 1
             * interval : ￥:2.5
             */

            private String barcode;
            private String name;
            private String imgurl;
            private int shopNum;
            private int eshopNum;
            private String interval;

            public String getBarcode() {
                return barcode;
            }

            public void setBarcode(String barcode) {
                this.barcode = barcode;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public int getShopNum() {
                return shopNum;
            }

            public void setShopNum(int shopNum) {
                this.shopNum = shopNum;
            }

            public int getEshopNum() {
                return eshopNum;
            }

            public void setEshopNum(int eshopNum) {
                this.eshopNum = eshopNum;
            }

            public String getInterval() {
                return interval;
            }

            public void setInterval(String interval) {
                this.interval = interval;
            }


            @Override
            public String toString() {
                return "SummaryBean{" +
                        "barcode='" + barcode + '\'' +
                        ", name='" + name + '\'' +
                        ", imgurl='" + imgurl + '\'' +
                        ", shopNum=" + shopNum +
                        ", eshopNum=" + eshopNum +
                        ", interval='" + interval + '\'' +
                        '}';
            }
        }

        public static class EshopBean {
            /**
             * price : 2.5
             * shopname : 1号店
             * dsid : 5
             */

            private double price;
            private String shopname;
            private int dsid;

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public int getDsid() {
                return dsid;
            }

            public void setDsid(int dsid) {
                this.dsid = dsid;
            }
        }
    }
}
