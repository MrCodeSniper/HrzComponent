package com.mujirenben.android.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.ch.android.common.R;
import com.orhanobut.logger.Logger;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.alibaba.android.arouter.launcher.ARouter.logger;

public class BackToTopView extends AppCompatImageView{
    private static final int TRIGGLE_TO_TOP_COUNT = 2; //所有请求达到两次就显示返回顶部按钮
    private static final int mScrollThreshold = 30;
    private boolean isShow;
    /** 可以滚动的view recyclerview listview scrollview**/
    private RecyclerView recycler;
    AnimationSet upAnimation ,downAnimation;
    public BackToTopView(Context context) {
        this(context,null);
    }

    public BackToTopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BackToTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVisibility(VISIBLE);
        upAnimation = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.top_up);
        downAnimation = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.top_down);
    }

    public void attathRecyclerView(RecyclerView recyclerView){
        recycler = recyclerView;
        setVisibility(GONE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当列表停止滚动的时候
                if (newState == SCROLL_STATE_IDLE ){
                    if (isShow){
                        show();
                    }else{
                        hide();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (Math.abs(dy) >= mScrollThreshold){
//                    isShow = scrollDistance >= 1000;
                    isShow = dy < 0;
                }

            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recycler != null){
                    recycler.smoothScrollToPosition(0);
                    hide();
                }
            }
        });
    }

    public void triggle(int count){
        if (count < TRIGGLE_TO_TOP_COUNT){
            setVisibility(View.GONE);
            return;
        }

        if (getVisibility() == VISIBLE){
            return;
        }

        show();
    }

    private void show(){
        setVisibility(VISIBLE);
        up();
    }

    private void hide(){
        down();
        setVisibility(GONE);
    }

    /**
     * 显示动画
     */
    private void up(){
        setAnimation(upAnimation);
        upAnimation.start();
    }

    /**
     * 隐藏动画
     */
    private void down(){
        setAnimation(downAnimation);
        downAnimation.start();
    }

}
