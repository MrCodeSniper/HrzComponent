package com.mujirenben.android.xsh.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/14.
 */
public class Share implements Serializable {

    public String thumb;
    public String introduce;
    public String linkurl;
    public String shareTitle;
    public String shareText;
    public Share(JSONObject obj){
        try {
            thumb=obj.getString("thumb");
            introduce=obj.getString("introduce");
            shareTitle=obj.getString("shareTitle");
            if (obj.getString("linkurl")!=null) {
                linkurl = obj.getString("linkurl");
            }
            shareText=obj.getString("shareText");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
