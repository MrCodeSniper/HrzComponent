package com.mujirenben.android.common.pay;

/**
 * @author: panrongfu
 * @date: 2018/8/18 10:45
 * @describe: 下单结果
 */

public class OrderResultBean {

    /**
     * code : 00000
     * message :
     * success : true
     * data : {"orderCode":"123553","amount":"23.00"}
     */

    private String code;
    private String message;
    private boolean success;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderCode : 123553
         * amount : 23.00
         */

        private String orderCode;
        private String amount;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "orderCode='" + orderCode + '\'' +
                    ", amount='" + amount + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderResultBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
