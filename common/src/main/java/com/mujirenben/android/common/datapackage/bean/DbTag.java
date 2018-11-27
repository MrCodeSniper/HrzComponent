package com.mujirenben.android.common.datapackage.bean;

/**
 * Created by mac on 2018/5/31.
 */

public class DbTag {

    private Long target_id;
    private String tag_name;
    private String belong;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Long getTarget_id() {
        return target_id;
    }

    public void setTarget_id(Long target_id) {
        this.target_id = target_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    @Override
    public String toString() {
        return "DbTag{" +
                "target_id=" + target_id +
                ", tag_name='" + tag_name + '\'' +
                '}';
    }
}
