package com.mujirenben.android.xsh.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int decoration;


    public SpaceItemDecoration(int decoration) {
        this.decoration = decoration;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left=decoration;
        outRect.right=decoration;
        outRect.bottom=decoration;

        if(parent.getChildPosition(view)==0){
            outRect.top=decoration;
        }

    }
}
