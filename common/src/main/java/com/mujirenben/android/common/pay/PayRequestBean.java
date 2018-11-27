package com.mujirenben.android.common.pay;

/**
 * @author: panrongfu
 * @date: 2018/8/18 10:48
 * @describe:
 */

public class PayRequestBean {


    /**
     * code : 00000
     * message :
     * success : true
     * data : {"status":0,"sdkStr":"alipay_sdk=alipay-sdk-java-3.3.4.ALL&app_id=2016091800538245&biz_content=%7B%22out_trade_no%22%3A%222018081612370692731278816%22%2C%22passback_params%22%3A%22%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E8%87%AA%E8%90%A5%E5%95%86%E5%93%81%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22396.00%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&sign=e%2FyZqJ5CjdosToOZqLdR%2B3RkFJMgx2dC7AEYt3jW91P0NgWRPoERuuexs8ITVT1jtcEx4Qpw1mCYUHDSutXq4KpjovZxk27fiywqpXiRq1kMnGLmfK82nTP%2Bz5GayNsVkZ%2FEhKil65kANlZg7R06rvZcpY6sxfjKSyXbj9UtmbIpJebln2fotNi20YXlxX1ttV%2FkEIb4M8VSdZ2kSwfijPHySVOW%2FlAbDr0PlZbgqcBypqEjsYDSyKdATAP0LjI8bX7zO9OzKsMsu5I%2BDf2kwtFsoCvSLNDE8V85HLJqPNWArMJtpZl8oZ5CJGq1AmGAKTjdcwE1FOyydszJt1qeCQ%3D%3D&sign_type=RSA2×tamp=2018-08-16+13%3A56%3A10&version=1.0"}
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
         * status : 0
         * sdkStr : alipay_sdk=alipay-sdk-java-3.3.4.ALL&app_id=2016091800538245&biz_content=%7B%22out_trade_no%22%3A%222018081612370692731278816%22%2C%22passback_params%22%3A%22%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E8%87%AA%E8%90%A5%E5%95%86%E5%93%81%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22396.00%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&sign=e%2FyZqJ5CjdosToOZqLdR%2B3RkFJMgx2dC7AEYt3jW91P0NgWRPoERuuexs8ITVT1jtcEx4Qpw1mCYUHDSutXq4KpjovZxk27fiywqpXiRq1kMnGLmfK82nTP%2Bz5GayNsVkZ%2FEhKil65kANlZg7R06rvZcpY6sxfjKSyXbj9UtmbIpJebln2fotNi20YXlxX1ttV%2FkEIb4M8VSdZ2kSwfijPHySVOW%2FlAbDr0PlZbgqcBypqEjsYDSyKdATAP0LjI8bX7zO9OzKsMsu5I%2BDf2kwtFsoCvSLNDE8V85HLJqPNWArMJtpZl8oZ5CJGq1AmGAKTjdcwE1FOyydszJt1qeCQ%3D%3D&sign_type=RSA2×tamp=2018-08-16+13%3A56%3A10&version=1.0
         */

        private int status;
        private String sdkStr;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSdkStr() {
            return sdkStr;
        }

        public void setSdkStr(String sdkStr) {
            this.sdkStr = sdkStr;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "status=" + status +
                    ", sdkStr='" + sdkStr + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PayRequestBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
