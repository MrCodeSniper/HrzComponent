package com.mujirenben.android.home.mvp.ui.adapter;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/10.Best Wishes to You!  []~(~▽~)~* Cheers!

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.home.R;
import com.mujirenben.android.home.mvp.model.entity.HomeModuleBean;

public class ToufuGridAdapter extends BaseQuickAdapter<HomeModuleBean.DataBean,BaseViewHolder> {


    public ToufuGridAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeModuleBean.DataBean item) {
        Glide.with(mContext).load(item.getImg()).into((ImageView)helper.getView(R.id.iv_toufu));
        ((TextView)helper.getView(R.id.tv_tofu)).setText(item.getTitle());
        if(TextUtils.isEmpty(item.getSubtitle())){
            helper.getView(R.id.miaosha_view).setVisibility(View.VISIBLE);
        }else {
            ((TextView)helper.getView(R.id.tv_subtitle)).setText(item.getSubtitle());
            helper.getView(R.id.miaosha_view).setVisibility(View.GONE);
        }
    }
}
