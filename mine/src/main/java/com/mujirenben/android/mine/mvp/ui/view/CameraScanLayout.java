package com.mujirenben.android.mine.mvp.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;


/**
 * @author: panrongfu
 * @date: 2018/8/11 18:49
 * @describe:
 */

public class CameraScanLayout extends RelativeLayout {

    private RectF rectF;
    private Paint mPaint;
    public CameraScanLayout(Context context) {
        super(context);
        initView();
    }

    public CameraScanLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CameraScanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#80000000"));
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        FrameLayout frameLayout = findViewById(R.id.camera_scan_fl);
        int marginLeft = frameLayout.getLeft();
        int marginTop = frameLayout.getTop();
        int marginRight = frameLayout.getRight();
        int marginBottom = frameLayout.getBottom();
        int parentHeight = getHeight();
        int parentWidth = getWidth();
        Log.e("marginLeft",marginLeft+"");
        Log.e("marginTop",marginTop+"");
        Log.e("marginRight",marginRight+"");
        Log.e("marginBottom",marginBottom+"");
        Log.e("parentHeight",parentHeight+"");
        Log.e("parentWidth",parentWidth+"");

        RectF rectFLeft = new RectF(0,0,marginLeft,parentHeight);
        canvas.drawRect(rectFLeft,mPaint);
        RectF rectFTop = new RectF(marginLeft,0,parentWidth,marginTop);
        canvas.drawRect(rectFTop,mPaint);
        RectF rectFRight = new RectF(marginRight,marginTop,parentWidth,parentHeight);
        canvas.drawRect(rectFRight,mPaint);
        RectF rectFBottom = new RectF(marginLeft,marginBottom,marginRight, parentHeight);
        canvas.drawRect(rectFBottom,mPaint);
    }
}
