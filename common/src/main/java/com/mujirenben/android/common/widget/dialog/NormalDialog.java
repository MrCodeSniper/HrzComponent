package com.mujirenben.android.common.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ch.android.common.R;

public class NormalDialog extends RxDialog {

    private TextView mMessageTV, mLeftActionTV, mRightActionTV;

    public NormalDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public NormalDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public NormalDialog(Context context) {
        super(context);
        initView();
    }

    public NormalDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public void setMessage(String msg) {
        mMessageTV.setText(msg);
    }

    public void setLeftAction(String actionName, View.OnClickListener action) {
        mLeftActionTV.setText(actionName);
        mLeftActionTV.setOnClickListener(action);
    }

    public void setRightAction(String actionName, View.OnClickListener action) {
        mRightActionTV.setText(actionName);
        mRightActionTV.setOnClickListener(action);
    }

    private void initView() {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_normal, null);
        mMessageTV = dialogView.findViewById(R.id.tv_message);
        mLeftActionTV = dialogView.findViewById(R.id.tv_left_action);
        mRightActionTV = dialogView.findViewById(R.id.tv_right_action);
        setContentView(dialogView);
    }
}
