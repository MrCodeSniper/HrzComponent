package com.mujirenben.android.common.datapackage.bean;

import java.util.List;

/**
 * 搜索提示词，热词数据Bean
 */
public class SearchHotWords {


    /**
     * code : 00000
     * message : null
     * success : true
     * data : {"searchBox":{"platform":0,"searchContent":"没毛病老铁"},"hotSearch":[{"platform":0,"searchContent":"上衣"},{"platform":0,"searchContent":"衣"},{"platform":0,"searchContent":"衣服"},{"platform":0,"searchContent":"鞋"}]}
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

    public static class DataBean {
        /**
         * searchBox : {"platform":0,"searchContent":"没毛病老铁"}
         * hotSearch : [{"platform":0,"searchContent":"上衣"},{"platform":0,"searchContent":"衣"},{"platform":0,"searchContent":"衣服"},{"platform":0,"searchContent":"鞋"}]
         */

        private SearchBoxBean searchBox;
        private List<HotSearchBean> hotSearch;

        public SearchBoxBean getSearchBox() {
            return searchBox;
        }

        public void setSearchBox(SearchBoxBean searchBox) {
            this.searchBox = searchBox;
        }

        public List<HotSearchBean> getHotSearch() {
            return hotSearch;
        }

        public void setHotSearch(List<HotSearchBean> hotSearch) {
            this.hotSearch = hotSearch;
        }

        public static class SearchBoxBean {
            /**
             * platform : 0
             * searchContent : 没毛病老铁
             */

            private int platform;
            private String searchContent;

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public String getSearchContent() {
                return searchContent;
            }

            public void setSearchContent(String searchContent) {
                this.searchContent = searchContent;
            }
        }

        public static class HotSearchBean {
            /**
             * platform : 0
             * searchContent : 上衣
             */

            private int platform;
            private String searchContent;

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public String getSearchContent() {
                return searchContent;
            }

            public void setSearchContent(String searchContent) {
                this.searchContent = searchContent;
            }
        }
    }
}
