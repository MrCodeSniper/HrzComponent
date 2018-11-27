package com.mujirenben.android.mine.mvp.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.DateTimeUtil;
import com.mujirenben.android.common.util.DeviceUtils;
import com.mujirenben.android.common.util.NumberUtils;
import com.mujirenben.android.mine.mvp.model.bean.OrderBean;
import com.mujirenben.android.mine.mvp.ui.activity.OrderListActivity;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.orhanobut.logger.Logger;

import java.util.List;

public class OrderListAdapter extends BaseQuickAdapter<OrderBean.DataBean, BaseViewHolder> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(OrderBean.DataBean orderItem);
        void onItemShareClick(OrderBean.DataBean orderItem);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public OrderListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderBean.DataBean orderItem) {

        LinearLayout order_list_item_discount_layout = helper.getView(R.id.order_list_item_discount_layout);
        ImageView platformImageView= helper.getView(R.id.order_list_item_platform_iv);
        TextView storeNameView = helper.getView(R.id.order_list_item_store_tv);
        TextView statusView = helper.getView(R.id.order_list_item_status_tv);
        TextView rewardTimeView = helper.getView(R.id.order_item_reward_reward_time_tv);
        TextView rewardNumberView = helper.getView(R.id.order_item_reward_reward_number_tv);
        TextView discountNumberView = helper.getView(R.id.order_item_reward_discount_number_tv);
        TextView numberView = helper.getView(R.id.order_item_product_number_tv);
        TextView realPriceView = helper.getView(R.id.order_item_real_price_tv);
        TextView freightView = helper.getView(R.id.order_item_freight_tv);
        TextView shareRewardView = helper.getView(R.id.order_item_share_reward_tv);
        TextView order_item_orgin_price = helper.getView(R.id.order_item_orgin_price);
        View view_divider_bar_for_offline = helper.getView(R.id.view_divider_bar_for_offline);
        View view_divider_above_ship = helper.getView(R.id.view_divider_above_ship);
        LinearLayout productListLayout = helper.getView(R.id.order_list_product_list_layout);
        View rewardLayout = helper.getView(R.id.order_list_item_reward_layout);
        View order_list_item_origin_layout = helper.getView(R.id.order_list_item_origin_layout);


        if (OrderListActivity.OrderType.INVALID.type.equalsIgnoreCase(OrderBean.getOrderStatus(orderItem.getStatus()))) {
            rewardLayout.setVisibility(View.GONE);
            order_list_item_discount_layout.setVisibility(View.GONE);
        } else {
            rewardLayout.setVisibility(View.VISIBLE);
            order_list_item_discount_layout.setVisibility(View.VISIBLE);
        }


        if (orderItem.getThirdpartId() == Const.Platform.OFFLINE) {//线下
            platformImageView.setVisibility(View.GONE);
            numberView.setText("实付款");
            view_divider_bar_for_offline.setVisibility(View.VISIBLE);
            storeNameView.setText("订单:" + orderItem.getParentOrderId());
            shareRewardView.setVisibility(View.GONE);
            view_divider_above_ship.setVisibility(View.INVISIBLE);
            if (orderItem != null && orderItem.getSubOrderInfo() != null && orderItem.getSubOrderInfo().get(0) != null) {
                //TODO 商品原价就是 没有减优惠的线下应付金额 后端有bug
                order_item_orgin_price.setText("￥" + orderItem.getSubOrderInfo().get(0).getProductPrice());
            }

            rewardLayout.setVisibility(View.VISIBLE);
            order_list_item_discount_layout.setVisibility(View.VISIBLE);

        } else if (orderItem.getThirdpartId() == Const.Platform.HRZ) {//自营
            platformImageView.setVisibility(View.VISIBLE);
            storeNameView.setText(orderItem.getShopName());
            shareRewardView.setVisibility(View.VISIBLE);
            shareRewardView.setOnClickListener(v -> {

                if (orderItem == null || TextUtils.isEmpty(orderItem.getParentOrderId())) {
                    ArmsUtils.makeText(mContext, "无法查询到您的物流订单号");
                    return;
                }

                String url = "http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2&orderID=" + orderItem.getParentOrderId() + "#/Logistics/Logistics";
                HrzRouter.getsInstance(mContext).navigation(url);
            });
            rewardLayout.setVisibility(View.GONE);
            order_list_item_discount_layout.setVisibility(View.GONE);

            view_divider_above_ship.setVisibility(View.VISIBLE);
            view_divider_bar_for_offline.setVisibility(View.INVISIBLE);
            numberView.setText(mContext.getString(R.string.mine_order_item_product_number, orderItem.getCount() + "")); // 补
        } else {//其他

            rewardLayout.setVisibility(View.VISIBLE);
            order_list_item_discount_layout.setVisibility(View.VISIBLE);

            view_divider_bar_for_offline.setVisibility(View.INVISIBLE);
            shareRewardView.setVisibility(View.GONE);
            platformImageView.setVisibility(View.VISIBLE);
            storeNameView.setText(orderItem.getShopName());
            view_divider_above_ship.setVisibility(View.INVISIBLE);
            numberView.setText(mContext.getString(R.string.mine_order_item_product_number, orderItem.getCount() + "")); // 补
        }


        List<OrderBean.DataBean.SubOrderInfoBean> productItemList = orderItem.getSubOrderInfo();


        switch (orderItem.getThirdpartId()){
            case Const.Platform.TAOBAO:
                platformImageView.setImageResource(R.drawable.icon_taobao);
                break;
            case Const.Platform.JD:
                platformImageView.setImageResource(R.drawable.icon_jingdong);
                break;
            case Const.Platform.VIP:
                platformImageView.setImageResource(R.drawable.icon_weipinhui);
                break;
            case Const.Platform.MGJ:
                platformImageView.setImageResource(R.drawable.icon_mogujie);
                break;
            case Const.Platform.HRZ:
                platformImageView.setImageResource(R.drawable.icon_ziying);
                break;
            default:
                platformImageView.setImageResource(R.drawable.icon_ziying);
                break;
        }



        statusView.setText(OrderBean.getOrderStatusStr(orderItem.getStatus(), orderItem.getThirdpartId()));
        rewardTimeView.setText(mContext.getString(R.string.mine_order_item_toaacount_time, DateTimeUtil.parseTimestamp(orderItem.getArriveTime() * 1000)));
        rewardNumberView.setText("¥ " + NumberUtils.subZeroAndDot(orderItem.getConsumerAward()));
        discountNumberView.setText("-¥ " + NumberUtils.subZeroAndDot(orderItem.getDiscounts()));
        if(TextUtils.equals(NumberUtils.subZeroAndDot(orderItem.getDiscounts()),"0")){
            order_list_item_discount_layout.setVisibility(View.GONE);
        }else {
            order_list_item_discount_layout.setVisibility(View.VISIBLE);
        }

        realPriceView.setText("¥ " + NumberUtils.subZeroAndDot(orderItem.getPayPrice()));


        freightView.setVisibility(View.GONE);


        setProductListView(mContext, productItemList, productListLayout, orderItem.getThirdpartId(), orderItem.getShopId());

        helper.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(orderItem);
            }
        });

    }

    public static void setProductListView(Context context, List<OrderBean.DataBean.SubOrderInfoBean> productItemList, LinearLayout productListLayout, int thirdPartId, String shopId) {

        productListLayout.removeAllViews();
        for (int i = 0; i < productItemList.size(); i++) {
            OrderBean.DataBean.SubOrderInfoBean orderProductItem = productItemList.get(i);
            View productItemView = buildProductItemView(context, orderProductItem, thirdPartId);
            productItemView.setOnClickListener(v -> {

                Logger.e(thirdPartId + "");
                if (thirdPartId == Const.Platform.OFFLINE) {
//					ToastUtils.getInstanc(context).showToast("跳商家店铺");
                    ARouter.getInstance().build(ARouterPaths.ALLIANCE_SHOPDETAIL)
                            .withInt("shopId", TextUtils.isEmpty(shopId) ? -1 : Integer.parseInt(shopId))
                            .navigation(context);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(Consts.GOODS_ID_INTENT_STR, orderProductItem.getProductId() + "");
                    bundle.putString(Consts.PLATFORM_ID_INTENT_STR, thirdPartId + "");
                    bundle.putString(Consts.ROUTER_FROM, "订单列表");
                    ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL)
                            .withBundle(Consts.HRZ_ROUTER_BUNDLE, bundle)
                            .navigation();
                }
            });

            productListLayout.addView(productItemView);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) productItemView.getLayoutParams();
            layoutParams.bottomMargin = (int) DeviceUtils.dpToPixel(context, context.getResources().getDimension(R.dimen.mine_order_list_product_item_margin_top));
        }
    }

    private static View buildProductItemView(Context context, OrderBean.DataBean.SubOrderInfoBean orderProductItem, int thirdPartId) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        LinearLayout itemView = (LinearLayout) layoutInflater.inflate(R.layout.mine_order_list_item_product_item_layout, null);


        ImageView iconView = itemView.findViewById(R.id.order_item_product_icon_iv);
        TextView activityView = itemView.findViewById(R.id.order_list_item_activity_tv);
        TextView titleView = itemView.findViewById(R.id.order_list_item_title_tv);
        TextView specView = itemView.findViewById(R.id.order_list_item_spec_tv);
        TextView numberView = itemView.findViewById(R.id.order_list_item_number_tv);
        TextView realPriceView = itemView.findViewById(R.id.order_list_item_real_price_tv);
        TextView oriPriceView = itemView.findViewById(R.id.order_list_item_ori_price_tv);
        TextView tvShopName = itemView.findViewById(R.id.mine_order_shop_tv);
        ImageView ivArrowRight = itemView.findViewById(R.id.iv_arrow_right);
        LinearLayout ll_mine_order_list = itemView.findViewById(R.id.ll_mine_order_list);

        if (thirdPartId == Const.Platform.OFFLINE) {
            ll_mine_order_list.setVisibility(View.GONE);
            tvShopName.setVisibility(View.VISIBLE);
            ivArrowRight.setVisibility(View.VISIBLE);
            Glide.with(context).load(orderProductItem.getProductImg()).into(iconView);
            tvShopName.setText(orderProductItem.getProductTitle());
        } else {
            ll_mine_order_list.setVisibility(View.VISIBLE);
            tvShopName.setVisibility(View.GONE);
            ivArrowRight.setVisibility(View.GONE);
            String processed_url = orderProductItem.getProductImg();
            activityView.setVisibility(View.GONE);
            Glide.with(context).load(processed_url).into(iconView);
            titleView.setText(orderProductItem.getProductTitle());
            specView.setText(orderProductItem.getProductSpec());
            numberView.setText("x" + orderProductItem.getProductNum());
            realPriceView.setText("￥" + NumberUtils.subZeroAndDot(orderProductItem.getProductPrice()));
            oriPriceView.setVisibility(View.INVISIBLE);
        }

        return itemView;
    }
}

