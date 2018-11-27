package com.mujirenben.android.vip.mvp.model.bean;

import com.mujirenben.android.common.entity.BaseEntity;

import java.util.List;

public class IndustryBean extends BaseEntity {

    @Override
    public String toString() {
        return "IndustryBean{" +
                "data=" + data +
                '}';
    }

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 270
         * name : 美食
         * pid : 0
         * status : 1
         * isDelete : 0
         * updateBy : null
         * createTime : null
         * updateTime : null
         * sons : [{"id":279,"name":"韩国料理","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":278,"name":"咖啡厅","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":277,"name":"面包甜点","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":276,"name":"西餐","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":275,"name":"日本料理","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":274,"name":"自助餐","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":272,"name":"快餐简餐","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":273,"name":"粤菜","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":271,"name":"全部美食","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":280,"name":"火锅","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":281,"name":"川菜","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":282,"name":"茶餐厅","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":283,"name":"小吃","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":284,"name":"小龙虾","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":285,"name":"东南亚菜","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":286,"name":"烧烤","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":287,"name":"粥粉面","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":288,"name":"素材","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":289,"name":"海鲜","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":290,"name":"下午茶","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null},{"id":291,"name":"其他美食","pid":270,"status":1,"isDelete":0,"updateBy":null,"createTime":null,"updateTime":null,"sons":null}]
         */

        private int id;
        private String name;
        private int pid;
        private int status;
        private int isDelete;
        private String updateBy;
        private String createTime;
        private String updateTime;
        private List<SonsBean> sons;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", pid=" + pid +
                    ", status=" + status +
                    ", isDelete=" + isDelete +
                    ", updateBy='" + updateBy + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", sons=" + sons +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<SonsBean> getSons() {
            return sons;
        }

        public void setSons(List<SonsBean> sons) {
            this.sons = sons;
        }

        public static class SonsBean {
            @Override
            public String toString() {
                return "SonsBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", pid=" + pid +
                        ", status=" + status +
                        ", isDelete=" + isDelete +
                        ", updateBy='" + updateBy + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", updateTime='" + updateTime + '\'' +
                        ", sons=" + sons +
                        '}';
            }

            /**
             * id : 279
             * name : 韩国料理
             * pid : 270
             * status : 1
             * isDelete : 0
             * updateBy : null
             * createTime : null
             * updateTime : null
             * sons : null
             */





            private int id;
            private String name;
            private int pid;
            private int status;
            private int isDelete;
            private String updateBy;
            private String createTime;
            private String updateTime;
            private Object sons;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(int isDelete) {
                this.isDelete = isDelete;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(String updateBy) {
                this.updateBy = updateBy;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public Object getSons() {
                return sons;
            }

            public void setSons(Object sons) {
                this.sons = sons;
            }
        }
    }
}
