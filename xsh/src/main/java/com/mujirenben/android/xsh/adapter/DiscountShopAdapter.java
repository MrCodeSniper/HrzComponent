package com.mujirenben.android.xsh.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.entity.ShopDetailBean;


/**
 * Created by admin on 2017/3/22.
 */

public class DiscountShopAdapter extends BaseQuickAdapter<ShopDetailBean.ResourceInfoBean.ResContentListBean.ItemDataContentBean, BaseViewHolder> {


    public DiscountShopAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetailBean.ResourceInfoBean.ResContentListBean.ItemDataContentBean item) {



        ImageView iv_shop= helper.getView(R.id.iv_shop_icon);
        TextView tv_shop_name=helper.getView(R.id.tv_shop_name);
        TextView tv_info=helper.getView(R.id.tv_info);


        Glide.with(mContext).load(item.getImageUrl()).into(iv_shop);
        tv_shop_name.setText(item.getMainTitle());

    }


}
