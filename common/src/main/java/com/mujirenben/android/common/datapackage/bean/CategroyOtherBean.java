package com.mujirenben.android.common.datapackage.bean;

import java.util.List;

public class CategroyOtherBean {


    /**
     * code : 0
     * msg : 获取二级分类成功
     * categoryInfo : {"categoryType":"一级分类","categoryScope":"具体一级分类","typeContentList":[{"position":0,"category":"二级分类1","isShow":true},{"position":1,"category":"二级分类2","isShow":true},{"position":2,"category":"二级分类3","isShow":true}]}
     */

    private String code;
    private String msg;
    private CategoryInfoBean categoryInfo;


    @Override
    public String toString() {
        return "CategroyOtherBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", categoryInfo=" + categoryInfo +
                '}';
    }

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

    public CategoryInfoBean getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(CategoryInfoBean categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public static class CategoryInfoBean {
        /**
         * categoryType : 一级分类
         * categoryScope : 具体一级分类
         * typeContentList : [{"position":0,"category":"二级分类1","isShow":true},{"position":1,"category":"二级分类2","isShow":true},{"position":2,"category":"二级分类3","isShow":true}]
         */

        private String categoryType;
        private String categoryScope;
        private List<TypeContentListBean> typeContentList;


        @Override
        public String toString() {
            return "CategoryInfoBean{" +
                    "categoryType='" + categoryType + '\'' +
                    ", categoryScope='" + categoryScope + '\'' +
                    ", typeContentList=" + typeContentList +
                    '}';
        }

        public String getCategoryType() {
            return categoryType;
        }

        public void setCategoryType(String categoryType) {
            this.categoryType = categoryType;
        }

        public String getCategoryScope() {
            return categoryScope;
        }

        public void setCategoryScope(String categoryScope) {
            this.categoryScope = categoryScope;
        }

        public List<TypeContentListBean> getTypeContentList() {
            return typeContentList;
        }

        public void setTypeContentList(List<TypeContentListBean> typeContentList) {
            this.typeContentList = typeContentList;
        }

        public static class TypeContentListBean {
            @Override
            public String toString() {
                return "TypeContentListBean{" +
                        "position=" + position +
                        ", category='" + category + '\'' +
                        ", isShow=" + isShow +
                        '}';
            }

            /**
             * position : 0
             * category : 二级分类1
             * isShow : true
             */



            private int position;
            private String category;
            private boolean isShow;

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public boolean isIsShow() {
                return isShow;
            }

            public void setIsShow(boolean isShow) {
                this.isShow = isShow;
            }
        }
    }
}
