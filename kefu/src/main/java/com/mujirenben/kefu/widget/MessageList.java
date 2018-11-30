package com.mujirenben.kefu.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.Message;
import com.mujirenben.android.kefu.R;
import com.mujirenben.android.kefu.R2;
import com.mujirenben.kefu.adapter.MessageAdapter;
import com.mujirenben.kefu.provider.CustomChatRowProvider;

public class MessageList extends RelativeLayout {
    protected static final String TAG = MessageList.class.getSimpleName();
    protected ListView listView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected Context context;
    protected Conversation conversation;
    protected String toChatUsername;
    protected MessageAdapter messageAdapter;
    protected boolean showKeFuNick;
    protected boolean showUserAvatar;
    protected boolean showKeFuAvatar;
    protected boolean showUserNick;
    protected Drawable myBubbleBg;
    protected Drawable otherBuddleBg;
    public static long defaultDelay = 200;

    public MessageList(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public MessageList(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseStyle(context, attrs);
        init(context);
    }

    public MessageList(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.hd_chat_message_list, this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.chat_swipe_layout);
        listView = (ListView) findViewById(R.id.list);
    }

    /**
     * init widget
     * @param toChatUsername
     * @param customChatRowProvider
     */
    public void init(String toChatUsername, CustomChatRowProvider customChatRowProvider) {
        this.toChatUsername = toChatUsername;

        conversation = ChatClient.getInstance().chatManager().getConversation(toChatUsername);
        messageAdapter = new MessageAdapter(context, toChatUsername, listView);
        messageAdapter.showKeFuAvatar(showKeFuAvatar);
        messageAdapter.showKeFuNick(showKeFuNick);
        messageAdapter.showUserAvatar(showUserAvatar);
        messageAdapter.showUserNick(showUserNick);
        messageAdapter.setMyBubbleBg(myBubbleBg);
        messageAdapter.setOtherBuddleBg(otherBuddleBg);
        messageAdapter.setCustomChatRowProvider(customChatRowProvider);
        // 设置adapter显示消息
        listView.setAdapter(messageAdapter);

        refreshSelectLast();
    }

    protected void parseStyle(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseChatMessageList);
        showUserAvatar = ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowUserAvatar, true);
        showUserNick = ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowUserNick, true);
        showKeFuAvatar = ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowKeFuAvatar, true);
        showKeFuNick =  ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowKeFuNick, true);
        myBubbleBg = ta.getDrawable(R.styleable.EaseChatMessageList_msgListMyBubbleBackground);
        otherBuddleBg = ta.getDrawable(R.styleable.EaseChatMessageList_msgListMyBubbleBackground);

        ta.recycle();
    }


    /**
     * 刷新列表
     */
    public void refresh(){
        if (messageAdapter != null) {
            messageAdapter.refresh();
        }
    }

    /**
     * 刷新列表，并且跳至最后一个item
     */
    public void refreshSelectLast(){
        if (messageAdapter != null) {
            messageAdapter.refreshSelectLast();
        }
    }

    public void refreshSelectLastDelay(long delay){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (messageAdapter != null) {
                    messageAdapter.refreshSelectLast();
                }
            }
        }, delay);
    }

    /**
     * 刷新页面,并跳至给定position
     * @param position
     */
    public void refreshSeekTo(int position){
        if (messageAdapter != null) {
            messageAdapter.refreshSeekTo(position);
        }
    }

    /**
     * 获取listview
     * @return
     */
    public ListView getListView() {
        return listView;
    }

    /**
     * 获取SwipeRefreshLayout
     * @return
     */
    public SwipeRefreshLayout getSwipeRefreshLayout(){
        return swipeRefreshLayout;
    }

    public Message getItem(int position){
        return messageAdapter.getItem(position);
    }

    /**
     * 设置是否显示用户昵称
     * @param showUserNick
     */
    public void setShowUserNick(boolean showUserNick){
        this.showUserNick = showUserNick;
    }

    public boolean isShowUserNick(){
        return showUserNick;
    }

    public interface MessageListItemClickListener{
        void onResendClick(Message message);
        /**
         * 控件有对气泡做点击事件默认实现，如果需要自己实现，return true。
         * 当然也可以在相应的chatrow的onBubbleClick()方法里实现点击事件
         * @param message
         * @return
         */
        boolean onBubbleClick(Message message);
        void onBubbleLongClick(Message message);
        void onUserAvatarClick(String username);
    }

    /**
     * 设置list item里控件的点击事件
     * @param listener
     */
    public void setItemClickListener(MessageListItemClickListener listener){
        if (messageAdapter != null) {
            messageAdapter.setItemClickListener(listener);
        }
    }

    /**
     * 设置自定义chatrow提供者
     * @param rowProvider
     */
    public void setCustomChatRowProvider(CustomChatRowProvider rowProvider){
        if (messageAdapter != null) {
            messageAdapter.setCustomChatRowProvider(rowProvider);
        }
    }
}