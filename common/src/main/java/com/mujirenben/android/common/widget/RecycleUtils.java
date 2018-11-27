package com.mujirenben.android.common.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

/**
 * Created by mac on 2018/9/12.
 */

public class RecycleUtils {
    /**
     * RecyclerView 移动到当前位置，
     *
     * @param n  要跳转的位置
     */
    public static void MoveToPosition(RecyclerView rv, int n) {
        if (n != -1) {
            rv.scrollToPosition(n);
            GridLayoutManager mLayoutManager =
                    (GridLayoutManager) rv.getLayoutManager();
            mLayoutManager.scrollToPositionWithOffset(n, 0);
        }
    }
}
