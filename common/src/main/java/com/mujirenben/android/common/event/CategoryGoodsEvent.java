package com.mujirenben.android.common.event;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/30.Best Wishes to You!  []~(~▽~)~* Cheers!


import java.io.Serializable;

public class CategoryGoodsEvent implements Serializable{

    private long catLeafId;
    private int  page;
    private int sortType;
    private int sortMethod;
    private int requestType;
    private String platformId;
    private int tag;

    @Override
    public String toString() {
        return "CategoryGoodsEvent{" +
                "catLeafId=" + catLeafId +
                ", page=" + page +
                ", sortType=" + sortType +
                ", sortMethod=" + sortMethod +
                ", requestType=" + requestType +
                ", platformId='" + platformId + '\'' +
                ", tag=" + tag +
                '}';
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public long getCatLeafId() {
        return catLeafId;
    }

    public void setCatLeafId(long catLeafId) {
        this.catLeafId = catLeafId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(int sortMethod) {
        this.sortMethod = sortMethod;
    }
}
