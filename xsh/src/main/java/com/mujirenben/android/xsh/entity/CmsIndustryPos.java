package com.mujirenben.android.xsh.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Thinkpad on 2018/4/25.
 */

public class CmsIndustryPos implements IPickerViewData {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CmsIndustryPos{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
