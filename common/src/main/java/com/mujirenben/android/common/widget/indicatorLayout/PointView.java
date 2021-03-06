package com.mujirenben.android.common.widget.indicatorLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.ch.android.common.R;
import java.util.concurrent.TimeUnit;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


/**
 * @author: panrongfu
 * @date: 2018/7/9 12:48
 * @describe: 自定义指示器小圆点，以控件的height为圆的直径
 */

public class PointView extends View {

    private static final int DEFAULT_COLOR = Color.WHITE;
    private static final int DEFAULT_STRETCH_COLOR = Color.YELLOW;
    private int pColor;
    /**拉伸条的颜色*/
    private int pStretchColor;
    private int pWidth;
    private int pHeight;
    private Paint mRectFPaint;
    /**圆点的画笔*/
    private Paint mPointPaint;
    private boolean layoutStartStretch = false;
    private boolean layoutStartCompress = false;
    private Disposable mDisposable;

    private RectF mRectF;
    private int mRectFLeft;
    private int mRectFRight;
    private int tempWidth=0;

    private boolean layoutChange;

    public PointView(Context context) {
        super(context);
        initView(context,null);
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context mContext, AttributeSet mAttrs) {

        TypedArray ta = mContext.obtainStyledAttributes(mAttrs, R.styleable.pointerView);
        pColor = ta.getColor(R.styleable.pointerView_pColor,DEFAULT_COLOR);
        pStretchColor = ta.getColor(R.styleable.pointerView_pStretchColor,DEFAULT_STRETCH_COLOR);
        /*使用完之后回收*/
        ta.recycle();

        mRectFPaint = new Paint();
        mPointPaint = new Paint();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        /**获取控件的宽度*/
        pWidth = getWidth();
        /**获取控件的高度*/
        pHeight =  getHeight();

      //  Log.e("onLayout","pWidth:"+pix2dip(pWidth));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //pWidth = getWidth();
     //   Log.e("onMeasure","pWidth:"+pix2dip(pWidth));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**获取控件的宽度*/
        pWidth = getWidth();
        /**获取控件的高度*/
        pHeight =  getHeight();
       // Log.e(">>>>>>","pWidth:"+pix2dip(pWidth));

        mRectFPaint.setStyle(Paint.Style.FILL);//充满
        mRectFPaint.setColor(pStretchColor);
        mRectFPaint.setAntiAlias(true);// 设置画笔的抗锯齿效果

        mPointPaint.setStyle(Paint.Style.FILL);//充满
        mPointPaint.setColor(pColor);
        mPointPaint.setAntiAlias(true);// 设置画笔的抗锯齿效果


        if(layoutStartStretch){//动画开始才绘制
            /*当布局大小改变的时候调用*/
            if(layoutChange){
                startStretch(2);
                layoutChange = false;
            }
        //    Log.e("onDraw-my","pHeight:"+ pix2dip(pHeight)+"   pWidth:"+pix2dip(pWidth)+"  mRectFLeft:"+pix2dip(mRectFLeft)+"   mRectFRight:"+pix2dip(mRectFRight));
            mRectF = new RectF(mRectFLeft,0,mRectFRight,pHeight);//绘制矩形
            canvas.drawRoundRect(mRectF,0,0,mRectFPaint);
            canvas.drawCircle(mRectFLeft+1,pHeight/2,pHeight/2,mRectFPaint);//在左边矩形画个圆
            canvas.drawCircle(mRectFRight-1,pHeight/2,pHeight/2,mRectFPaint);//在右矩形边上画个圆
        }else {
            canvas.drawCircle(pWidth/2,pHeight/2,pHeight/2,mPointPaint);//在左边矩形画个圆
        }
    }


    public boolean isLayoutStartStretch() {
        return layoutStartStretch;
    }

    public void setLayoutStartStretch(boolean layoutStartStretch) {
        this.layoutStartStretch = layoutStartStretch;
    }

    public boolean isLayoutStartCompress() {
        return layoutStartCompress;
    }

    public void setLayoutStartCompress(boolean layoutStartCompress) {
        this.layoutStartCompress = layoutStartCompress;
    }

    public boolean isLayoutChange() {
        return layoutChange;
    }

    public void setLayoutChange(boolean layoutChange) {
        this.layoutChange = layoutChange;
    }

    /**
     * 开始拉伸
     * @param milliseconds
     */
    public void startStretch(int milliseconds){
        mRectFLeft = mRectFRight = pWidth/2;
     //   Log.e("startStretch","  mRectFLeft:"+pix2dip(mRectFLeft)+"   mRectFRight:"+pix2dip(mRectFRight));
        mDisposable = Flowable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .doOnNext(aLong -> {})
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    tempWidth ++;//这里循环+1是为了取余，从而达到mRectFLeft 和 mRectFRight 都有数值变化的目的
                    if(tempWidth%2==0){
                        //当mRectFLeft 小于圆的半径 或者刚好等于圆的半径则停止拉伸
                        if(mRectFLeft < pHeight/2 || mRectFLeft == pHeight/2){
                            mRectFLeft = pHeight/2;
                            stretchStop();
                        }
                        mRectFLeft--;
                    }else {
                        //当mRectFRight的位置超过了pWidth - pHeight/2的值（即右边留出的部分已经不够绘制半圆了）
                        //或是刚好等于pWidth - pHeight/2 则停止拉伸
                        if(mRectFRight > pWidth - pHeight/2 || mRectFRight == pWidth - pHeight/2){
                            mRectFRight = pWidth - pHeight/2;
                            stretchStop();
                        }
                        mRectFRight++;
                    }
                    invalidate();
                });
    }

    /**
     * 开始收缩
     * @param milliseconds
     */
    public void startCompress(int milliseconds){

        mDisposable = Flowable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .doOnNext(aLong -> {})
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    invalidate();
                    mRectFLeft+=2;//每隔 milliseconds 左边矩形距离left加1
                    if(pWidth/2-mRectFLeft>pHeight/2
                            ||pWidth/2 -mRectFLeft == pHeight/2){
                        mRectFLeft = pWidth/2-pHeight/2;
                        compressStop();
                    }

                    mRectFRight-=2;//每隔 milliseconds 右边矩形right加1
                    if(mRectFRight < pWidth/2+pHeight/2 || mRectFRight == pWidth/2+pHeight/2){
                        mRectFRight = pWidth/2+pHeight/2;
                        compressStop();
                    }
                });
    }

    /**
     * 回缩停止
     */
    private void compressStop() {
        layoutStartStretch = false;
        invalidate();
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }

    /**
     * 拉伸停止
     */
    public void stretchStop(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }

    public  int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * pix转dip
     */
    public  int pix2dip(float pix) {
        final float densityDpi = getResources().getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }

    public void setpColor(int pColor) {
        this.pColor = pColor;
    }

    public void setpStretchColor(int pStretchColor) {
        this.pStretchColor = pStretchColor;
    }
}
