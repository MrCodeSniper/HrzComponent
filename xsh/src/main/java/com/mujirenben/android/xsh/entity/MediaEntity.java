package com.mujirenben.android.xsh.entity;

/**
 * Created by Cyj on 2018/8/3.
 */
public class MediaEntity extends BaseEntity{


    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "MediaEntity{" +
                "data='" + data + '\'' +
                '}';
    }
}
