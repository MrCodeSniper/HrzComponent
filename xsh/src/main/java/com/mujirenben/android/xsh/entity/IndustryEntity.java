package com.mujirenben.android.xsh.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by Thinkpad on 2018/4/25.
 */

public class IndustryEntity implements IPickerViewData {
    private String id;
    private String name;
    private List<CmsIndustryPos> cmsIndustryPos;

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

    public List<CmsIndustryPos> getCmsIndustryPos() {
        return cmsIndustryPos;
    }

    public void setCmsIndustryPos(List<CmsIndustryPos> cmsIndustryPos) {
        this.cmsIndustryPos = cmsIndustryPos;
    }

    @Override
    public String toString() {
        return "IndustryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cmsIndustryPos=" + cmsIndustryPos +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
