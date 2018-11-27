package com.mujirenben.android.vip.mvp.ui.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.mvp.model.bean.FanListBean;


import java.util.List;

import static com.mujirenben.android.vip.mvp.ui.activity.MyFanActivity.MY_PERMISSIONS_REQUEST_CALL_PHONE;


/**
 * Author: panrongfu
 * Date:2018/6/30 17:01
 * Description:
 */

public class FanAdapter extends BaseQuickAdapter<FanListBean.DataBean,BaseViewHolder> {

    private RCImageView fanIv;
    private TextView fanNameTv;
    private ImageView phoneIv;
    private ImageView fanTypeIv;
    private static final int NORMAL_FAN = 0;
    private static final int CROWN_FAN = 1;
    private static final int SHOP_FAN = 2;
    private String phoneNumber;

    public FanAdapter(@Nullable List<FanListBean.DataBean> data) {
        super(R.layout.fan_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final FanListBean.DataBean data) {
        fanIv = helper.getView(R.id.fan_item_avatar_iv);
        fanNameTv = helper.getView(R.id.fan_item_user_name_tv);
        phoneIv = helper.getView(R.id.fan_item_phone_iv);
        fanTypeIv = helper.getView(R.id.fan_header_type_iv);
        String fanNameText = ""+data.getNikeName();
        //账号级别，0-粉丝，1-皇冠，2-店主
        int fanType = data.getLv();
        Log.e("convert",fanType+"");
        Glide.with(fanIv).load(data.getAvatarUrl()).into(fanIv);
        fanTypeIv.setBackground(null);
        switch (fanType){
            case NORMAL_FAN:
                fanTypeIv.setImageResource(R.drawable.fan_icon);
                break;
            case CROWN_FAN:
                fanTypeIv.setImageResource(R.drawable.crown_icon);
                break;
            case SHOP_FAN:
                fanTypeIv.setBackgroundResource(R.drawable.shop_keeper_icon);
                break;
            default:
                break;
        }

         fanNameTv.setText(fanNameText);
         //拨打电话
         phoneIv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (ContextCompat.checkSelfPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                     ActivityCompat.requestPermissions((Activity) mContext,
                             new String[]{Manifest.permission.CALL_PHONE},
                             MY_PERMISSIONS_REQUEST_CALL_PHONE);
                 } else {
                     phoneNumber = data.getPhone();
                     if(TextUtils.isEmpty(phoneNumber)){
                         ArmsUtils.makeText(mContext,"暂无该用户的手机信息");
                         return;
                     }
                     Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNumber));
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     mContext.startActivity(intent);
                 }
             }
         });
    }

    /**
     * 授权之后跳转到打电话页面
     */
    public void gotoCallPhone(){
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
