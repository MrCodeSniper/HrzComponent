package com.mujirenben.android.common.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ch.android.common.R;

public class MessageOnlyDialog extends RxDialog {

    private TextView mMessageTV;
    private TextView mActionTV;

    public MessageOnlyDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public MessageOnlyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public MessageOnlyDialog(Context context) {
        super(context);
        initView();
    }

    public MessageOnlyDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public void setMessage(String msg) {
        mMessageTV.setText(msg);
    }

    public void setAction(String actionName, View.OnClickListener action) {
        mActionTV.setText(actionName);
        mActionTV.setOnClickListener(action);
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_message_only, null);
        mMessageTV = dialogView.findViewById(R.id.tv_message);
        mActionTV = dialogView.findViewById(R.id.tv_action);
        mActionTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(dialogView);
    }
}
