package com.mujirenben.android.xsh.entity;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/5.Best Wishes to You!  []~(~▽~)~* Cheers!


import java.util.List;

public class AddressBean extends BaseEntity{


    private List<ProvinceTag> data;

    public List<ProvinceTag> getData() {
        return data;
    }

    public void setData(List<ProvinceTag> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "data=" + data +
                '}';
    }

    public static class ProvinceTag {

        @Override
        public String toString() {
            return "ProvinceTag{" +
                    "id=" + id +
                    ", province='" + province + '\'' +
                    ", status=" + status +
                    ", citys=" + citys +
                    '}';
        }

        /**
         * id : 1
         * province : qwe
         * status : 1
         * citys : [{"id":1,"city":"ewq","provinceid":1,"status":1}]
         */

        private int id;
        private String province;
        private int status;
        private List<CitysTag> citys;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<CitysTag> getCitys() {
            return citys;
        }

        public void setCitys(List<CitysTag> citys) {
            this.citys = citys;
        }

        public static class CitysTag{

            @Override
            public String toString() {
                return "CitysTag{" +
                        "id=" + id +
                        ", city='" + city + '\'' +
                        ", provinceid=" + provinceid +
                        ", status=" + status +
                        '}';
            }

            /**
             * id : 1
             * city : ewq
             * provinceid : 1
             * status : 1
             */

            private int id;
            private String city;
            private int provinceid;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getProvinceid() {
                return provinceid;
            }

            public void setProvinceid(int provinceid) {
                this.provinceid = provinceid;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

        }
    }
}
