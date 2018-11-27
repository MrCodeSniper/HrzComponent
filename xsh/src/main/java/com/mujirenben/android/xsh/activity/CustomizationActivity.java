package com.mujirenben.android.xsh.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mujirenben.android.xsh.R;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.widget.LoadingDialog;

public class CustomizationActivity extends BaseActivity
{

	@Override
	public void setupActivityComponent(@NonNull AppComponent appComponent)
	{

	}

	@Override
	public int initView(@Nullable Bundle savedInstanceState)
	{
		StatusBarUtil.setStatusBarColor(this, R.color.white);
		return R.layout.activity_customization;
	}

	@Override
	public void initData(@Nullable Bundle savedInstanceState)
	{

		// LoadingDialog.getInstance(this).show();
		// LoadingDialog.getInstance(this).hide();

	}
}
