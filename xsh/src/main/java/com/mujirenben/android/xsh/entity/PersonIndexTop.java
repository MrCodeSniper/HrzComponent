package com.mujirenben.android.xsh.entity;

import java.util.List;

/**
 * Created by Thinkpad on 2018/3/30.
 */

public class PersonIndexTop extends AllianceBaseEntity{

    @Override
    public String toString() {
        return "PersonIndexTop{" +
                "username='" + username + '\'' +
                ", introduce='" + introduce + '\'' +
                ", notice='" + notice + '\'' +
                ", status='" + status + '\'' +
                ", thumb1='" + thumb1 + '\'' +
                ", thumb2='" + thumb2 + '\'' +
                ", thumb3='" + thumb3 + '\'' +
                ", thumb='" + thumb + '\'' +
                ", hrz360='" + hrz360 + '\'' +
                ", banner=" + banner +
                ", thumbtext='" + thumbtext + '\'' +
                '}';
    }

    /**
     * username :
     * introduce : 1、红人装开启对接线下衣、食、住、行、吃、喝、玩、乐360行，现有1300万用户，帮助合作商家实现引流客户与跨界经营，提升盈利点！
     2、店主申请成为红人装商家联盟对接人可共享红人装1300万用户，帮助商家引流的同时，终生享受该合作商家利润15％的提成，轻松打造“个人连锁店”，无需经营，坐享收益！
     3、申请成为对接人后，可以无限对接商家，对接的越多，未来的收益越多！
     * notice : 1、统一正装，良好的态度，去和商家对接合作！
     2、以中高端店为主（环境要好）
     3、协议佣金1-50％之间（餐饮酒店在10-30％之间）
     4、定期与商家做好后期服务工作！
     * status : 0
     * thumb1 :
     * thumb2 :
     * thumb3 :
     */

    private String username;
    private String introduce;
    private String notice;
    private String status;
    private String thumb1;
    private String thumb2;
    private String thumb3;
    private String thumb;
    private String hrz360;
    private List<BannerBean> banner;


    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public String getHrz360() {
        return hrz360;
    }

    public void setHrz360(String hrz360) {
        this.hrz360 = hrz360;
    }

    private String thumbtext;

    public String getThumbtext() {
        return thumbtext;
    }

    public void setThumbtext(String thumbtext) {
        this.thumbtext = thumbtext;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb1() {
        return thumb1;
    }

    public void setThumb1(String thumb1) {
        this.thumb1 = thumb1;
    }

    public String getThumb2() {
        return thumb2;
    }

    public void setThumb2(String thumb2) {
        this.thumb2 = thumb2;
    }

    public String getThumb3() {
        return thumb3;
    }

    public void setThumb3(String thumb3) {
        this.thumb3 = thumb3;
    }


    public class  BannerBean{


        @Override
        public String toString() {
            return "BannerBean{" +
                    "thumb='" + thumb + '\'' +
                    ", url='" + url + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        private String thumb;
        private String url;
        private String title;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
