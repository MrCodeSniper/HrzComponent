package com.mujirenben.android.home.mvp.ui.callback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mujirenben.android.common.util.EmptyUtils;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class ImageAutoLoadListener extends RecyclerView.OnScrollListener {

    private Context context;

    public ImageAutoLoadListener(Context context) {
        this.context = context;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState){
            case SCROLL_STATE_IDLE:
                if(EmptyUtils.isNotEmpty(context)){
                    Glide.with(context).resumeRequests();
                }
                break;
            case SCROLL_STATE_DRAGGING:
                if(EmptyUtils.isNotEmpty(context)){
                    Glide.with(context).pauseRequests();
                }
                break;
            case SCROLL_STATE_SETTLING:
                if(EmptyUtils.isNotEmpty(context)){
                    Glide.with(context).pauseRequests();
                }
                break;

        }






    }
}
