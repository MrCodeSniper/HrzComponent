package com.mujirenben.android.xsh.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.LinkDataBean;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.bean.Const;

import java.text.DecimalFormat;
import java.util.List;

public class LinkListAdapter extends BaseMultiItemQuickAdapter<LinkDataBean.LinkItem, BaseViewHolder> implements View.OnClickListener {
	private Context mContext;
	private LinkItemActionListener mLinkItemActionListener;
	private boolean isShowNothing = true;

	public boolean isShowNothing() {
		return isShowNothing;
	}

	public void setShowNothing(boolean showNothing) {
		isShowNothing = showNothing;
	}

	public interface LinkItemActionListener {
		void confirmRate(int merchantUserId);
		void refuseRate(int merchantUserId);
	}
	/**
	 * Same as QuickAdapter#QuickAdapter(Context,int) but with
	 * some initialization data.
	 *
	 * @param data A new list is created out of this one to avoid mutable list
	 */
	public LinkListAdapter(List<LinkDataBean.LinkItem> data, Context context) {
		super(data);

		this.mContext = context;

		//  1:对接人未确认佣金，2：对接人不同意佣金，3：未审核，4：审核通过，5：审核不通过，6：未签约，7：未激活，8：激活成功，9：激活失败， 10：开始收益
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_HEADER, R.layout.alliance_linkstore_item_header_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_TO_CONFIRM_RATE, R.layout.alliance_linkstore_item_to_confirm_rate_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_REFUSE_RATE, R.layout.alliance_linkstore_item_check_success_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_TO_VERIFY, R.layout.alliance_linkstore_item_check_success_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_VERIFY_SUCCESS, R.layout.alliance_linkstore_item_check_success_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_VERIFY_FAILURE, R.layout.alliance_linkstore_item_check_failure_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_TO_SIGN, R.layout.alliance_linkstore_item_check_success_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_TO_ACTIVITY, R.layout.alliance_linkstore_item_check_success_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_ACTIVITY_SUCCESS, R.layout.alliance_linkstore_item_check_success_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_ACTIVITY_FAILURE, R.layout.alliance_linkstore_item_check_failure_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_HAS_REWARD,R.layout.alliance_linkstore_item_success_layout);
		addItemType(Constants.TYPE_ALLIANCE_LINKLIST_UNKNOWN, R.layout.alliance_linkstore_item_check_failure_layout);
	}

	public void setLinkItemActionListener(LinkItemActionListener linkItemActionListener) {
		mLinkItemActionListener = linkItemActionListener;
	}

	@Override
	protected void convert(BaseViewHolder viewHolder, LinkDataBean.LinkItem item) {
		int itemType = item.getItemType();

		if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_HEADER) {
			bindHeaderView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_TO_CONFIRM_RATE) {
			bindToConfirmRateView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_REFUSE_RATE) {
			bindRefuseRateView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_TO_VERIFY) {
			bindToVerifyView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_VERIFY_FAILURE) {
			bindVerifyFailureView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_VERIFY_SUCCESS) {
			bindVerifySuccessView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_TO_SIGN) {
			bindToSignView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_TO_ACTIVITY) {
			bindToActivityView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_ACTIVITY_SUCCESS) {
			bindActivitySuccessView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_ACTIVITY_FAILURE) {
			bindActivityFailureView(item, viewHolder);
		} else if (itemType == Constants.TYPE_ALLIANCE_LINKLIST_HAS_REWARD) {
			bindHasRewardView(item, viewHolder);
		} else {
			bindUnknowStateView(item, viewHolder);
		}

		if (itemType != Constants.TYPE_ALLIANCE_LINKLIST_HEADER) {
			if (item.isFirstItem()) {
				viewHolder.setImageResource(R.id.alliance_linkstore_item_banner_bottom, R.drawable.alliance_linkman_banner_bottom);
			} else {
				viewHolder.setImageBitmap(R.id.alliance_linkstore_item_banner_bottom, null);
			}
		}
	}

	private void bindUnknowStateView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "状态无法解析");
		viewHolder.setText(R.id.alliance_linkstore_item_income_rate, getRewardRate(item.getCommission()));
	}

	private void bindHasRewardView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state,  "入驻成功");
		viewHolder.setText(R.id.alliance_linkstore_item_income_rate, getRewardRate(item.getCommission()));
		viewHolder.setText(R.id.alliance_linkstore_item_income_total, item.getProfit() + "");
		viewHolder.setText(R.id.alliance_linkstore_item_income_est, item.getExpectProfit() + "");
		viewHolder.setText(R.id.alliance_linkstore_item_income_today, item.getTodayIncome() + "");
		View viewDetailsBtn = viewHolder.getView(R.id.alliance_linkstore_item_view_details);
//		viewDetailsBtn.setOnClickListener((view -> {
//			Intent profitIntent = new Intent(mContext, ProfitDetailsActivity.class);
//			if (!(mContext instanceof Activity)) {
//				profitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			}
//
//			mContext.startActivity(profitIntent);
//		}));
	}

	private void bindActivityFailureView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "激活失败");
		viewHolder.setText(R.id.alliance_linkstore_item_failure_details, item.getActivationComment());
	}

	private void bindActivitySuccessView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "激活成功");
		viewHolder.setText(R.id.alliance_linkstore_item_income_rate, getRewardRate(item.getCommission()));
	}

	private void bindToActivityView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "未激活");
		viewHolder.setText(R.id.alliance_linkstore_item_income_rate, getRewardRate(item.getCommission()));
	}

	private void bindToSignView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "未签名");
		viewHolder.setText(R.id.alliance_linkstore_item_income_rate, getRewardRate(item.getCommission()));
	}

	private void bindVerifyFailureView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "平台审核未通过");
		viewHolder.setText(R.id.alliance_linkstore_item_failure_details, item.getComment());
	}

	private void bindVerifySuccessView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "审核已通过");
		viewHolder.setText(R.id.alliance_linkstore_item_income_rate, getRewardRate(item.getCommission()));
	}

	private void bindToVerifyView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "未审核");
		viewHolder.setText(R.id.alliance_linkstore_item_income_rate, getRewardRate(item.getCommission()));
	}

	private void bindRefuseRateView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "对接人驳回");
		viewHolder.setText(R.id.alliance_linkstore_item_income_rate, getRewardRate(item.getCommission()));
	}

	private void bindToConfirmRateView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		viewHolder.setText(R.id.alliance_linkstore_item_store_name, item.getStoreName());
		viewHolder.setText(R.id.alliance_linkstore_item_store_address, item.getAddress());
		viewHolder.setText(R.id.alliance_linkstore_item_verify_state, "待对接人确认佣金");
		viewHolder.setText(R.id.alliance_linkstore_item_income_rate, getRewardRate(item.getCommission()));

		View confirmView = viewHolder.getView(R.id.alliance_linkstore_item_confirm_rate);
		View refuseView = viewHolder.getView(R.id.alliance_linkstore_item_refuse_rate);

		confirmView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mLinkItemActionListener != null) {
					mLinkItemActionListener.confirmRate(item.getId());
				}
			}
		});

		refuseView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mLinkItemActionListener != null) {
					mLinkItemActionListener.refuseRate(item.getId());
				}
			}
		});
	}

	private void bindHeaderView(LinkDataBean.LinkItem item, BaseViewHolder viewHolder) {
		TextView tv_buy = viewHolder.getView(R.id.tv_buy);
		RelativeLayout relativeLayout = viewHolder.getView(R.id.rl_nomore);

		if(isShowNothing){
			relativeLayout.setVisibility(View.VISIBLE);
		}else {
			relativeLayout.setVisibility(View.GONE);
		}
		tv_buy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO ????
				Bundle bundle=new Bundle();
				bundle.putString(Consts.GOODS_ID_INTENT_STR,"26190");
				bundle.putString(Consts.PLATFORM_ID_INTENT_STR, String.valueOf(Const.Platform.HRZ));
				bundle.putString(Consts.ROUTER_FROM,"对接人");
				ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL)
						.withBundle(Consts.HRZ_ROUTER_BUNDLE,bundle)
						.navigation(mContext);
			}
		});
	}

	private String getRewardRate(double commission) {
		DecimalFormat fnum = new DecimalFormat("##0.0");
		String a = fnum.format(Float.parseFloat(commission + "")* 100);
		return a  +"%";
	}


	@Override
	public void onClick(View view) {

	}
}
