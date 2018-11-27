package com.mujirenben.android.vip.mvp.model.bean;

import java.util.List;

/**
 * Author: panrongfu
 * Time:2018/6/21 16:20
 * Description: 商铺信息
 */

public class ShopBean {


    /**
     * statusCode : 200
     * data : {"shopTotal":300,"shopList":[{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"147","shopDis":"4.6km","shopName":"雪豹休闲","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"}]}
     */

    private int statusCode;
    private DataBean data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * shopTotal : 300
         * shopList : [{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"147","shopDis":"4.6km","shopName":"雪豹休闲","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"},{"shopCover":"https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg","shopType":"地区","shopPer":"143","shopDis":"3.6km","shopName":"狼人体育","shopSale":"8%"}]
         */

        private int shopTotal;
        private List<ShopListBean> shopList;

        public int getShopTotal() {
            return shopTotal;
        }

        public void setShopTotal(int shopTotal) {
            this.shopTotal = shopTotal;
        }

        public List<ShopListBean> getShopList() {
            return shopList;
        }

        public void setShopList(List<ShopListBean> shopList) {
            this.shopList = shopList;
        }

        public static class ShopListBean {
            /**
             * shopCover : https://img14.360buyimg.com/n0/jfs/t10846/362/1411419479/581961/1ad5d8f3/59e081c5N7e502ceb.jpg
             * shopType : 地区
             * shopPer : 147
             * shopDis : 4.6km
             * shopName : 雪豹休闲
             * shopSale : 8%
             */

            private String shopCover;
            private String shopType;
            private String shopPer;
            private String shopDis;
            private String shopName;
            private String shopSale;

            public String getShopCover() {
                return shopCover;
            }

            public void setShopCover(String shopCover) {
                this.shopCover = shopCover;
            }

            public String getShopType() {
                return shopType;
            }

            public void setShopType(String shopType) {
                this.shopType = shopType;
            }

            public String getShopPer() {
                return shopPer;
            }

            public void setShopPer(String shopPer) {
                this.shopPer = shopPer;
            }

            public String getShopDis() {
                return shopDis;
            }

            public void setShopDis(String shopDis) {
                this.shopDis = shopDis;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopSale() {
                return shopSale;
            }

            public void setShopSale(String shopSale) {
                this.shopSale = shopSale;
            }
        }
    }
}
