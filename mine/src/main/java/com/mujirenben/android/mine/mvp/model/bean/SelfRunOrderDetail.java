package com.mujirenben.android.mine.mvp.model.bean;

import android.text.TextUtils;

import java.util.Locale;

public class SelfRunOrderDetail {

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
        return "SelfRunOrderDetail{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public class Data {

        private long id;
        private String amount;
        private String actualAmount;
        private String walletOrderCode;
        private long createTime;
        private long payTime;
        private int payWay;
        private String payer;
        private int status;
        private int isVirtual;
        private String userName;
        private String phone;
        private String detailInfo;
        private String provinceName;
        private String cityName;
        private String countyName;
        private String postage;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getActualAmount() {
            return actualAmount;
        }

        public void setActualAmount(String actualAmount) {
            this.actualAmount = actualAmount;
        }

        public String getWalletOrderCode() {
            return walletOrderCode;
        }

        public void setWalletOrderCode(String walletOrderCode) {
            this.walletOrderCode = walletOrderCode;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getPayTime() {
            return payTime;
        }

        public void setPayTime(long payTime) {
            this.payTime = payTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsVirtual() {
            return isVirtual;
        }

        public void setIsVirtual(int isVirtual) {
            this.isVirtual = isVirtual;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDetailInfo() {
            return detailInfo;
        }

        public void setDetailInfo(String detailInfo) {
            this.detailInfo = detailInfo;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCountyName() {
            return countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }

        public String getPostage() {
            return postage;
        }

        public void setPostage(String postage) {
            this.postage = postage;
        }

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
        }

        public String getPayer() {
            return payer;
        }

        public void setPayer(String payer) {
            this.payer = payer;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", amount='" + amount + '\'' +
                    ", actualAmount='" + actualAmount + '\'' +
                    ", walletOrderCode='" + walletOrderCode + '\'' +
                    ", createTime=" + createTime +
                    ", payTime=" + payTime +
                    ", payWay=" + payWay +
                    ", payer='" + payer + '\'' +
                    ", status=" + status +
                    ", isVirtual=" + isVirtual +
                    ", userName='" + userName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", detailInfo='" + detailInfo + '\'' +
                    ", provinceName='" + provinceName + '\'' +
                    ", cityName='" + cityName + '\'' +
                    ", countyName='" + countyName + '\'' +
                    ", postage='" + postage + '\'' +
                    '}';
        }
    }

    /**
     * 获取订单地址
     * @return
     */
    public String getOrderAddress(){
        if (data == null){
            return "地址获取异常";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("收货地址：");
        if (!TextUtils.isEmpty(data.getProvinceName())){
            sb.append(data.getProvinceName());
            sb.append(" ");
        }

        if (!TextUtils.isEmpty(data.getCityName())){
            sb.append(data.getCityName());
            sb.append(" ");
        }

        if (!TextUtils.isEmpty(data.getCountyName())){
            sb.append(data.getCountyName());
            sb.append(" ");
        }

        if (!TextUtils.isEmpty(data.getDetailInfo())){
            sb.append(data.getDetailInfo());
            sb.append(" ");
        }
        return sb.toString();
    }

    public String getAmount(){
        return getMoneyWithSinal(data.actualAmount);
    }

    public String getUserName(){
        if (!TextUtils.isEmpty(data.userName)){
            StringBuilder sb = new StringBuilder();
            sb.append("收件人：");
            sb.append(data.userName);
            return sb.toString();
        }
        return "获取数据错误";
    }

    /**
     * 在金额前面加入人民币符号，顺便处理为空问题
     * @param money
     * @return
     */
    private String getMoneyWithSinal(String money){
        boolean isEmpty = TextUtils.isEmpty(money);
        String tmp = isEmpty ? "0" : money;
        return String.format(Locale.getDefault(),"￥%s",tmp);
    }

    public String getActualAmount(){
        return getMoneyWithSinal(data.actualAmount);
    }

    public String getWalletCode(){
        if (data != null && !TextUtils.isEmpty(data.walletOrderCode)){
            return data.walletOrderCode;
        }
        return "数据获取异常";
    }
}
