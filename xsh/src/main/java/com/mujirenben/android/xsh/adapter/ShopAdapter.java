package com.mujirenben.android.xsh.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.entity.AllianceHomeShopsEntity;
import com.mujirenben.android.xsh.utils.DateUtil;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.util.EmptyUtils;

import java.text.DecimalFormat;


/**
 * Created by admin on 2017/3/22.
 */

public class ShopAdapter extends BaseQuickAdapter<AllianceHomeShopsEntity.ShopBean, BaseViewHolder> {


    public ShopAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllianceHomeShopsEntity.ShopBean item) {


        RCImageView iv_shop = helper.getView(R.id.iv_shop);
        TextView tv_shop_name = helper.getView(R.id.tv_shop_name);
        TextView tv_distance = helper.getView(R.id.tv_distance);
        TextView tv_street = helper.getView(R.id.tv_street);
        TextView tv_shop_type = helper.getView(R.id.tv_shop_type);
        TextView tv_shop_bussiness_time = helper.getView(R.id.tv_shop_bussiness_time);
        TextView iv_discounts = helper.getView(R.id.iv_discounts);
        TextView tv_full_discount = helper.getView(R.id.tv_full_discount);
        TextView tv_pokect_discount = helper.getView(R.id.tv_pokect_discount);
        TextView tv_average_consume = helper.getView(R.id.tv_average_consume);
        View view_divide = helper.getView(R.id.view_divide);
        View divide_bar = helper.getView(R.id.divide_bar);
        TextView tv_shop_distance = helper.getView(R.id.tv_shop_distance);


        if (!EmptyUtils.isEmpty(item.getDistance())) {
            DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位
            String format = decimalFormat.format((double) item.getDistance() / 1000);
            tv_shop_distance.setText(format + "km");
            if (!BaseApplication.isLocation)
            {
                tv_shop_distance.setVisibility(View.GONE);
            }
            else
            {
            tv_shop_distance.setVisibility(View.VISIBLE);
            }
        }
        Glide.with(mContext).load(item.getStorefrontThumb()).into(iv_shop);
        tv_shop_name.setText(item.getStoreName());
        tv_street.setText(item.getAddress());

        tv_shop_type.setText(item.getIndustry());


        Log.e("fafaf", item.getCmsMerchantTimes().toString());


        if (!EmptyUtils.isEmpty(item.getCmsMerchantTimes())) {

            String str_bussiniess_time = "";
            for (AllianceHomeShopsEntity.CmsMerchantTimesBean items : item.getCmsMerchantTimes()) {
                str_bussiniess_time += DateUtil.strFormat(items.getStartTime()) + "-" + DateUtil.strFormat(items.getEndTime()) + " ";
            }


            tv_shop_bussiness_time.setText("营业时间 " + str_bussiniess_time);
            tv_shop_bussiness_time.setVisibility(View.VISIBLE);

        } else {
            tv_shop_bussiness_time.setVisibility(View.GONE);
        }

        if (!EmptyUtils.isEmpty(item.getPerCapita())) {
            tv_average_consume.setText("人均 ¥" + item.getPerCapita());
            tv_average_consume.setVisibility(View.VISIBLE);
        } else {
            tv_average_consume.setVisibility(View.GONE);
        }

        if (EmptyUtils.isEmpty(item.getCmsMerchantTimes()) && EmptyUtils.isEmpty(item.getPerCapita())) {
            view_divide.setVisibility(View.GONE);
        } else {
            view_divide.setVisibility(View.VISIBLE);
        }


        if (EmptyUtils.isEmpty(item.getCouponInfo())) {
            iv_discounts.setVisibility(View.GONE);
            tv_full_discount.setVisibility(View.GONE);
            divide_bar.setVisibility(View.GONE);
        } else {

            String str_format = item.getCouponInfo().replace(",", "；");
            tv_full_discount.setText(str_format);


            iv_discounts.setVisibility(View.VISIBLE);
            tv_full_discount.setVisibility(View.VISIBLE);
            divide_bar.setVisibility(View.VISIBLE);
        }


    }


}
