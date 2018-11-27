package com.mujirenben.android.xsh.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.activity.DockingActivity;
import com.mujirenben.android.xsh.entity.PersonData;
import com.mujirenben.android.xsh.entity.PersonIndexTop;
import com.mujirenben.android.xsh.service.AllianceService;
import com.mujirenben.android.xsh.utils.NetServiceUtils;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.util.Logger;
import com.mujirenben.android.common.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by Cyj on 2018/8/2.
 */
public class ActivityRulesFragment extends BaseFragment
{

	private Retrofit						retrofit;
	private AllianceService					allianceService;
	private View							view;
	private static ActivityRulesFragment	activityRulesFragment;
	private PersonIndexTop					personIndexTop;
	private ImageView						iv_imgicon;

	@Override
	public void setupFragmentComponent(@NonNull AppComponent appComponent)
	{

	}

	@Override
	public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.hrz_fragment_activityrules, container, false);
		iv_imgicon = (ImageView) view.findViewById(R.id.iv_imgicon);
		return view;
	}

	@Override
	public void initData(@Nullable Bundle savedInstanceState)
	{
		LoadingDialog.getInstance(getActivity()).show();
		prepareInit();
		getTopData();
	}

	@Override
	public void setData(@Nullable Object data)
	{

	}

	private void prepareInit()
	{
		retrofit = NetServiceUtils.getRetrofitNew();
		allianceService = retrofit.create(AllianceService.class);
	}

	public static ActivityRulesFragment getInstance()
	{
		if (activityRulesFragment == null)
		{
			activityRulesFragment = new ActivityRulesFragment();
		}
		return activityRulesFragment;
	}

	private void getTopData()
	{

		Observable<PersonData> call = allianceService.getPersonIndex(HttpParamUtil.getCommonSignParamMap(getActivity(), null));
		call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(personIndexTop -> {
			if (personIndexTop != null && personIndexTop.getData() != null)
			{
				Glide.with(getActivity()).load(personIndexTop.getData().getRuleUrl()).into(iv_imgicon);

			}
		}, throwable -> {
			com.orhanobut.logger.Logger.e(throwable.getMessage());
		});
	}

}
