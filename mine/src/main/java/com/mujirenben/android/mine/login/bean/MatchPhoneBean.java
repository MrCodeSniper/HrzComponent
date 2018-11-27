package com.mujirenben.android.mine.login.bean;

/**
 * @author: panrongfu
 * @date: 2018/8/23 20:26
 * @describe:
 */

public class MatchPhoneBean {


    /**
     * code : 00000
     * message :
     * success : true
     * data : {"code":"","match":false}
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
         * code :
         * match : false
         */

        private String PhoneCode;
        private boolean match;

        public String getPhoneCode() {
            return PhoneCode;
        }

        public void setPhoneCode(String phoneCode) {
            PhoneCode = phoneCode;
        }

        public boolean isMatch() {
            return match;
        }

        public void setMatch(boolean match) {
            this.match = match;
        }
    }
}
