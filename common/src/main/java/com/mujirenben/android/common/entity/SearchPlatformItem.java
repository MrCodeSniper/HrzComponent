package com.mujirenben.android.common.entity;

/**
 * 搜索页面平台选择Bean
 */
public class SearchPlatformItem {
	public String platformName;
	public int iconLargeResId;
	public int iconSmallResId;

	public SearchPlatformItem(String platformName, int iconLargeResId, int iconSmallResId) {
		this.platformName = platformName;
		this.iconLargeResId = iconLargeResId;
		this.iconSmallResId = iconSmallResId;
	}

	public SearchPlatformItem() {

	}
}
