package com.mujirenben.android.vip.mvp.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.mvp.model.bean.ShopBean;

import java.util.List;

/**
 * Author: panrongfu
 * Time:2018/6/22 18:57
 * Description: 店铺列表
 */

public class VipRecycleAdapter extends BaseQuickAdapter<ShopBean.DataBean.ShopListBean,BaseViewHolder> {

    private ImageView shopIv;
    private TextView titleTv;
    private TextView typeTv;
    private TextView disTv;
    private TextView perTv;
    private TextView saleTv;

    public VipRecycleAdapter(int layoutResId, @Nullable List<ShopBean.DataBean.ShopListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopBean.DataBean.ShopListBean item) {
        shopIv = helper.getView(R.id.vip_shop_item_iv);
        titleTv = helper.getView(R.id.vip_shop_item_title_tv);
        typeTv  = helper.getView(R.id.vip_shop_item_type_tv);
        disTv   = helper.getView(R.id.vip_shop_item_dis_tv);
        perTv   = helper.getView(R.id.vip_shop_item_per_tv);
        saleTv  = helper.getView(R.id.vip_shop_item_sale_tv);

        String shopIvURL = item.getShopCover();
        String titleText = item.getShopName();
        String typeText = item.getShopType();
        String disText = item.getShopDis();
        String perText = item.getShopPer();
        String saleText = "省"+item.getShopSale();
        if(mContext != null){
            Glide.with(mContext).load(shopIvURL).into(shopIv);
        }

        titleTv.setText(titleText);
        typeTv.setText(typeText);
        disTv.setText(disText);
       // String shopType = mContext.getResources().getString(R.string.vip_shop_type_text);
        RxTextTool.getBuilder(typeText,mContext)
                .append("   ")
                .append("广州")
                .into(typeTv);

        int goldenColor = mContext.getResources().getColor(R.color.golden_deep_color);
        RxTextTool.getBuilder("人均",mContext)
                .append(" ")
                .append("￥")
                .setForegroundColor(goldenColor)
                .append(perText)
                .setProportion(1.5f)
                .setForegroundColor(goldenColor)
                .into(perTv);

        saleTv.setText(saleText);
    }
}
