package com.mujirenben.android.mine.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import butterknife.BindView;

@Route(path = ARouterPaths.SETTINGS_WEB_VIEW_ACTIVITY)
public class SettingsWebViewActivity extends BaseActivity {

    @BindView(R2.id.layout_title_bar)
    RelativeLayout mTitleBar;

    @BindView(R2.id.wb_content)
    WebView mContent;

    private String mTitle;
    private String mUriStr;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        initImmersionBar();
        return R.layout.mine_activity_settings_web_view;
    }

    protected void initImmersionBar() {
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        parseInfo();
        setupTitleBar();
        setupWebView();
    }

    private void parseInfo() {
        mTitle = getIntent().getStringExtra("title");
        mUriStr = getIntent().getStringExtra("content_uri");
    }

    private void setupTitleBar() {
        TextView title = mTitleBar.findViewById(R.id.tv_title);
        title.setText(mTitle);

        ImageButton mLeftAction = mTitleBar.findViewById(R.id.ib_left_action);
        mLeftAction.setVisibility(View.VISIBLE);
        mLeftAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsWebViewActivity.this.finish();
            }
        });
        mTitleBar.findViewById(R.id.ib_right_action).setVisibility(View.GONE);
    }

    private void setupWebView() {
        mContent.loadUrl(mUriStr);
        mContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }
}
