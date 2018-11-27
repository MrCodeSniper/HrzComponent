package com.mujirenben.android.common.util.throwable;

/**
 * @author: panrongfu
 * @date: 2018/8/15 13:14
 * @describe:
 */

public interface HandlerCallback{
    /**
     * 数据异常
     * @param msg
     */
    void dataException(String msg);

    /**
     * 网络异常
     * @param msg
     */
    void networkException(String msg);
}
