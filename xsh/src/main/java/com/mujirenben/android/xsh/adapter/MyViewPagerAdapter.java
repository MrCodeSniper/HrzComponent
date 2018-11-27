package com.mujirenben.android.xsh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentPagerAdapter将每一个生成的Fragment保存在内存中
 * ，limit外Fragment没有销毁，生命周期为onPause->onStop->onDestroyView,onCreateView->onStart->onResume，
 * 但Fragment的成员变量都没有变，所以可以缓存根View，避免重复inflate。
 *
 * on 2018/7/12
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter
{
	private List<Fragment> mList		= new ArrayList<>();
	private List<String> titleList	= new ArrayList<>();

	public MyViewPagerAdapter(FragmentManager fm, List<Fragment> mList, List<String> titleList)
	{
		super(fm);
		this.mList = mList;
		this.titleList = titleList;
	}

	@Override
	public Fragment getItem(int position)
	{
		return mList.get(position);
	}

	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return titleList.get(position);
	}
}
