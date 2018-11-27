package com.mujirenben.android.common.base.adapter;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/17.Best Wishes to You!  []~(~▽~)~* Cheers!


import java.util.List;


    public class AdapterParam <T,V>{

        private V headData;//头部绑定数据

        private List<T> mDatas;//items绑定数据

        private int headLayoutId;//头部对应的布局文件

        private int itemLayouyId;//item对应的布局文件

        private int headBrId;//头部对应的资源

        private int itemBrId;//item对应的资源

        public V getHeadData() {
            return headData;
        }

        public void setHeadData(V headData) {
            this.headData = headData;
        }

        public List<T> getmDatas() {
            return mDatas;
        }

        public void setmDatas(List<T> mDatas) {
            this.mDatas = mDatas;
        }

        public int getHeadLayoutId() {
            return headLayoutId;
        }

        public void setHeadLayoutId(int headLayoutId) {
            this.headLayoutId = headLayoutId;
        }

        public int getItemLayouyId() {
            return itemLayouyId;
        }

        public void setItemLayouyId(int itemLayouyId) {
            this.itemLayouyId = itemLayouyId;
        }

        public int getHeadBrId() {
            return headBrId;
        }

        public void setHeadBrId(int headBrId) {
            this.headBrId = headBrId;
        }

        public int getItemBrId() {
            return itemBrId;
        }

        public void setItemBrId(int itemBrId) {
            this.itemBrId = itemBrId;
        }
    }

