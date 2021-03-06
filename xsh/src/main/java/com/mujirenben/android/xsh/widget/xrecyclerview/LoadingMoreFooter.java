package com.mujirenben.android.xsh.widget.xrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.utils.DeviceUtils;
import com.mujirenben.android.xsh.widget.xrecyclerview.progressindicator.AVLoadingIndicatorView;

public class LoadingMoreFooter extends LinearLayout
{

	private SimpleViewSwithcer	progressCon;
	private Context				mContext;
	public final static int		STATE_LOADING	= 0;
	public final static int		STATE_COMPLETE	= 1;
	public final static int		STATE_NOMORE	= 2;
	private String				loadingHint;
	private String				noMoreHint;
	private TextView			mText;

	public LoadingMoreFooter(Context context)
	{
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LoadingMoreFooter(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	public void initView(Context context)
	{
		mContext = context;

		LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				DeviceUtils.dip2px(getContext(), 35));
		layoutParams2.setMargins(15, 0, 15, 10);
		setGravity(Gravity.CENTER);
		setLayoutParams(layoutParams2);

		setBackgroundColor(Color.parseColor("#ECECEC"));
		progressCon = new SimpleViewSwithcer(context);
		progressCon.setLayoutParams(
				new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
		progressView.setIndicatorColor(0xffB5B5B5);
		progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
		progressCon.setView(progressView);

		addView(progressCon);
		mText = new TextView(context);
		mText.setText("正在加载...");
		mText.setTextColor(Color.parseColor("#ed4143"));
		LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins((int) getResources().getDimension(R.dimen.textandiconmargin), 0, 0, 0);

		mText.setLayoutParams(layoutParams);
		addView(mText);
	}

	public void setProgressStyle(int style)
	{
		if (style == ProgressStyle.SysProgress)
		{
			progressCon.setView(new ProgressBar(mContext, null, android.R.attr.progressBarStyleSmall));
		}
		else
		{
			AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
			progressView.setIndicatorColor(0xffB5B5B5);
			progressView.setIndicatorId(style);
			progressCon.setView(progressView);
		}
	}

	public void setLoadingHint(String hint)
	{
		loadingHint = hint;
	}

	public void setNoMoreHint(String hint)
	{
		noMoreHint = hint;
	}

	public void setState(int state)
	{
		Log.i("info", "state:::::::::::::::::" + state);
		switch (state)
		{
		case STATE_LOADING:
			progressCon.setVisibility(View.VISIBLE);
			mText.setText(mContext.getText(R.string.listview_loading));

			this.setVisibility(View.VISIBLE);
			break;
		case STATE_COMPLETE:
			mText.setText(mContext.getText(R.string.listview_loading));
			this.setVisibility(View.GONE);
			break;
		case STATE_NOMORE:
			mText.setText(mContext.getText(R.string.listview_loading));
			this.setVisibility(View.GONE);
			// mText.setText(mContext.getText(R.string.nomore_loading));
			// progressCon.setVisibility(View.GONE);
			// this.setVisibility(View.VISIBLE);
			break;
		}

	}
}
