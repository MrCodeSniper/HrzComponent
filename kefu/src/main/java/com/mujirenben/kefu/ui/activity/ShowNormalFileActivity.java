package com.mujirenben.kefu.ui.activity;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.util.FileUtils;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.kefu.R;
import com.mujirenben.android.kefu.R2;
import com.mujirenben.kefu.base.BaseActivity;

import java.io.File;

public class ShowNormalFileActivity extends BaseActivity {
    private ProgressBar progressBar;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_activity_show_file);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final String msgId = getIntent().getStringExtra("messageId");

        Message message = ChatClient.getInstance().chatManager().getMessage(msgId);
        if (message == null){
            finish();
            return;
        }

        EMFileMessageBody messageBody = (EMFileMessageBody) message.body();
        file = new File(messageBody.getLocalUrl());
        message.setMessageStatusCallback(new Callback() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FileUtils.openFile(file, ShowNormalFileActivity.this);
                        finish();
                    }
                });
            }

            @Override
            public void onError(int error, final String errorMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (file != null && file.exists() && file.isFile()){
                            file.delete();
                        }
                        String str4 = getResources().getString(R.string.Failed_to_download_file);
                        ArmsUtils.makeText(ShowNormalFileActivity.this, str4 + errorMsg);
                        finish();
                    }
                });

            }

            @Override
            public void onProgress(final int progress, String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                    }
                });

            }
        });

        ChatClient.getInstance().chatManager().downloadAttachment(message);

    }
}
