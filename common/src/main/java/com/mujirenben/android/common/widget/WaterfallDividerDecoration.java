package com.mujirenben.android.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ch.android.common.R;

/**
 * Created by mac on 2018/8/27.
 */

public class WaterfallDividerDecoration extends RecyclerView.ItemDecoration{

    private BaseQuickAdapter adapter;
    private Context context;
    private int mWaterfallItemGutterPx;

    public WaterfallDividerDecoration(BaseQuickAdapter adapter,Context context) {
        this.context=context;
        this.adapter = adapter;
        mWaterfallItemGutterPx = context.getResources().getDimensionPixelSize(R.dimen.waterfall_item_gutter);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);


        if(adapter!=null){
            if (adapter.getHeaderLayout() == view) {
                outRect.left = outRect.right = outRect.bottom = outRect.top = 0;
                return;
            }

        }


        if(view.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams){
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            if (lp.getSpanIndex() == 0) {
                outRect.left = context.getResources().getDimensionPixelSize(R.dimen.waterfall_item_border_gutter);
                outRect.right = mWaterfallItemGutterPx / 2;
            } else {
                outRect.left = mWaterfallItemGutterPx / 2;
                outRect.right = context.getResources().getDimensionPixelSize(R.dimen.waterfall_item_border_gutter);
            }
            outRect.bottom = mWaterfallItemGutterPx;
        }else if(view.getLayoutParams() instanceof GridLayoutManager.LayoutParams){
             GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            if (lp.getSpanIndex() == 0) {
                outRect.left = context.getResources().getDimensionPixelSize(R.dimen.waterfall_item_border_gutter);
                outRect.right = mWaterfallItemGutterPx / 2;
            } else {
                outRect.left = mWaterfallItemGutterPx / 2;
                outRect.right = context.getResources().getDimensionPixelSize(R.dimen.waterfall_item_border_gutter);
            }
            outRect.bottom = mWaterfallItemGutterPx;
        }



    }





}
