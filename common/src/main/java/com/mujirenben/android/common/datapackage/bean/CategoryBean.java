package com.mujirenben.android.common.datapackage.bean;

import java.util.List;

public class CategoryBean {


    /**
     * code : 0
     * resourceInfo : [{"resType":"category","resScope":"classify","resContentList":[{"templateId":"1","businessId":"男装","configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":0,"itemDataContent":[{"imageUrl":"https://img11.360buyimg.com/n8/jfs/t18241/93/1773843676/54679/5279526/5ad82af5N79213102.jpg","clickType":"webview","clickUrl":"https://www.jd.com/","mainTitle":"T恤","subTitle":"子标题0","sourceFrom":"京东"},{"imageUrl":"https://img13.360buyimg.com/n8/jfs/t8683/79/570114784/100653/129354fa/59aae28cN8b094634.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"POLO衫","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n8/jfs/t18643/126/2280674315/135399/51531307/5af10406Nf7a11305.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"套装","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img12.360buyimg.com/n8/jfs/t17881/110/2067702573/112745/3fcb1f5c/5ae3fa6aNc88a95a5.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"衬衫","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n8/jfs/t16462/177/1012552467/110299/50546465/5a46f8cbNe9e53ed3.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"夹克","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img12.360buyimg.com/n8/jfs/t19345/329/1678063832/46566/7e67995f/5ad348e0N8d715fa0.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"西服","subTitle":"子标题1","sourceFrom":"红人装"}]},{"templateId":"2","businessId":"女装","configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":1,"itemDataContent":[{"imageUrl":"https://img13.360buyimg.com/cms/jfs/t20149/70/63224936/8231/e63a4c75/5af8ee4aNc2a2e6b4.jpg!q95.webp","clickType":"webview","clickUrl":"https://www.jd.com/","mainTitle":"T恤","subTitle":"子标题0","sourceFrom":"京东"},{"imageUrl":"https://img12.360buyimg.com/n8/jfs/t17752/347/993923774/39278/83d9020f/5ab45a28N96957c14.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"运动裤","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/cms/jfs/t21697/68/88669875/17527/d0bb1e3c/5afb95d5N7261a8fb.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"连衣裙","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img11.360buyimg.com/cms/jfs/t16612/183/228654972/30685/32b236e3/5a6557ceNf4003160.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"针织衫","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img13.360buyimg.com/n8/jfs/t17980/315/2065729003/150888/28ffd902/5ae53deaN8e20bd5e.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"阔腿裤","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/n8/jfs/t19213/224/2065213858/101736/463f3540/5ae427d9N9b2a7482.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"卫衣","subTitle":"子标题1","sourceFrom":"红人装"}]},{"templateId":"3","businessId":"鞋包","configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":2,"itemDataContent":[{"imageUrl":"https://img11.360buyimg.com/n7/jfs/t14143/249/2052242385/68052/6dd2696e/5a581014N1e87959a.jpg","clickType":"webview","clickUrl":"https://www.jd.com/","mainTitle":"女鞋","subTitle":"子标题0","sourceFrom":"京东"},{"imageUrl":"https://img14.360buyimg.com/cms/jfs/t15877/181/2090503297/6891/16a7582/5a8f7289Nb671aecd.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"男鞋","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n7/jfs/t18175/297/736810885/93012/a1c6bc44/5aa1e235N9deced0c.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"凉鞋","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/cms/jfs/t12862/260/711013947/12563/4ac7852/5a123b99N3bcbf71d.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"皮鞋","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/cms/jfs/t17731/180/1112108158/24568/4e401110/5abde11bN5755eb58.png!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"休闲鞋","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/cms/jfs/t19708/338/1211887419/17061/227c6ec0/5ac1d036N24713e96.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"运动鞋","subTitle":"子标题1","sourceFrom":"红人装"}]}]}]
     */

    private String code;
    private List<ResourceInfoBean> resourceInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResourceInfoBean> getResourceInfo() {
        return resourceInfo;
    }

    public void setResourceInfo(List<ResourceInfoBean> resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

    public static class ResourceInfoBean {
        /**
         * resType : category
         * resScope : classify
         * resContentList : [{"templateId":"1","businessId":"男装","configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":0,"itemDataContent":[{"imageUrl":"https://img11.360buyimg.com/n8/jfs/t18241/93/1773843676/54679/5279526/5ad82af5N79213102.jpg","clickType":"webview","clickUrl":"https://www.jd.com/","mainTitle":"T恤","subTitle":"子标题0","sourceFrom":"京东"},{"imageUrl":"https://img13.360buyimg.com/n8/jfs/t8683/79/570114784/100653/129354fa/59aae28cN8b094634.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"POLO衫","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n8/jfs/t18643/126/2280674315/135399/51531307/5af10406Nf7a11305.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"套装","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img12.360buyimg.com/n8/jfs/t17881/110/2067702573/112745/3fcb1f5c/5ae3fa6aNc88a95a5.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"衬衫","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n8/jfs/t16462/177/1012552467/110299/50546465/5a46f8cbNe9e53ed3.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"夹克","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img12.360buyimg.com/n8/jfs/t19345/329/1678063832/46566/7e67995f/5ad348e0N8d715fa0.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"西服","subTitle":"子标题1","sourceFrom":"红人装"}]},{"templateId":"2","businessId":"女装","configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":1,"itemDataContent":[{"imageUrl":"https://img13.360buyimg.com/cms/jfs/t20149/70/63224936/8231/e63a4c75/5af8ee4aNc2a2e6b4.jpg!q95.webp","clickType":"webview","clickUrl":"https://www.jd.com/","mainTitle":"T恤","subTitle":"子标题0","sourceFrom":"京东"},{"imageUrl":"https://img12.360buyimg.com/n8/jfs/t17752/347/993923774/39278/83d9020f/5ab45a28N96957c14.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"运动裤","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/cms/jfs/t21697/68/88669875/17527/d0bb1e3c/5afb95d5N7261a8fb.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"连衣裙","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img11.360buyimg.com/cms/jfs/t16612/183/228654972/30685/32b236e3/5a6557ceNf4003160.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"针织衫","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img13.360buyimg.com/n8/jfs/t17980/315/2065729003/150888/28ffd902/5ae53deaN8e20bd5e.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"阔腿裤","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/n8/jfs/t19213/224/2065213858/101736/463f3540/5ae427d9N9b2a7482.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"卫衣","subTitle":"子标题1","sourceFrom":"红人装"}]},{"templateId":"3","businessId":"鞋包","configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":2,"itemDataContent":[{"imageUrl":"https://img11.360buyimg.com/n7/jfs/t14143/249/2052242385/68052/6dd2696e/5a581014N1e87959a.jpg","clickType":"webview","clickUrl":"https://www.jd.com/","mainTitle":"女鞋","subTitle":"子标题0","sourceFrom":"京东"},{"imageUrl":"https://img14.360buyimg.com/cms/jfs/t15877/181/2090503297/6891/16a7582/5a8f7289Nb671aecd.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"男鞋","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n7/jfs/t18175/297/736810885/93012/a1c6bc44/5aa1e235N9deced0c.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"凉鞋","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/cms/jfs/t12862/260/711013947/12563/4ac7852/5a123b99N3bcbf71d.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"皮鞋","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/cms/jfs/t17731/180/1112108158/24568/4e401110/5abde11bN5755eb58.png!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"休闲鞋","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img10.360buyimg.com/cms/jfs/t19708/338/1211887419/17061/227c6ec0/5ac1d036N24713e96.jpg!q95.webp","clickType":"local","clickUrl":"local://com/user/","mainTitle":"运动鞋","subTitle":"子标题1","sourceFrom":"红人装"}]}]
         */

        private String resType;
        private String resScope;
        private List<ResContentListBean> resContentList;

        public String getResType() {
            return resType;
        }

        public void setResType(String resType) {
            this.resType = resType;
        }

        public String getResScope() {
            return resScope;
        }

        public void setResScope(String resScope) {
            this.resScope = resScope;
        }

        public List<ResContentListBean> getResContentList() {
            return resContentList;
        }

        public void setResContentList(List<ResContentListBean> resContentList) {
            this.resContentList = resContentList;
        }

        public static class ResContentListBean {
            /**
             * templateId : 1
             * businessId : 男装
             * configuration : {"backgroundColor":"#FFF","spanLine":"none"}
             * isShow : 1
             * position : 0
             * itemDataContent : [{"imageUrl":"https://img11.360buyimg.com/n8/jfs/t18241/93/1773843676/54679/5279526/5ad82af5N79213102.jpg","clickType":"webview","clickUrl":"https://www.jd.com/","mainTitle":"T恤","subTitle":"子标题0","sourceFrom":"京东"},{"imageUrl":"https://img13.360buyimg.com/n8/jfs/t8683/79/570114784/100653/129354fa/59aae28cN8b094634.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"POLO衫","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n8/jfs/t18643/126/2280674315/135399/51531307/5af10406Nf7a11305.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"套装","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img12.360buyimg.com/n8/jfs/t17881/110/2067702573/112745/3fcb1f5c/5ae3fa6aNc88a95a5.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"衬衫","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img14.360buyimg.com/n8/jfs/t16462/177/1012552467/110299/50546465/5a46f8cbNe9e53ed3.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"夹克","subTitle":"子标题1","sourceFrom":"红人装"},{"imageUrl":"https://img12.360buyimg.com/n8/jfs/t19345/329/1678063832/46566/7e67995f/5ad348e0N8d715fa0.jpg","clickType":"local","clickUrl":"local://com/user/","mainTitle":"西服","subTitle":"子标题1","sourceFrom":"红人装"}]
             */

            private String templateId;
            private String businessId;
            private ConfigurationBean configuration;
            private int isShow;
            private int position;
            private List<ItemDataContentBean> itemDataContent;

            public String getTemplateId() {
                return templateId;
            }

            public void setTemplateId(String templateId) {
                this.templateId = templateId;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public ConfigurationBean getConfiguration() {
                return configuration;
            }

            public void setConfiguration(ConfigurationBean configuration) {
                this.configuration = configuration;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public List<ItemDataContentBean> getItemDataContent() {
                return itemDataContent;
            }

            public void setItemDataContent(List<ItemDataContentBean> itemDataContent) {
                this.itemDataContent = itemDataContent;
            }

            public static class ConfigurationBean {
                /**
                 * backgroundColor : #FFF
                 * spanLine : none
                 */

                private String backgroundColor;
                private String spanLine;

                public String getBackgroundColor() {
                    return backgroundColor;
                }

                public void setBackgroundColor(String backgroundColor) {
                    this.backgroundColor = backgroundColor;
                }

                public String getSpanLine() {
                    return spanLine;
                }

                public void setSpanLine(String spanLine) {
                    this.spanLine = spanLine;
                }
            }

            public static class ItemDataContentBean {
                /**
                 * imageUrl : https://img11.360buyimg.com/n8/jfs/t18241/93/1773843676/54679/5279526/5ad82af5N79213102.jpg
                 * clickType : webview
                 * clickUrl : https://www.jd.com/
                 * mainTitle : T恤
                 * subTitle : 子标题0
                 * sourceFrom : 京东
                 */

                private String imageUrl;
                private String clickType;
                private String clickUrl;
                private String mainTitle;
                private String subTitle;
                private String sourceFrom;

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getClickType() {
                    return clickType;
                }

                public void setClickType(String clickType) {
                    this.clickType = clickType;
                }

                public String getClickUrl() {
                    return clickUrl;
                }

                public void setClickUrl(String clickUrl) {
                    this.clickUrl = clickUrl;
                }

                public String getMainTitle() {
                    return mainTitle;
                }

                public void setMainTitle(String mainTitle) {
                    this.mainTitle = mainTitle;
                }

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public String getSourceFrom() {
                    return sourceFrom;
                }

                public void setSourceFrom(String sourceFrom) {
                    this.sourceFrom = sourceFrom;
                }
            }
        }
    }
}
