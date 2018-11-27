package com.mujirenben.android.mine.mvp.model.bean;

public class ProfitNoticeBean {
    /**
     * code : 00000
     * message : null
     * success : true
     * data : {"mainTitle":"收益说明","mainDesc":"商品预期收入金额会在下单日起算的25个工作日后自动转到累计收益，该收益可提现到支付宝和微信。","subTitle":"注意：","subDesc":"若用户付款后退货或取消订单，则收益会减少且不会转到累计收益中。若用户未确认收货/延期收货/发起维权等，都会导致收益延期入账。"}
     */

    private String code;
    private String message;
    private boolean success;
    private DescBean data;

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

    public DescBean getData() {
        return data;
    }

    public void setData(DescBean data) {
        this.data = data;
    }

    public static class DescBean {
        /**
         * mainTitle : 收益说明
         * mainDesc : 商品预期收入金额会在下单日起算的25个工作日后自动转到累计收益，该收益可提现到支付宝和微信。
         * subTitle : 注意：
         * subDesc : 若用户付款后退货或取消订单，则收益会减少且不会转到累计收益中。若用户未确认收货/延期收货/发起维权等，都会导致收益延期入账。
         */

        private String mainTitle;
        private String mainDesc;
        private String subTitle;
        private String subDesc;

        public String getMainTitle() {
            return mainTitle;
        }

        public void setMainTitle(String mainTitle) {
            this.mainTitle = mainTitle;
        }

        public String getMainDesc() {
            return mainDesc;
        }

        public void setMainDesc(String mainDesc) {
            this.mainDesc = mainDesc;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getSubDesc() {
            return subDesc;
        }

        public void setSubDesc(String subDesc) {
            this.subDesc = subDesc;
        }
    }
}
