package com.mujirenben.android.common.widget;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/28.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ch.android.common.R;
import com.mujirenben.android.common.util.rxtools.RxTextTool;

public class JdPopDialog extends Dialog {


    private Context mContext;
    private TextView tv_agreement;
    private TextView tv_cancel;
    private TextView tv_agree_dialog;
    private JdDialogInterface jdDialogInterface;

    public interface JdDialogInterface{
        void cancel();
        void read();
    }

    public void setJdDialogInterface(JdDialogInterface jdDialogInterface) {
        this.jdDialogInterface = jdDialogInterface;
    }

    public JdPopDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public JdPopDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected JdPopDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.jd_returnlink_layout);
    }

    private void initData() {
        tv_agreement=findViewById(R.id.tv_agreement);
        tv_agree_dialog=findViewById(R.id.tv_agree_dialog);
        tv_cancel=findViewById(R.id.tv_cancel_dialog);
        tv_agreement.setText(RxTextTool.getBuilder("为保障您的愉快购物，请在购买京东商品仔细阅读",mContext)
                .append(" 《购买须知》").setForegroundColor(mContext.getResources().getColor(R.color.main_color))
                .create());

        tv_cancel.setOnClickListener(v -> {
            if(jdDialogInterface!=null){
                jdDialogInterface.cancel();
            }
        });

        tv_agree_dialog.setOnClickListener(v -> {
            if(jdDialogInterface!=null){
                jdDialogInterface.read();
            }
        });





    }


}
