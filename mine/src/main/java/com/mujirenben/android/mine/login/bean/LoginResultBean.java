package com.mujirenben.android.mine.login.bean;

import com.mujirenben.android.common.user.UserInfoBean;

public class LoginResultBean {

    @Override
    public String toString() {
        return "LoginResultBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    /**
     * code : 00000
     * message : null
     * success : true
     * data : {"accountSecToken":"def2130317a8443eb54e3642538f0d73","accountSecKey":"920c5c3e45d74010b9ec8ef34234ca3f","info":{"id":1,"sex":null,"phone":"1599123123","phoneValidate":"1","email":"","emailValidate":"0","nikeName":"","unionId":"","openId":"","avatarUrl":"","status":null}}
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
         * accountSecToken : def2130317a8443eb54e3642538f0d73
         * accountSecKey : 920c5c3e45d74010b9ec8ef34234ca3f
         **/

        private String accountSecToken;
        private String accountSecKey;
        private UserInfoBean info;

        public String getAccountSecToken() {
            return accountSecToken;
        }

        public void setAccountSecToken(String accountSecToken) {
            this.accountSecToken = accountSecToken;
        }

        public String getAccountSecKey() {
            return accountSecKey;
        }

        public void setAccountSecKey(String accountSecKey) {
            this.accountSecKey = accountSecKey;
        }

        public UserInfoBean getInfo() {
            return info;
        }

        public void setInfo(UserInfoBean info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "accountSecToken='" + accountSecToken + '\'' +
                    ", accountSecKey='" + accountSecKey + '\'' +
                    ", info=" + (info == null? info : info.toString()) +
                    '}';
        }
    }

}
