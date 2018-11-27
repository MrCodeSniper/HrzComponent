package com.mujirenben.android.vip.mvp.model.bean;

public class VipUpgradeInfo {

    private String code;
    private String message;
    private boolean success;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VipUpgradeInfo{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public class Data {

        /**
         * 0: 粉丝
         * 1: 皇冠
         * 2: 店主
         */
        private int lv;

        private FansUpgradeInfo lv0;
        private CrownUpgradeInfo lv1;
        private ShopkeeperUpgradeInfo lv2;

        public int getLv() {
            return lv;
        }

        public void setLv(int lv) {
            this.lv = lv;
        }

        public FansUpgradeInfo getLv0() {
            return lv0;
        }

        public void setLv0(FansUpgradeInfo lv0) {
            this.lv0 = lv0;
        }

        public CrownUpgradeInfo getLv1() {
            return lv1;
        }

        public void setLv1(CrownUpgradeInfo lv1) {
            this.lv1 = lv1;
        }

        public ShopkeeperUpgradeInfo getLv2() {
            return lv2;
        }

        public void setLv2(ShopkeeperUpgradeInfo lv2) {
            this.lv2 = lv2;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "lv=" + lv +
                    ", lv0=" + lv0 +
                    ", lv1=" + lv1 +
                    ", lv2=" + lv2 +
                    '}';
        }
    }

    /**
     * 粉丝升级信息
     */
    public class FansUpgradeInfo {

        private int done;
        private int required;
        private float moreProfit;

        public int getDone() {
            return done;
        }

        public void setDone(int done) {
            this.done = done;
        }

        public int getRequired() {
            return required;
        }

        public void setRequired(int required) {
            this.required = required;
        }

        public float getMoreProfit() {
            return moreProfit;
        }

        public void setMoreProfit(float moreProfit) {
            this.moreProfit = moreProfit;
        }

        @Override
        public String toString() {
            return "FansUpgradeInfo{" +
                    "done=" + done +
                    ", required=" + required +
                    ", moreProfit=" + moreProfit +
                    '}';
        }
    }

    /**
     * 皇冠升级信息
     */
    public class CrownUpgradeInfo {

        private int done;
        private int required;
        private long lastUpTime;
        private float moreProfit;

        public int getDone() {
            return done;
        }

        public void setDone(int done) {
            this.done = done;
        }

        public int getRequired() {
            return required;
        }

        public void setRequired(int required) {
            this.required = required;
        }

        public long getLastUpTime() {
            return lastUpTime;
        }

        public void setLastUpTime(long lastUpTime) {
            this.lastUpTime = lastUpTime;
        }

        public float getMoreProfit() {
            return moreProfit;
        }

        public void setMoreProfit(float moreProfit) {
            this.moreProfit = moreProfit;
        }

        @Override
        public String toString() {
            return "CrownUpgradeInfo{" +
                    "done=" + done +
                    ", required=" + required +
                    ", lastUpTime=" + lastUpTime +
                    ", moreProfit=" + moreProfit +
                    '}';
        }
    }

    /**
     * 店主升级信息
     */
    public class ShopkeeperUpgradeInfo {

        private int done;
        private int lv1Required;
        private int lv2Required;
        private int lv3Required;
        private long lastUpTime;
        private int lastLv;
        private int remainDays;
        private float moreProfit;

        public int getDone() {
            return done;
        }

        public void setDone(int done) {
            this.done = done;
        }

        public int getLv1Required() {
            return lv1Required;
        }

        public void setLv1Required(int lv1Required) {
            this.lv1Required = lv1Required;
        }

        public int getLv2Required() {
            return lv2Required;
        }

        public void setLv2Required(int lv2Required) {
            this.lv2Required = lv2Required;
        }

        public int getLv3Required() {
            return lv3Required;
        }

        public void setLv3Required(int lv3Required) {
            this.lv3Required = lv3Required;
        }

        public long getLastUpTime() {
            return lastUpTime;
        }

        public void setLastUpTime(long lastUpTime) {
            this.lastUpTime = lastUpTime;
        }

        public int getRemainDays() {
            return remainDays;
        }

        public void setRemainDays(int remainDays) {
            this.remainDays = remainDays;
        }

        public int getLastLv() {
            return lastLv;
        }

        public void setLastLv(int lastLv) {
            this.lastLv = lastLv;
        }

        public float getMoreProfit() {
            return moreProfit;
        }

        public void setMoreProfit(float moreProfit) {
            this.moreProfit = moreProfit;
        }

        @Override
        public String toString() {
            return "ShopkeeperUpgradeInfo{" +
                    "done=" + done +
                    ", lv1Required=" + lv1Required +
                    ", lv2Required=" + lv2Required +
                    ", lv3Required=" + lv3Required +
                    ", lastUpTime=" + lastUpTime +
                    ", lastLv=" + lastLv +
                    ", remainDays=" + remainDays +
                    ", moreProfit=" + moreProfit +
                    '}';
        }
    }
}
