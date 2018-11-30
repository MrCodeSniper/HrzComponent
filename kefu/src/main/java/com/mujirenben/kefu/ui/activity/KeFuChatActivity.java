package com.mujirenben.kefu.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hrz.poplayer.HrzLayerView;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.util.PathUtil;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;

import com.mujirenben.android.kefu.R;
import com.mujirenben.android.kefu.R2;
import com.mujirenben.kefu.UIProvider;
import com.mujirenben.kefu.adapter.CommonFragmentPagerAdapter;
import com.mujirenben.kefu.recorder.MediaManager;
import com.mujirenben.kefu.ui.fragment.ChatFunctionFragment;
import com.mujirenben.kefu.util.GlobalOnItemClickManagerUtils;
import com.mujirenben.kefu.widget.EmotionInputDetector;
import com.mujirenben.kefu.widget.MessageList;
import com.mujirenben.kefu.widget.NoScrollViewPager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hrz.poplayer.HrzLayerView.STATE_DIALOG;


/**
 * @author: panrongfu
 * @date: 2018/7/25 13:35
 * @describe: 进入客服聊天页面之前，必须检测是否登录 {@Link ChatClient.getInstance().login()}
 */
@Route(path = ARouterPaths.KE_FU_ACTIVITY)
public class KeFuChatActivity extends AppCompatActivity implements ChatManager.MessageListener {

    @BindView(R2.id.message_list)
    MessageList messageList;
    @BindView(R2.id.emotion_voice)
    ImageView emotionVoice;
    @BindView(R2.id.edit_text)
    EditText editText;
    @BindView(R2.id.voice_text)
    TextView voiceText;
    @BindView(R2.id.emotion_button)
    ImageView emotionButton;
    @BindView(R2.id.emotion_add)
    ImageView emotionAdd;
    @BindView(R2.id.emotion_send)
    TextView emotionSend;
    @BindView(R2.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R2.id.emotion_layout)
    RelativeLayout emotionLayout;
    @BindView(R2.id.ib_left_action)
    ImageButton ibLeftAction;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.ib_right_title)
    TextView ibRightTitle;
    private ArrayList<Fragment> fragments;
    private ChatFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter adapter;

    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected int pagesize = 20;
    private Conversation conversation;

    private static String toChatUsername = "hongrenzhuang2";
    private static String loginAccount = "11983";
    private static String password = "20aa45d37b34428587d43bf5aa2d6db7";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private boolean isMessageListInited;

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_LOCAL = 2;
    public static final int REQUEST_CODE_SELECT_VIDEO = 3;
    public static final int REQUEST_CODE_EVAL = 5;
    public static final int REQUEST_CODE_SELECT_FILE = 6;
    public static String cameraFilePath = null;

    //录音相关
    int animationRes = 0;
    int res = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this, R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        setContentView(R.layout.activity_kefu_chat);
        ButterKnife.bind(this);
        initViewUI();
        initWidget();
        initMessageListener();
        onConversationInit();
        onMessageListInit();
        setRefreshLayoutListener();
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //不需要的时候移除listener，如在activity的onDestroy()时
        ChatClient.getInstance().chatManager().removeMessageListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChatClient.getInstance().chatManager().unbindChat();
    }

    private void initWidget() {
        fragments = new ArrayList<>();
//      chatEmotionFragment = new ChatEmotionFragment();
//      fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        fragments.add(chatFunctionFragment);
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        //0表示语音状态
        emotionVoice.setTag(0);
        //0表示加号状态
        emotionAdd.setTag(0);
        EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(messageList)
                .bindToEditText(editText)
                .bindToEmotionButton(emotionButton)
                .bindToAddButton(emotionAdd)
                .bindToSendButton(emotionSend)
                .bindToVoiceButton(emotionVoice)
                .bindToVoiceText(voiceText)
                .build();
        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);
    }

    protected void initViewUI() {
        toChatUsername = LoginDataManager.getsInstance(this).getKeFuImNumber();
        // 消息列表layout
        messageList.setShowUserNick(true);
        listView = messageList.getListView();
        messageList.setOnTouchListener((v, event) -> {
            emotionLayout.setVisibility(View.GONE);
            return false;
        });
        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    protected void onConversationInit() {
        // 获取当前conversation对象
        conversation = ChatClient.getInstance().chatManager().getConversation(toChatUsername);
        if (conversation != null) {
            // 把此会话的未读数置为0
            conversation.markAllMessagesAsRead();
            final List<Message> msgs = conversation.getAllMessages();
            int msgCount = msgs != null ? msgs.size() : 0;
            if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
                String msgId = null;
                if (msgs != null && msgs.size() > 0) {
                    msgId = msgs.get(0).messageId();
                }
                conversation.loadMessages(msgId, pagesize - msgCount);
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    protected void onMessageListInit() {
        messageList.init(toChatUsername, null);
        //设置list item里的控件的点击事件
        setListItemClickListener();

//        messageList.getListView().setOnTouchListener((v, event) -> {
//            //录音时，点击列表不做操作
////            if (!inputMenu.isVoiceRecording()){
////                hideKeyboard();
////                inputMenu.hideExtendMenuContainer();
////            }
//            return false;
//        });
        isMessageListInited = true;

    }

    protected void setListItemClickListener() {
        messageList.setItemClickListener(new MessageList.MessageListItemClickListener() {

            /**
             * 用户头像点击事件
             * @param username
             */
            @Override
            public void onUserAvatarClick(String username) {

            }

            @Override
            public void onResendClick(final Message message) {
                ChatClient.getInstance().chatManager().resendMessage(message);
            }

            @Override
            public void onBubbleLongClick(Message message) {

            }

            @Override
            public boolean onBubbleClick(Message message) {

                return false;
            }
        });
    }

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (isFinishing()) {
                            return;
                        }
                        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
                            List<Message> messages = null;
                            try {
                                messages = conversation.loadMessages(messageList.getItem(0).messageId(),
                                        pagesize);
                            } catch (Exception e1) {
                                swipeRefreshLayout.setRefreshing(false);
                                return;
                            }
                            if (messages != null && messages.size() > 0) {
                                messageList.refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }

                            isloading = false;

                        } else {
                            ArmsUtils.makeText(KeFuChatActivity.this, getResources().getString(R.string.no_more_messages));
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }

    /**
     * 设置信息监听
     */
    private void initMessageListener() {
        ChatClient.getInstance().chatManager().bindChat(toChatUsername);
        ChatClient.getInstance().chatManager().addMessageListener(this);
        UIProvider.getInstance().pushActivity(this);
    }


    @Override
    public void onMessage(List<Message> list) {
        //收到普通消息
        Log.e("onMessage", list.toString());
        messageList.refreshSelectLast();
    }

    @Override
    public void onCmdMessage(List<Message> list) {
        //收到命令消息，命令消息不存数据库，一般用来作为系统通知，例如留言评论更新，
        //会话被客服接入，被转接，被关闭提醒
    }

    @Override
    public void onMessageStatusUpdate() {
        //消息的状态修改，一般可以用来刷新列表，显示最新的状态
        messageList.refreshSelectLast();
    }

    @Override
    public void onMessageSent() {
        //发送消息后，会调用，可以在此刷新列表，显示最新的消息
        messageList.refreshSelectLast();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick({R2.id.ib_left_action, R2.id.ib_right_title})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.ib_left_action) {
            finish();
        } else if (id == R.id.ib_right_title) {
            showClearHistoryDialog();
        }
    }

    /**
     *  弹出清除历史记录的对话框
     */
    private void showClearHistoryDialog() {
        HrzLayerView dialog = new HrzLayerView(this, STATE_DIALOG);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_normal, null);
        TextView contentTv = view.findViewById(R.id.tv_message);
        TextView cancelTv = view.findViewById(R.id.tv_right_action);
        TextView confirmTv = view.findViewById(R.id.tv_left_action);
        contentTv.setText(R.string.kefu_delete_dialog_content);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) contentTv.getLayoutParams();
        params.gravity = Gravity.CENTER;
        contentTv.setTextColor(Color.parseColor("#B2B2B2"));
        confirmTv.setText(R.string.kefu_delete_dialog_okay_text);
        cancelTv.setOnClickListener(v -> dialog.dismiss());
        confirmTv.setOnClickListener(v ->{
            MediaManager.release();
            ChatClient.getInstance().chatManager().clearConversation(toChatUsername);
            messageList.refresh();
            dialog.dismiss();
        });
        dialog.setDialogView(view);
        dialog.initLayerView(null);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // 发送照片
            if (requestCode == REQUEST_CODE_CAMERA) {
                sendImageMessage(cameraFilePath);
                // 发送本地图片
            } else if (requestCode == REQUEST_CODE_LOCAL) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_SELECT_FILE) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        sendFileByUri(uri);
                    }
                }
            } else if (requestCode == REQUEST_CODE_SELECT_VIDEO) {
                if (data != null) {
                    int duration = data.getIntExtra("dur", 0);
                    String videoPath = data.getStringExtra("path");
                    File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                        ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.close();
                        sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 根据图库图片uri发送图片
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(this, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(this, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }

    /**
     * 根据uri发送文件
     * @param uri
     */
    protected void sendFileByUri(Uri uri) {
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;

            try {
                cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (cursor != null){
                    cursor.close();
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        if (filePath == null){
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            ArmsUtils.makeText(this, getResources().getString(R.string.File_does_not_exist));
            return;
        }
        sendFileMessage(filePath);

    }

    /**
     * 发送图片
     * @param imagePath
     */
    protected void sendImageMessage(String imagePath) {
        if (TextUtils.isEmpty(imagePath)){
            return;
        }
        File imageFile = new File(imagePath);
        if (!imageFile.exists()){
            return;
        }

        Message message = Message.createImageSendMessage(imagePath, false, toChatUsername);
        //attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("sendImageMessage","sendImageMessage");
            }

            @Override
            public void onError(int code, String error) {
                Log.e("sendImageMessage","onError");
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.e("sendImageMessage","sendImageMessage");
            }
        });
        messageList.refreshSelectLastDelay(MessageList.defaultDelay);
    }

    /**
     * 发送文件
     * @param filePath
     */
    protected void sendFileMessage(String filePath) {
        Message message = Message.createFileSendMessage(filePath, toChatUsername);
        //attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
        messageList.refreshSelectLastDelay(MessageList.defaultDelay);
    }

    /**
     * 发送视频
     * @param videoPath
     * @param thumbPath
     * @param videoLength
     */
    protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        Message message = Message.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        //attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
        messageList.refreshSelectLastDelay(MessageList.defaultDelay);
    }
}
