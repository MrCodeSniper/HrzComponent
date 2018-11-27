
package com.mujirenben.android.mine.mvp.ui.adapter;

import android.view.View;

import com.mujirenben.android.common.base.adapter.DefaultAdapter;
import com.mujirenben.android.common.base.holder.BaseHolder;
import com.mujirenben.android.common.datapackage.bean.DbTag;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;import com.mujirenben.android.mine.mvp.ui.holder.AddressItemHolder;

import java.util.List;


/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * ================================================
 */
public class AddressAdapter extends DefaultAdapter<DbTag> {
    public AddressAdapter(List<DbTag> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<DbTag> getHolder(View v, int viewType) {
        return new AddressItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.mine_holder_item_address_layout;
    }
}
