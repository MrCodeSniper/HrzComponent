package com.mujirenben.android.xsh.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class WelfareBean implements MultiItemEntity {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_RED_PACKET = 1;
    public static final int TYPE_COUPONS = 2;

    private int mType;
    private TitleSubBean mTitleSubBean;
    private RedPacketSubBean mRedPacketSubBean;
    private CouponsSubBean mCouponsSubBean;

    public WelfareBean(int type,
                       TitleSubBean titleSubBean,
                       RedPacketSubBean redPacketSubBean,
                       CouponsSubBean couponsSubBean) {
        mType = type;

        switch (type) {
            case TYPE_TITLE:
                mTitleSubBean = titleSubBean;
                mRedPacketSubBean = null;
                mCouponsSubBean = null;
                break;

            case TYPE_RED_PACKET:
                mTitleSubBean = null;
                mRedPacketSubBean = redPacketSubBean;
                mCouponsSubBean = null;
                break;

            case TYPE_COUPONS:
                mTitleSubBean = null;
                mRedPacketSubBean = null;
                mCouponsSubBean = couponsSubBean;
                break;

            default:
                throw new IllegalArgumentException("The type is invalid.");
        }
    }

    @Override
    public int getItemType() {
        return mType;
    }

    public TitleSubBean getTitleSubBean() {
        return mTitleSubBean;
    }

    public RedPacketSubBean getRedPacketSubBean() {
        return mRedPacketSubBean;
    }

    public CouponsSubBean getCouponsSubBean() {
        return mCouponsSubBean;
    }

    public static class TitleSubBean {
        public String title;
    }

    public static class RedPacketSubBean {
        public String shopName;
        public int amount;
        public String useLimit;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getUseLimit() {
            return useLimit;
        }

        public void setUseLimit(String useLimit) {
            this.useLimit = useLimit;
        }
    }

    public static class CouponsSubBean {
        public String shopName;
        public int amount;
        public String useLimit;
        public String takeEffectTime;
        public String invalidTime;
        public String shopImageUrl;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getUseLimit() {
            return useLimit;
        }

        public void setUseLimit(String useLimit) {
            this.useLimit = useLimit;
        }

        public String getTakeEffectTime() {
            return takeEffectTime;
        }

        public void setTakeEffectTime(String takeEffectTime) {
            this.takeEffectTime = takeEffectTime;
        }

        public String getInvalidTime() {
            return invalidTime;
        }

        public void setInvalidTime(String invalidTime) {
            this.invalidTime = invalidTime;
        }

        public String getShopImageUrl() {
            return shopImageUrl;
        }

        public void setShopImageUrl(String shopImageUrl) {
            this.shopImageUrl = shopImageUrl;
        }
    }
}
