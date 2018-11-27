package com.mujirenben.android.xsh.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by weiyq on 2018/4/25.
 */
public class CityEntity implements IPickerViewData {
    private String id;
    private String city;
    private String cityid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    @Override
    public String getPickerViewText() {
        return city;
    }
}
