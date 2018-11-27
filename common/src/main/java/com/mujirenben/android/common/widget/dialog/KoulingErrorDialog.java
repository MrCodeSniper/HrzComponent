package com.mujirenben.android.common.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.ch.android.common.R;
import com.mujirenben.android.common.widget.widgetcallback.OnKouLingCloseDialog;

/**
 * Created by Administrator on 2018\9\7 0007.
 */

public class KoulingErrorDialog extends RxDialog {

    private Context mContext;

    public OnKouLingCloseDialog kouLingCloseDialog;


    public void setKouLingCloseDialog(OnKouLingCloseDialog kouLingCloseDialog) {
        this.kouLingCloseDialog = kouLingCloseDialog;
    }

    public KoulingErrorDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public KoulingErrorDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initView();
    }

    public KoulingErrorDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        initView();
    }

    private void initView(){
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_taokouling_error, null);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(dialogView);
        setFullScreen();
        dialogView.findViewById(R.id.iv_close).setOnClickListener(v -> {
            if(kouLingCloseDialog!=null){
                kouLingCloseDialog.hide();
            }
        });
    }




}
