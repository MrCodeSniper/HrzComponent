package com.mujirenben.android.common.entity;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/16.Best Wishes to You!  []~(~▽~)~* Cheers!


import java.io.Serializable;
import java.util.List;

public class AddressBean {


    /**
     * code : 0000
     * message : null
     * success : true
     * data : [{"userName":"收货人","phone":"13143361234","provinceName":"省","cityName":"市","countyName":"区","detailInfo":"详细收货地址信息","flag":1},{"userName":"收货人","phone":"13143361234","provinceName":"省","cityName":"市","countyName":"区","detailInfo":"详细收货地址信息","flag":0}]
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

    public Object getMessage() {
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


    @Override
    public String toString() {
        return "AddressBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable{
        /**
         * userName : 收货人
         * phone : 13143361234
         * provinceName : 省
         * cityName : 市
         * countyName : 区
         * detailInfo : 详细收货地址信息
         * flag : 1
         */
        private Long id;
        private String userName;
        private String phone;
        private String provinceName;
        private String cityName;
        private String countyName;
        private String detailInfo;
        private int flag;  //是否為默認 0否1是

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
            return "DataBean{" +
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
