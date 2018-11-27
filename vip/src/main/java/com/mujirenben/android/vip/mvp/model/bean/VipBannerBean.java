package com.mujirenben.android.vip.mvp.model.bean;

import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/8/18 14:18
 * @describe:
 */

public class VipBannerBean {

    /**
     * code : 0000
     * message :
     * success : true
     * data : [{"sequence":1,"title":"hrz","url":"https://baidu.com","img":"baidu.png","startTime":1531746247,"endTime":1531790751},{"sequence":2,"title":"hrz","url":"https://baidu.com","img":"baidu.png","startTime":1531746247,"endTime":1531790751}]
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
         * sequence : 1
         * title : hrz
         * url : https://baidu.com
         * img : baidu.png
         * startTime : 1531746247
         * endTime : 1531790751
         */

        private int sequence;
        private String title;
        private String url;
        private String img;
        private int startTime;
        private int endTime;

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }
    }
}
