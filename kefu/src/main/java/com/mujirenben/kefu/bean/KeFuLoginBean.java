package com.mujirenben.kefu.bean;

/**
 * @author: panrongfu
 * @date: 2018/8/25 19:17
 * @describe:
 */

public class KeFuLoginBean {

    /**
     * code : 00000
     * message :
     * success : true
     * data : {"appKey":"1466170912061689#kefuchannelapp27539","tenantId":"27539","username":"1000701","password":"9d166b0403c70d2e5c83adadc59aaf23","token":"YWMt_Z9cDqmvEeiD7pf5ZParpAAAAWaulpoxC_CAV-SqE_3-B_j5oRvO6IWQLXg"}
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
         * appKey : 1466170912061689#kefuchannelapp27539
         * tenantId : 27539
         * username : 1000701
         * password : 9d166b0403c70d2e5c83adadc59aaf23
         * token : YWMt_Z9cDqmvEeiD7pf5ZParpAAAAWaulpoxC_CAV-SqE_3-B_j5oRvO6IWQLXg
         */

        private String appKey;
        private String tenantId;
        private String username;
        private String password;
        private String token;
        private String imNumber;

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getImNumber() {
            return imNumber;
        }

        public void setImNumber(String imNumber) {
            this.imNumber = imNumber;
        }
    }
}
