package com.mujirenben.android.xsh.entity;

import java.util.List;

/**
 * Created by Cyj on 2018/8/6.
 */
public class ShareEntity extends BaseEntity {

    /**
     * data : {"data":[{"id":2,"title":"B","content":"BBB","status":1,"author":"B","avatarUrl":"BBB","startTime":0,"createTime":0,"imgs":[{"id":5,"img":"b","sort":1,"relationId":2},{"id":6,"img":"bb","sort":2,"relationId":2},{"id":7,"img":"bbb","sort":3,"relationId":2},{"id":8,"img":"bbbb","sort":4,"relationId":2},{"id":9,"img":"bbbbb","sort":5,"relationId":2},{"id":10,"img":"bbbbbb","sort":6,"relationId":2},{"id":11,"img":"bbbbbbb","sort":7,"relationId":2},{"id":12,"img":"bbbbbbbb","sort":8,"relationId":2},{"id":13,"img":"bbbbbbbbb","sort":9,"relationId":2}]}],"pageSize":1,"pageNum":1,"totalNum":9,"pages":9,"status":"0"}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShareEntity{" +
                "data=" + data +
                '}';
    }

    public  class DataBeanX {
        /**
         * data : [{"id":2,"title":"B","content":"BBB","status":1,"author":"B","avatarUrl":"BBB","startTime":0,"createTime":0,"imgs":[{"id":5,"img":"b","sort":1,"relationId":2},{"id":6,"img":"bb","sort":2,"relationId":2},{"id":7,"img":"bbb","sort":3,"relationId":2},{"id":8,"img":"bbbb","sort":4,"relationId":2},{"id":9,"img":"bbbbb","sort":5,"relationId":2},{"id":10,"img":"bbbbbb","sort":6,"relationId":2},{"id":11,"img":"bbbbbbb","sort":7,"relationId":2},{"id":12,"img":"bbbbbbbb","sort":8,"relationId":2},{"id":13,"img":"bbbbbbbbb","sort":9,"relationId":2}]}]
         * pageSize : 1
         * pageNum : 1
         * totalNum : 9
         * pages : 9
         * status : 0
         */

        private int pageSize;
        private int pageNum;
        private int totalNum;
        private int pages;
        private String status;
        private List<DataBean> data;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }


        @Override
        public String toString() {
            return "DataBeanX{" +
                    "pageSize=" + pageSize +
                    ", pageNum=" + pageNum +
                    ", totalNum=" + totalNum +
                    ", pages=" + pages +
                    ", status='" + status + '\'' +
                    ", data=" + data +
                    '}';
        }

        public  class DataBean {
            /**
             * id : 2
             * title : B
             * content : BBB
             * status : 1
             * author : B
             * avatarUrl : BBB
             * startTime : 0
             * createTime : 0
             * imgs : [{"id":5,"img":"b","sort":1,"relationId":2},{"id":6,"img":"bb","sort":2,"relationId":2},{"id":7,"img":"bbb","sort":3,"relationId":2},{"id":8,"img":"bbbb","sort":4,"relationId":2},{"id":9,"img":"bbbbb","sort":5,"relationId":2},{"id":10,"img":"bbbbbb","sort":6,"relationId":2},{"id":11,"img":"bbbbbbb","sort":7,"relationId":2},{"id":12,"img":"bbbbbbbb","sort":8,"relationId":2},{"id":13,"img":"bbbbbbbbb","sort":9,"relationId":2}]
             */

            private int id;
            private String title;
            private String content;
            private int status;
            private String author;
            private String avatarUrl;
            private int startTime;
            private int createTime;
            private List<ImgsBean> imgs;

            @Override
            public String toString() {
                return "DataBean{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", content='" + content + '\'' +
                        ", status=" + status +
                        ", author='" + author + '\'' +
                        ", avatarUrl='" + avatarUrl + '\'' +
                        ", startTime=" + startTime +
                        ", createTime=" + createTime +
                        ", imgs=" + imgs +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
            }

            public int getCreateTime() {
                return createTime;
            }

            public void setCreateTime(int createTime) {
                this.createTime = createTime;
            }

            public List<ImgsBean> getImgs() {
                return imgs;
            }

            public void setImgs(List<ImgsBean> imgs) {
                this.imgs = imgs;
            }

            public  class ImgsBean {
                @Override
                public String toString() {
                    return "ImgsBean{" +
                            "id=" + id +
                            ", img='" + img + '\'' +
                            ", sort=" + sort +
                            ", relationId=" + relationId +
                            '}';
                }

                /**
                 * id : 5
                 * img : b
                 * sort : 1
                 * relationId : 2
                 */


                private int id;
                private String img;
                private int sort;
                private int relationId;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public int getRelationId() {
                    return relationId;
                }

                public void setRelationId(int relationId) {
                    this.relationId = relationId;
                }
            }
        }
    }
}
