package com.mujirenben.android.mine.mvp.model.bean;

import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/8/6 16:29
 * @describe:
 */

public class WithdrawListBean {

    /**
     * code : 00000
     * message : null
     * success : true
     * data : [{"type":"1","withdrawInfo":{"id":53,"way":0,"amount":"10.65","taxAmount":"0.64","actualAmount":"10.01","withdrawOrderCode":"2018090317401116622119822","outTradeCode":"2018090315343251024548875","cashierConfirmDesc":"","cashConfirmTime":0,"withdrawRequestTime":1535967611,"status":0,"alipayAcount":"eamjcr6596@sandbox.com"},"identityServiceInfo":{"id":699,"amount":"0.15","remark":"验证通过","transactionTime":1536028509,"walletOrderCode":"2018090410350933791089069","accountId":1000701}}]
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
         * type : 1
         * withdrawInfo : {"id":53,"way":0,"amount":"10.65","taxAmount":"0.64","actualAmount":"10.01","withdrawOrderCode":"2018090317401116622119822","outTradeCode":"2018090315343251024548875","cashierConfirmDesc":"","cashConfirmTime":0,"withdrawRequestTime":1535967611,"status":0,"alipayAcount":"eamjcr6596@sandbox.com"}
         * identityServiceInfo : {"id":699,"amount":"0.15","remark":"验证通过","transactionTime":1536028509,"walletOrderCode":"2018090410350933791089069","accountId":1000701}
         */

        private String type;
        private WithdrawInfoBean withdrawInfo;
        private IdentityServiceInfoBean identityServiceInfo;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public WithdrawInfoBean getWithdrawInfo() {
            return withdrawInfo;
        }

        public void setWithdrawInfo(WithdrawInfoBean withdrawInfo) {
            this.withdrawInfo = withdrawInfo;
        }

        public IdentityServiceInfoBean getIdentityServiceInfo() {
            return identityServiceInfo;
        }

        public void setIdentityServiceInfo(IdentityServiceInfoBean identityServiceInfo) {
            this.identityServiceInfo = identityServiceInfo;
        }

        public static class WithdrawInfoBean {
            /**
             * id : 53
             * way : 0
             * amount : 10.65
             * taxAmount : 0.64
             * actualAmount : 10.01
             * withdrawOrderCode : 2018090317401116622119822
             * outTradeCode : 2018090315343251024548875
             * cashierConfirmDesc :
             * cashConfirmTime : 0
             * withdrawRequestTime : 1535967611
             * status : 0
             * alipayAcount : eamjcr6596@sandbox.com
             */

            private int id;
            private int way;
            private String amount;
            private String taxAmount;
            private String actualAmount;
            private String withdrawOrderCode;
            private String outTradeCode;
            private String cashierConfirmDesc;
            private int cashConfirmTime;
            private int withdrawRequestTime;
            private int status;
            private String alipayAcount;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getWay() {
                return way;
            }

            public void setWay(int way) {
                this.way = way;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getTaxAmount() {
                return taxAmount;
            }

            public void setTaxAmount(String taxAmount) {
                this.taxAmount = taxAmount;
            }

            public String getActualAmount() {
                return actualAmount;
            }

            public void setActualAmount(String actualAmount) {
                this.actualAmount = actualAmount;
            }

            public String getWithdrawOrderCode() {
                return withdrawOrderCode;
            }

            public void setWithdrawOrderCode(String withdrawOrderCode) {
                this.withdrawOrderCode = withdrawOrderCode;
            }

            public String getOutTradeCode() {
                return outTradeCode;
            }

            public void setOutTradeCode(String outTradeCode) {
                this.outTradeCode = outTradeCode;
            }

            public String getCashierConfirmDesc() {
                return cashierConfirmDesc;
            }

            public void setCashierConfirmDesc(String cashierConfirmDesc) {
                this.cashierConfirmDesc = cashierConfirmDesc;
            }

            public int getCashConfirmTime() {
                return cashConfirmTime;
            }

            public void setCashConfirmTime(int cashConfirmTime) {
                this.cashConfirmTime = cashConfirmTime;
            }

            public int getWithdrawRequestTime() {
                return withdrawRequestTime;
            }

            public void setWithdrawRequestTime(int withdrawRequestTime) {
                this.withdrawRequestTime = withdrawRequestTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getAlipayAcount() {
                return alipayAcount;
            }

            public void setAlipayAcount(String alipayAcount) {
                this.alipayAcount = alipayAcount;
            }
        }

        public static class IdentityServiceInfoBean {
            /**
             * id : 699
             * amount : 0.15
             * remark : 验证通过
             * transactionTime : 1536028509
             * walletOrderCode : 2018090410350933791089069
             * accountId : 1000701
             */

            private int id;
            private String amount;
            private String remark;
            private int transactionTime;
            private String walletOrderCode;
            private int accountId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getTransactionTime() {
                return transactionTime;
            }

            public void setTransactionTime(int transactionTime) {
                this.transactionTime = transactionTime;
            }

            public String getWalletOrderCode() {
                return walletOrderCode;
            }

            public void setWalletOrderCode(String walletOrderCode) {
                this.walletOrderCode = walletOrderCode;
            }

            public int getAccountId() {
                return accountId;
            }

            public void setAccountId(int accountId) {
                this.accountId = accountId;
            }
        }
    }
}
