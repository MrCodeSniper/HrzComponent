package com.mujirenben.android.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.android.common.R;
import com.mujirenben.android.common.util.DoubleChooseListener;

/**
 * Created by Administrator on 2018\9\7 0007.
 */

public class TokenOutTimeDialog extends Dialog {

    private Context mContext;
    private DoubleChooseListener listener;


    public void setListener(DoubleChooseListener listener) {
        this.listener = listener;
    }

    public TokenOutTimeDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public TokenOutTimeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initView();
    }

    public TokenOutTimeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        initView();
    }



    private void initView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_normal, null);
        TextView contentTv = view.findViewById(R.id.tv_message);
        TextView cancelTv = view.findViewById(R.id.tv_right_action);
        TextView confirmTv = view.findViewById(R.id.tv_left_action);
        contentTv.setText("您的登录信息已过期，请重新登录");
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) contentTv.getLayoutParams();
        params.gravity = Gravity.CENTER;
        contentTv.setTextColor(Color.parseColor("#B2B2B2"));
        confirmTv.setText("重新登录");
        cancelTv.setText("取消");
        cancelTv.setOnClickListener(v ->{
         if(listener!=null){
             listener.onCancel();
          }
        });
        confirmTv.setOnClickListener(v ->{
            if(listener!=null){
                listener.onConfirm();
            }
        });
        setContentView(view);
    }


}
