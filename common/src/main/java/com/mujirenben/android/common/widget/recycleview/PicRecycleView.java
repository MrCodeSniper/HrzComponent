package com.mujirenben.android.common.widget.recycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by mac on 2018/8/26.
 */

public class PicRecycleView extends RecyclerView{

    private Context mContext;

    public PicRecycleView(Context context) {
        super(context);
    }

    public PicRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PicRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
