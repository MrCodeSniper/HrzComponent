package com.mujirenben.android.vip.mvp.model.bean;

import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/8/18 14:20
 * @describe:
 */

public class NoticeBean {


    /**
     * code : 00000
     * message :
     * success : true
     * data : [{"nikeName":"半夏","lv":1,"notice":"恭喜半夏升级为皇冠"}]
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
         * nikeName : 半夏
         * lv : 1
         * notice : 恭喜半夏升级为皇冠
         */

        private String nikeName;
        private int lv;
        private String notice;

        public String getNikeName() {
            return nikeName;
        }

        public void setNikeName(String nikeName) {
            this.nikeName = nikeName;
        }

        public int getLv() {
            return lv;
        }

        public void setLv(int lv) {
            this.lv = lv;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }
    }
}
