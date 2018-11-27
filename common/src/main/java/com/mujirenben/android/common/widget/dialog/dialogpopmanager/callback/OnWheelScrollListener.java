package com.mujirenben.android.common.widget.dialog.dialogpopmanager.callback;


import com.mujirenben.android.common.widget.dialog.dialogpopmanager.widget.WheelView;

/**
 * Created by Administrator on 2018/8/9 8
 */
public interface OnWheelScrollListener {
    /**
     * Callback method to be invoked when scrolling started.
     *
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingStarted(WheelView wheel);

    /**
     * Callback method to be invoked when scrolling ended.
     *
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingFinished(WheelView wheel);
}
