package com.mujirenben.android.xsh.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Thinkpad on 2018/7/12.
 */

public class DistanceEntity implements IPickerViewData {
    @Override
    public String getPickerViewText() {
        return distance;
    }
    private String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public DistanceEntity(String distance) {
        this.distance = distance;
    }
}
