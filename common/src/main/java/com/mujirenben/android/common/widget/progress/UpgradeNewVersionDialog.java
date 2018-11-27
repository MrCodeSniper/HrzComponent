package com.mujirenben.android.common.widget.progress;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/25.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ch.android.common.R;


public class UpgradeNewVersionDialog extends Dialog {


    private ProgressBar mPbDownload;
    private TextView mTvState;


    public UpgradeNewVersionDialog(@NonNull Context context) {
        super(context);
        initView();
    }

    public UpgradeNewVersionDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected UpgradeNewVersionDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }


    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.common_activity_upgrade_dialog, null);
        mPbDownload = dialogView.findViewById(R.id.pb_dowload_new_apk);
        mTvState = dialogView.findViewById(R.id.tv_progress_text);
        getWindow().setContentView(dialogView);
    }


    public void setmTvState(TextView mTvState) {
        this.mTvState = mTvState;
    }

    public void setProgressMax(int max) {
        mPbDownload.setMax(max);
    }

    public void setProgress(int progress) {
        mPbDownload.setProgress(progress);
        mTvState.setText(progress+"%");
    }



}
