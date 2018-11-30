package com.mujirenben.kefu.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.Logger;
import com.mujirenben.android.kefu.R;
import com.mujirenben.kefu.util.AudioRecoderUtils;
import com.mujirenben.kefu.util.PopupWindowFactory;
import com.mujirenben.kefu.util.Utils;

/**
 * 作者：Rance on 2016/12/13 15:19
 * 邮箱：rance935@163.com
 * 输入框管理类
 */
public class EmotionInputDetector {

    private static final String SHARE_PREFERENCE_NAME = "com.dss886.emotioninputdetector";
    private static final String SHARE_PREFERENCE_TAG = "soft_input_height";

    private Activity mActivity;
    private InputMethodManager mInputManager;
    private SharedPreferences sp;
    private RelativeLayout mEmotionLayout;
    private EditText mEditText;
    private TextView mVoiceText;
    private MessageList messageList;
    private ViewPager mViewPager;
    private View mSendButton;
    private ImageView mAddButton;
    private Boolean isShowEmotion = false;
    private Boolean isShowAdd = false;
    private AudioRecoderUtils mAudioRecoderUtils;
    private PopupWindowFactory mVoicePop;
    private TextView mPopVoiceText;
    private static String toChatUsername = "hongrenzhuang2";
    private EmotionInputDetector() {
    }

    public static EmotionInputDetector with(Activity activity) {
        EmotionInputDetector emotionInputDetector = new EmotionInputDetector();
        emotionInputDetector.mActivity = activity;
        emotionInputDetector.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        emotionInputDetector.sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return emotionInputDetector;
    }

    public EmotionInputDetector bindToContent(MessageList contentView) {
        messageList = contentView;
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    public EmotionInputDetector bindToEditText(EditText editText) {
        mEditText = editText;
        mEditText.requestFocus();
//        mEditText.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP && mEmotionLayout.isShown()) {
//                lockContentHeight();
//                hideEmotionLayout(true);
//
//                mEditText.postDelayed(() -> unlockContentHeightDelayed(), 200L);
//            }
//            return false;
//        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                  //  mAddButton.setVisibility(View.GONE);
                   // mSendButton.setVisibility(View.VISIBLE);
                    mSendButton.setEnabled(true);
                    mSendButton.setBackgroundResource(R.drawable.red_round_bg);
                } else {
                  //  mAddButton.setVisibility(View.VISIBLE);
                   // mSendButton.setVisibility(View.GONE);
                    mSendButton.setEnabled(false);
                    mSendButton.setBackgroundResource(R.drawable.gray_round_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return this;
    }

    public EmotionInputDetector bindToEmotionButton(View emotionButton) {
        emotionButton.setOnClickListener(v -> {
            if (mEmotionLayout.isShown()) {
                if (isShowAdd) {
                    mViewPager.setCurrentItem(0);
                    isShowEmotion = true;
                    isShowAdd = false;
                } else {
                    lockContentHeight();
                    hideEmotionLayout(true);
                    isShowEmotion = false;
                    unlockContentHeightDelayed();
                }
            } else {
                if (isSoftInputShown()) {
                    lockContentHeight();
                    showEmotionLayout();
                    unlockContentHeightDelayed();
                } else {
                    showEmotionLayout();
                }
                mViewPager.setCurrentItem(0);
                isShowEmotion = true;
            }
        });
        return this;
    }

    public EmotionInputDetector bindToAddButton(ImageView addButton) {
        mAddButton = addButton;
        addButton.setOnClickListener(v -> {
            int tag = (int) mAddButton.getTag();
            if(tag == 0){
                mAddButton.setImageResource(R.mipmap.icon_chat_sub);
                mAddButton.setTag(1);
            }else if(tag == 1){
                mAddButton.setImageResource(R.mipmap.icon_chat_add);
                mAddButton.setTag(0);
            }
            if (mEmotionLayout.isShown()) {
//                if (isShowEmotion) {
//                    mViewPager.setCurrentItem(1);
//                    isShowAdd = true;
//                    isShowEmotion = false;
//                } else {
//                    lockContentHeight();
//                    hideEmotionLayout(true);
//                    isShowAdd = false;
//                    unlockContentHeightDelayed();
//                }

                hideEmotionLayout(false);
            } else {
                if (isSoftInputShown()) {
                    lockContentHeight();
                    showEmotionLayout();
                    unlockContentHeightDelayed();
                } else {
                    showEmotionLayout();
                }
                mViewPager.setCurrentItem(1);
                isShowAdd = true;
            }
        });
        return this;
    }

    public EmotionInputDetector bindToSendButton(TextView sendButton) {
        mSendButton = sendButton;
        sendButton.setOnClickListener(v -> {
            String content = mEditText.getText().toString();
            if(TextUtils.isEmpty(content)){
                ArmsUtils.makeText(mActivity,mActivity.getString(R.string.kefu_send_not_empty));
                return;
            }
            sendTextMessage(content);
            mEditText.setText("");
        });
        return this;
    }
    
    public EmotionInputDetector bindToVoiceButton(ImageView voiceButton) {
        voiceButton.setOnClickListener(v -> {
            int tag = (int) voiceButton.getTag();
            if(tag == 0){
                voiceButton.setImageResource(R.mipmap.icon_chat_keyboard);
                voiceButton.setTag(1);
            }else if(tag == 1){
                voiceButton.setImageResource(R.mipmap.icon_chat_voice);
                voiceButton.setTag(0);
            }
            hideEmotionLayout(false);
            hideSoftInput();
            mVoiceText.setVisibility(mVoiceText.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            mEditText.setVisibility(mVoiceText.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });
        return this;
    }

    public EmotionInputDetector bindToVoiceText(TextView voiceText) {
        mVoiceText = voiceText;
        mVoiceText.setOnTouchListener((v, event) -> {
            // 获得x轴坐标
            int x = (int) event.getX();
            // 获得y轴坐标
            int y = (int) event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mVoicePop.showAtLocation(v, Gravity.CENTER, 0, 0);
                    mVoiceText.setText("松开结束");
                    mPopVoiceText.setText("手指上滑，取消发送");
                    mVoiceText.setTag("1");
                    mAudioRecoderUtils.startRecord(mActivity);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (wantToCancle(x, y)) {
                        mVoiceText.setText("松开结束");
                        mPopVoiceText.setText("松开手指，取消发送");
                        mVoiceText.setTag("2");
                    } else {
                        mVoiceText.setText("松开结束");
                        mPopVoiceText.setText("手指上滑，取消发送");
                        mVoiceText.setTag("1");
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mVoicePop.dismiss();
                    if (mVoiceText.getTag().equals("2")) {
                        //取消录音（删除录音文件）
                        mAudioRecoderUtils.cancelRecord();
                    } else {
                        //结束录音（保存录音文件）
                        mAudioRecoderUtils.stopRecord();
                    }
                    mVoiceText.setText("按住说话");
                    mVoiceText.setTag("3");
                    mVoiceText.setVisibility(View.GONE);
                    mEditText.setVisibility(View.VISIBLE);
                    break;
            }
            return true;
        });
        return this;
    }

    private boolean wantToCancle(int x, int y) {
        // 超过按钮的宽度
        if (x < 0 || x > mVoiceText.getWidth()) {
            return true;
        }
        // 超过按钮的高度
        if (y < -50 || y > mVoiceText.getHeight() + 50) {
            return true;
        }
        return false;
    }

    public EmotionInputDetector setEmotionView(RelativeLayout emotionView) {
        mEmotionLayout = emotionView;
        return this;
    }

    public EmotionInputDetector setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        return this;
    }

    public EmotionInputDetector build() {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        hideSoftInput();
        mAudioRecoderUtils = new AudioRecoderUtils();

        View view = View.inflate(mActivity, R.layout.layout_microphone, null);
        mVoicePop = new PopupWindowFactory(mActivity, view);

        //PopupWindow布局文件里面的控件
        final ImageView mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
        final TextView mTextView = (TextView) view.findViewById(R.id.tv_recording_time);
        mPopVoiceText = (TextView) view.findViewById(R.id.tv_recording_text);
        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                mTextView.setText(Utils.long2String(time));
            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(long time, String filePath) {
                mTextView.setText(Utils.long2String(0));
//                MessageInfo messageInfo = new MessageInfo();
//                messageInfo.setFilepath(filePath);
//                messageInfo.setVoiceTime(time);
//                EventBus.getDefault().post(messageInfo);
                sendVoiceMessage(filePath, (int) time/1000);
            }

            @Override
            public void onError() {
                mVoiceText.setVisibility(View.GONE);
                mEditText.setVisibility(View.VISIBLE);
            }
        });
        return this;
    }

    public boolean interceptBackPress() {
        if (mEmotionLayout.isShown()) {
            hideEmotionLayout(false);
            return true;
        }
        return false;
    }

    private void showEmotionLayout() {
        int softInputHeight = getSupportSoftInputHeight();
        if (softInputHeight == 0) {
            softInputHeight = sp.getInt(SHARE_PREFERENCE_TAG, 787);
        }
        hideSoftInput();
        mEmotionLayout.getLayoutParams().height = ArmsUtils.dip2px(mActivity,75f);
        mEmotionLayout.setVisibility(View.VISIBLE);
    }

    public void hideEmotionLayout(boolean showSoftInput) {
        if (mEmotionLayout.isShown()) {
            mEmotionLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    private void lockContentHeight() {
 //       LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) messageList.getLayoutParams();
//        params.height = messageList.getHeight();
//        params.weight = 0.0F;
    }

    private void unlockContentHeightDelayed() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
               // ((LinearLayout.LayoutParams) messageList.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }

    private void showSoftInput() {
        mEditText.requestFocus();
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        });
    }

    public void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - r.bottom;
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }
        if (softInputHeight < 0) {
            Log.w("EmotionInputDetector", "Warning: value of softInputHeight is below zero!");
        }
        if (softInputHeight > 0) {
            sp.edit().putInt(SHARE_PREFERENCE_TAG, softInputHeight).apply();
        }
        return softInputHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 发送纯文本
     * @param content
     */
    protected void sendTextMessage(String content) {
        if (content != null && content.length() > 1500){
            ArmsUtils.makeText(mActivity, mActivity.getResources().getString(R.string.message_content_beyond_limit));
            return;
        }
        Message message = Message.createTxtSendMessage(content, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                Logger.dump("sendMessage","onSuccess");
            }

            @Override
            public void onError(int code, String error) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArmsUtils.makeText(mActivity,error);
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
                Logger.dump("sendMessage","progress:"+status);
            }
        });
        messageList.refreshSelectLast();
    }

    /**
     * 发送语音
     * @param filePath
     * @param length
     */
    protected void sendVoiceMessage(String filePath, int length) {
        if (TextUtils.isEmpty(filePath)){
            return;
        }
        Message message = Message.createVoiceSendMessage(filePath, length, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
        messageList.refreshSelectLast();
    }

    public void attachMessageAttrs(Message message){
    }
}
