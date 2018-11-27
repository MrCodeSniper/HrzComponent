package com.mujirenben.android.xsh.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.entity.WelfareBean;

import java.util.List;

public class WelfareContentAdapter extends BaseMultiItemQuickAdapter<WelfareBean, BaseViewHolder> {

    public WelfareContentAdapter(List<WelfareBean> data) {
        super(data);
        addItemType(WelfareBean.TYPE_TITLE, R.layout.layout_welfare_item_type_title);
        addItemType(WelfareBean.TYPE_RED_PACKET, R.layout.layout_welfare_item_type_red_packet);
        addItemType(WelfareBean.TYPE_COUPONS, R.layout.layout_welfare_item_type_coupons);
    }

    @Override
    protected void convert(BaseViewHolder helper, WelfareBean item) {
        int type = item.getItemType();
        if (type == WelfareBean.TYPE_TITLE) {
            bindTitleData(helper, item);
        } else if (type == WelfareBean.TYPE_RED_PACKET) {
            bindRedPacketData(helper, item);
        } else if (type == WelfareBean.TYPE_COUPONS) {
            bindCouponsData(helper, item);
        }
    }

    private void bindTitleData(BaseViewHolder holder, WelfareBean item) {
        holder.setText(R.id.tv_title, item.getTitleSubBean().title);
    }

    private void bindRedPacketData(BaseViewHolder holder, WelfareBean item) {
        WelfareBean.RedPacketSubBean redPacket = item.getRedPacketSubBean();
        holder.setText(R.id.tv_shop_name, redPacket.shopName);
        holder.setText(R.id.tv_amount, redPacket.amount + "");
        holder.setText(R.id.tv_limit, redPacket.useLimit);

        holder.setOnClickListener(R.id.btn_get, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("jan", "bindRedPacketData click");
                // TODO
            }
        });
    }

    private void bindCouponsData(BaseViewHolder holder, WelfareBean item) {
        WelfareBean.CouponsSubBean coupons = item.getCouponsSubBean();
        holder.setText(R.id.tv_shop_name, coupons.shopName);
        holder.setText(R.id.tv_limit, coupons.useLimit);
        holder.setText(R.id.tv_time_scope, coupons.takeEffectTime + "~" + coupons.invalidTime);
        holder.setText(R.id.tv_amount, coupons.amount + "");

        ImageView shopImage = holder.getView(R.id.iv_shop_image);
        Glide.with(mContext).load(coupons.shopImageUrl).into(shopImage);

        holder.setOnClickListener(R.id.btn_get, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("jan", "bindCouponsData click");
                // TODO
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager)layoutManager).setSpanSizeLookup(
                    new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getData().get(position).getItemType() == WelfareBean.TYPE_RED_PACKET) {
                        return 1;
                    } else {
                        return 2;
                    }
                }
            });
        }
    }
}
