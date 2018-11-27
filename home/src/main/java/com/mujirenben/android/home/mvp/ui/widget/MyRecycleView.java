package com.mujirenben.android.home.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mujirenben.android.common.util.EmptyUtils;
import com.mujirenben.android.home.mvp.ui.adapter.HomeMultipleRecycleAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by mac on 2018/5/11.
 */

public class MyRecycleView extends RecyclerView implements GestureDetector.OnGestureListener{

    private float moveY, startsY = -1, dY;
    private static final float minXlength = 20;
    private float startX, startY;
    private JDHeaderView view;
    private HomeMultipleRecycleAdapter adapter;
    private OnScrolledLinstener onScrolledLinstener;
    private LayoutManager layout;
    private boolean scrlled;//是否滚动了,优化用
    private int scroll_count;
    private GestureDetector mGestureDetector;//手势监听


    private int firstVisibleItemPostion;//第一个可见item的position

    private boolean hasMore;//设置是否有加载更多(用户设置)

    private boolean hasRefresh;//设置是否有下拉刷新（用户设置）

    private boolean canRefresh;//是否可以下拉刷新（逻辑判断）

    private boolean isLoading;//列表是否正在刷新或加载更多中

    private boolean canScroll;//列表是否可以滚动（刷新加载中禁止用户进行列表操作）


    /** 是锁定状态 */
    private boolean isLockStatus = false;
    /** 是否可下拉（纵向下拉距离超过横向滑动距离且纵向下拉超过一定距离才判断为下拉状态，否则判定位滑动状态） */


    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        canRefresh = true;
        hasMore = false;
        hasRefresh = true;
        isLoading = false;
        canScroll=true;

        setLinsteners();

        mGestureDetector = new GestureDetector(context, this);
        scroll_count = -1;

    }

    private void setLinsteners() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        addOnScrollListener(new ImageAutoLoadScrollListener());
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Logger.e("添加了监听器3");

    }


    //====================================================================================================

    public void setView(JDHeaderView view) {
        this.view = view;
    }


    @Override
    public void setLayoutManager(LayoutManager layout) {
        this.layout=layout;
        super.setLayoutManager(layout);
    }


    public void setAdapter(HomeMultipleRecycleAdapter adapter) {
        this.adapter = adapter;
        super.setAdapter(adapter);
    }

    //====================================================================================================

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("xxx", "onFling: " + Math.abs(velocityY));
        if(Math.abs(velocityY) > 4000){
            if(EmptyUtils.isNotEmpty(adapter)){
            //    adapter.setScrolling(true);
            }
        }
        return false;
    }


    //====================================================================================================

    public class ImageAutoLoadScrollListener extends OnScrollListener{

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);


            if(dy != 0){
                scrlled = true;
            }


            if(onScrolledLinstener != null){
                onScrolledLinstener.onScrolled(recyclerView, dx, dy);
            }

        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.i("xxx", "stated:" + newState + "  time:" + System.currentTimeMillis());
            switch (newState){

//                case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
//                    //对于滚动不加载图片的尝试
//                    scroll_count ++;
//                    if(adapter!=null){
//                        if(adapter.isScrolling()&&scrlled){
//                            adapter.setScrolling(false);
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                    scrlled=false;
//                    break;
//                case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
//                   if(adapter!=null){
//                       adapter.setScrolling(true);
//                   }
//                    break;
//                case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under
//                   if(adapter!=null){
//                       adapter.setScrolling(true);
//                   }
//                    break;


            }
        }
    }


    //====================================================================================================

    public void setOnScrollLinstener(OnScrolledLinstener onScrolledLinstener){
        this.onScrolledLinstener = onScrolledLinstener;
    }

    public interface OnScrolledLinstener{
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }


//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return !canScroll;
//    }
















    //====================================================================================================

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isLockStatus) {
                    float xGap = Math.abs(event.getX() - startX);
                    float yGap = Math.abs(event.getY() - startY);
                    if (xGap > yGap && xGap > minXlength){
                        canRefresh = false;
                        isLockStatus = true;
                    } else if (yGap > xGap && yGap >minXlength) {
                        canRefresh = true;
                        isLockStatus = true;
                    }
                }

                if (view != null) {
                    view.setPullToRefresh(canRefresh);

                }
                if (canRefresh && getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            default:
                isLockStatus = false;
                canRefresh = false;
                if (view != null) view.setPullToRefresh(true);
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
