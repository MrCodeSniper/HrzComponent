package com.mujirenben.android.mine.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.DateTimeUtil;
import com.mujirenben.android.mine.mvp.model.bean.ProfitListDetailsBean;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.ArrayList;
import java.util.List;

/**
 * 老工程搬过来的代码，如有bug,可以参考老代码
 */
public class ProfitDetailsListAdapter extends BaseMultiItemQuickAdapter<ProfitListDetailsBean.ProfitItem, BaseViewHolder> implements View.OnClickListener {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context mContext;
    private List<ProfitListDetailsBean.ProfitItem> revenueEntities = new ArrayList<>();

    private final static int TYPE_UNRECORDED = 0;
    private final static int TYPE_RECORDER = 1;
    private final static int TYPE_REFUND = 2;

    public ProfitDetailsListAdapter(List<ProfitListDetailsBean.ProfitItem> data, Context context) {
        super(data);
        mContext = context;

        addItemType(1, R.layout.mine_profit_newshareexcepted_item); // 个人消费
        addItemType(2, R.layout.mine_profit_newhuodong_item); // 活动
        addItemType(3, R.layout.mine_profit_newhuangguan_item); // 皇冠
        addItemType(4, R.layout.mine_profit_newfensi_item); // 店铺套用粉丝UI
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void convert(BaseViewHolder holder, ProfitListDetailsBean.ProfitItem item) {
        switch (item.getItemType()) {
            case 1: {
                MyViewHolderForFans itemone = new MyViewHolderForFans(holder);
                itemone.ll_top.setVisibility(View.VISIBLE);
                if (item == null) return;
                ProfitListDetailsBean.ProfitItem revenueEntityone = item;
                Glide.with(mContext).load(revenueEntityone.getImg()).into(itemone.icon);
                itemone.goodName.setText(revenueEntityone.getTitle());
                itemone.relation.setText("来源:" + revenueEntityone.getSource());
                long createTime = revenueEntityone.getCreateTime();
                if (createTime == 0) {
                    //为0不显示
                    itemone.createTime.setText("");
                } else {
                    String dateTime = DateTimeUtil.parseTimestamp(createTime * 1000L);
                    itemone.createTime.setText("创建时间:" + dateTime);
                }

                itemone.tv_fanmoney.setText(revenueEntityone.getIncome());

                itemone.tv_xiaofei.setText(revenueEntityone.getTitle());

                switch (revenueEntityone.getStatus()) {
                    case TYPE_UNRECORDED:
                        itemone.usertuihuo.setVisibility(View.GONE);
                        itemone.notsettle.setVisibility(View.VISIBLE);
                        itemone.already.setVisibility(View.GONE);
                        itemone.endTime.setVisibility(View.VISIBLE);
                        itemone.userweiquan.setVisibility(View.GONE);
                        itemone.tv_weiquan.setVisibility(View.GONE);
                        long endTime = revenueEntityone.getTime();
                        if (endTime == 0) {
                            itemone.endTime.setText("");
                        } else {
                            String endTimeStr = DateTimeUtil.parseTimestamp(endTime * 1000L);
                            itemone.endTime.setText("预计到账时间:" + endTimeStr);
                        }

                        itemone.tv_tuihuo.setVisibility(View.GONE);
                        break;
                    case TYPE_RECORDER:
                        itemone.usertuihuo.setVisibility(View.GONE);
                        itemone.notsettle.setVisibility(View.GONE);
                        itemone.already.setVisibility(View.VISIBLE);
                        itemone.userweiquan.setVisibility(View.GONE);
                        itemone.tv_weiquan.setVisibility(View.GONE);
                        itemone.endTime.setVisibility(View.VISIBLE);
                        long endTimeRecorder = revenueEntityone.getTime();
                        if (endTimeRecorder == 0) {
                            itemone.endTime.setText("");
                        } else {
                            String endTimeRecorderStr = DateTimeUtil.parseTimestamp(endTimeRecorder * 1000L);
                            itemone.endTime.setText("到账时间:" + endTimeRecorderStr);
                        }

                        itemone.tv_tuihuo.setVisibility(View.GONE);
                        break;
                    case TYPE_REFUND:
                        itemone.usertuihuo.setVisibility(View.VISIBLE);
                        itemone.notsettle.setVisibility(View.GONE);
                        itemone.already.setVisibility(View.GONE);
                        itemone.userweiquan.setVisibility(View.GONE);
                        itemone.tv_weiquan.setVisibility(View.GONE);
                        itemone.endTime.setVisibility(View.INVISIBLE);
                        itemone.tv_fanmoney.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        itemone.tv_fu.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        itemone.tv_tuihuo.setVisibility(View.VISIBLE);
                        break;
                    default:
                        itemone.notsettle.setVisibility(View.GONE);
                        itemone.already.setVisibility(View.GONE);
                        itemone.userweiquan.setVisibility(View.VISIBLE);
                        itemone.tv_weiquan.setVisibility(View.VISIBLE);
                        itemone.endTime.setVisibility(View.INVISIBLE);
                        itemone.tv_fanmoney.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        itemone.tv_fu.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));

                        break;
                }
                break;
            }
            case 2: {
                MyViewHolderJiangLi itemtwo = new MyViewHolderJiangLi(holder);
                ProfitListDetailsBean.ProfitItem revenueEntityActivity = item;

                Glide.with(mContext.getApplicationContext()).load(revenueEntityActivity.getImg()).into(itemtwo.iv_money);
                itemtwo.origin.setText("来源:" + revenueEntityActivity.getSource());
                long createTime = revenueEntityActivity.getTime();
                if (createTime == 0) {
                    itemtwo.createTime.setText("");
                } else {
                    String createTimeStr = DateTimeUtil.parseTimestamp(createTime * 1000L);
                    itemtwo.createTime.setText("创建时间:" + createTimeStr);
                }
                itemtwo.tv_xiaofei.setText(revenueEntityActivity.getTitle());
                itemtwo.tv_fanmoney.setText(revenueEntityActivity.getIncome());
                switch (revenueEntityActivity.getStatus()) {
                    case TYPE_UNRECORDED:
                        itemtwo.usertuihuo.setVisibility(View.GONE);
                        itemtwo.notsettle.setVisibility(View.VISIBLE);
                        itemtwo.already.setVisibility(View.GONE);
                        itemtwo.endTime.setVisibility(View.VISIBLE);
                        long endTime = revenueEntityActivity.getTime();
                        if (endTime == 0) {
                            itemtwo.endTime.setText("");
                        } else {
                            String endTimeStr = DateTimeUtil.parseTimestamp(endTime * 1000L);
                            itemtwo.endTime.setText("预计到账时间:" + endTimeStr);
                        }

                        break;
                    case TYPE_RECORDER:
                        itemtwo.usertuihuo.setVisibility(View.GONE);
                        itemtwo.notsettle.setVisibility(View.GONE);
                        itemtwo.already.setVisibility(View.VISIBLE);
                        itemtwo.endTime.setVisibility(View.VISIBLE);
                        long endTimeRecorder = revenueEntityActivity.getTime();
                        if (endTimeRecorder == 0) {
                            itemtwo.endTime.setText("");
                        } else {
                            String endTimeRecorderStr = DateTimeUtil.parseTimestamp(endTimeRecorder * 1000L);
                            itemtwo.endTime.setText("到账时间:" + endTimeRecorderStr);
                        }
                        break;
                    case TYPE_REFUND:
                        itemtwo.usertuihuo.setVisibility(View.VISIBLE);
                        itemtwo.notsettle.setVisibility(View.GONE);
                        itemtwo.already.setVisibility(View.GONE);
                        itemtwo.endTime.setVisibility(View.INVISIBLE);
                        itemtwo.tv_fanmoney.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        itemtwo.tv_fu.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        break;
                }
                break;
            }
            case 3: {
                MyViewHolderHuangguan holderHuangguan = new MyViewHolderHuangguan(holder);
                ProfitListDetailsBean.ProfitItem dataListBeanhuangguan = item;
                if (item == null) return;
                Glide.with(mContext.getApplicationContext()).load(dataListBeanhuangguan.getImg()).into(holderHuangguan.iv_icon);
                holderHuangguan.tv_fanmoney.setText(dataListBeanhuangguan.getIncome());
                long createTime = dataListBeanhuangguan.getCreateTime();
                long endTime = dataListBeanhuangguan.getTime();
                if (createTime == 0) {
                    holderHuangguan.createTime.setText("");
                } else {
                    holderHuangguan.createTime.setText("创建时间:" + DateTimeUtil.parseTimestamp(createTime * 1000L));
                }
                if (endTime == 0) {
                    holderHuangguan.endTime.setText("");
                } else {
                    holderHuangguan.endTime.setText("预计到账时间:" + DateTimeUtil.parseTimestamp(endTime * 1000L));
                }

                holderHuangguan.rl_top.setVisibility(View.VISIBLE);
                if (dataListBeanhuangguan.getTitle().length() > 6) {
                    String substring = dataListBeanhuangguan.getTitle().substring(0, 6);

                    holderHuangguan.tv_xiaofei.setText(substring + "...升级皇冠收益");
                } else {
                    holderHuangguan.tv_xiaofei.setText(dataListBeanhuangguan.getTitle() + "升级皇冠收益");
                }

                holderHuangguan.origin.setText("来源:" + dataListBeanhuangguan.getSource());
                switch (dataListBeanhuangguan.getStatus()) {
                    case TYPE_UNRECORDED:
                        holderHuangguan.usertuihuo.setVisibility(View.GONE);
                        holderHuangguan.notsettle.setVisibility(View.VISIBLE);
                        holderHuangguan.already.setVisibility(View.GONE);
                        break;
                    case TYPE_RECORDER:
                        holderHuangguan.usertuihuo.setVisibility(View.GONE);
                        holderHuangguan.notsettle.setVisibility(View.GONE);
                        holderHuangguan.already.setVisibility(View.VISIBLE);
                        break;
                    case TYPE_REFUND:
                        holderHuangguan.usertuihuo.setVisibility(View.VISIBLE);
                        holderHuangguan.notsettle.setVisibility(View.GONE);
                        holderHuangguan.already.setVisibility(View.GONE);
                        holderHuangguan.endTime.setVisibility(View.INVISIBLE);
                        holderHuangguan.tv_tuihuo.setVisibility(View.VISIBLE);
                        holderHuangguan.tv_fanmoney.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        holderHuangguan.tv_fu.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        break;
                }
                break;
            }
            case 4: {

                MyViewHolderFenSi myViewHolderFenSi = new MyViewHolderFenSi(holder);

                ProfitListDetailsBean.ProfitItem revenueEntityshop = item;
                if (item == null) return;
                myViewHolderFenSi.tv_xiaofei.setText("xxx" + "的消费收益");
                myViewHolderFenSi.tv_fanmoney.setText(revenueEntityshop.getIncome());
                Glide.with(mContext.getApplicationContext()).load(revenueEntityshop.getImg()).into(myViewHolderFenSi.iv_icon);
                long createTime = revenueEntityshop.getCreateTime();
                if (createTime == 0) {
                    myViewHolderFenSi.createTime.setText("");
                } else {
                    myViewHolderFenSi.createTime.setText("创建时间:" + DateTimeUtil.parseTimestamp(createTime * 1000L));
                }
                myViewHolderFenSi.origin.setText("来源:" + revenueEntityshop.getSource());
                myViewHolderFenSi.rl_top.setVisibility(View.VISIBLE);

                if (revenueEntityshop.getType() == 1) {
                    myViewHolderFenSi.tv_dianpu.setVisibility(View.VISIBLE);
                } else {
                    myViewHolderFenSi.tv_dianpu.setVisibility(View.GONE);
                }

                if (revenueEntityshop.getTitle().length() > 6) {
                    String substring = revenueEntityshop.getTitle().substring(0, 6);

                    myViewHolderFenSi.tv_xiaofei.setText(substring + "...的消费收益");
                } else {
                    myViewHolderFenSi.tv_xiaofei.setText(revenueEntityshop.getTitle() + "的消费收益");
                }

//
                switch (revenueEntityshop.getStatus()) {
                    case TYPE_UNRECORDED:

                        myViewHolderFenSi.usertuihuo.setVisibility(View.GONE);
                        myViewHolderFenSi.notsettle.setVisibility(View.VISIBLE);
                        myViewHolderFenSi.already.setVisibility(View.GONE);
                        long endTimeUn = revenueEntityshop.getTime();
                        if (endTimeUn == 0) {
                            myViewHolderFenSi.endTime.setText("");
                        } else {
                            myViewHolderFenSi.endTime.setText("预计到账时间:" + DateTimeUtil.parseTimestamp(endTimeUn * 1000L));
                        }
                        myViewHolderFenSi.endTime.setVisibility(View.VISIBLE);
                        myViewHolderFenSi.tv_tuihuo.setVisibility(View.GONE);
                        myViewHolderFenSi.userweiquan.setVisibility(View.GONE);
                        myViewHolderFenSi.tv_weiquan.setVisibility(View.GONE);
                        break;
                    case TYPE_RECORDER:
                        myViewHolderFenSi.usertuihuo.setVisibility(View.GONE);
                        myViewHolderFenSi.notsettle.setVisibility(View.GONE);
                        myViewHolderFenSi.already.setVisibility(View.VISIBLE);
                        long endTimeRe = revenueEntityshop.getTime();
                        if (endTimeRe == 0) {
                            myViewHolderFenSi.endTime.setText("");
                        } else {
                            myViewHolderFenSi.endTime.setText("到账时间:" + DateTimeUtil.parseTimestamp(endTimeRe * 1000l));
                        }

                        myViewHolderFenSi.endTime.setVisibility(View.VISIBLE);
                        myViewHolderFenSi.tv_tuihuo.setVisibility(View.GONE);
                        myViewHolderFenSi.userweiquan.setVisibility(View.GONE);
                        myViewHolderFenSi.tv_weiquan.setVisibility(View.GONE);
                        break;
                    case TYPE_REFUND:
                        myViewHolderFenSi.usertuihuo.setVisibility(View.VISIBLE);
                        myViewHolderFenSi.notsettle.setVisibility(View.GONE);
                        myViewHolderFenSi.already.setVisibility(View.GONE);
                        myViewHolderFenSi.endTime.setVisibility(View.GONE);
                        myViewHolderFenSi.tv_fanmoney.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        myViewHolderFenSi.tv_fu.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        myViewHolderFenSi.tv_tuihuo.setVisibility(View.VISIBLE);
                        myViewHolderFenSi.userweiquan.setVisibility(View.GONE);
                        myViewHolderFenSi.tv_weiquan.setVisibility(View.GONE);
                        break;
                    default:
                        myViewHolderFenSi.notsettle.setVisibility(View.GONE);
                        myViewHolderFenSi.already.setVisibility(View.GONE);
                        myViewHolderFenSi.userweiquan.setVisibility(View.VISIBLE);
                        myViewHolderFenSi.tv_weiquan.setVisibility(View.VISIBLE);
                        myViewHolderFenSi.endTime.setVisibility(View.GONE);
                        myViewHolderFenSi.tv_fanmoney.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));
                        myViewHolderFenSi.tv_fu.setTextColor(mContext.getResources().getColor(R.color.profit_gray_nomal));

                        break;
                }

                break;
            }

        }
    }

    class MyViewHolderForFans {
        private AppCompatImageView icon;
        private AppCompatTextView goodName;
        private AppCompatTextView relation;
        private AppCompatTextView payMoneyInt;
        private AppCompatTextView payMoneyFloat;
        private AppCompatTextView downInt;
        private AppCompatTextView downFloat;
        private AppCompatTextView createTime;
        private AppCompatTextView status;
        private AppCompatTextView userStatus;
        private LinearLayout ll_top;
        private AppCompatTextView tv_fanmoney;
        private AppCompatImageView already;
        private AppCompatTextView notsettle;
        private AppCompatTextView usertuihuo;
        private AppCompatTextView endTime;
        private AppCompatTextView tv_fu;
        private AppCompatTextView tv_xiaofei;
        private AppCompatTextView tv_tuihuo;
        private AppCompatTextView userweiquan;
        private AppCompatTextView tv_weiquan;

        private MyViewHolderForFans(BaseViewHolder itemView) {
            ll_top = itemView.getView(R.id.ll_top);
            status = itemView.getView(R.id.status);
            userStatus = itemView.getView(R.id.userStatus);
            icon = itemView.getView(R.id.goodIcon);
            goodName = itemView.getView(R.id.goodName);
            relation = itemView.getView(R.id.relation);
            payMoneyInt = itemView.getView(R.id.payMoneyInt);
            payMoneyFloat = itemView.getView(R.id.payMoneyFloat);
            downInt = itemView.getView(R.id.downInt);
            downFloat = itemView.getView(R.id.downFloat);
            createTime = itemView.getView(R.id.createTime);
            tv_fanmoney = itemView.getView(R.id.tv_fanmoney);
            already = itemView.getView(R.id.already);
            notsettle = itemView.getView(R.id.notsettle);
            usertuihuo = itemView.getView(R.id.usertuihuo);
            endTime = itemView.getView(R.id.endTime);
            tv_fu = itemView.getView(R.id.tv_fu);
            tv_xiaofei = itemView.getView(R.id.tv_xiaofei);
            tv_tuihuo = itemView.getView(R.id.tv_tuihuo);
            userweiquan = itemView.getView(R.id.userweiquan);
            tv_weiquan = itemView.getView(R.id.tv_weiquan);
        }
    }

    class MyViewHolderJiangLi {

        private final RelativeLayout rl_top;
        private final AppCompatTextView tv_fanmoney;
        private final AppCompatTextView origin;
        private final AppCompatTextView createTime;
        private final AppCompatTextView endTime;
        private final AppCompatImageView already;
        private final RCImageView iv_money;
        private final AppCompatTextView tv_fu;
        private final AppCompatTextView notsettle;
        private final AppCompatTextView usertuihuo;
        private final AppCompatTextView tv_xiaofei;

        public MyViewHolderJiangLi(BaseViewHolder itemView) {
            rl_top = itemView.getView(R.id.rl_top);
            tv_fanmoney = itemView.getView(R.id.tv_fanmoney);
            origin = itemView.getView(R.id.origin);
            createTime = itemView.getView(R.id.createTime);
            endTime = itemView.getView(R.id.endTime);
            already = itemView.getView(R.id.already);
            iv_money = itemView.getView(R.id.iv_money);
            tv_fu = itemView.getView(R.id.tv_fu);
            notsettle = itemView.getView(R.id.notsettle);
            usertuihuo = itemView.getView(R.id.usertuihuo);
            tv_xiaofei = itemView.getView(R.id.tv_xiaofei);
        }
    }

    class MyViewHolderFenSi {


        private RCImageView iv_icon;
        private AppCompatTextView usertuihuo;
        private AppCompatTextView tv_xiaofei;
        private AppCompatTextView tv_fu;
        private AppCompatTextView tv_fanmoney;
        private AppCompatTextView createTime;
        private AppCompatTextView endTime;
        private AppCompatTextView tv_tuihuo;
        private AppCompatImageView already;
        private AppCompatTextView userStatus;
        private RelativeLayout rl_top;

        private AppCompatTextView origin;
        private AppCompatTextView notsettle;
        private AppCompatTextView tv_dianpu;
        private AppCompatTextView userweiquan;
        private AppCompatTextView tv_weiquan;

        public MyViewHolderFenSi(BaseViewHolder itemView) {
            iv_icon = (RCImageView) itemView.getView(R.id.iv_icon);
            tv_xiaofei = (AppCompatTextView) itemView.getView(R.id.tv_xiaofei);
            tv_fu = (AppCompatTextView) itemView.getView(R.id.tv_fu);
            tv_fanmoney = (AppCompatTextView) itemView.getView(R.id.tv_fanmoney);
            origin = (AppCompatTextView) itemView.getView(R.id.origin);
            createTime = (AppCompatTextView) itemView.getView(R.id.createTime);
            endTime = (AppCompatTextView) itemView.getView(R.id.endTime);
            tv_tuihuo = (AppCompatTextView) itemView.getView(R.id.tv_tuihuo);
            already = (AppCompatImageView) itemView.getView(R.id.already);
            userStatus = (AppCompatTextView) itemView.getView(R.id.userStatus);
            usertuihuo = (AppCompatTextView) itemView.getView(R.id.usertuihuo);
            rl_top = (RelativeLayout) itemView.getView(R.id.rl_top);
            notsettle = (AppCompatTextView) itemView.getView(R.id.notsettle);
            tv_dianpu = (AppCompatTextView) itemView.getView(R.id.tv_dianpu);
            userweiquan = (AppCompatTextView) itemView.getView(R.id.userweiquan);
            tv_weiquan = (AppCompatTextView) itemView.getView(R.id.tv_weiquan);

        }
    }

    class MyViewHolderHuangguan {


        private RelativeLayout rl_top;
        private AppCompatTextView tv_fanmoney;
        private AppCompatTextView origin;
        private AppCompatTextView createTime;
        private AppCompatTextView endTime;
        private AppCompatImageView already;
        private RCImageView iv_icon;
        private AppCompatTextView usertuihuo;
        private AppCompatTextView notsettle;
        private AppCompatTextView tv_title;
        private AppCompatTextView tv_fu;
        private AppCompatTextView tv_xiaofei;
        private AppCompatTextView tv_tuihuo;

        public MyViewHolderHuangguan(BaseViewHolder itemView) {
            rl_top = itemView.getView(R.id.rl_top);
            tv_fanmoney = itemView.getView(R.id.tv_fanmoney);
            origin = itemView.getView(R.id.origin);
            createTime = itemView.getView(R.id.createTime);
            endTime = itemView.getView(R.id.endTime);
            already = itemView.getView(R.id.already);
            iv_icon = itemView.getView(R.id.iv_icon);
            usertuihuo = itemView.getView(R.id.usertuihuo);
            notsettle = itemView.getView(R.id.notsettle);
            tv_title = itemView.getView(R.id.tv_title);
            tv_fu = itemView.getView(R.id.tv_fu);
            tv_xiaofei = itemView.getView(R.id.tv_xiaofei);
            tv_tuihuo = itemView.getView(R.id.tv_tuihuo);
        }
    }
}

