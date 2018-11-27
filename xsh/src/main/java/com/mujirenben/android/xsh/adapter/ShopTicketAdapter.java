package com.mujirenben.android.xsh.adapter;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.entity.ShopTicketEntity;
import com.mujirenben.android.xsh.utils.DateUtil;
import com.mujirenben.android.common.util.EmptyUtils;

import java.text.DecimalFormat;


/**
 * Created by Cyj on 2018/8/2.
 */
public class ShopTicketAdapter extends BaseQuickAdapter<ShopTicketEntity.DataBean,BaseViewHolder> {

    private OnItemQuanListern onItemQuanListern;
    public void setOnItemQuanListern(OnItemQuanListern onItemQuanListern)
    {
        this.onItemQuanListern=onItemQuanListern;
    }
    public ShopTicketAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopTicketEntity.DataBean item) {
        ImageView iv_shop_icon = helper.getView(R.id.iv_shop_icon);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_distance = helper.getView(R.id.tv_distance);
        TextView tv_ticket_money = helper.getView(R.id.tv_ticket_money);
        TextView tv_use_ticket = helper.getView(R.id.tv_usenow_ticket);
        TextView tv_fu = helper.getView(R.id.tv_fu);
        RelativeLayout rl_parent = helper.getView(R.id.rl_parent);
        TextView tv_time = helper.getView(R.id.tv_time);
        tv_use_ticket.setOnClickListener(v -> onItemQuanListern.onItemQuan(tv_use_ticket,tv_distance,tv_time,tv_title,tv_fu,tv_ticket_money,rl_parent,item));

        Glide.with(mContext.getApplicationContext()).load(item.getStorefrontImg()).into(iv_shop_icon);
        tv_title.setText(item.getStoreName());
        if(!EmptyUtils.isEmpty(item.getDistance())) {
            DecimalFormat decimalFormat =new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位
            String format = decimalFormat.format((double) item.getDistance() / 1000);
            tv_distance.setText("<"+format+"km");
        }

        tv_ticket_money.setText(item.getDiscounted()+"");
        tv_time.setText("有效期至:"+ DateUtil.getStandardTime(item.getEndTime()));
        if (item.getStatus()==1)
        {
            tv_use_ticket.setText("已经\n领取");
            tv_use_ticket.setEnabled(false);
            tv_distance.setTextColor(mContext.getResources().getColor(R.color.text_color_blur));
            tv_time.setTextColor(mContext.getResources().getColor(R.color.text_color_blur));
            tv_title.setTextColor(mContext.getResources().getColor(R.color.text_color_blur));
            tv_fu.setTextColor(mContext.getResources().getColor(R.color.text_color_blur));
            tv_ticket_money.setTextColor(mContext.getResources().getColor(R.color.text_color_blur));
            tv_use_ticket.setTextColor(mContext.getResources().getColor(R.color.text_color_ticket_received));
            rl_parent.setBackgroundResource(R.drawable.hrz_alliance_shoptiticket_nomal);
        }
        else
        {
            tv_distance.setTextColor(mContext.getResources().getColor(R.color.text_color_ticket_not_received));
            tv_time.setTextColor(mContext.getResources().getColor(R.color.text_color_ticket_not_received));
            tv_title.setTextColor(mContext.getResources().getColor(R.color.text_color));
            tv_fu.setTextColor(mContext.getResources().getColor(R.color.banner_point_solid));
            tv_ticket_money.setTextColor(mContext.getResources().getColor(R.color.banner_point_solid));
            tv_use_ticket.setText("立即\n领取");
            tv_use_ticket.setEnabled(true);
            tv_use_ticket.setTextColor(mContext.getResources().getColor(R.color.text_color_ticket_not_received));
            rl_parent.setBackgroundResource(R.drawable.hrz_alliance_shoptiticket);
        }



    }


    public interface  OnItemQuanListern{
        void onItemQuan(TextView tv_use_ticket, TextView tv_distance, TextView tv_time, TextView tv_title, TextView tv_fu, TextView view, RelativeLayout rl_parent, ShopTicketEntity.DataBean item);
    }





}
