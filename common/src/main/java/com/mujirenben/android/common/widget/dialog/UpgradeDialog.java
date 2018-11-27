package com.mujirenben.android.common.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ch.android.common.R;


/**
 * @author vondear
 * @date 2016/7/19
 * 确认 取消 Dialog
 */
public class UpgradeDialog extends RxDialog {

    TextView tv_upgrade_info;
    TextView tv_goto_upgrade;
    TextView tv_no_need_upgrade;

    public UpgradeDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public UpgradeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public UpgradeDialog(Context context) {
        super(context);
        initView();
    }

    public UpgradeDialog(Activity context) {
        super(context);
        initView();
    }

    public UpgradeDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }


    public TextView getTv_upgrade_info() {
        return tv_upgrade_info;
    }

    public void setTv_upgrade_info(TextView tv_upgrade_info) {
        this.tv_upgrade_info = tv_upgrade_info;
    }

    public TextView getTv_goto_upgrade() {
        return tv_goto_upgrade;
    }

    public void setTv_goto_upgrade(TextView tv_goto_upgrade) {
        this.tv_goto_upgrade = tv_goto_upgrade;
    }

    public TextView getTv_no_need_upgrade() {
        return tv_no_need_upgrade;
    }

    public void setTv_no_need_upgrade(TextView tv_no_need_upgrade) {
        this.tv_no_need_upgrade = tv_no_need_upgrade;
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_upgrade, null);
        tv_upgrade_info = (TextView) dialogView.findViewById(R.id.tv_upgrade_info);
        tv_goto_upgrade = (TextView) dialogView.findViewById(R.id.tv_goto_upgrade);
        tv_no_need_upgrade = (TextView) dialogView.findViewById(R.id.tv_no_need_upgrade);
        tv_no_need_upgrade.setPaintFlags(Paint. UNDERLINE_TEXT_FLAG);
        tv_no_need_upgrade.getPaint().setAntiAlias(true);//这里要加抗锯齿
        setContentView(dialogView);
    }
}
