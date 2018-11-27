package com.mujirenben.android.mine.mvp.ui.adapter;

import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.mine.mvp.model.bean.MateriaFriendsBean;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.math.BigDecimal;



/**
 * Created by admin on 2017/3/22.
 */

public class MateriaFriendsAdapter extends BaseQuickAdapter<MateriaFriendsBean.ItemInfoListBean.ItemContentListBean,BaseViewHolder> {


    public MateriaFriendsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MateriaFriendsBean.ItemInfoListBean.ItemContentListBean item) {



        ((TextView)helper.getView(R.id.show_toast)).setText(item.itemSubTitle);
        Glide.with(mContext).load(item.imageUrl).into((ImageView) helper.getView(R.id.iv_materia));




        Button btn_reward_amount=helper.getView(R.id.btn_reward);
        btn_reward_amount.setText("分享奖励¥"+item.rewardAmount);

        TextView tv_ticket_amount=helper.getView(R.id.tv_ticket_amount);
        tv_ticket_amount.setText("券 ¥"+getInt(item.ticketAmount));



        TextView tv_month_sale_amount=helper.getView(R.id.tv_month_sale_amount);
        tv_month_sale_amount.setText("月销:"+item.saleAmount);


        TextView tv_descrition_part1=helper.getView(R.id.tv_descrition_part1);
        TextView tv_descrition_part2=helper.getView(R.id.tv_descrition_part2);
        tv_descrition_part1.setVisibility(View.VISIBLE);
        tv_descrition_part2.setVisibility(View.VISIBLE);

        calculateTag1(tv_descrition_part1,tv_descrition_part2,item.itemTitle);



        String now_price=String.valueOf(item.discountPrice);
        int point_index=now_price.indexOf(".");

        String part1=now_price.substring(0,point_index);
        String part2=now_price.substring(point_index+1);



        RxTextTool.getBuilder("",mContext)
                .append("券后 ")
                .append("¥").setForegroundColor(mContext.getResources().getColor(R.color.main_color))
                .append(part1).setForegroundColor(mContext.getResources().getColor(R.color.main_color)).setProportion(1.8f)
                .append("."+part2).setForegroundColor(mContext.getResources().getColor(R.color.main_color))
                .into(helper.getView(R.id.tv_rxtool));

        RxTextTool.getBuilder("原价: ¥"+item.prePrice,mContext).setStrikethrough().into(helper.getView(R.id.tv_preprice));
    }


    public  int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    public  void calculateTag1(TextView first, TextView second, final String text) {
        ViewTreeObserver observer = first.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Layout layout = first.getLayout();
                int lineEnd = layout.getLineEnd(0);
                String substring = text.substring(0, lineEnd);
                String substring1 = text.substring(lineEnd, text.length());
                if (TextUtils.isEmpty(substring1)) {
                    second.setVisibility(View.GONE);
                    second.setText(null);
                    first.setText(substring);
                } else {
                    second.setVisibility(View.VISIBLE);
                    second.setText(substring1);
                    first.setText(substring);
                }
                first.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });

    }

}
