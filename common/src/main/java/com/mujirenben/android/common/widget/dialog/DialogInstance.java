package com.mujirenben.android.common.widget.dialog;

import android.app.Dialog;

/**
 * Created by Administrator on 2018\8\24 0024.
 */

public class DialogInstance {
    private boolean showType;//0为点击取消 1为计时取消
    private Dialog dialogView;
    private int showTime;

    public DialogInstance() {
    }

    public boolean isShowType() {
        return showType;
    }

    public void setShowType(boolean showType) {
        this.showType = showType;
    }

    public Dialog getDialogView() {
        return dialogView;
    }

    public void setDialogView(Dialog dialogView) {
        this.dialogView = dialogView;
    }

    public int getShowTime() {
        return showTime;
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }
}
