package com.mujirenben.android.common.base.greendao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "T_City",createInDb = false)
public class City {


    @Override
    public String toString() {
        return "City{" +
                "CityName='" + CityName + '\'' +
                ", ProID=" + ProID +
                ", CitySort=" + CitySort +
                '}';
    }

    public String getCityName() {
        return this.CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public Long getProID() {
        return this.ProID;
    }

    public void setProID(Long ProID) {
        this.ProID = ProID;
    }

    public Long getCitySort() {
        return this.CitySort;
    }

    public void setCitySort(Long CitySort) {
        this.CitySort = CitySort;
    }

    @Property(nameInDb = "CityName")
    private String CityName;
    @Property(nameInDb = "ProID")
    private Long ProID;

    @Id(autoincrement = true)
    @Property(nameInDb = "CitySort")
    private Long CitySort;

    @Generated(hash = 1367934507)
    public City(String CityName, Long ProID, Long CitySort) {
        this.CityName = CityName;
        this.ProID = ProID;
        this.CitySort = CitySort;
    }

    @Generated(hash = 750791287)
    public City() {
    }

}
