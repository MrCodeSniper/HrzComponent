package com.mujirenben.kefu.widget.chatrow;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.Message;
import com.hyphenate.util.DensityUtil;
import com.hyphenate.util.ImageUtils;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.kefu.ImageCache;
import com.mujirenben.android.kefu.R;
import com.mujirenben.kefu.adapter.MessageAdapter;
import com.mujirenben.kefu.util.KeFuUtils;

import java.io.File;
import java.util.ArrayList;

public class ChatRowImage extends ChatRowFile {

    protected ImageView imageView;
    private EMImageMessageBody imgBody;

    public ChatRowImage(Context context, Message message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct() == Message.Direct.RECEIVE ? R.layout.hd_row_received_picture : R.layout.hd_row_sent_picture, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.percentage);
        imageView = (ImageView) findViewById(R.id.image);
    }


    @Override
    protected void onSetUpView() {
        imgBody = (EMImageMessageBody) message.body();
        // 接收方向的消息
        if (message.direct() == Message.Direct.RECEIVE) {
            if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                    imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
                imageView.setImageResource(R.drawable.hd_default_image);
                setMessageReceiveCallback();
            } else {
                progressBar.setVisibility(View.GONE);
                percentageView.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.hd_default_image);
                String thumbPath = imgBody.thumbnailLocalPath();
                if (!new File(thumbPath).exists()) {
                    // 兼容旧版SDK收到的thumbnail
                    thumbPath = KeFuUtils.getThumbnailImagePath(imgBody.getLocalUrl());
                }
                showImageView(thumbPath, imageView, imgBody.getLocalUrl(), message);
            }
            return;
        }

        String filePath = imgBody.getLocalUrl();
        if (filePath != null) {
            showImageView(KeFuUtils.getThumbnailImagePath(filePath), imageView, filePath, message);
        }
        handleSendMessage();
    }

    @Override
    protected void onUpdateView() {
        //super.onUpdateView();
        if (adapter instanceof MessageAdapter) {
            ((MessageAdapter) adapter).refreshSelectLast();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onBubbleClick() {
        String imagePath = "";
        if(message.direct() == Message.Direct.RECEIVE){
            imagePath = imgBody.getRemoteUrl();
        }else if(message.direct() == Message.Direct.SEND){
            imagePath = imgBody.getLocalUrl();
        }
        Bundle bundle = new Bundle();
        ArrayList<String> urlList = new ArrayList();
        urlList.add(imagePath);
        bundle.putStringArrayList("image_urls", urlList);
        bundle.putInt("current_image_url_index",0);
        ARouter.getInstance()
                .build(ARouterPaths.IMAGE_BROWSE_ACTIVITY)
                .withBundle("fromOther",bundle)
                .navigation(context);

//        Intent intent = new Intent(context, ShowBigImageActivity.class);
//        File file = new File(imgBody.getLocalUrl());
//        if (file.exists()) {
//            Uri uri = Uri.fromFile(file);
//            intent.putExtra("uri", uri);
//        } else {
//            // The local full size pic does not exist yet.
//            // ShowBigImage needs to download it from the server
//            // first
//            intent.putExtra("messageId", message.messageId());
//            intent.putExtra("localUrl", imgBody.getLocalUrl());
//        }
//        context.startActivity(intent);
    }

    /**
     * load image into image view
     *
     * @param thumbernailPath
     * @param iv
     * @param localFullSizePath
     * @return the image exists or not
     */
    private boolean showImageView(final String thumbernailPath, final ImageView iv, final String localFullSizePath, final Message message) {
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = ImageCache.getInstance().get(thumbernailPath);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            iv.setImageBitmap(bitmap);
            return true;
        } else {
            final int width = DensityUtil.dip2px(getContext(), 70);
            new AsyncTask<Object, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Object... args) {
                    File file = new File(thumbernailPath);
                    if (file.exists()) {
                        return ImageUtils.decodeScaleImage(thumbernailPath, width, width);
                    } else {
                        if (message.direct() == Message.Direct.SEND) {
                            if (localFullSizePath != null && new File(localFullSizePath).exists()) {
                                return ImageUtils.decodeScaleImage(localFullSizePath, width, width);
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    }
                }

                protected void onPostExecute(Bitmap image) {
                    if (image != null) {
                        iv.setImageBitmap(image);
                        ImageCache.getInstance().put(thumbernailPath, image);
                    } else {
                        EMImageMessageBody imageBody = (EMImageMessageBody) message.body();
                        if (imageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING
                                || imageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {

                        } else {
                            if (KeFuUtils.isNetWorkConnected(activity)) {
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        ChatClient.getInstance().chatManager().downloadThumbnail(message);
                                    }
                                }).start();
                            }
                        }
                    }
                }
            }.execute();

            return true;
        }
    }

}