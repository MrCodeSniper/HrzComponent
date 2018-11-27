package com.mujirenben.android.common.widget.dialog;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/28.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ch.android.common.R;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.orhanobut.logger.Logger;

public class LinkPlatformPopDialog extends Dialog {

    private Context mContext;
    private TextView tv_save_money_amount;
    private TextView tv_title_buy_earn_amount;
    private TextView tv_goto_platform;
    private TextView tv_title_main;
    private TextView tv_earn_money;
    private LinearLayout ll_buy_earn_second;
    private LinearLayout ll_buy_earn;
    private TextView tv_title_buy_earn_amount_second;
    private TextView tv_earn_money_second;

    public LinkPlatformPopDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        Logger.e("init");
        initView();
    }

    public LinkPlatformPopDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        Logger.e("init");
        initView();
    }

    protected LinkPlatformPopDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        Logger.e("init");
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.platform_link_dialog_layout,null);
        tv_title_main=view.findViewById(R.id.tv_title_main);
        ll_buy_earn=view.findViewById(R.id.ll_buy_earn);
        ll_buy_earn_second=view.findViewById(R.id.ll_buy_earn_second);
        tv_title_buy_earn_amount_second=view.findViewById(R.id.tv_title_buy_earn_amount_second);
        tv_save_money_amount=view.findViewById(R.id.tv_save_money_amount);
        tv_earn_money_second=view.findViewById(R.id.tv_earn_money_second);
        tv_earn_money=view.findViewById(R.id.tv_earn_money);
        tv_goto_platform=view.findViewById(R.id.tv_goto_platform);
        tv_title_buy_earn_amount=view.findViewById(R.id.tv_title_buy_earn_amount);
        setContentView(view);
    }

    public void setSaveAmount(String saveAmount){

        if(TextUtils.equals(saveAmount,"0.00")||TextUtils.equals(saveAmount,"0")){
            tv_title_main.setVisibility(View.INVISIBLE);
            tv_save_money_amount.setVisibility(View.INVISIBLE);

            ll_buy_earn.setVisibility(View.GONE);
            ll_buy_earn_second.setVisibility(View.VISIBLE);


        }else {
            tv_title_main.setVisibility(View.VISIBLE);
            tv_save_money_amount.setVisibility(View.VISIBLE);

            ll_buy_earn.setVisibility(View.VISIBLE);
            ll_buy_earn_second.setVisibility(View.GONE);

        }

        RxTextTool.getBuilder("¥ ",mContext)
                .append(saveAmount).setBold().setProportion(1.5f)
                .into((tv_save_money_amount));
    }

    public void setEarnAmount(String earnAmount){

        if(TextUtils.equals(earnAmount,"0.00")||TextUtils.equals(earnAmount,"0")){
            tv_title_buy_earn_amount.setVisibility(View.INVISIBLE);
            tv_earn_money.setVisibility(View.INVISIBLE);
        }else {
            tv_title_buy_earn_amount.setVisibility(View.VISIBLE);
            tv_earn_money.setVisibility(View.VISIBLE);
        }





        RxTextTool.getBuilder("¥ ",mContext)
                .append(earnAmount).setBold().setProportion(1.5f)
                .into((tv_earn_money));

        RxTextTool.getBuilder("¥ ",mContext)
                .append(earnAmount).setBold().setProportion(1.5f)
                .into((tv_earn_money_second));
    }

    public void setPlatForm(String platForm){
        tv_goto_platform.setText("正在去往"+platForm+"...");
    }

}
