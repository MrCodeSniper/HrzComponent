package com.mujirenben.android.vip.mvp.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.mujirenben.android.common.util.Logger;
import com.mujirenben.android.vip.R;


import cn.bingoogolapple.bgabanner.BGABanner;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Author: panrongfu
 * Date:2018/6/25 20:37
 * Description:
 */

public class VipRefreshLayout extends PtrFrameLayout implements PtrUIHandler {

    private static final String TAG = "JDHeaderView";
    private TextView status_text;
    private ImageView ren;
    private ImageView hezi;
    private int viewHeight;
    private ImageView donghua;
    private AnimationDrawable drawable;
    private com.mujirenben.android.common.widget.JDHeadersView.RefreshDistanceListener listener;
    /**
     * 自开始下拉 0.2倍height内是否执行了缩放，
     */
    private boolean isScale;

    public void setOnRefreshDistanceListener(com.mujirenben.android.common.widget.JDHeadersView.RefreshDistanceListener listener) {
        this.listener = listener;
    }

    public VipRefreshLayout(Context context) {
        super(context);
        initView();
    }

    public VipRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VipRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    // 记录viewPager是否拖拽的标记
    private boolean mIsHorizontalMove;
    // 记录事件是否已被分发
    private boolean isDeal;
    private BGABanner mViewPager;
    private int mTouchSlop;
    private float startY;
    private float startX;
    private ObjectAnimator animator;

    public void setViewPager(BGABanner viewPager) {
        this.mViewPager = viewPager;
        if (mViewPager == null) {
            throw new IllegalArgumentException("viewPager can not be null");
        }
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (mViewPager == null) {
//            return super.dispatchTouchEvent(ev);
//        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsHorizontalMove = false;
                isDeal = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果已经判断出是否由横向还是纵向处理，则跳出
                if (isDeal) {
                    break;
                }
                /**拦截禁止交给Ptr的 dispatchTouchEvent处理**/
                mIsHorizontalMove = true;
                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                if (distanceX != distanceY) {
                    // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                    if (distanceX > mTouchSlop && distanceX > distanceY) {
                        mIsHorizontalMove = true;
                        isDeal = true;
                    } else if (distanceY > mTouchSlop) {
                        mIsHorizontalMove = false;
                        isDeal = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //下拉刷新状态时如果滚动了viewpager 此时mIsHorizontalMove为true 会导致PtrFrameLayout无法恢复原位
                // 初始化标记,
                mIsHorizontalMove = false;
                isDeal = false;
                break;
        }
        if (mIsHorizontalMove) {
            return dispatchTouchEventSupper(ev);
        }
        return super.dispatchTouchEvent(ev);
    }









    private void initView() {
        View view = View.inflate(this.getContext(), R.layout.hrz_header_view_layout,null);


//        status_text = (TextView) view.findViewById(R.id.status_test);
//        ren = (ImageView) view.findViewById(R.id.ren);
//        hezi = (ImageView) view.findViewById(R.id.hezi);
        donghua = (ImageView) view.findViewById(R.id.donghua);
        donghua.setVisibility(View.VISIBLE);
        animator = ObjectAnimator.ofFloat(donghua, "rotation", 0,360);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setDuration(750);
        animator.setInterpolator(new LinearInterpolator());
        // drawable = (AnimationDrawable) donghua.getDrawable();
        setRatioOfHeaderHeightToRefresh(1.0f);
        setHeaderView(view);
        addPtrUIHandler(this);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        //drawable.stop();
        animator.cancel();
        donghua.setVisibility(View.GONE);
//        ren.setVisibility(View.VISIBLE);
//        hezi.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        if(frame.isPullToRefresh()){
            //   status_text.setText("松开刷新...");
            donghua.setVisibility(View.VISIBLE);
        }else{
            //      status_text.setText("下拉刷新...");
            donghua.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {//开始更新
//        ren.setVisibility(View.GONE);
//        hezi.setVisibility(View.GONE);
        donghua.setVisibility(View.VISIBLE);
        animator.start();
        //   status_text.setText("更新中...");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
    }



    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        //ptrIndicator.setRatioOfHeaderHeightToRefresh(1.0f);
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();
        if(listener != null){
            listener.onPositionChange(currentPos);
        }
        if(viewHeight == 0)
            viewHeight = ptrIndicator.getHeaderHeight();
        float v = currentPos * 1.0f / viewHeight;
        if(v > 1)v= 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //此处防止首次下拉到0.2height时突然缩小
            if(!isScale && v <= 0.2){
                isScale = true;
                setImgScale(0.2f);
            }
            if(v > 0.2){
                setImgScale(v);
            }
        }

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                //      status_text.setText("下拉刷新...");
                donghua.setVisibility(View.VISIBLE);

            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                //   status_text.setText("松开刷新...");
                donghua.setVisibility(View.VISIBLE);

            }
        }

    }

    private void setImgScale(float v) {
//        ren.setScaleY(v);
//        ren.setScaleX(v);
//        hezi.setScaleY(v);
//        hezi.setScaleX(v);
    }

    public interface RefreshDistanceListener{
        void onPositionChange(int currentPosY);
    }

}

