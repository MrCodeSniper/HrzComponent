package com.mujirenben.android.mine.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

public class MateriaCircleBean implements Serializable{


    @Override
    public String toString() {
        return "MateriaCircleBean{" +
                "statusCode=" + statusCode +
                ", data=" + data +
                '}';
    }

    /**
     * statusCode : 200
     * data : [{"materiaStatus":"success","userIconUrl":"https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=751a875e7d094b36cf9f13bfc2a517bc/e1fe9925bc315c60701f126e86b1cb13485477ac.jpg","userName":"红人装小编","materiaType":1,"dispatchDate":"2018-06-09","shareContent":"红人装淘宝拉新陆续到账啦!晒单实在太火爆!感恩努力的你们,好产品，好平台，好事业👍,每一份努力在『红人装』都不会白费💯","circleMateriaList":[{"storeName":"红人装","activityType":"自营","iconUrl":"https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=43190ac389025aafc73f76999a84c001/8601a18b87d6277f0c257e3b23381f30e924fcfa.jpg","title":"红人装宣传写真"}]}]
     */

    private int statusCode;
    private List<DataBean> data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * materiaStatus : success
         * userIconUrl : https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=751a875e7d094b36cf9f13bfc2a517bc/e1fe9925bc315c60701f126e86b1cb13485477ac.jpg
         * userName : 红人装小编
         * materiaType : 1
         * dispatchDate : 2018-06-09
         * shareContent : 红人装淘宝拉新陆续到账啦!晒单实在太火爆!感恩努力的你们,好产品，好平台，好事业👍,每一份努力在『红人装』都不会白费💯
         * circleMateriaList : [{"storeName":"红人装","activityType":"自营","iconUrl":"https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=43190ac389025aafc73f76999a84c001/8601a18b87d6277f0c257e3b23381f30e924fcfa.jpg","title":"红人装宣传写真"}]
         */

        private String materiaStatus;
        private String userIconUrl;
        private String userName;
        private int materiaType;
        private String dispatchDate;
        private String shareContent;
        private List<CircleMateriaListBean> circleMateriaList;

        @Override
        public String toString() {
            return "DataBean{" +
                    "materiaStatus='" + materiaStatus + '\'' +
                    ", userIconUrl='" + userIconUrl + '\'' +
                    ", userName='" + userName + '\'' +
                    ", materiaType=" + materiaType +
                    ", dispatchDate='" + dispatchDate + '\'' +
                    ", shareContent='" + shareContent + '\'' +
                    ", circleMateriaList=" + circleMateriaList +
                    '}';
        }

        public String getMateriaStatus() {
            return materiaStatus;
        }

        public void setMateriaStatus(String materiaStatus) {
            this.materiaStatus = materiaStatus;
        }

        public String getUserIconUrl() {
            return userIconUrl;
        }

        public void setUserIconUrl(String userIconUrl) {
            this.userIconUrl = userIconUrl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getMateriaType() {
            return materiaType;
        }

        public void setMateriaType(int materiaType) {
            this.materiaType = materiaType;
        }

        public String getDispatchDate() {
            return dispatchDate;
        }

        public void setDispatchDate(String dispatchDate) {
            this.dispatchDate = dispatchDate;
        }

        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public List<CircleMateriaListBean> getCircleMateriaList() {
            return circleMateriaList;
        }

        public void setCircleMateriaList(List<CircleMateriaListBean> circleMateriaList) {
            this.circleMateriaList = circleMateriaList;
        }

        public static class CircleMateriaListBean implements Serializable{
            /**
             * storeName : 红人装
             * activityType : 自营
             * iconUrl : https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=43190ac389025aafc73f76999a84c001/8601a18b87d6277f0c257e3b23381f30e924fcfa.jpg
             * title : 红人装宣传写真
             */

            private String storeName;
            private String activityType;
            private String iconUrl;
            private String title;

            @Override
            public String toString() {
                return "CircleMateriaListBean{" +
                        "storeName='" + storeName + '\'' +
                        ", activityType='" + activityType + '\'' +
                        ", iconUrl='" + iconUrl + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getActivityType() {
                return activityType;
            }

            public void setActivityType(String activityType) {
                this.activityType = activityType;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
