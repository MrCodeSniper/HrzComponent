package com.mujirenben.android.home.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.mujirenben.android.home.mvp.ui.callback.ImageAutoLoadListener;

public class HrzRecycleView extends RecyclerView {

    private Context mContext;


    public HrzRecycleView(Context context) {
        super(context);
        this.mContext=context;
        prepare();
    }

    public HrzRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        prepare();
    }

    public HrzRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext=context;
        prepare();
    }

    public void prepare(){
        addOnScrollListener(new ImageAutoLoadListener(mContext));
    }








}
