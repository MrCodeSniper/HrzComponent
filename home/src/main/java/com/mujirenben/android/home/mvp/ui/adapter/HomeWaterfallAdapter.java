package com.mujirenben.android.home.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.datapackage.http.imageLoader.glide.ImageConfigImpl;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.GoodsUtil;
import com.mujirenben.android.common.util.NumberUtils;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.home.R;
import com.mujirenben.android.home.mvp.model.entity.HomeModuleBean;
import com.mujirenben.android.home.mvp.model.entity.WaterfallBean;

import java.text.NumberFormat;
import java.util.List;

public class HomeWaterfallAdapter extends BaseMultiItemQuickAdapter<WaterfallBean, BaseViewHolder> {

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;


    public HomeWaterfallAdapter(List<WaterfallBean> data, Context context) {
        super(data);
        mContext = context;
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(mContext);
        mImageLoader = mAppComponent.imageLoader();
        addItemType(WaterfallBean.TYPE_PRODUCT, R.layout.product_item);
        addItemType(WaterfallBean.TYPE_SHOP, R.layout.activity_item);
        addItemType(WaterfallBean.TYPE_ADVERTISE, R.layout.activity_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, WaterfallBean item) {
        switch (item.getItemType()) {
            case WaterfallBean.TYPE_PRODUCT:
                bindProductData(helper, item);
                break;
            case WaterfallBean.TYPE_SHOP:
                bindShopData(helper, item);
                break;
            case WaterfallBean.TYPE_ADVERTISE:
                bindAdvertiseData(helper, item);
                break;
        }
    }

    private void bindProductData(BaseViewHolder holder, WaterfallBean item) {
        if(item==null) return;
        HomeModuleBean.DataBean.Product product = item.getProduct();
        if(TextUtils.isEmpty(product.getImg())){
            return;
        }
        mImageLoader.loadImage(
                mContext,
                ImageConfigImpl
                        .builder()
                        .isCrossFade(true)
                        .isClearMemory(true)
                        .isClearDiskCache(true)
                        .errorPic(R.drawable.load_error)
                        .url(product.getImg())
                        .imageView(holder.getView(R.id.image_item))
                        .build());


        ImageView home_iv_goods_tag=holder.getView(R.id.home_iv_goods_tag);

        switch (product.getPlatform()){
            case Const.Platform.TAOBAO:
                home_iv_goods_tag.setImageResource(R.drawable.icon_taobao);
                break;
            case Const.Platform.JD:
                home_iv_goods_tag.setImageResource(R.drawable.icon_jingdong);
                break;
            case Const.Platform.VIP:
                home_iv_goods_tag.setImageResource(R.drawable.icon_weipinhui);
                break;
            case Const.Platform.MGJ:
                home_iv_goods_tag.setImageResource(R.drawable.icon_mogujie);
                break;
            case Const.Platform.HRZ:
                home_iv_goods_tag.setImageResource(R.drawable.icon_ziying);
                break;
            default:
                home_iv_goods_tag.setImageResource(R.drawable.icon_taobao);
                break;
        }







        ((TextView)holder.getView(R.id.home_tv_goods_tag)).setText(
                GoodsUtil.getPlatformById(product.getPlatform()));

        int length = ((TextView)holder.getView(R.id.home_tv_goods_tag)).getText().length();
        if (length == 2) {
            ((TextView)holder.getView(R.id.name_item)).setText(
                    "          " + product.getProductName());
        } else if (length == 3) {
            ((TextView)holder.getView(R.id.name_item)).setText(
                    "             " + product.getProductName());
        }


        ((TextView)holder.getView(R.id.home_tv_goods_tag)).setTextColor(
                mContext.getResources().getColor(R.color.white));
        ((TextView)holder.getView(R.id.home_tv_goods_tag)).setBackgroundResource(
                R.drawable.home_waterfall_platform);


        float postPrice = product.getAfterPrice();
        NumberFormat numberFormat=NumberFormat.getNumberInstance();
        String number=numberFormat.format(postPrice);

        String str = NumberUtils.floatToString(postPrice);
        String decimals = "00";
        int dotIndex = str.indexOf('.')+1;
        if (dotIndex == -1) {
            decimals = "00";
        } else {
            decimals = str.substring(dotIndex, Math.min(dotIndex + 3, str.length()));
        }



        if(Double.parseDouble(decimals)!=0){//有小数点

            if(product.getCoupon() > -0.000001 && product.getCoupon() < +0.000001){
                RxTextTool.getBuilder("", mContext)
                        .append("¥ ").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color))
                        .append((int)postPrice + "").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color)).setProportion(1.8f)
                        .append("."+decimals+"  ").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color))
                        .into((holder.getView(R.id.tv_rxtool)));
            }else {
                RxTextTool.getBuilder("", mContext)
                        .append("¥ ").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color))
                        .append((int)postPrice + "").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color)).setProportion(1.8f)
                        .append("."+decimals+"  ").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color))
                        .append("¥ "+NumberUtils.subZeroAndDot(product.getOrgPrice()+"")).setStrikethrough().setForegroundColor(mContext.getResources().getColor(R.color.text_color_blur))
                        .into((holder.getView(R.id.tv_rxtool)));
            }


        }else {//没有小数点


            if(product.getCoupon() > -0.000001 && product.getCoupon() < +0.000001){
                RxTextTool.getBuilder("", mContext)
                        .append("¥ ").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color))
                        .append((int)postPrice + " ").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color)).setProportion(1.8f)
                        .into((holder.getView(R.id.tv_rxtool)));
            }else {
                RxTextTool.getBuilder("", mContext)
                        .append("¥ ").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color))
                        .append((int)postPrice + " ").setForegroundColor(
                        mContext.getResources().getColor(R.color.main_color)).setProportion(1.8f)
                        .append("¥ "+NumberUtils.subZeroAndDot(product.getOrgPrice()+"")).setStrikethrough().setForegroundColor(mContext.getResources().getColor(R.color.text_color_blur))
                        .into((holder.getView(R.id.tv_rxtool)));
            }




        }



        holder.getView(R.id.ll_cutoff_type_second).setVisibility(View.GONE);
        holder.getView(R.id.ll_cutoff).setVisibility(View.GONE);



        if(product.getSaleVolume()!=0){
            ((TextView)holder.getView(R.id.tv_month_sale_amount)).setText("月销"+NumberUtils.intChange2Str(product.getSaleVolume()) + "");
        }else {
            ((TextView)holder.getView(R.id.tv_month_sale_amount)).setText("月销0");
        }








        if (Float.compare(product.getCoupon(), 0.0f) == 0) {
            ((TextView)holder.getView(R.id.tv_ticket_amount)).setVisibility(View.INVISIBLE);
        } else {
            ((TextView)holder.getView(R.id.tv_ticket_amount)).setVisibility(View.VISIBLE);
            ((TextView)holder.getView(R.id.tv_ticket_amount)).setText("券 ¥" + NumberUtils.doubleToStr(product.getCoupon()));
        }



        //奖励

        TextView tv_reward_amount= ((TextView)holder.getView(R.id.tv_reward_amount_second));
        tv_reward_amount.setText("奖励 ¥" + product.getMaxCommission());
        if(Math.abs(product.getMaxCommission()) < 0.000001){
            tv_reward_amount.setVisibility(View.INVISIBLE);
        }else {
            tv_reward_amount.setVisibility(View.VISIBLE);
        }


    }

    private void bindShopData(BaseViewHolder holder, WaterfallBean item) {
        HomeModuleBean.DataBean.Shop shop = item.getShop();
        if (shop.getShopImg() == null) return;
        mImageLoader.loadImage(
                mContext,
                ImageConfigImpl
                        .builder()
                        .isCrossFade(true)
                        .isClearMemory(true)
                        .isClearDiskCache(true)
                        .errorPic(R.drawable.load_error)
                        .url(shop.getShopImg())
                        .imageView(holder.getView(R.id.iv_activity))
                        .build());
    }

    private void bindAdvertiseData(BaseViewHolder holder, WaterfallBean item) {
        if (item.getAdvertise().getImg() == null) return;
        HomeModuleBean.DataBean.Advertise advertise = item.getAdvertise();
        mImageLoader.loadImage(
                mContext,
                ImageConfigImpl
                        .builder()
                        .isCrossFade(true)
                        .isClearMemory(true)
                        .isClearDiskCache(true)
                        .errorPic(R.drawable.load_error)
                        .url(advertise.getImg())
                        .imageView(holder.getView(R.id.iv_activity))
                        .build());
    }
}



