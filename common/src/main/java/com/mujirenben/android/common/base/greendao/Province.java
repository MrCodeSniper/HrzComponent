package com.mujirenben.android.common.base.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "T_Province",createInDb = false)
public class Province {
    @Property(nameInDb = "ProName")
    private String ProName;

    @Property(nameInDb = "ProRemark")
    private String ProRemark;

    @Id(autoincrement = true)
    @Property(nameInDb = "ProSort")
    private Long ProSort;


    @Generated(hash = 1380152166)
    public Province(String ProName, String ProRemark, Long ProSort) {
        this.ProName = ProName;
        this.ProRemark = ProRemark;
        this.ProSort = ProSort;
    }


    @Generated(hash = 1309009906)
    public Province() {
    }


    @Override
    public String toString() {
        return "Province{" +
                "ProName='" + ProName + '\'' +
                ", ProRemark='" + ProRemark + '\'' +
                ", ProSort=" + ProSort +
                '}';
    }


    public String getProName() {
        return this.ProName;
    }


    public void setProName(String ProName) {
        this.ProName = ProName;
    }


    public String getProRemark() {
        return this.ProRemark;
    }


    public void setProRemark(String ProRemark) {
        this.ProRemark = ProRemark;
    }


    public Long getProSort() {
        return this.ProSort;
    }


    public void setProSort(Long ProSort) {
        this.ProSort = ProSort;
    }
}
