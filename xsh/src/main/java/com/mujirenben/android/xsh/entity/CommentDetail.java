package com.mujirenben.android.xsh.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 评论详情
 * Created by Administrator on 2015/12/23.
 */
public class CommentDetail implements Serializable {

    public int userid;
    public String commentid;
    public int touserid;
    public String username;
    public String avatar;
    public String message;
    public String addtime;
    public String auth;
    public String level;
    public String at;

    public CommentDetail(){

    }

    public CommentDetail(JSONObject object){
        try {
            commentid=object.getString("commentid");
            userid=object.getInt("userid");
            username=object.getString("username");
            if(!object.isNull("touserid")){
                touserid=object.getInt("touserid");
            }

            if(!object.isNull("at")){
                at=object.getString("at");
            }
            avatar=object.getString("avatar");
            message=object.getString("message");
            addtime=object.getString("addtime");
            auth=object.getString("auth");
            level=object.getString("level");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

