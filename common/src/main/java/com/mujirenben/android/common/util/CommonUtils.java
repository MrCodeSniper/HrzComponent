package com.mujirenben.android.common.util;

/**
 *
 *
 */
public class CommonUtils {
	public static long lastClickTime;   //最后一次有效点击的时间
	/**
	 * 是两次快速点击则不进行请求提交
	 * @return
	 */
	public static boolean isFastDoubleClick(){
		long time=System.currentTimeMillis();
		long timeD=time-lastClickTime;
		if(timeD>0 && timeD<1000)
			return true;
		else
		{
			lastClickTime=time;   //不是两次快速点击，则更新最后点击时间
			return false;
		}
	}

	public static String takeOutLastZero(float source) {
		String s = source + "";
		if(s.indexOf(".") > 0){
			s = s.replaceAll("0+?$", "");//去掉后面无用的零
			s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
		}
		return s;
	}
}
