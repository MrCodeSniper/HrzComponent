package com.mujirenben.android.vip.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.entity.SearchResult;
import com.mujirenben.android.common.util.NumberUtils;
import com.mujirenben.android.common.util.TextDivideRowUtils;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.vip.R;

/**
 * Created by mac on 2018/8/26.
 */

public class SelfBussinessAdapter extends BaseQuickAdapter<SearchResult.SearchData.SearchBean,BaseViewHolder> {


    public SelfBussinessAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResult.SearchData.SearchBean item) {
        if(item==null) return;




        ((RelativeLayout)(helper.getView(R.id.rl_ticket_and_earn))).setVisibility(View.GONE);

        ((TextView)helper.getView(R.id.name_item)).setText(item.getName());
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.image_item));
        helper.getView(R.id.view_white_divide_line).setVisibility(View.INVISIBLE);
        ImageView home_iv_goods_tag=helper.getView(R.id.home_iv_goods_tag);


        switch (item.getPlatform()){
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
                home_iv_goods_tag.setImageResource(R.drawable.icon_ziying);
                break;
        }



        //奖励  double为0 转换为字符串会变0.0
        TextView tv_reward_amount=helper.getView(R.id.tv_reward_amount_second);
        tv_reward_amount.setText("奖励 ¥"+ NumberUtils.doubleToString(item.getMaxCommission()));

//        Logger.e(item.getMaxCommission()+"");

        if(Math.abs(item.getMaxCommission()) < 0.000001){
            tv_reward_amount.setVisibility(View.INVISIBLE);
        }else {
            tv_reward_amount.setVisibility(View.VISIBLE);
        }

        //券
        TextView tv_ticket_amount=helper.getView(R.id.tv_ticket_amount);
        tv_ticket_amount.setText("券 ¥"+NumberUtils.doubleToString(item.getCoupon()));
        if(Math.abs(item.getCoupon()) < 0.000001){
            tv_ticket_amount.setVisibility(View.INVISIBLE);
        }else {
            tv_ticket_amount.setVisibility(View.VISIBLE);
        }

        //销量
        ((TextView)helper.getView(R.id.tv_month_sale_amount)).setText("月销"+NumberUtils.intChange2Str(item.getSaleVolume()) + "");
        if(TextUtils.isEmpty(NumberUtils.intChange2Str(item.getSaleVolume()))){
            ((TextView)helper.getView(R.id.tv_month_sale_amount)).setText("月销0");
        }



        //标题
        TextView tv_descrition_part1=helper.getView(R.id.tv_descrition_part1);
        TextView tv_descrition_part2=helper.getView(R.id.tv_descrition_part2);
        TextView name_item=helper.getView(R.id.name_item);
        name_item.setVisibility(View.GONE);
        tv_descrition_part1.setVisibility(View.VISIBLE);
        tv_descrition_part2.setVisibility(View.VISIBLE);


        ViewTreeObserver vto = tv_descrition_part1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tv_descrition_part1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                String[] strs=TextDivideRowUtils.CurStringToTwoPartByTextPaint(item.getName(),tv_descrition_part1.getMeasuredWidth(),tv_descrition_part1.getPaint());
                if(strs!=null){
                    if(strs.length>0&&strs.length<=1){
                        tv_descrition_part1.setText(strs[0]);
                    }else if(strs.length>0&&strs.length<=2){
                        tv_descrition_part1.setText(strs[0]);
                        tv_descrition_part2.setText(strs[1]);
                    }
                }


            }
        });


        //小数点处理
        String str = NumberUtils.sub(item.getOrgPrice(),item.getCoupon());
        String decimals = "00";

        if(decimals.contains(".")){
            int dotIndex = str.indexOf('.')+1;
            if (dotIndex == -1) {
                decimals = "00";
            } else {
                decimals = str.substring(dotIndex, Math.min(dotIndex + 3, str.length()));
            }
        }



        if(Double.parseDouble(decimals)!=0){//有小数点
            RxTextTool.getBuilder("", mContext)
                    .append("¥ ").setForegroundColor(
                    mContext.getResources().getColor(R.color.main_color))
                    .append((int)Double.parseDouble(str) + "").setForegroundColor(
                    mContext.getResources().getColor(R.color.main_color)).setProportion(1.8f)
                    .append("."+decimals+"  ").setForegroundColor(
                    mContext.getResources().getColor(R.color.main_color))
                    .into((helper.getView(R.id.tv_rxtool)));
        }else {//没有小数点
            RxTextTool.getBuilder("", mContext)
                    .append("¥ ").setForegroundColor(
                    mContext.getResources().getColor(R.color.main_color))
                    .append((int)Double.parseDouble(str) + " ").setForegroundColor(
                    mContext.getResources().getColor(R.color.main_color)).setProportion(1.8f)
                    .into((helper.getView(R.id.tv_rxtool)));
        }
    }


}
