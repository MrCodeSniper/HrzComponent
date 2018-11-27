package com.mujirenben.android.mine.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author: panrongfu
 * @date: 2018/8/6 15:51
 * @describe: 账户信息
 */

public class AccountInfoBean implements Serializable {

    /**
     * code : 00000
     * message :
     * success : true
     * data : {"accountId":1000084,"balance":"47.65","realName":"沙箱环境","alipayAcount":"eamjcr6596@sandbox.com","idNumber":"947674192807244616","identityPictureVilidate":1,"frontUrl":"","reverseUrl":"","relId":1000083}
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

    public static class DataBean implements Parcelable {
        /**
         * accountId : 1000084
         * balance : 47.65
         * realName : 沙箱环境
         * alipayAcount : eamjcr6596@sandbox.com
         * idNumber : 947674192807244616
         * identityVilidate : 1
         * frontUrl :
         * reverseUrl :
         * relId : 1000083
         */

        private int accountId;
        private String balance;
        private String realName;
        private String alipayAcount;
        private String idNumber;
        private int identityValidate;
        private String frontUrl;
        private String reverseUrl;
        private int relId;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            accountId = in.readInt();
            balance = in.readString();
            realName = in.readString();
            alipayAcount = in.readString();
            idNumber = in.readString();
            identityValidate = in.readInt();
            frontUrl = in.readString();
            reverseUrl = in.readString();
            relId = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getAlipayAcount() {
            return alipayAcount;
        }

        public void setAlipayAcount(String alipayAcount) {
            this.alipayAcount = alipayAcount;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public int getIdentityValidate() {
            return identityValidate;
        }

        public void setIdentityValidate(int identityVilidate) {
            this.identityValidate = identityVilidate;
        }

        public String getFrontUrl() {
            return frontUrl;
        }

        public void setFrontUrl(String frontUrl) {
            this.frontUrl = frontUrl;
        }

        public String getReverseUrl() {
            return reverseUrl;
        }

        public void setReverseUrl(String reverseUrl) {
            this.reverseUrl = reverseUrl;
        }

        public int getRelId() {
            return relId;
        }

        public void setRelId(int relId) {
            this.relId = relId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(accountId);
            dest.writeString(balance);
            dest.writeString(realName);
            dest.writeString(alipayAcount);
            dest.writeString(idNumber);
            dest.writeInt(identityValidate);
            dest.writeString(frontUrl);
            dest.writeString(reverseUrl);
            dest.writeInt(relId);
        }
    }
}
