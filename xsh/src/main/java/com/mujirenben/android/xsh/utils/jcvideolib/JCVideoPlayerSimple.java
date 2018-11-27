package com.mujirenben.android.xsh.utils.jcvideolib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.mujirenben.android.xsh.R;
import com.mujirenben.android.common.util.ArmsUtils;


public class JCVideoPlayerSimple extends JCVideoPlayer {

    public JCVideoPlayerSimple(Context context) {
        super(context);
    }

    public JCVideoPlayerSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.jc_layout_base;
    }

    @Override
    public boolean setUp(String url, Object... objects) {
        if (super.setUp(url, objects)) {
//      if (mIfCurrentIsFullscreen) {
//        fullscreenButton.setImageResource(R.drawable.jc_shrink);
//      } else {
//        fullscreenButton.setImageResource(R.drawable.jc_enlarge);
//      }
            fullscreenButton.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    @Override
    protected void setStateAndUi(int state) {
        super.setStateAndUi(state);
        switch (mCurrentState) {
            case CURRENT_STATE_NORMAL:
                startButton.setVisibility(View.VISIBLE);
                break;
            case CURRENT_STATE_PREPAREING:
                startButton.setVisibility(View.INVISIBLE);
                break;
            case CURRENT_STATE_PLAYING:
                startButton.setVisibility(View.VISIBLE);
                break;
            case CURRENT_STATE_PAUSE:
                break;
            case CURRENT_STATE_ERROR:
                break;
        }
        updateStartImage();
    }

    private void updateStartImage() {
        if (mCurrentState == CURRENT_STATE_PLAYING) {
            startButton.setImageResource(R.drawable.jc_click_pause_selector);
        } else if (mCurrentState == CURRENT_STATE_ERROR) {
            startButton.setImageResource(R.drawable.jc_click_error_selector);
        } else {
            startButton.setImageResource(R.drawable.jc_click_play_selector);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fullscreen && mCurrentState == CURRENT_STATE_NORMAL) {
            ArmsUtils.makeText(getContext(), "Play video first");
            return;
        }
        super.onClick(v);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            if (mCurrentState == CURRENT_STATE_NORMAL) {
                ArmsUtils.makeText(getContext(), "Play video first");
                return;
            }
        }
        super.onProgressChanged(seekBar, progress, fromUser);
    }
}
