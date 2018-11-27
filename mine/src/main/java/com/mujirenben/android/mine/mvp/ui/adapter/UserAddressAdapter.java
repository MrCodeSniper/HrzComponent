package com.mujirenben.android.mine.mvp.ui.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.entity.AddressBean;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;


/**
 * Created by admin on 2017/3/22.
 */

public class UserAddressAdapter extends BaseQuickAdapter<AddressBean.DataBean, BaseViewHolder> {


    public UserAddressAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean.DataBean item) {


        TextView tv_user_ship_name= helper.getView(R.id.tv_user_ship_name);
        TextView tv_user_ship_phone= helper.getView(R.id.tv_user_ship_phone);
        TextView tv_ship_address_detail= helper.getView(R.id.tv_ship_address_detail);
        TextView tv_set_default_ship_address=helper.getView(R.id.tv_set_default_ship_address);
        ImageView iv_default_selector=helper.getView(R.id.iv_default_address_select);
        TextView tv_edit_user_ship=helper.getView(R.id.tv_edit_user_ship);
        TextView tv_delete_user_ship=helper.getView(R.id.tv_delete_user_ship);
        RelativeLayout rl_click_area_one=helper.getView(R.id.rl_click_area_one);

        tv_user_ship_name.setText(item.getUserName());
        tv_user_ship_phone.setText(item.getPhone());
        tv_ship_address_detail.setText(item.getProvinceName()+item.getCityName()+item.getCountyName()+item.getDetailInfo());
        iv_default_selector.setSelected(item.getFlag()==1?true:false);
        tv_set_default_ship_address.setText(item.getFlag()==1?"默认地址":"设为默认地址");

        Bundle bundle=new Bundle();
        bundle.putSerializable("shipaddress",item);
//        bundle.putBoolean("isEdit",true);
        tv_edit_user_ship.setOnClickListener((View v)-> ARouter.getInstance().build(ARouterPaths.USER_INFOS)
                .withBundle("bundle",bundle)
                .navigation(mContext));

        helper.addOnClickListener(R.id.ll_ship_above);
        helper.addOnClickListener(R.id.tv_delete_user_ship);
        helper.addOnClickListener(R.id.iv_default_address_select);
        helper.addOnClickListener(R.id.rl_click_area_one);
        helper.addOnClickListener(R.id.tv_ship_address_detail);


    }


}
