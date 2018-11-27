package com.mujirenben.android.xsh.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.R2;
import com.mujirenben.android.xsh.adapter.MyViewPagerAdapter;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.PersonData;
import com.mujirenben.android.xsh.entity.PersonIndexTop;
import com.mujirenben.android.xsh.fragment.ActivityCheckFragment;
import com.mujirenben.android.xsh.fragment.ActivityRulesFragment;
import com.mujirenben.android.xsh.fragment.ActivityShareFragment;
import com.mujirenben.android.xsh.service.AllianceService;
import com.mujirenben.android.xsh.utils.NetServiceUtils;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.ToastUtils;
import com.orhanobut.logger.Logger;


import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by Cyj on 2018/8/2.
 */
@Route(path = ARouterPaths.ALLIANCE_DOCKING)
public class DockingActivity<T> extends BaseActivity implements TabLayout.OnTabSelectedListener
{

	@BindView(R2.id.tb_layout)
	TabLayout				tbLayout;
	@BindView(R2.id.submit)
	AppCompatTextView		submit;
	@BindView(R2.id.viewpager)
	ViewPager				viewpager;
	@BindView(R2.id.convenientBanner)
	BGABanner				convenientBanner;
	@BindView(R2.id.tv_back)
	ImageView				tvBack;
	@BindView(R2.id.tv_titlebar)
	TextView				tvTitlebar;
	private Retrofit		retrofit;
	private AllianceService	allianceService;

	public static void startSelf(Context context)
	{
		context.startActivity(new Intent(context, DockingActivity.class));
	}

	private PersonData		personData;
	private List<Fragment>	fragmentList	= new ArrayList<>();

	private List<String>	mTitleList		= new ArrayList<>();

	private List<String>	localImages		= new ArrayList<>();

	@Override
	public void setupActivityComponent(@NonNull AppComponent appComponent)
	{


	}

	@Override
	public int initView(@Nullable Bundle savedInstanceState)
	{
//		StatusBarUtil.setStatusBarWhite(this);
		if (SpUtil.getIsMIUI(this)){
			StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
		}else {
			StatusBarUtil.setStatusBarWhite(this);
		}
		x.Ext.init(getApplication());



		return R.layout.hrz_activity_docking;
	}

	@Override
	public void initData(@Nullable Bundle savedInstanceState)
	{
		tvTitlebar.setText("成为对接人");
		tvBack.setOnClickListener(v -> finish());
		mTitleList.add("活动规则");
		mTitleList.add("分享素材");
		mTitleList.add("对接商家查询");
		viewpager.setOffscreenPageLimit(mTitleList.size());
		initData();
	}

	private void prepareInit()
	{
		retrofit = NetServiceUtils.getRetrofitNew();
		allianceService = retrofit.create(AllianceService.class);
	}

	private void initData()
	{
		prepareInit();
		getTopData();
		fragmentList.add(new ActivityRulesFragment());
		fragmentList.add(new ActivityShareFragment());
		fragmentList.add(new ActivityCheckFragment());

		MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList, mTitleList);
		viewpager.setAdapter(adapter);
		tbLayout.setupWithViewPager(viewpager);
		tbLayout.setOnTabSelectedListener(this);

		// HttpParamUtil.getCommonSignParamMap()

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void getTopData()
	{

		Observable<PersonData> call = allianceService.getPersonIndex(HttpParamUtil.getCommonSignParamMap(this, null));
		call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(personIndexTop -> {
			if (personIndexTop != null && personIndexTop.getData() != null)
			{
				personData = personIndexTop;
				if (personIndexTop.getData().getBanners() != null)
				{
					for (int i = 0; i < personIndexTop.getData().getBanners().size(); i++)
					{
						localImages.add(personIndexTop.getData().getBanners().get(i).getImg());
					}
					convenientBanner.setData(R.layout.banner_item, localImages, null);
					convenientBanner.setAdapter((banner, itemView, url, position) -> {
						ImageView simpleDraweeView = (ImageView) itemView.findViewById(R.id.sdv_item_fresco_content);
						Glide.with(DockingActivity.this).load(url).into(simpleDraweeView);
					});

					if ("0".equals(personIndexTop.getData().getStatus() + ""))
					{
						submit.setText("您暂时没有权限");
					}
					else if ("1".equals(personIndexTop.getData().getStatus() + ""))
					{
						submit.setText("申请并成为对接人");
					}
					else if ("2".equals(personIndexTop.getData().getStatus() + ""))
					{
						submit.setText("查看我的邀请码");
					}
				}
			}
		}, throwable -> {
			Logger.e(throwable.getMessage());
		});

	}

	@OnClick(R2.id.submit)
	public void onViewClicked(View view)
	{

		if (personData == null)
		{
			Intent intent = new Intent();
			intent.setClass(this, SqCodeActivity.class);
			intent.putExtra(Constants.IntentConstant.FENLEI_NAME,
					LoginDataManager.getsInstance(this).getDisplayName() + "");
			startActivity(intent);
			return;
		}

		int i = view.getId();
		if (i == R.id.submit)
		{
			if (personData.getData().getStatus() == 0)
			{
				ArmsUtils.makeText(this,"您暂时没有权限");

			}
			else if (personData.getData().getStatus() == 1)
			{

				Dialog dialog = getDialog(DockingActivity.this, R.layout.dialog_isdocker);
				dialog.show();
				TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
				TextView commit = (TextView) dialog.findViewById(R.id.commit);
				TextView tv_code = (TextView) dialog.findViewById(R.id.tv_code);
				tv_code.setText("对接人编码:"+LoginDataManager.getsInstance(this).getUserId() + "");
				cancel.setOnClickListener((v1) -> {
					dialog.dismiss();
				});
				commit.setOnClickListener((v1) -> {
					Intent intent = new Intent();
					intent.setClass(this, SqCodeActivity.class);
					intent.putExtra(Constants.IntentConstant.FENLEI_NAME,
							LoginDataManager.getsInstance(this).getDisplayName() + "");
					startActivity(intent);
					dialog.dismiss();
				});


			}else if(personData.getData().getStatus() == 2){
				Intent intent = new Intent();
				intent.setClass(this, SqCodeActivity.class);
				intent.putExtra(Constants.IntentConstant.FENLEI_NAME,
						LoginDataManager.getsInstance(this).getDisplayName() + "");
				startActivity(intent);
			}

		}
	}

	@Override
	public void onTabSelected(TabLayout.Tab tab)
	{
		switch (tab.getPosition())
		{
		case 0:
			convenientBanner.setVisibility(View.VISIBLE);
			break;
		default:
			convenientBanner.setVisibility(View.GONE);
			break;
		}
	}

	public static Dialog getDialog(Context context, int layout)
	{
		final Dialog dlgs = new Dialog(context);
		dlgs.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dlgs.setContentView(layout);
		return dlgs;
	}


	@Override
	public void onTabUnselected(TabLayout.Tab tab)
	{

	}

	@Override
	public void onTabReselected(TabLayout.Tab tab)
	{

	}

}
