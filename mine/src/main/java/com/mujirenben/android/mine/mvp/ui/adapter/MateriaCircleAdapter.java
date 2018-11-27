package com.mujirenben.android.mine.mvp.ui.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.mujirenben.android.mine.mvp.model.bean.MateriaCircleBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by admin on 2017/3/22.
 */

public class MateriaCircleAdapter extends BaseQuickAdapter<MateriaCircleBean.DataBean,BaseViewHolder> {


    public MateriaCircleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MateriaCircleBean.DataBean item) {

        RCImageView ivUserIcon=helper.getView(R.id.iv_users_icon);
        TextView tvSender=helper.getView(R.id.tv_sender);
        TextView tvSendTime=helper.getView(R.id.tv_sendtime);
        Button btnSendToCircle=helper.getView(R.id.btn_resend);
        TextView tvSendContent=helper.getView(R.id.tv_send_msg);
        ImageView rvImages=helper.getView(R.id.rv_images);
        LinearLayout llContentImgTypeTwo=helper.getView(R.id.ll_type_two);
        ImageView ivTypeTwoOfOne=helper.getView(R.id.iv_type_two_of_one);
        ImageView ivTypeTwoOfTwo=helper.getView(R.id.iv_type_two_of_two);

        LinearLayout llContentImgTypeThree=helper.getView(R.id.ll_type_third);
        ImageView ivTypeThreeOfOne=helper.getView(R.id.iv_type_three_of_one);
        ImageView ivTypeThreeOfTwo=helper.getView(R.id.iv_type_three_of_two);
        ImageView ivTypeThreeOfThree=helper.getView(R.id.iv_type_three_of_three);



        LinearLayout llContentImgTypeFour=helper.getView(R.id.ll_type_four);
        ImageView ivTypeFourOfOne=helper.getView(R.id.iv_type_four_of_one);
        ImageView ivTypeFourOfTwo=helper.getView(R.id.iv_type_four_of_two);
        ImageView ivTypeFourOfThree=helper.getView(R.id.iv_type_four_of_three);
        ImageView ivTypeFourOfFour=helper.getView(R.id.iv_type_four_of_four);



        Glide.with(mContext).load(item.getUserIconUrl()).into(ivUserIcon);
        if(item.getMateriaType()==2){
            llContentImgTypeTwo.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getCircleMateriaList().get(0).getIconUrl()).into(ivTypeTwoOfOne);
            Glide.with(mContext).load(item.getCircleMateriaList().get(1).getIconUrl()).into(ivTypeTwoOfTwo);
        }else if(item.getMateriaType()==3){
            llContentImgTypeThree.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getCircleMateriaList().get(0).getIconUrl()).into(ivTypeThreeOfOne);
            Glide.with(mContext).load(item.getCircleMateriaList().get(1).getIconUrl()).into(ivTypeThreeOfTwo);
            Glide.with(mContext).load(item.getCircleMateriaList().get(2).getIconUrl()).into(ivTypeThreeOfThree);
        } else if(item.getMateriaType()==4){
            llContentImgTypeFour.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getCircleMateriaList().get(0).getIconUrl()).into(ivTypeFourOfOne);
            Glide.with(mContext).load(item.getCircleMateriaList().get(1).getIconUrl()).into(ivTypeFourOfTwo);
            Glide.with(mContext).load(item.getCircleMateriaList().get(2).getIconUrl()).into(ivTypeFourOfThree);
            Glide.with(mContext).load(item.getCircleMateriaList().get(3).getIconUrl()).into(ivTypeFourOfFour);
        } else {
            rvImages.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getCircleMateriaList().get(0).getIconUrl()).into(rvImages);
        }






        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sf.parse(item.getDispatchDate());
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
            String dateString = formatter.format(date);
            tvSendTime.setText(dateString+"发布");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvSender.setText(item.getUserName());
        btnSendToCircle.setOnClickListener((View v)-> {

        });

        tvSendContent.setText(item.getShareContent());







    }



}
