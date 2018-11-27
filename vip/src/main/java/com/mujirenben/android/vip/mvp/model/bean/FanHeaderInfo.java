package com.mujirenben.android.vip.mvp.model.bean;

/**
 * Author: panrongfu
 * Date:2018/6/30 16:11
 * Description:
 */

public class FanHeaderInfo {
    /**
     * code : 00000
     * message : 查询成功
     * success : true
     * data : {"hasRel":false,"nikeName":"\u201d\u201c","avatarUrl":"\u201d\u201c","lv":1,"expiredLv":0,"lv0Count":0,"lv1Count":0,"lv2Count":0,"totalFans":0,"myIncome":"20.00","nextIncome":"36.00","incomePercent":"80"}
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
         * hasRel : false
         * nikeName : ”“
         * avatarUrl : ”“
         * lv : 1
         * expiredLv : 0
         * lv0Count : 0
         * lv1Count : 0
         * lv2Count : 0
         * totalFans : 0
         * myIncome : 20.00
         * nextIncome : 36.00
         * incomePercent : 80
         */

        private boolean hasRel;
        private String nikeName;
        private String avatarUrl;
        private int lv;
        private int expiredLv;
        private long expiredDay;
        private int lv0Count;
        private int lv1Count;
        private int lv2Count;
        private int totalFans;
        private int surplusCount;
        private String myIncome;
        private String nextIncome;
        private String incomePercent;
        private String phone;

        public boolean isHasRel() {
            return hasRel;
        }

        public void setHasRel(boolean hasRel) {
            this.hasRel = hasRel;
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

        public int getExpiredLv() {
            return expiredLv;
        }

        public void setExpiredLv(int expiredLv) {
            this.expiredLv = expiredLv;
        }

        public int getLv0Count() {
            return lv0Count;
        }

        public void setLv0Count(int lv0Count) {
            this.lv0Count = lv0Count;
        }

        public int getLv1Count() {
            return lv1Count;
        }

        public void setLv1Count(int lv1Count) {
            this.lv1Count = lv1Count;
        }

        public int getLv2Count() {
            return lv2Count;
        }

        public void setLv2Count(int lv2Count) {
            this.lv2Count = lv2Count;
        }

        public int getTotalFans() {
            return totalFans;
        }

        public void setTotalFans(int totalFans) {
            this.totalFans = totalFans;
        }

        public String getMyIncome() {
            return myIncome;
        }

        public void setMyIncome(String myIncome) {
            this.myIncome = myIncome;
        }

        public String getNextIncome() {
            return nextIncome;
        }

        public void setNextIncome(String nextIncome) {
            this.nextIncome = nextIncome;
        }

        public String getIncomePercent() {
            return incomePercent;
        }

        public void setIncomePercent(String incomePercent) {
            this.incomePercent = incomePercent;
        }

        public int getSurplusCount() {
            return surplusCount;
        }

        public void setSurplusCount(int surplusCount) {
            this.surplusCount = surplusCount;
        }

        public long getExpiredDay() {
            return expiredDay;
        }

        public void setExpiredDay(long expiredDay) {
            this.expiredDay = expiredDay;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
