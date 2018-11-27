package com.mujirenben.android.common.widget.switcher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.ch.android.common.R;
import com.mujirenben.android.common.util.ArmsUtils;



/**
 * @author pan
 */
public class AdvTextSwitcher extends TextSwitcher {
	private final Context mContext;
	private String[] mTips = new String[]{};
	private int currentPos;
	private OnTipClickListener mListener;
	private int switcherTextColor;
	public AdvTextSwitcher(Context context, AttributeSet attrs){
		super(context, attrs);
		this.mContext = context;
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.advTextViewSwitcher);
		switcherTextColor = ta.getColor(R.styleable.advTextViewSwitcher_switcherTextColor, Color.BLACK);
		//拿到的是px
		int switcherTextSize = ta.getDimensionPixelSize(R.styleable.advTextViewSwitcher_switcherTextSize, 14);
        int switcherTextColor = ta.getColor(R.styleable.advTextViewSwitcher_switcherTextColor,Color.WHITE);
		int switcherAnimInRes = ta.getResourceId(R.styleable.advTextViewSwitcher_switcherAnimInRes, R.anim.fade_in_slide_in);
        int switcherAnimOutRes = ta.getResourceId(R.styleable.advTextViewSwitcher_switcherAnimOutRes, R.anim.fade_out_slide_out);
        int switcherMarginLeft = (int) ta.getDimension(R.styleable.advTextViewSwitcher_switcherMarginLeft,5);
        int switcherMarginRight = (int) ta.getDimension(R.styleable.advTextViewSwitcher_switcherMarginRight,5);
        int switcherGravity = ta.getInt(R.styleable.advTextViewSwitcher_switcherGravity, 0x03);
		ta.recycle();
		this.setFactory(() -> {
            TextView innerText = new TextView(mContext);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            params.gravity = switcherGravity == 0x03? Gravity.CENTER:Gravity.LEFT|Gravity.CENTER;
            params.leftMargin = switcherMarginLeft;
            params.rightMargin = switcherMarginRight;
            innerText.setLayoutParams(params);
            innerText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,pix2dip(switcherTextSize));
            innerText.setSingleLine();
            innerText.setEllipsize(TextUtils.TruncateAt.END);
            innerText.setTextColor(switcherTextColor);
            innerText.setOnClickListener(p1 -> mListener.onTipClick(currentPos));
            return innerText;
        });

		Animation animIn = AnimationUtils.loadAnimation(mContext, switcherAnimInRes);
		Animation animOut = AnimationUtils.loadAnimation(mContext, switcherAnimOutRes);

		this.setInAnimation(animIn);
		this.setOutAnimation(animOut);
	}


	public void setAnim(int animInRes, int animOutRes){
		Animation animIn = AnimationUtils.loadAnimation(mContext, animInRes);
		Animation animOut = AnimationUtils.loadAnimation(mContext, animOutRes);
		this.setInAnimation(animIn);
		this.setOutAnimation(animOut);
	}
	
	public void setAnim(Animation animIn, Animation animOut){
		this.setInAnimation(animIn);
		this.setOutAnimation(animOut);
	}

	public void setSwitcherTextColor(int switcherTextColor) {
		this.switcherTextColor = switcherTextColor;
		int count = getChildCount();
		for(int i=0;i<count;i++){
            TextView textView = (TextView) getChildAt(i);
            textView.setTextColor(switcherTextColor);
        }
	}

	public void clearTips(){
	    mTips = null;
    }

	public void setTpis(String[] tips){
		if (tips.length > 0){
			this.mTips = tips;
			this.currentPos = 0;
			updateTips();
		}
	}
	
	public void setOnTipClickListener(OnTipClickListener listener){
		mListener = listener;
	}

	public void nextTips(){
		if (mTips.length > 0){
			if (currentPos < mTips.length - 1){
				currentPos++;
			}else{
				currentPos = 0;
			}
            updateTips();
		}
	}


	public interface OnTipClickListener{
		 void onTipClick(int position);
	}

    /**
     * 更新textview文字
     */
	private void updateTips(){
		this.setText(Html.fromHtml(mTips[currentPos]));
	}

    public int dip2px( float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

	public int pix2dip(int pix) {
		final float densityDpi = getResources().getDisplayMetrics().density;
		return (int) (pix / densityDpi + 0.5f);
	}
}