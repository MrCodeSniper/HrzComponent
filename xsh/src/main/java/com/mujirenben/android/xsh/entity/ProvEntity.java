package com.mujirenben.android.xsh.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by weiyq on 2018/4/25.
 */
public class ProvEntity implements IPickerViewData {
    private String id;
    private String province;
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }



    @Override
    public String getPickerViewText() {
        return province;
    }
}
