package com.mujirenben.android.common.widget;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.android.common.R;


/**
 * @author: panrongfu
 * @date: 2018/8/22 15:09
 * @describe:
 */

public class LoadingButton extends LinearLayout {

    private TextView  loadingTv;
    private ImageView loadingIv;
    private Animation rotate;

    public LoadingButton(Context context) {
        super(context);
        initViewUI(context);
    }

    public LoadingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViewUI(context);
    }

    public LoadingButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewUI(context);
    }

    private void initViewUI(Context context) {
        setBackground(getContext().getResources().getDrawable(R.drawable.button_coner_transparent_login));
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        loadingTv = new TextView(context);
        loadingIv = new ImageView(context);
        loadingIv.setImageResource(R.drawable.samll_loading_icon);
        LayoutParams loadParams = new LayoutParams(dip2px(15f),dip2px(15f));
        loadParams.gravity = Gravity.CENTER_VERTICAL;
        loadParams.leftMargin = dip2px(8f);
        loadingIv.setLayoutParams(loadParams);

        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        loadingTv.setLayoutParams(textParams);
        loadingTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,dip2px(16f));
        addView(loadingTv);
        addView(loadingIv);
    }

    /**
     * 默认的状态（未激活状态）
     * @param text
     */
    public void setUnActivateState(String text){
        setBackground(getContext().getResources().getDrawable(R.drawable.button_coner_transparent_login));
        loadingTv.setText(text);
        loadingTv.setTextColor(Color.parseColor("#80ffffff"));
        loadingIv.setVisibility(GONE);
    }

    /**
     * 设置加载文字（激活状态）
     * @param text
     */
    public void setActivateState(String text){
        setBackground(getContext().getResources().getDrawable(R.drawable.solid_white_corner_22dp_bg));
        loadingTv.setText(text);
        loadingTv.setTextColor(getContext().getResources().getColor(R.color.main_color));
        loadingIv.setVisibility(GONE);
    }

    /**
     * 开始加载
     */
    @SuppressLint("WrongConstant")
    public void startLoading(String loadingText){
        loadingIv.setVisibility(VISIBLE);
        loadingTv.setTextColor(Color.WHITE);
        loadingTv.setText(loadingText);
        setBackground(getContext().getResources().getDrawable(R.drawable.button_coner_transparent_login));

        ObjectAnimator animator = ObjectAnimator.ofFloat(loadingIv, "rotation", 0,360);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    /**
     * dip转pix
     * @param dpValue
     * @return
     */
    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
