package com.mujirenben.android.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ch.android.common.R;

public class DownloadProgressDialog extends Dialog {

    public interface ClickListener {
        void onLeftButtonClicked();
        void onRightButtonClicked();
    }

    private ProgressBar mPbDownload;
    private TextView mTvState;

    private ClickListener mListener;

    public DownloadProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public DownloadProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public DownloadProgressDialog(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_download_progress, null);
        mPbDownload = dialogView.findViewById(R.id.pb_dowload);
        mTvState = dialogView.findViewById(R.id.tv_state_download);
        setContentView(dialogView);

        dialogView.findViewById(R.id.tv_left_action).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onLeftButtonClicked();
                }
            }
        });
        dialogView.findViewById(R.id.tv_right_action).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onRightButtonClicked();
                        }
                    }
                });
    }

    public void setProgressMax(int max) {
        mPbDownload.setMax(max);
    }

    public void setProgress(int progress) {
        mPbDownload.setProgress(progress);
    }

    public void setDownloadState(String state) {
        mTvState.setText(state);
    }

    public void setClickListener(ClickListener listener) {
        mListener = listener;
    }
}
