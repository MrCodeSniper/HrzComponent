package com.mujirenben.android.common.datapackage.bean;

import java.io.Serializable;
import java.util.List;

public class QaBean {


    /**
     * code : 0
     * questionInfo : [{"questionType":"消费","questionScope":"用户","resContentList":[{"configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":0,"question":"为什么无法支付?","answer":"可能是您余额不足"},{"configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":1,"question":"为什么无法跳转淘宝?","answer":"可能是您没有申请淘宝权限"}]},{"questionType":"优惠券","questionScope":"用户","resContentList":[{"configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":0,"question":"为什么无法领取优惠券?","answer":"可能优惠券已经被领完了，也可能是您已经使用过此优惠券故不能重复领取"},{"configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":1,"question":"为什么无法使用优惠券?","answer":"可能是您已经使用过了哦！或是此商品不支持你所持有的优惠券"}]},{"questionType":"佣金","questionScope":"用户","resContentList":[{"configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":0,"question":"佣金可以体现吗?","answer":"佣金最低提现额为100元,发起提现后三个有效日内到账哦"},{"configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":1,"question":"分享商品账户内没有佣金积累?","answer":"请确保你的分享链接被客户点击购买喽！"}]}]
     */

    private String code;
    private List<QuestionInfoBean> questionInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<QuestionInfoBean> getQuestionInfo() {
        return questionInfo;
    }

    public void setQuestionInfo(List<QuestionInfoBean> questionInfo) {
        this.questionInfo = questionInfo;
    }

    @Override
    public String toString() {
        return "QaBean{" +
                "code='" + code + '\'' +
                ", questionInfo=" + questionInfo +
                '}';
    }

    public static class QuestionInfoBean implements Serializable{
        /**
         * questionType : 消费
         * questionScope : 用户
         * resContentList : [{"configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":0,"question":"为什么无法支付?","answer":"可能是您余额不足"},{"configuration":{"backgroundColor":"#FFF","spanLine":"none"},"isShow":1,"position":1,"question":"为什么无法跳转淘宝?","answer":"可能是您没有申请淘宝权限"}]
         */

        private String questionType;
        private String questionScope;
        private List<ResContentListBean> resContentList;

        public String getQuestionType() {
            return questionType;
        }

        public void setQuestionType(String questionType) {
            this.questionType = questionType;
        }

        public String getQuestionScope() {
            return questionScope;
        }

        public void setQuestionScope(String questionScope) {
            this.questionScope = questionScope;
        }

        public List<ResContentListBean> getResContentList() {
            return resContentList;
        }

        public void setResContentList(List<ResContentListBean> resContentList) {
            this.resContentList = resContentList;
        }


        @Override
        public String toString() {
            return "QuestionInfoBean{" +
                    "questionType='" + questionType + '\'' +
                    ", questionScope='" + questionScope + '\'' +
                    ", resContentList=" + resContentList +
                    '}';
        }

        public static class ResContentListBean implements Serializable{
            /**
             * configuration : {"backgroundColor":"#FFF","spanLine":"none"}
             * isShow : 1
             * position : 0
             * question : 为什么无法支付?
             * answer : 可能是您余额不足
             */

            private ConfigurationBean configuration;
            private int isShow;
            private int position;
            private String question;
            private String answer;

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

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public static class ConfigurationBean implements Serializable{
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

            @Override
            public String toString() {
                return "ResContentListBean{" +
                        "configuration=" + configuration +
                        ", isShow=" + isShow +
                        ", position=" + position +
                        ", question='" + question + '\'' +
                        ", answer='" + answer + '\'' +
                        '}';
            }
        }



    }
}
