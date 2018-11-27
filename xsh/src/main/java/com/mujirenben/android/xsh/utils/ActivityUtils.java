package com.mujirenben.android.xsh.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 */
public class ActivityUtils
{

	/**
	 * 跳转
	 * 
	 * @param aty
	 * @param cls
	 *            void
	 */
	public static void skipActivity(Activity aty, Class<?> cls)
	{
		Intent intent = new Intent();
		intent.setClass(aty, cls);
		aty.startActivity(intent);
	}

	/**
	 * 跳转
	 * 
	 * @param aty
	 * @param it
	 *            void
	 */
	public static void skipActivity(Activity aty, Intent it)
	{
		aty.startActivity(it);
	}

	/**
	 * 带参数的跳转
	 * 
	 * @param aty
	 * @param cls
	 * @param extras
	 *            void
	 */
	public static void skipActivity(Activity aty, Class<?> cls, Bundle extras)
	{
		Intent intent = new Intent();
		intent.putExtras(extras);
		intent.setClass(aty, cls);
		aty.startActivity(intent);
	}

	/**
	 * 需要返回的跳转
	 * 
	 * @param aty
	 * @param cls
	 * @param requestCode
	 *            void
	 */
	public static void skipActivityForResult(Activity aty, Class<?> cls, int requestCode)
	{
		Intent intent = new Intent();
		intent.setClass(aty, cls);
		aty.startActivityForResult(intent, requestCode);
	}

	/**
	 * 需要返回的跳转
	 * 
	 * @param aty
	 * @param it
	 * @param requestCode
	 *            void
	 */
	public static void skipActivityForResult(Activity aty, Intent it, int requestCode)
	{
		aty.startActivityForResult(it, requestCode);
	}

	/**
	 * 带参数需要返回的跳转
	 * 
	 * @param aty
	 * @param cls
	 * @param extras
	 * @param requestCode
	 *            void
	 */
	public static void skipActivityForResult(Activity aty, Class<?> cls, Bundle extras, int requestCode)
	{
		Intent intent = new Intent();
		intent.putExtras(extras);
		intent.setClass(aty, cls);
		aty.startActivityForResult(intent, requestCode);
	}

}
