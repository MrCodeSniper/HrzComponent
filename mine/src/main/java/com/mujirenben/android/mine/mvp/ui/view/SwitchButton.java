package com.mujirenben.android.mine.mvp.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mujirenben.android.common.util.LogUtil;

public class SwitchButton extends View {

    public static final int STATE_OFF = 0;
    public static final int STATE_ON = 1;

    private int mState = STATE_OFF;

    private int mOnBackgroundColor = 0xFFED4143;
    private int mOffBackgroundColor = 0xFFECECEC;
    private int mThumbColor = 0xFFFFFFFF;

    private Paint mBackgroundPaint = new Paint();
    private Paint mThumbPaint = new Paint();

    private int mPaddingThumbAndOutlinePx;//dp

    private OnStateChangedListener mListener;

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaddingThumbAndOutlinePx = (int) (1 * getContext().getResources().getDisplayMetrics().density);

        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(isChecked() ? mOnBackgroundColor : mOffBackgroundColor);

        mThumbPaint.setAntiAlias(true);
        mThumbPaint.setColor(mThumbColor);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(!isChecked());
            }
        });

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBackgroundPaint.setColor(isChecked() ? mOnBackgroundColor : mOffBackgroundColor);
        canvas.drawCircle(getHeight() / 2, getHeight() / 2, getHeight() / 2, mBackgroundPaint);
        canvas.drawCircle(getWidth() - getHeight() / 2, getHeight() / 2, getHeight() / 2, mBackgroundPaint);
        canvas.drawRect(getHeight() / 2, 0, getWidth() - getHeight() / 2, getHeight(), mBackgroundPaint);
        int radius = getHeight() / 2 - mPaddingThumbAndOutlinePx;
        if (isChecked()) {
            canvas.drawCircle(getWidth() - getHeight() / 2, getHeight() / 2, radius, mThumbPaint);
        } else {
            canvas.drawCircle(getHeight() / 2, getHeight() / 2, radius, mThumbPaint);
        }
    }

    public boolean isChecked() {
        return mState == STATE_ON;
    }

    public void setChecked(boolean checked) {
        if (checked == isChecked()) {
            return;
        }

        mState = checked ? STATE_ON : STATE_OFF;
        if (mListener != null) {
            mListener.onStateChanged(this, isChecked());
        }
        postInvalidate();
    }

    public void setOnStateChangedListener(OnStateChangedListener listener) {
        this.mListener = listener;
    }

    public interface OnStateChangedListener {
        void onStateChanged(SwitchButton which, boolean checked);
    }
}
