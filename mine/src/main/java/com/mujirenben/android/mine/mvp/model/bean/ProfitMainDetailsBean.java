package com.mujirenben.android.mine.mvp.model.bean;

public class ProfitMainDetailsBean {
    /**
     * code : 00000
     * message : null
     * success : true
     * data : {"totalIncome":"20.00","predictIncome":"20.00","cash":"8.00","withDraw":"0.00","withDrawing":"3.00"}
     */

    private String code;
    private String message;
    private boolean success;
    private MainProfit data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public MainProfit getData() {
        return data;
    }

    public void setData(MainProfit data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProfitMainDetailsBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public static class MainProfit {
        /**
         * totalIncome : 20.00
         * predictIncome : 20.00
         * cash : 8.00
         * withDraw : 0.00
         * withDrawing : 3.00
         */

        private String totalIncome;
        private String predictIncome;
        private String cash;
        private String withDraw;
        private String withDrawing;

        @Override
        public String toString() {
            return "MainProfit{" +
                    "totalIncome='" + totalIncome + '\'' +
                    ", predictIncome='" + predictIncome + '\'' +
                    ", cash='" + cash + '\'' +
                    ", withDraw='" + withDraw + '\'' +
                    ", withDrawing='" + withDrawing + '\'' +
                    '}';
        }

        public String getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(String totalIncome) {
            this.totalIncome = totalIncome;
        }

        public String getPredictIncome() {
            return predictIncome;
        }

        public void setPredictIncome(String predictIncome) {
            this.predictIncome = predictIncome;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public String getWithDraw() {
            return withDraw;
        }

        public void setWithDraw(String withDraw) {
            this.withDraw = withDraw;
        }

        public String getWithDrawing() {
            return withDrawing;
        }

        public void setWithDrawing(String withDrawing) {
            this.withDrawing = withDrawing;
        }
    }
}
