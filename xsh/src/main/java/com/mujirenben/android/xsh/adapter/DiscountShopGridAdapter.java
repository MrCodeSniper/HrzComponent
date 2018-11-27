package com.mujirenben.android.xsh.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.activity.AllianceShopDetailActivity;
import com.mujirenben.android.xsh.entity.ShopDetailBean;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.util.Logger;
import com.mujirenben.android.common.widget.label.LabelsView;

import java.util.ArrayList;


/**
 * Created by admin on 2017/3/22.
 */

public class DiscountShopGridAdapter extends BaseQuickAdapter<ShopDetailBean.ResourceInfoBean.ResContentListBean.ItemDataContentBean, BaseViewHolder> {


    public DiscountShopGridAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetailBean.ResourceInfoBean.ResContentListBean.ItemDataContentBean item) {



        ImageView iv_shop= helper.getView(R.id.iv_shop_icon);
        TextView tv_shop_name=helper.getView(R.id.tv_shop_name);
        TextView tv_info=helper.getView(R.id.tv_info);
        TextView tv_distance=helper.getView(R.id.tv_distance);
        LabelsView labelsView=helper.getView(R.id.labels);

        Glide.with(mContext).load(item.getImageUrl()).into(iv_shop);
        com.orhanobut.logger.Logger.e("banner图片:" + item.getImageUrl());





        tv_shop_name.setText(item.getMainTitle());






        ArrayList<String> label = new ArrayList<>();
        label.add("Android");
        label.add("IOS");
        label.add("前端");
        label.add("后台");
        label.add("微信开发");
        label.add("游戏开发");
        labelsView.setLabels(label); //直接设置一个字符串数组就可以了。








    }


}
