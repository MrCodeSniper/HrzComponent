package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.util.DateTimeUtil;
import com.mujirenben.android.common.util.DeviceUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.ToastUtils;
import com.mujirenben.android.mine.di.component.DaggerOrderComponent;
import com.mujirenben.android.mine.di.module.OrderModule;
import com.mujirenben.android.mine.mvp.contract.OrderContract;
import com.mujirenben.android.mine.mvp.model.bean.OrderBean;
import com.mujirenben.android.mine.mvp.model.bean.OrderDetail;
import com.mujirenben.android.mine.mvp.model.bean.OrderInfo;
import com.mujirenben.android.mine.mvp.model.bean.SelfRunOrderDetail;
import com.mujirenben.android.mine.mvp.presenter.OrderPresenter;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.List;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

@Route(path = ARouterPaths.ORDER_DETAILS)
public class OrderDetailsActivity extends BaseActivity<OrderPresenter> implements OrderContract.View, View.OnClickListener{

	@BindView(R2.id.tv_back)
	ImageView backBtn;
	@BindView(R2.id.tv_titlebar)
	TextView titleView;
	@BindView(R2.id.mine_order_ask_question_ll)
	View askQuestionLayout;
	@BindView(R2.id.ll_sub_orders)
	LinearLayout ll_sub_orders;
	@BindView(R2.id.ll_order_ship)
	LinearLayout ll_order_ship;
	TextView mStatusMsg;
	ImageView mStatusIcon;

	private String orderId="1194026403309802";
	private static int thirdPartId=1;

	@Override
	public void setupActivityComponent(@NonNull AppComponent appComponent) {
		DaggerOrderComponent
				.builder()
				.appComponent(appComponent)
				.orderModule(new OrderModule(this))
				.build()
				.inject(this);
	}

	@Override
	public int initView(@Nullable Bundle savedInstanceState) {
		if (SpUtil.getIsMIUI(this)){
			StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
		}else {
			StatusBarUtil.setStatusBarWhite(this);
		}
		return R.layout.mine_order_details_layout;
	}

	@Override
	public void initData(@Nullable Bundle savedInstanceState) {
		orderId=getIntent().getStringExtra("orderId");
		RetrofitUrlManager.getInstance().putDomain("server_order_detail", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
		mPresenter.getOrderDetailById(orderId);
		titleView.setText(R.string.mine_order_details_page_title);
		backBtn.setOnClickListener(this);
		askQuestionLayout.setOnClickListener(this);
	}

	@Override
	public Activity getActivity() {
		return OrderDetailsActivity.this;
	}

	@Override
	public void showOrderList(OrderInfo orderInfo) {

	}

	@Override
	public void showOrderDetails(OrderDetail orderItem) {
		showOrderStatus(orderItem);
		showShippingAddressInfo(orderItem);
		showProductListInfo(orderItem);
		showChargeInfo(orderItem);
		showPayInfo(orderItem);
	}


	private void showOrderStatus(OrderDetail orderDetail) {
		String msg = "订单成功";
		int iconId = R.drawable.order_status_pay_success;
		switch (orderDetail.getData().getStatus()) {
			case 1:
				msg = "订单成功";
				iconId = R.drawable.order_status_pay_success;
				break;
			case 2:
				msg = "订单结算";
				iconId = R.drawable.order_status_ing;
				break;
			case 3:
				msg = "订单失效";
                iconId = R.drawable.order_status_close;
				break;
		}

		mStatusMsg = findViewById(R.id.tv_status_msg);
		mStatusIcon = findViewById(R.id.iv_status_icon);
		mStatusMsg.setText(msg);
		mStatusIcon.setImageResource(iconId);
	}

	@Override
	public void showOrders(List<OrderBean.DataBean> orderBean) {

	}




	@Override
	public void showRequestError(String errorMsg) {

	}

	@Override
	public void requestInitDataEmpty() {

	}

	@Override
	public void requestMoreDataEmpty() {

	}


	@Override
	public void showOrderDetailEmpty() {

	}

	@Override
	public void getOrderListByUserIdFail() {

	}

	@Override
	public void getOrdersByType() {

	}

	@Override
	public void onSelfRunOrderDetailLoaded(SelfRunOrderDetail detail) {

	}

	@Override
	public void onSelfRunOrderDetailFailed(Throwable error) {

	}

	private void showProductListInfo(OrderDetail orderItem) {
		TextView platformView = findViewById(R.id.mine_order_details_platform_tv);
		TextView storeView = findViewById(R.id.mine_order_details_store_tv);
		LinearLayout productListView = findViewById(R.id.mine_order_details_product_list_layout);
		setProductListView(OrderDetailsActivity.this,orderItem.getData().getSubOrderInfo(),ll_sub_orders,orderItem.getData().getShopId());
		thirdPartId=orderItem.getData().getThirdpartId();
		platformView.setText(OrderBean.getThirdPartyName(orderItem.getData().getThirdpartId()));
		storeView.setText(orderItem.getData().getShopName());
		//OrderListAdapter.setProductListView(getActivity(), orderItem.getOrderProductList(), productListView);
	}

	private void showShippingAddressInfo(OrderDetail orderItem) {
		TextView ownerView = findViewById(R.id.mine_order_details_owner_name_tv);
		TextView phoneView = findViewById(R.id.mine_order_details_owner_phone_tv);
		TextView addressView = findViewById(R.id.mine_order_details_owner_address_tv);

//		OrderShippingAddress orderShippingAddress = null;
//		if (orderShippingAddress != null) {
//			ownerView.setText(orderShippingAddress.getUserName());
//			phoneView.setText(orderShippingAddress.getUserPhone());
//			addressView.setText(orderShippingAddress.getUserAddress());
//		}
	}

	private void showChargeInfo(OrderDetail orderItem) {
		TextView chargeTotalView = findViewById(R.id.mine_order_details_charge_total_tv);
		TextView rewardView = findViewById(R.id.mine_order_details_reward_tv);
		TextView discountView = findViewById(R.id.mine_order_details_discount_tv);
		TextView freightView = findViewById(R.id.mine_order_details_freight_tv);
		TextView totalPayView = findViewById(R.id.mine_order_details_charge_pay_tv);

		if (orderItem.getData().getRawPrice() == null ||
				Float.compare(0.0f, Float.valueOf(orderItem.getData().getRawPrice())) == 0) {
			findViewById(R.id.ll_goods_total_price).setVisibility(View.GONE);
		} else {
			chargeTotalView.setText("¥ " + orderItem.getData().getRawPrice() + "");
		}

		if (orderItem.getData().getConsumerAward() == null ||
				Float.compare(0.0f, Float.valueOf(orderItem.getData().getConsumerAward())) == 0) {
			findViewById(R.id.ll_award).setVisibility(View.GONE);
			findViewById(R.id.divider_h).setVisibility(View.GONE);
		} else {
			rewardView.setText("¥ " + orderItem.getData().getConsumerAward() + "");
		}

		if (orderItem.getData().getDiscounts() == null ||
				Float.compare(0.0f, Float.valueOf(orderItem.getData().getDiscounts())) == 0) {
			findViewById(R.id.ll_discounts).setVisibility(View.GONE);
		} else {
			discountView.setText("-¥ " + orderItem.getData().getDiscounts());
		}

//		freightView.setText("+￥" + orderItem.getFreight() + "");
		totalPayView.setText("¥ " + orderItem.getData().getPayPrice() + "");
	}

	private void showPayInfo(OrderDetail orderItem) {
		TextView searialView = findViewById(R.id.mine_order_details_order_searial_tv);
		TextView orderTime = findViewById(R.id.mine_order_details_order_book_time_tv);
		TextView payMethodView = findViewById(R.id.mine_order_details_pay_method_tv);
		TextView payTimeView = findViewById(R.id.mine_order_details_pay_time_tv);
		TextView tranTimeView = findViewById(R.id.mine_order_details_tran_time_tv);
		TextView receiveTimeView = findViewById(R.id.mine_order_details_receive_time_tv);

		OrderDetail.DataBean orderPayInfo = orderItem.getData();

		searialView.setText(orderPayInfo.getParentOrderId()+"");
		orderTime.setText(DateTimeUtil.parseTimestamp(orderPayInfo.getCreateTime()*1000));
//		payMethodView.setText(orderPayInfo.getPayType());
//		payTimeView.setText(orderPayInfo.getPayTime());
        if (orderPayInfo.getEarningTime() == 0) {
            findViewById(R.id.ll_transaction_time).setVisibility(View.GONE);
        } else {
            findViewById(R.id.ll_transaction_time).setVisibility(View.VISIBLE);
            tranTimeView.setText(DateTimeUtil.parseTimestamp(orderPayInfo.getEarningTime() * 1000));
        }

		receiveTimeView.setText(DateTimeUtil.parseTimestamp(orderPayInfo.getArriveTime()*1000));
	}

	@Override
	public void showLoading() {

	}

	@Override
	public void hideLoading() {

	}

	@Override
	public void showMessage(@NonNull String message) {

	}

	@Override
	public void launchActivity(@NonNull Intent intent) {

	}

	@Override
	public void killMyself() {

	}

	@Override
	public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_back) {
            finish();
        } else if (id == R.id.mine_order_ask_question_ll) {
			HrzRouter.getsInstance(this).navigation(Consts.getHrzRouterUrl(Consts.HRZ_FAQ_URL,"常见问题"));
        }
	}


	public  void setProductListView(Context context, List<OrderDetail.DataBean.SubOrderInfoBean> productItemList, LinearLayout productListLayout,String shopId) {
		productListLayout.removeAllViews();
		for (int i = 0; i < productItemList.size(); i++) {
			OrderDetail.DataBean.SubOrderInfoBean orderProductItem = productItemList.get(i);
			View productItemView = buildProductItemView(context, orderProductItem);
			productItemView.setOnClickListener(v -> {

				if(thirdPartId== Const.Platform.OFFLINE){

					if(TextUtils.isEmpty(shopId)){
						ToastUtils.getInstanc(this).showToast("找不到该订单所属店铺");
					}else {
						ARouter.getInstance().build(ARouterPaths.ALLIANCE_SHOPDETAIL)
								.withInt("shopId", Integer.parseInt(shopId))
								.navigation(context);
					}


				}else {
					Bundle bundle=new Bundle();
					bundle.putString(Consts.PLATFORM_ID_INTENT_STR, thirdPartId+"");
					bundle.putString(Consts.GOODS_ID_INTENT_STR, orderProductItem.getProductId()+"");
					bundle.putString(Consts.ROUTER_FROM,"订单详情");
					ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL)
							.withBundle(Consts.HRZ_ROUTER_BUNDLE,bundle)
							.navigation();
				}


			});


			productListLayout.addView(productItemView);

			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) productItemView.getLayoutParams();
			layoutParams.bottomMargin = (int) DeviceUtils.dpToPixel(context,
					context.getResources().getDimension(R.dimen.mine_order_list_product_item_margin_top));
		}

	}


		private  View buildProductItemView(Context context,OrderDetail.DataBean.SubOrderInfoBean orderProductItem) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			LinearLayout itemView = (LinearLayout) layoutInflater.inflate(R.layout.mine_order_list_item_product_item_layout, null);

			ImageView iconView = itemView.findViewById(R.id.order_item_product_icon_iv);
			TextView activityView = itemView.findViewById(R.id.order_list_item_activity_tv);
			TextView titleView = itemView.findViewById(R.id.order_list_item_title_tv);
			TextView specView = itemView.findViewById(R.id.order_list_item_spec_tv);
			TextView numberView = itemView.findViewById(R.id.order_list_item_number_tv);
			TextView realPriceView = itemView.findViewById(R.id.order_list_item_real_price_tv);
			TextView oriPriceView = itemView.findViewById(R.id.order_list_item_ori_price_tv);
			TextView tvShopName=itemView.findViewById(R.id.mine_order_shop_tv);
			ImageView ivArrowRight=itemView.findViewById(R.id.iv_arrow_right);
			LinearLayout ll_mine_order_list=itemView.findViewById(R.id.ll_mine_order_list);



			String processed_url=orderProductItem.getProductImg();
			activityView.setVisibility(View.GONE);
			Glide.with(context).load(processed_url).into(iconView);
//		activityView.setText(orderProductItem.getActivityType());
			titleView.setText(orderProductItem.getProductTitle());
			specView.setText(orderProductItem.getProductSpec());
			numberView.setText("x" + orderProductItem.getProductNum());
//		realPriceView.setText("￥" + orderProductItem.get());
			realPriceView.setText("¥ " + orderProductItem.getProductPrice());
			oriPriceView.setVisibility(View.INVISIBLE);

			return itemView;
		}


}
