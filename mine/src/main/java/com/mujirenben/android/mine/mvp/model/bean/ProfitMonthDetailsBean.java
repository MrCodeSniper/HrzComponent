package com.mujirenben.android.mine.mvp.model.bean;

public class ProfitMonthDetailsBean {
    /**
     * code : 00000
     * message : null
     * success : true
     * data : {"shopIncome":"1.00","activityIncome":"2.00","crownIncome":"3.00","storeIncome":"4.00","serviceIncome":"5.00"}
     */

    private String code;
    private Object message;
    private boolean success;
    private MonthDetails data;

    @Override
    public String toString() {
        return "ProfitMonthDetailsBean{" +
                "code='" + code + '\'' +
                ", message=" + message +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public MonthDetails getData() {
        return data;
    }

    public void setData(MonthDetails data) {
        this.data = data;
    }

    public static class MonthDetails {
        /**
         * shopIncome : 1.00
         * activityIncome : 2.00
         * crownIncome : 3.00
         * storeIncome : 4.00
         * serviceIncome : 5.00
         */

        private String shopIncome;
        private String activityIncome;
        private String crownIncome;
        private String storeIncome;
        private String serviceIncome;

        public String getShopIncome() {
            return shopIncome;
        }

        public void setShopIncome(String shopIncome) {
            this.shopIncome = shopIncome;
        }

        public String getActivityIncome() {
            return activityIncome;
        }

        public void setActivityIncome(String activityIncome) {
            this.activityIncome = activityIncome;
        }

        public String getCrownIncome() {
            return crownIncome;
        }

        public void setCrownIncome(String crownIncome) {
            this.crownIncome = crownIncome;
        }

        public String getStoreIncome() {
            return storeIncome;
        }

        public void setStoreIncome(String storeIncome) {
            this.storeIncome = storeIncome;
        }

        public String getServiceIncome() {
            return serviceIncome;
        }

        public void setServiceIncome(String serviceIncome) {
            this.serviceIncome = serviceIncome;
        }

        @Override
        public String toString() {
            return "MonthDetails{" +
                    "shopIncome='" + shopIncome + '\'' +
                    ", activityIncome='" + activityIncome + '\'' +
                    ", crownIncome='" + crownIncome + '\'' +
                    ", storeIncome='" + storeIncome + '\'' +
                    ", serviceIncome='" + serviceIncome + '\'' +
                    '}';
        }
    }
}
