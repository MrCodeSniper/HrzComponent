package com.mujirenben.android.common.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.ch.android.common.R;
import com.mujirenben.android.common.util.Logger;


/**
 * Author: panrongfu
 * Time:2018/6/25 9:59
 * Description:加载更多视图
 */

public final class HrzEmptyLoadMoreView extends LoadMoreView {

    @Override public int getLayoutId() {
        Logger.dump("HrzEmptyLoadMoreView","getLayoutId");
        return R.layout.common_empty_load_view;
    }
    @Override protected int getLoadingViewId() {
        return R.id.ll;
    }
    @Override protected int getLoadFailViewId() {
        return R.id.ll;
    }
    @Override protected int getLoadEndViewId() {
        return R.id.ll;
    }
}
