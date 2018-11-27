package com.mujirenben.android.mine.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.mine.mvp.model.bean.FuncOption;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.List;

/**
 * Created by mac on 2018/9/25.
 */

public class OptionAdapter extends BaseQuickAdapter<FuncOption,BaseViewHolder> {

    public OptionAdapter(int layoutResId, @Nullable List<FuncOption> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FuncOption item) {
        TextView tv_option=helper.getView(R.id.tv_option_view);
        tv_option.setText(item.getOption_tag());
        Glide.with(mContext).load(item.getPic_id()).into((ImageView) helper.getView(R.id.iv_option_icon));
    }
}
