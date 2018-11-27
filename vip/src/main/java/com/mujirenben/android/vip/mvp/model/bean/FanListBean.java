package com.mujirenben.android.vip.mvp.model.bean;

import java.util.List;

/**
 * Author: panrongfu
 * Date:2018/6/30 16:12
 * Description:
 */

public class FanListBean {

    /**
     * code : 00000
     * message : 查询成功
     * success : true
     * data : [{"accountId":1000004,"phone":"15555151515","nikeName":"","avatarUrl":"","lv":1,"star":0},{"accountId":1000013,"phone":"","nikeName":"","avatarUrl":"","lv":2,"star":10}]
     */

    private String code;
    private String message;
    private boolean success;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * accountId : 1000004
         * phone : 15555151515
         * nikeName :
         * avatarUrl :
         * lv : 1
         * star : 0
         */

        private int accountId;
        private String phone;
        private String nikeName;
        private String avatarUrl;
        private int lv;
        private int star;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNikeName() {
            return nikeName;
        }

        public void setNikeName(String nikeName) {
            this.nikeName = nikeName;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public int getLv() {
            return lv;
        }

        public void setLv(int lv) {
            this.lv = lv;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }
    }
}
