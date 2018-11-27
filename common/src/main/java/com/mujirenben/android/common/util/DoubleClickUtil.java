package com.mujirenben.android.common.util;

import com.mujirenben.android.common.widget.DialogUtils;

/**
 * 按钮click事件处理-防止快速点击工具类
 */
public class DoubleClickUtil {
	public static final long WAIT_TIME_MILLS_ONE_SECOND = 3000; // 每次点击有效间隔时间
	private static long lastClickTimeMills = -1; // 最后一次点击时间

	/**
	 * 检查单次点击是否有效
	 *
	 * @return
	 */
	  public static boolean allowClick(long waitTimeMills) {
		long curTimeMills = System.currentTimeMillis();

		if (lastClickTimeMills < 0) {
			lastClickTimeMills = curTimeMills;
			return true;
		} else {
			boolean result = (curTimeMills - lastClickTimeMills >= waitTimeMills);
			lastClickTimeMills = curTimeMills;

			return result;
		}
	}

    /**
     * 无参方法
     * @return
     */
	public static  boolean allowClick(){
        long curTimeMills = System.currentTimeMillis();
        if (lastClickTimeMills < 0) {
            lastClickTimeMills = curTimeMills;
            return true;
        } else {
            boolean result = (curTimeMills - lastClickTimeMills >= WAIT_TIME_MILLS_ONE_SECOND);
            lastClickTimeMills = curTimeMills;
            return result;
        }
    }
}
