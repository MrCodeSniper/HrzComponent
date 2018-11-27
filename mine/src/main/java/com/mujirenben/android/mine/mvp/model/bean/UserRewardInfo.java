package com.mujirenben.android.mine.mvp.model.bean;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/6.Best Wishes to You!  []~(~▽~)~* Cheers!


import com.mujirenben.android.common.datapackage.bean.BaseMockEntity;

public class UserRewardInfo extends BaseMockEntity{

    @Override
    public String toString() {
        return "UserRewardInfo{" +
                "data=" + data +
                '}';
    }

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * totalIncome : 8.00
         * todayIncome : 0.00
         * predictIncome : 20.00
         * todayReturns : 0.00
         * cash : 20.00
         */

        private String totalIncome;
        private String todayIncome;
        private String predictIncome;
        private String todayReturns;
        private String cash;

        public String getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(String totalIncome) {
            this.totalIncome = totalIncome;
        }

        public String getTodayIncome() {
            return todayIncome;
        }

        public void setTodayIncome(String todayIncome) {
            this.todayIncome = todayIncome;
        }

        public String getPredictIncome() {
            return predictIncome;
        }

        public void setPredictIncome(String predictIncome) {
            this.predictIncome = predictIncome;
        }

        public String getTodayReturns() {
            return todayReturns;
        }

        public void setTodayReturns(String todayReturns) {
            this.todayReturns = todayReturns;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "totalIncome='" + totalIncome + '\'' +
                    ", todayIncome='" + todayIncome + '\'' +
                    ", predictIncome='" + predictIncome + '\'' +
                    ", todayReturns='" + todayReturns + '\'' +
                    ", cash='" + cash + '\'' +
                    '}';
        }
    }
}
