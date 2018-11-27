package com.mujirenben.android.mine.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class ProfitListDetailsBean {
    /**
     * code : 00000
     * message : null
     * success : true
     * data : [{"income":"1.00","img":"http://....","source":"淘宝","title":"米雪时装店","time":1521524215,"createTime":1521524215,"status":0,"type":1}]
     */

    private String code;
    private String message;
    private boolean success;
    private List<ProfitItem> data;
    private int dataType;

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

    public List<ProfitItem> getData() {
        return data;
    }

    public void setData(List<ProfitItem> data) {
        this.data = data;
    }

    public static class ProfitItem implements MultiItemEntity {
        /**
         * income : 1.00
         * img : http://....
         * source : 淘宝
         * title : 米雪时装店
         * time : 1521524215
         * createTime : 1521524215
         * status : 0
         * type : 1
         */

        private String income;
        private String img;
        private String source;
        private String title;
        private long time;
        private long createTime;
        private int status;
        private int type;
        private int itemType;

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
