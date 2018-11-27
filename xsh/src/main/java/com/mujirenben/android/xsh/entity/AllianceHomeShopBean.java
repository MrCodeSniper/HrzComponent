package com.mujirenben.android.xsh.entity;

import java.util.List;

public class AllianceHomeShopBean {


    /**
     * statusCode : 200
     * data : [{"shopIcon":"https://img30.360buyimg.com/babel/s190x110_jfs/t16738/281/2637306411/8583/e64d3641/5b06a2a6Ne3cde9d6.jpg!q90!cc_190x110","shopName":"海底捞火锅","belongStreet":"区域街道","bussinessType":"火锅","bussinessTime":"11:00-22:00","averageConsume":"61.00","redPocket":[{"pocketId":1,"pocketName":"无门槛红包","pocketAmount":3}],"fullReduce":[{"fullReduceId":1,"fullReduceName":"满减红包","fullAmount":"30.00","fullReduceAmount":"4.00"},{"fullReduceId":2,"fullReduceName":"满减红包","fullAmount":"68.00","fullReduceAmount":"10.00"},{"fullReduceId":3,"fullReduceName":"满减红包","fullAmount":"128.00","fullReduceAmount":"18.00"}]},{"shopIcon":"https://img12.360buyimg.com/babel/s190x110_jfs/t20440/123/773540946/11421/70022abc/5b174b89N1191f905.jpg!q90!cc_190x110","shopName":"海底捞火锅","belongStreet":"区域街道","bussinessType":"火锅","bussinessTime":"11:00-22:00","averageConsume":"61.00","redPocket":[{"pocketId":1,"pocketName":"无门槛红包","pocketAmount":3}],"fullReduce":[{"fullReduceId":1,"fullReduceName":"满减红包","fullAmount":"30.00","fullReduceAmount":"4.00"},{"fullReduceId":2,"fullReduceName":"满减红包","fullAmount":"68.00","fullReduceAmount":"10.00"},{"fullReduceId":3,"fullReduceName":"满减红包","fullAmount":"128.00","fullReduceAmount":"18.00"}]},{"shopIcon":"https://img11.360buyimg.com/babel/s190x110_jfs/t22792/59/1738710/24052/4a88523/5b223c29Nc019f320.jpg!q90!cc_190x110","shopName":"海底捞火锅","belongStreet":"区域街道","bussinessType":"火锅","bussinessTime":"11:00-22:00","averageConsume":"61.00","redPocket":[{"pocketId":1,"pocketName":"无门槛红包","pocketAmount":3}],"fullReduce":[{"fullReduceId":1,"fullReduceName":"满减红包","fullAmount":"30.00","fullReduceAmount":"4.00"},{"fullReduceId":2,"fullReduceName":"满减红包","fullAmount":"68.00","fullReduceAmount":"10.00"},{"fullReduceId":3,"fullReduceName":"满减红包","fullAmount":"128.00","fullReduceAmount":"18.00"}]}]
     */

    private int statusCode;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "AllianceHomeShopBean{" +
                "statusCode=" + statusCode +
                ", data=" + data +
                '}';
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * shopIcon : https://img30.360buyimg.com/babel/s190x110_jfs/t16738/281/2637306411/8583/e64d3641/5b06a2a6Ne3cde9d6.jpg!q90!cc_190x110
         * shopName : 海底捞火锅
         * belongStreet : 区域街道
         * bussinessType : 火锅
         * bussinessTime : 11:00-22:00
         * averageConsume : 61.00
         * redPocket : [{"pocketId":1,"pocketName":"无门槛红包","pocketAmount":3}]
         * fullReduce : [{"fullReduceId":1,"fullReduceName":"满减红包","fullAmount":"30.00","fullReduceAmount":"4.00"},{"fullReduceId":2,"fullReduceName":"满减红包","fullAmount":"68.00","fullReduceAmount":"10.00"},{"fullReduceId":3,"fullReduceName":"满减红包","fullAmount":"128.00","fullReduceAmount":"18.00"}]
         */

        private String shopIcon;
        private String shopName;
        private String belongStreet;
        private String bussinessType;
        private String bussinessTime;
        private String averageConsume;
        private List<RedPocketBean> redPocket;
        private List<FullReduceBean> fullReduce;


        @Override
        public String toString() {
            return "DataBean{" +
                    "shopIcon='" + shopIcon + '\'' +
                    ", shopName='" + shopName + '\'' +
                    ", belongStreet='" + belongStreet + '\'' +
                    ", bussinessType='" + bussinessType + '\'' +
                    ", bussinessTime='" + bussinessTime + '\'' +
                    ", averageConsume='" + averageConsume + '\'' +
                    ", redPocket=" + redPocket +
                    ", fullReduce=" + fullReduce +
                    '}';
        }

        public String getShopIcon() {
            return shopIcon;
        }

        public void setShopIcon(String shopIcon) {
            this.shopIcon = shopIcon;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getBelongStreet() {
            return belongStreet;
        }

        public void setBelongStreet(String belongStreet) {
            this.belongStreet = belongStreet;
        }

        public String getBussinessType() {
            return bussinessType;
        }

        public void setBussinessType(String bussinessType) {
            this.bussinessType = bussinessType;
        }

        public String getBussinessTime() {
            return bussinessTime;
        }

        public void setBussinessTime(String bussinessTime) {
            this.bussinessTime = bussinessTime;
        }

        public String getAverageConsume() {
            return averageConsume;
        }

        public void setAverageConsume(String averageConsume) {
            this.averageConsume = averageConsume;
        }

        public List<RedPocketBean> getRedPocket() {
            return redPocket;
        }

        public void setRedPocket(List<RedPocketBean> redPocket) {
            this.redPocket = redPocket;
        }

        public List<FullReduceBean> getFullReduce() {
            return fullReduce;
        }

        public void setFullReduce(List<FullReduceBean> fullReduce) {
            this.fullReduce = fullReduce;
        }

        public static class RedPocketBean {
            /**
             * pocketId : 1
             * pocketName : 无门槛红包
             * pocketAmount : 3
             */

            private int pocketId;
            private String pocketName;
            private int pocketAmount;

            public int getPocketId() {
                return pocketId;
            }

            public void setPocketId(int pocketId) {
                this.pocketId = pocketId;
            }

            public String getPocketName() {
                return pocketName;
            }

            public void setPocketName(String pocketName) {
                this.pocketName = pocketName;
            }

            public int getPocketAmount() {
                return pocketAmount;
            }

            public void setPocketAmount(int pocketAmount) {
                this.pocketAmount = pocketAmount;
            }
        }

        public static class FullReduceBean {
            /**
             * fullReduceId : 1
             * fullReduceName : 满减红包
             * fullAmount : 30.00
             * fullReduceAmount : 4.00
             */

            private int fullReduceId;
            private String fullReduceName;
            private String fullAmount;
            private String fullReduceAmount;

            public int getFullReduceId() {
                return fullReduceId;
            }

            public void setFullReduceId(int fullReduceId) {
                this.fullReduceId = fullReduceId;
            }

            public String getFullReduceName() {
                return fullReduceName;
            }

            public void setFullReduceName(String fullReduceName) {
                this.fullReduceName = fullReduceName;
            }

            public String getFullAmount() {
                return fullAmount;
            }

            public void setFullAmount(String fullAmount) {
                this.fullAmount = fullAmount;
            }

            public String getFullReduceAmount() {
                return fullReduceAmount;
            }

            public void setFullReduceAmount(String fullReduceAmount) {
                this.fullReduceAmount = fullReduceAmount;
            }
        }
    }
}
