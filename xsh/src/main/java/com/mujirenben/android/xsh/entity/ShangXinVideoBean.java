package com.mujirenben.android.xsh.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class ShangXinVideoBean implements Serializable {

    public int status;
    public String reason;
    public String thumb;
    public String title;
    public String linkurl;
    public String introduce;
    public String addtime;
    public String resolution;
    public String views;
    public int favourite;
    public String agrees;
    public String comments;
    public List<User> userList;
    public Store store;
    public List<Goods> goodsList;
    public String flagfavourite;
    public int flagagree;
    public Share share;
    public List<CommentDetail> commentDetailList;

    public ShangXinVideoBean(String json){
        try {
            JSONObject obj=new JSONObject(json);
            status=obj.getInt("status");
            switch (status){
                case 200:
                    JSONObject dataObj=obj.getJSONObject("data");
                    thumb=dataObj.getString("thumb");
                    title=dataObj.getString("title");
                    linkurl=dataObj.getString("linkurl");
                    introduce=dataObj.getString("introduce");
                    addtime=dataObj.getString("addtime");
                    resolution=dataObj.getString("resolution");
                    views=dataObj.getString("views");
                    favourite=dataObj.getInt("favourite");
                    agrees=dataObj.getString("agrees");
                    comments= dataObj.getString("comments");
                    userList=new ArrayList<>();
                    JSONArray userArray=dataObj.getJSONArray("user");
                    int userArrayLength=userArray.length();
                    User user;
                    for (int i = 0; i <userArrayLength ; i++) {
                         user=new User(userArray.getJSONObject(i));
                         userList.add(user);
                    }
                    commentDetailList=new ArrayList<>();
                    JSONArray commentJsonArray=dataObj.getJSONArray("comment");
                    int commentJsonArrayLength=commentJsonArray.length();
                    CommentDetail commentDetail;
                    for(int i=0;i<commentJsonArrayLength;i++){
                        commentDetail=new CommentDetail(commentJsonArray.getJSONObject(i));
                        commentDetailList.add(commentDetail);
                    }
                    store=new Store(dataObj.getJSONObject("store"));
                    JSONArray goodsArray=dataObj.getJSONArray("goods");
                    int goodsArrayLength=goodsArray.length();
                    Goods goods;
                    goodsList=new ArrayList<>();
                    for(int i=0;i<goodsArrayLength;i++){

                        goods=new Goods(goodsArray.getJSONObject(i));
                        if(i==0){
                            goods.isSelect=true;
                        }
                        goodsList.add(goods);
                    }
                    JSONObject flagObject=dataObj.getJSONObject("flag");
                    flagfavourite=flagObject.getString("favourite");
                    flagagree=flagObject.getInt("agree");
                    share=new Share(dataObj.getJSONObject("share"));
                    break;
                default:
                    reason=obj.getString("reason");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class Goods implements Serializable {
        public boolean isSelect;
        public int goodsid;
        public String price;
        public String thumb;
        public String title;
        public String detail;
        public String profile;
        public String shoufa;
        public String favourites;
        public int profileHeight;
        public String profilep;
        public String open_iid;
        public String linkurl;
        public int time;
        public int start;
        public String buy;
        public Goods(JSONObject obj){
            try {
                goodsid=obj.getInt("goodsid");
                price=obj.getString("price");
                detail=obj.getString("detail");
                thumb=obj.getString("thumb");
                title=obj.getString("title");
                profileHeight=obj.getInt("profileHeight");
                profilep=obj.getString("profilep");
                profile=obj.getString("profile");
                open_iid=obj.getString("open_iid");
                buy=obj.getString("buy");
                shoufa=obj.getString("shoufa");
                favourites=obj.getString("favourites");
                linkurl=obj.getString("linkurl");
                time=obj.getInt("time");
                start=obj.getInt("start");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    public class Store implements Serializable {
        public int id;
        public int storeid;
        public String thumb;
        public String avatar;
        public String type;
        public String title;
        public Store(JSONObject obj){
            try {
                id=obj.getInt("id");
                storeid=obj.getInt("storeid");
                thumb=obj.getString("thumb");
                avatar=obj.getString("avatar");
                //type=obj.getString("type");
                title=obj.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public class User implements Serializable {
        public int userid;
        public String avatar;
        public String username;
        public String gender;
        public String note;
        public String level;
        public String auth;
        public String fans;
        public String focus;
        public User(JSONObject obj){
            try {
                userid=obj.getInt("userid");
                avatar=obj.getString("avatar");
                username=obj.getString("username");
                gender=obj.getString("gender");
                note=obj.getString("note");
                level=obj.getString("level");
                auth=obj.getString("auth");
                fans=obj.getString("fans");
                focus=obj.getString("focus");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
