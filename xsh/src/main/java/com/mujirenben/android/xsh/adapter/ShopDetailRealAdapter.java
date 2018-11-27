package com.mujirenben.android.xsh.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.common.entity.AllianceDetailBeans;

/**
 * Created by admin on 2017/3/22.
 */
public class ShopDetailRealAdapter extends BaseQuickAdapter<AllianceDetailBeans.DataBean, BaseViewHolder> {

    public ShopDetailRealAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllianceDetailBeans.DataBean item) {

        ImageView iv_shop= helper.getView(R.id.iv_shop);
        TextView tv_shop_name=helper.getView(R.id.tv_shop_name);
        TextView tv_distance=helper.getView(R.id.tv_distance);
        TextView tv_street=helper.getView(R.id.tv_street);
        TextView tv_shop_type=helper.getView(R.id.tv_shop_type);
        TextView tv_shop_bussiness_time=helper.getView(R.id.tv_shop_bussiness_time);
        TextView tv_full_discount=helper.getView(R.id.tv_full_discount);
        TextView tv_pokect_discount =helper.getView(R.id.tv_pokect_discount);
        TextView tv_average_consume=helper.getView(R.id.tv_average_consume);




        Glide.with(mContext).load(item.getStorefrontThumb()).into(iv_shop);
        tv_shop_name.setText(item.getStoreName());
        tv_street.setText(item.getAddress());
        tv_shop_type.setText(item.getIndustry());
       // tv_shop_bussiness_time.setText("营业时间 "+item.getBussinessTime());
       // tv_average_consume.setText("人均￥"+item.getAverageConsume());










    }


}
