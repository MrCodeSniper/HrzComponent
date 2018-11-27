package com.mujirenben.android.common.base.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "T_Zone",createInDb = false)
public class Zone {

    @Property(nameInDb = "ZoneName")
    private String ZoneName;

    @Property(nameInDb = "CityID")
    private Long CityID;

    @Id(autoincrement = true)
    @Property(nameInDb = "ZoneID")
    private Long ZoneID;


    @Generated(hash = 1702482708)
    public Zone(String ZoneName, Long CityID, Long ZoneID) {
        this.ZoneName = ZoneName;
        this.CityID = CityID;
        this.ZoneID = ZoneID;
    }


    @Generated(hash = 1333518924)
    public Zone() {
    }


    @Override
    public String toString() {
        return "Zone{" +
                "ZoneName='" + ZoneName + '\'' +
                ", CityID=" + CityID +
                ", ZoneID=" + ZoneID +
                '}';
    }


    public String getZoneName() {
        return this.ZoneName;
    }


    public void setZoneName(String ZoneName) {
        this.ZoneName = ZoneName;
    }


    public Long getCityID() {
        return this.CityID;
    }


    public void setCityID(Long CityID) {
        this.CityID = CityID;
    }


    public Long getZoneID() {
        return this.ZoneID;
    }


    public void setZoneID(Long ZoneID) {
        this.ZoneID = ZoneID;
    }





}
