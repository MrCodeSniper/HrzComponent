package com.mujirenben.android.common.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.ch.android.common.R;
import com.mujirenben.android.common.util.Logger;


/**
 * Author: panrongfu
 * Time:2018/6/25 9:59
 * Description:加载更多视图
 */

public final class HrzLoadMoreView extends LoadMoreView {

    @Override public int getLayoutId() {
        Logger.dump("HrzLoadMoreView","getLayoutId");
        return R.layout.quick_view_load_more;
    }
    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }
    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }
    @Override protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
