package com.mujirenben.android.xsh.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.ShopDetailBean;
import com.mujirenben.android.common.util.rxtools.RxTextTool;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by mac on 2018/5/20.
 */

public class ShopDetailAdapter extends BaseMultiItemQuickAdapter<ShopDetailBean.ResourceInfoBean.ResContentListBean, BaseViewHolder> {



    public ShopDetailAdapter(List<ShopDetailBean.ResourceInfoBean.ResContentListBean> data, Context context) {
        super(data);
        mContext=context;
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_BANNER, R.layout.alliance_shopdetail_holder_banner_item);
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_SHOPNAME, R.layout.alliance_shopdetail_holder_shopname_item);
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_EMPTY, R.layout.alliance_shopdetail_holder_empty_item);
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_SHOPDETAILPARAM, R.layout.alliance_shopdetail_holder_info_item);
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_SHOPPICS, R.layout.alliance_shopdetail_holder_shoppics_item);
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_BUSSINESS_SITUATION, R.layout.alliance_shopdetail_holder_situation_item);
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_LOCATION, R.layout.alliance_shopdetail_holder_location_item);
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_COMMONTICKET, R.layout.alliance_shopdetail_holder_ticket_item);
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_REDPOCKET, R.layout.alliance_shopdetail_holder_empty_item);
        addItemType(Constants.TYPE_ALLIANCE_SHOPDETAIL_SHOPMENU, R.layout.alliance_shopdetail_holder_menu_item);
        addItemType(Constants.TYPE_ALLIANCEE_SHOPDETAIL_DISCOUNT_SHOPS, R.layout.alliance_shopdetail_holder_discount_shops);

    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        if ("Banner".equals(item.getBusinessId())) {
            bindBannerData(helper, item);
        } else if("shopName".equals(item.getBusinessId())){
            bindshopNameData(helper, item);
        }else if("shopDetailParam".equals(item.getBusinessId())){
            bindShopDetailParam(helper,item);
        }else if("shopPics".equals(item.getBusinessId())){
            bindShopPics(helper,item);
        }else if("shopBussinessSituation".equals(item.getBusinessId())){
            bindShopBussinessSituation(helper,item);
        }else if("location".equals(item.getBusinessId())){
            bindLocation(helper,item);
        }else if("commonTicket".equals(item.getBusinessId())){
            bindCommonTicket(helper,item);
        }else if("redPocket".equals(item.getBusinessId())){
      //      bindRedPocket(helper,item);
        }else if("shopMenu".equals(item.getBusinessId())){
            bindShopMenu(helper,item);
        }else if("shopDiscount".equals(item.getBusinessId())){
            bindShopDiscount(helper,item);
        }
    }

    private void bindShopDiscount(BaseViewHolder helper, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
//        Log.e("xxx",item.getItemDataContent().toString());
//       RecyclerView rv_discount_shops= ((RecyclerView)helper.getView(R.id.rv_discount_shops));
//       rv_discount_shops.addItemDecoration(new GridSpacingItemDecoration(2,35,false));
//       DiscountShopGridAdapter discountShopAdapter=new DiscountShopGridAdapter(R.layout.item_shop_discount_grid);
//       rv_discount_shops.setLayoutManager( new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
//       rv_discount_shops.setAdapter(discountShopAdapter);
//       discountShopAdapter.setNewData(item.getItemDataContent());
    }

    private void bindShopDetailParam(BaseViewHolder holder, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        ((TextView)holder.getView(R.id.tv_shop_type)).setText(item.getItemDataContent().get(0).getBussinesType());
        ((TextView)holder.getView(R.id.tv_shop_city)).setText(item.getItemDataContent().get(0).getCity());


        ((TextView)holder.getView(R.id.tv_shop_price)).setText("¥ "+(new Double(item.getItemDataContent().get(0).getAveragePrice())).intValue()+"/人");
    }

    private void bindshopNameData(BaseViewHolder holder, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        ((TextView)holder.getView(R.id.tv_shopdetail_shopname)).setText(item.getItemDataContent().get(0).getMainTitle());
    }

    private void bindLocation(BaseViewHolder holder, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        ((TextView)holder.getView(R.id.tv_location)).setText(item.getItemDataContent().get(0).getMainTitle());
        String phoneNumber="15168264355";
        ((ImageView)holder.getView(R.id.iv_phone_call)).setOnClickListener((View v)->{
            Intent Intent =  new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));//跳转到拨号界面，同时传递电话号码
            mContext.startActivity(Intent);
        });

    }


    private void bindBannerData(BaseViewHolder holder, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        Glide.with(mContext).load(item.getItemDataContent().get(0).getImageUrl()).into((ImageView) holder.getView(R.id.iv_shopdetail_banner));


//        ((ImageView) holder.getView(R.id.iv_shopdetail_banner)).setOnClickListener((View v)->{
//            Intent intent=new Intent(mContext, NewVideoActivity.class);
//            intent.putExtra(Contant.IntentConstant.INTENT_ID,"http://videocdn.tlgn365.com/video/2017-12-20/%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8%E8%98%91%E8%8F%87%E8%A1%97%E8%B4%AD%E7%89%A9.mp4");
//            intent.putExtra(Contant.IntentConstant.TITLE,"视频标题");
//            intent.putExtra(Contant.IntentConstant.LINK_URL,"http://videocdn.tlgn365.com/video/2017-12-20/%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8%E8%98%91%E8%8F%87%E8%A1%97%E8%B4%AD%E7%89%A9.mp4");
//            intent.putExtra(Contant.IntentConstant.SEARCH_TXT,"介绍");
//            intent.putExtra(Contant.IntentConstant.ISWXIMG,"http://videocdn.tlgn365.com/thumb/2018-01-06/15152244502679.jpg");
//            mContext.startActivity(intent);
//        });
    }

    private void bindShopMenu(BaseViewHolder holder, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        LinearLayout ll_shop_tickets=  ((LinearLayout)holder.getView(R.id.ll_shop_menu));
        ll_shop_tickets.removeAllViews();
        for ( ShopDetailBean.ResourceInfoBean.ResContentListBean.ItemDataContentBean items:item.getItemDataContent()) {
            LinearLayout view= (LinearLayout) View.inflate(mContext, R.layout.alliance_item_menu,null);

            Glide.with(mContext).load(items.getImageUrl()).into((ImageView) view.findViewById(R.id.iv_shop_menu_item));
            if(items.getMenuType().equals("新品")){
                ((TextView)view.findViewById(R.id.iv_menu_type)).setText("新品");
                //Glide.with(mContext).load(R.drawable.sign_new).into((ImageView) view.findViewById(R.id.iv_menu_type));
            }else if(items.getMenuType().equals("招牌")){
                ((TextView)view.findViewById(R.id.iv_menu_type)).setText("老板强力推荐");
              //  Glide.with(mContext).load(R.drawable.sign_recommend).into((ImageView) view.findViewById(R.id.iv_menu_type));
            }

            ((TextView)view.findViewById(R.id.tv_menu_name)).setText(items.getMainTitle());
            ll_shop_tickets.addView(view);
        }
    }

    private void bindCommonTicket(BaseViewHolder holder, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        LinearLayout ll_shop_tickets=  ((LinearLayout)holder.getView(R.id.ll_shop_tickets));
        ll_shop_tickets.removeAllViews();
        for ( ShopDetailBean.ResourceInfoBean.ResContentListBean.ItemDataContentBean items:item.getItemDataContent()) {
            LinearLayout view=(LinearLayout) View.inflate(mContext, R.layout.alliance_ticket_item,null);

            Double amounts=items.getTicketAmount();

            DecimalFormat df = new DecimalFormat("######0.00"); //保留两位小数点
            df.format(amounts);


            String floats= String.valueOf(amounts).substring(String.valueOf(amounts).indexOf("."));

            int amount = items.getTicketAmount().intValue();
            RxTextTool.getBuilder("¥",mContext)
                    .append(amount+"").setForegroundColor(mContext.getResources().getColor(R.color.white)).setProportion(2.5f)
//                    .append(floats+"0").setForegroundColor(mContext.getResources().getColor(R.color.yellow))
                    .into((TextView)view.findViewById(R.id.tv_ticket_amount));

            ((TextView)view.findViewById(R.id.tv_ticket_limit)).setText("满"+(new Double(items.getTicketUperLimit())).intValue()+"使用");
            ll_shop_tickets.addView(view);
        }
    }

    private void bindRedPocket(BaseViewHolder holder, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        LinearLayout ll_shop_tickets=  ((LinearLayout)holder.getView(R.id.ll_shop_red_bag));
        for ( ShopDetailBean.ResourceInfoBean.ResContentListBean.ItemDataContentBean items:item.getItemDataContent()) {

            int amount = (int)items.getRedPocketAmount();
            BigDecimal bg = new BigDecimal(items.getRedPocketAmount());
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Log.e("xxx",f1+"");
            String str=(f1+"").substring((f1+"").lastIndexOf("."));


            View view= View.inflate(mContext, R.layout.alliance_redbag_item,null);
            RxTextTool.getBuilder("¥",mContext)
                    .append(amount+"").setForegroundColor(mContext.getResources().getColor(R.color.yellow)).setProportion(2.7f).setBold()
                    .append(str+"  无门槛红包").setForegroundColor(mContext.getResources().getColor(R.color.yellow))
                    .into((TextView)view.findViewById(R.id.tv_redbag_amount));
            //((TextView)view.findViewById(R.id.tv_redbag_amount)).setText("￥"+items.getRedPocketAmount());
            ((TextView)view.findViewById(R.id.tv_redbag_limit)).setText("仅限本店使用 | 仅限新人使用\n"+items.getRedPocketTimeLimit());
            ll_shop_tickets.addView(view);
        }
    }

    private void bindShopPics(BaseViewHolder holder, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        LinearLayout ll_shop_pics=  ((LinearLayout)holder.getView(R.id.ll_shop_pics));
        ll_shop_pics.removeAllViews();
        for (ShopDetailBean.ResourceInfoBean.ResContentListBean.ItemDataContentBean items:item.getItemDataContent()) {
            FrameLayout view= (FrameLayout) View.inflate(mContext, R.layout.alliance_item_iv,null);
             Glide.with(mContext).load(items.getImageUrl()).into((ImageView) view.findViewById(R.id.iv_shop_pic_item));
            ll_shop_pics.addView(view);
        }
    }



    private void  bindShopBussinessSituation(BaseViewHolder holder, ShopDetailBean.ResourceInfoBean.ResContentListBean item) {
        if(item.getItemDataContent().get(0).getParkEnable()==1){
            ((TextView)holder.getView(R.id.tv_park)).setVisibility(View.VISIBLE);
        }
        if(item.getItemDataContent().get(0).getWifiEnable()==1){
            ((TextView)holder.getView(R.id.tv_wifi)).setVisibility(View.VISIBLE);
        }
        ((TextView)holder.getView(R.id.tv_bussiness_time)).setText("营业中   "+item.getItemDataContent().get(0).getBussinessTime()+"营业");
    }


}


