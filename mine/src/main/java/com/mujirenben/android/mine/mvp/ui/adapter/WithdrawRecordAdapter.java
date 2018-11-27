package com.mujirenben.android.mine.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.util.DateTimeUtil;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawListBean;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
/**
 * Created by admin on 2017/3/22.
 */
public class WithdrawRecordAdapter extends BaseQuickAdapter<WithdrawListBean.DataBean, BaseViewHolder> {

    public WithdrawRecordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawListBean.DataBean item) {

        String type = item.getType();
        if(TextUtils.isEmpty(type)) return;
        if(type.equals("1")){
            //身份证验证信息费用
            showIdentifyLayout(helper,item.getIdentityServiceInfo());
        }else if (type.equals("0")){
            //提现信息
            showWithdrawLayout(helper,item.getWithdrawInfo());
        }
    }

    /**
     * 提现信息
     * @param helper
     * @param withdrawInfo
     */
    private void showWithdrawLayout(BaseViewHolder helper, WithdrawListBean.DataBean.WithdrawInfoBean withdrawInfo){
        View withdrawStatusLayout = helper.getView(R.id.withdraw_status_ll);
        View withdrawIdentifyLayout = helper.getView(R.id.withdraw_identify_ll);
        ImageView iv_plantform= helper.getView(R.id.iv_plantform);
        TextView actualAmountTv = helper.getView(R.id.tv_withdraw_actual_amount);
        TextView taxAmount = helper.getView(R.id.tv_withdraw_tax_amount);
        TextView tv_withdraw_status=helper.getView(R.id.tv_withdraw_status);
        TextView tv_withdraw_amount=helper.getView(R.id.tv_withdraw_amount);
        TextView tv_withdraw_account=helper.getView(R.id.tv_withdraw_account);
        TextView tv_apply_date=helper.getView(R.id.tv_apply_date);
        RelativeLayout rl_intoaccount_time =helper.getView(R.id.rl_intoaccount_time);
        TextView tv_intoaccount_time=helper.getView(R.id.tv_intoaccount_time);
        LinearLayout ll_bg=helper.getView(R.id.ll_bg);
        RelativeLayout rl_fail_msg =helper.getView(R.id.rl_fail_msg);
        TextView tv_fail_msg=helper.getView(R.id.tv_fail_msg);

        //隐藏身份认证信息费用布局
        withdrawIdentifyLayout.setVisibility(View.GONE);
        //显示提现记录布局
        withdrawStatusLayout.setVisibility(View.VISIBLE);

        tv_withdraw_amount.setText(withdrawInfo == null ? "¥--":"¥"+withdrawInfo.getAmount());
        tv_withdraw_account.setText(withdrawInfo == null ? "--" : withdrawInfo.getAlipayAcount());
        long timestamp = withdrawInfo.getWithdrawRequestTime()*1000L;
        String requestTime = DateTimeUtil.parseTimestamp(timestamp);
        actualAmountTv.setText(withdrawInfo == null ? "¥--":"¥"+withdrawInfo.getActualAmount());
        taxAmount.setText(withdrawInfo == null ? "¥--":"¥"+withdrawInfo.getTaxAmount());
        tv_apply_date.setText(requestTime);

//        if(item.getAlipayAcount().equals("alipay")){
//            iv_plantform.setImageResource(R.drawable.icon_of_alipay);
//        }else if(item.getAlipayAcount().equals("wx")){
//            iv_plantform.setImageResource(R.drawable.icon_of_wx);
//        }
        iv_plantform.setImageResource(R.drawable.icon_of_alipay);
        withdrawIdentifyLayout.setVisibility(View.GONE);
        if(withdrawInfo.getStatus() == 2){
            tv_withdraw_status.setText("提现成功");
            rl_intoaccount_time.setVisibility(View.VISIBLE);
            long timeStamp = withdrawInfo.getCashConfirmTime()*1000L;
            String cashConfirmTime = DateTimeUtil.parseTimestamp(timeStamp);
            //到账时间
            tv_intoaccount_time.setText(cashConfirmTime);
        }else if(withdrawInfo.getStatus() == 0 || withdrawInfo.getStatus() == 1 ){
            tv_withdraw_status.setText("提现中");
            // ll_bg.setBackgroundColor(mContext.getResources().getColor(R.color.bg_grey));
        }else if(withdrawInfo.getStatus() == 3|| withdrawInfo.getStatus() == 4){
            tv_withdraw_status.setText("提现失败");
            tv_withdraw_amount.setTextColor(mContext.getResources().getColor(R.color.main_color));
            rl_fail_msg.setVisibility(View.VISIBLE);
            tv_fail_msg.setText(withdrawInfo.getCashierConfirmDesc());
        }
    }

    /**
     * 身份认证信息费用
     * @param helper
     * @param identityServiceInfo
     */
    public void showIdentifyLayout(BaseViewHolder helper, WithdrawListBean.DataBean.IdentityServiceInfoBean identityServiceInfo){

        View withdrawStatusLayout = helper.getView(R.id.withdraw_status_ll);
        View withdrawIdentifyLayout = helper.getView(R.id.withdraw_identify_ll);
        TextView  tvIdentifyAmount =  helper.getView(R.id.tv_identify_amount);
        TextView  tvIdentifyTaxTime = helper.getView(R.id.tv_identify_tax_time);
        TextView  tvIdentifyState = helper.getView(R.id.tv_identify_state);
        //显示身份认证信息费用布局
        withdrawIdentifyLayout.setVisibility(View.VISIBLE);
        //隐藏提现记录布局
        withdrawStatusLayout.setVisibility(View.GONE);

        tvIdentifyAmount.setText(identityServiceInfo == null?"¥--":"¥"+identityServiceInfo.getAmount());
        tvIdentifyTaxTime.setText(identityServiceInfo == null?"--":DateTimeUtil.parseTimestamp(identityServiceInfo.getTransactionTime()*1000L));
        tvIdentifyState.setText(identityServiceInfo == null?"--":identityServiceInfo.getRemark());
    }
}
