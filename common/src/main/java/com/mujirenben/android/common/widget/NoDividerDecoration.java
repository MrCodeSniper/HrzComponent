package com.mujirenben.android.common.widget;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/9/3.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class NoDividerDecoration extends RecyclerView.ItemDecoration{


    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=0;
        outRect.right=0;
        outRect.bottom=0;
        outRect.top=0;
        //如果是第一列设置上方的值
        if(parent.getChildAdapterPosition(view)==0){
            outRect.top=0;
        }
    }



}
