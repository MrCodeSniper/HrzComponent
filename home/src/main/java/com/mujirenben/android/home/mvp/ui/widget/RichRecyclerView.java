package com.mujirenben.android.home.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mujirenben.android.common.util.Logger;

import java.util.Arrays;

/**
 * 实现了滑动到底部的处理，暂未添加自定义监听器
 * 实现了当LayoutManger是StaggeredGridLayoutManager时，滑到底部的那一行上的子view高度占满RecyclerView
 *
 * author : stone
 * email  : aa86799@163.com
 * time   : 16/5/6 14 23
 */
public class RichRecyclerView extends RecyclerView {




    public RichRecyclerView(Context context) {
        this(context, null);
    }

    public RichRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

//    @Override
//    public void onScrollStateChanged(int state) {
//        if (state == RecyclerView.SCROLL_STATE_IDLE) {
//            LayoutManager layoutManager = getLayoutManager();
//            if (layoutManager instanceof StaggeredGridLayoutManager) {
//                StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) layoutManager;
////                int columnCount = lm.getColumnCountForAccessibility(null,null);//列数
//                int columnCount = lm.getSpanCount();
//                com.orhanobut.logger.Logger.e(columnCount+"&&");
//                int positions[] = new int[columnCount];
//                lm.findLastVisibleItemPositions(positions);//添加 可见的最后一行的 position 数据到数组 positions
//                System.out.println("----" +Arrays.toString(positions));
//                for (int i = 0; i < positions.length; i++) {
//                    System.out.println("当前视图上的最后可见列的位置" + positions[i]);
//                }
//                for (int i = 0; i < positions.length; i++) {
//                    /**
//                     * 判断lastItem的底边到recyclerView顶部的距离
//                     * 是否小于recyclerView的高度
//                     * 如果小于或等于 说明滚动到了底部
//                     */
//                    if (positions[i] >= lm.getItemCount() - columnCount) {
////                        System.out.println("滑动到底了1");
//
////                        System.out.println("总adapter的条目数:" + lm.getItemCount()); //内部取的adapter的方法
////                        System.out.println("总的列数：" + columnCount);
////                        System.out.println("符号条件的最后可见列上的position" + positions[i]);
//
//                        View child = lm.findViewByPosition(positions[i]);
//                        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
//                        layoutParams.height = getHeight() - child.getBottom() + child.getHeight();
//                        child.setLayoutParams(layoutParams);
//                    }
//                }
//                int[] into = new int[columnCount];
//                lm.findFirstCompletelyVisibleItemPositions(into);
//                for (int i = 0; i < into.length; i++) {
//                    System.out.println("首次完全可见的view位置：" + into[i]);
//                }
//
//                lm.findFirstVisibleItemPositions(into);
//                for (int i = 0; i < into.length; i++) {
//                    System.out.println("首次可见的view位置(即使部份可见)：" + into[i]);
//                }
//
//            } else if (layoutManager instanceof LinearLayoutManager){
//                LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
//                int position = lm.findLastVisibleItemPosition();
//                if (position + 1 == lm.getItemCount()) {
//                    System.out.println("滑动到底了2");
//
//                }
//            }
//        }
//        super.onScrollStateChanged(state);
//    }
}