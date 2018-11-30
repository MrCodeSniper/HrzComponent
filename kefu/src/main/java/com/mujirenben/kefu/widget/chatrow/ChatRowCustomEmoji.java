package com.mujirenben.kefu.widget.chatrow;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.manager.EmojiconManager.EmojiconEntity;
import com.hyphenate.helpdesk.model.MessageHelper;
import com.hyphenate.util.EMLog;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.kefu.R;
import com.mujirenben.kefu.adapter.MessageAdapter;

import java.io.File;
import java.util.ArrayList;


/**
 *
 * @author tiancruyff
 * @date 2017/10/23
 */

public class ChatRowCustomEmoji extends ChatRow {

	protected ImageView imageView;
	protected String remoteUrl;
	protected EmojiconEntity emojiconEntity;

	public ChatRowCustomEmoji(Context context, Message message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflatView() {
		inflater.inflate(message.direct() == Message.Direct.RECEIVE ? R.layout.hd_row_received_custom_emoji : R.layout.hd_row_sent_custom_emoji, this);
	}

	@Override
	protected void onFindViewById() {
		percentageView = (TextView) findViewById(R.id.percentage);
		imageView = (ImageView) findViewById(R.id.image);
		imageView.setVisibility(GONE);
	}


	@Override
	protected void onSetUpView() {
		remoteUrl = MessageHelper.getCustomEmojiMessage(message);
		emojiconEntity = ChatClient.getInstance().emojiconManager().getEmojicon(remoteUrl);
		if (emojiconEntity == null) {
			return;
		}

		if (!TextUtils.isEmpty(emojiconEntity.origin.remoteUrl)) {
			File localOrigin = new File(emojiconEntity.origin.localUrl);
			if (localOrigin.exists()) {
				Glide.with(getContext()).load(emojiconEntity.origin.localUrl).into(imageView);
			} else {
				Glide.with(getContext()).load(emojiconEntity.origin.remoteUrl).into(imageView);
			}
		} else if (!TextUtils.isEmpty(emojiconEntity.thumbnail.remoteUrl)) {
			File localThumb = new File(emojiconEntity.thumbnail.localUrl);
			if (localThumb.exists()) {
				Glide.with(getContext()).load(emojiconEntity.thumbnail.localUrl).into(imageView);
			} else {
				Glide.with(getContext()).load(emojiconEntity.thumbnail.remoteUrl).into(imageView);
			}
		} else {
			EMLog.e(TAG, "emojiconEntity date wrong");
			return;
		}
		progressBar.setVisibility(View.GONE);
		percentageView.setVisibility(View.GONE);
		imageView.setVisibility(VISIBLE);
	}

	@Override
	protected void onUpdateView() {
		if (adapter instanceof MessageAdapter) {
			((MessageAdapter) adapter).refresh();
		} else {
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	protected void onBubbleClick() {
		if (emojiconEntity == null || TextUtils.isEmpty(emojiconEntity.origin.remoteUrl)) {
			return;
		}
		Bundle bundle = new Bundle();
		ArrayList<String> urlList = new ArrayList();
		urlList.add(emojiconEntity.origin.remoteUrl);
		bundle.putStringArrayList("image_urls", urlList);
		bundle.putInt("current_image_url_index",0);
		ARouter.getInstance().build(ARouterPaths.IMAGE_BROWSE_ACTIVITY)
                .withBundle("fromKeFu",bundle).navigation(context);
	}
}


