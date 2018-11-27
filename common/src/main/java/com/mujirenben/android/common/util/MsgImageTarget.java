package com.mujirenben.android.common.util;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/24.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

public class MsgImageTarget  extends BitmapImageViewTarget {
    // 长图，宽图比例阈值
    public static final int RATIO_OF_LARGE = 3;
    // 长图截取后的高宽比（宽图截取后的宽高比）
    public static int HW_RATIO = 3;

    public MsgImageTarget(ImageView view)
    {
        super(view);
    }

    @Override
    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
        super.onResourceReady(resource, transition);
    }

    private Bitmap resolveBitmap(Bitmap resource)
    {
        int srcWidth = resource.getWidth();
        int srcHeight = resource.getHeight();

        if (srcWidth > srcHeight)
        {
            float srcWHRatio = (float) srcWidth / srcHeight;
            // 宽图
            if (srcWHRatio > RATIO_OF_LARGE)
            {
                return Bitmap.createBitmap(resource, 0, 0, srcHeight * HW_RATIO, srcHeight);
            }
        }
        else
        {
            float srcHWRatio = (float) srcHeight / srcWidth;
            // 长图
            if (srcHWRatio > RATIO_OF_LARGE)
            {
                return Bitmap.createBitmap(resource, 0, 0, srcWidth, srcWidth * HW_RATIO);
            }
        }
        return resource;
    }


}
