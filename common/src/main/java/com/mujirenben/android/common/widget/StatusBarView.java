package com.mujirenben.android.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mujirenben.android.common.util.StatusBarUtil;

public class StatusBarView extends View {

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sbHeight = StatusBarUtil.getStatusBarHeight(getContext());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { // 5.0以下的机器不能做沉浸式，所以这个view的高度为0
            sbHeight = 0;
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
                sbHeight, MeasureSpec.EXACTLY));
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    public void adjustStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        Drawable drawable = getBackground();
        if (drawable instanceof ColorDrawable) {
            int color = ((ColorDrawable)drawable).getColor();

            View decor = activity.getWindow().getDecorView();
            int newSysUiVisibility = decor.getSystemUiVisibility();

            if (Color.alpha(color) == 0) { // 透明
                newSysUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                int grayValue = getGrayValue(color);
                if (grayValue < 128) { // 偏黑的颜色，设置白色的状态栏
                    Log.i("jan3", "黑色");
                    newSysUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    Log.i("jan3", "白色");
                    newSysUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
            }

            decor.setSystemUiVisibility(newSysUiVisibility);
        }
    }

    private int getGrayValue(int color) {
        int r = (color & 0x00ff0000) >> 16;
        int g = (color & 0x0000ff00) >> 8;
        int b = (color & 0x000000ff);
        return (r * 38 + g * 75 + b * 15) >> 7;
    }
}
