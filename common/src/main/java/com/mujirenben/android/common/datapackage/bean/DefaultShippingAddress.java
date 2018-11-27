package com.mujirenben.android.common.datapackage.bean;

import java.io.Serializable;

public class DefaultShippingAddress implements Serializable {

    private String code;
    private String msg;
    private Data data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DefaultShippingAddress{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable {

        private String userName;
        private String phone;
        private String provinceName;
        private String cityName;
        private String countyName;
        private String detailInfo;
        private int flag;

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

        public String getDetailInfo() {
            return detailInfo;
        }

        public void setDetailInfo(String detailInfo) {
            this.detailInfo = detailInfo;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "userName='" + userName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", provinceName='" + provinceName + '\'' +
                    ", cityName='" + cityName + '\'' +
                    ", countyName='" + countyName + '\'' +
                    ", detailInfo='" + detailInfo + '\'' +
                    ", flag=" + flag +
                    '}';
        }
    }
}
