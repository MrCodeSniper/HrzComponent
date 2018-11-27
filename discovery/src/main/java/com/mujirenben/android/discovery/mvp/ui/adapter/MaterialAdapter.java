package com.mujirenben.android.discovery.mvp.ui.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.sensorHelper.SensorHelper;
import com.mujirenben.android.common.util.wxHelper.ShareDialogHelper;
import com.mujirenben.android.common.util.wxHelper.WeiXinHelper;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickLinkListener;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickSessionListener;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickTimeLineListener;
import com.mujirenben.android.discovery.R;
import com.mujirenben.android.discovery.mvp.model.entity.DiscoveryBean;
import com.mujirenben.android.discovery.mvp.presenter.DiscoveryPresenter;
import com.mujirenben.android.discovery.mvp.ui.activity.ImageBrowseActivity;
import com.mujirenben.android.discovery.mvp.ui.fragment.DiscoveryFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MaterialAdapter extends BaseQuickAdapter<DiscoveryBean.Data, BaseViewHolder> {

    private List<DiscoveryBean.Data> mData;
    private int mItemsGapPx = 24;
    private int mScreenW;
    private int mItemHorizontalPadding;
    private int mItemImageGutter;

    private DiscoveryPresenter mPresenter;

    private int[] mImageIds = {
            R.id.iv_image_1,
            R.id.iv_image_2,
            R.id.iv_image_3,
            R.id.iv_image_4,
            R.id.iv_image_5,
            R.id.iv_image_6,
            R.id.iv_image_7,
            R.id.iv_image_8,
            R.id.iv_image_9,
    };

    public MaterialAdapter(Context context, int layoutResId,
                           @Nullable List<DiscoveryBean.Data> data,
                           DiscoveryPresenter presenter) {
        super(layoutResId, data);
        mData = data;
        mPresenter = presenter;

        Resources res = context.getResources();
        mItemsGapPx = res.getDimensionPixelSize(R.dimen.discovery_items_gap);
        mScreenW = res.getDisplayMetrics().widthPixels;
        mItemHorizontalPadding = res.getDimensionPixelSize(R.dimen.discovery_items_horizontal_padding);
        mItemImageGutter = res.getDimensionPixelSize(R.dimen.discovery_items_image_gutter);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        return viewHolder;
    }

    @Override
    protected void convert(BaseViewHolder helper, final DiscoveryBean.Data item) {
        // 调整每个item的上间距
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams)helper.itemView.getLayoutParams();
        int pos = helper.getAdapterPosition();
        if (pos != 0) {
            params.topMargin = mItemsGapPx;
        } else {
            params.topMargin = 0;
        }
        helper.itemView.setLayoutParams(params);

        // 隐藏商品的布局
        helper.getView(R.id.rl_goods).setVisibility(View.GONE);

//        Glide.with(helper.itemView)
//                .load("http://img5.duitang.com/uploads/item/201508/09/20150809005334_rxVJH.jpeg")
//                .into((ImageView)helper.getView(R.id.iv_portrait));
        ((TextView)helper.getView(R.id.tv_content)).setText(item.getContent());
        ((TextView)helper.getView(R.id.tv_publish_time)).setText(formatTimestamp(item.getCreateTime() * 1000));

        helper.setText(R.id.tv_item_action, R.string.material_item_action_name);
        helper.setOnClickListener(R.id.tv_item_action_ll, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(item);

                HashMap<String,Object> sensorMap = new HashMap<>();
                sensorMap.put("ID", item.getId());
                sensorMap.put("category", "素材");
                SensorHelper.uploadTrack("FindShare", sensorMap);
            }
        });

        for (int i = 0; i < mImageIds.length; i++) {
            ImageView iv = helper.getView(mImageIds[i]);

            // 动态设置各个ImageView的大小和间距
            ViewGroup.MarginLayoutParams ivParams = (ViewGroup.MarginLayoutParams)iv.getLayoutParams();
            int size = (mScreenW - 2 * mItemHorizontalPadding - 2 * mItemImageGutter) / 3;
            ivParams.width = ivParams.height = size;
            if (i % 3 == 0) { // 最左边的一列
                ivParams.leftMargin = 0;
            } else {
                ivParams.leftMargin = mItemImageGutter;
            }
            ivParams.topMargin = mItemImageGutter;
            ivParams.bottomMargin = 0;
            ivParams.rightMargin = 0;
            iv.setLayoutParams(ivParams);

            final ArrayList<String> imageUrls = new ArrayList<>(item.getImgs());

            if (imageUrls != null && i < imageUrls.size()) {
                iv.setVisibility(View.VISIBLE);
                Glide.with(helper.itemView).load(imageUrls.get(i)).into(iv);

                final int currentIndex = i;

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageBrowseActivity.launchImageBrowserActivity(mContext, imageUrls, currentIndex);
                    }
                });
            } else {
                iv.setVisibility(View.GONE);
            }
        }
    }

    private void share(final DiscoveryBean.Data item) {
        mPresenter.clickShare(item.getId());

        final String[] uris = new String[item.getImgs().size()];
        for (int i = 0; i < item.getImgs().size(); i++) {
            uris[i] = item.getImgs().get(i);
        }

        final ClipboardManager cm = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData data = ClipData.newPlainText(null, item.getContent());
        if (cm != null) {
            cm.setPrimaryClip(data);
        }

        ShareDialogHelper.getBuilder(mContext)
                .setDialogTitle("标题")
                .setDialogContent("内容")
                .setOnLinkListener(null)
                .setOnSessionListener(new OnClickSessionListener() {
                    @Override
                    public void onClick() {
                        jointShareStyleWithPermissionRequest(new Runnable() {
                            @Override
                            public void run() {

                                if(uris == null || uris.length == 0 ){
                                    //没有图片，则分享文字
                                    WeiXinHelper.getBuilder(mContext)
                                            .setTitle("" + item.getId())
                                            .setDescription(item.getContent())
                                            .build()
                                            .shareTextTo(WeiXinHelper.ShareToType.SESSION);
                                }else {
                                    //有图片，则分享文字+图片
                                    WeiXinHelper.getBuilder(mContext)
                                            .setTitle("" + item.getId())
                                            .setPictureThumbPaths(uris)
                                            .setDescription(item.getContent())
                                            .build()
                                            .sharePictureTo(WeiXinHelper.ShareToType.SESSION);
                                    ArmsUtils.makeText(mContext, "已为您复制宣传文案，记得复制粘贴哦");
                                }
                            }
                        });
                    }
                })
                .setOnTimeLineListener(new OnClickTimeLineListener() {
                    @Override
                    public void onClick() {
                        jointShareStyleWithPermissionRequest(new Runnable() {
                            @Override
                            public void run() {
                                if(uris == null || uris.length == 0 ){
                                    WeiXinHelper.getBuilder(mContext)
                                            .setTitle("" + item.getId())
                                            .setDescription(item.getContent())
                                            .build()
                                            .shareTextTo(WeiXinHelper.ShareToType.TIMELINE);
                                }else {
                                    WeiXinHelper.getBuilder(mContext)
                                            .setTitle("" + item.getId())
                                            .setPictureThumbPaths(uris)
                                            .setDescription(item.getContent())
                                            .build()
                                            .sharePictureTo(WeiXinHelper.ShareToType.TIMELINE);
                                    ArmsUtils.makeText(mContext, "已为您复制宣传文案，记得复制粘贴哦");
                                }

                            }
                        });
                    }
                })
                .build()
                .showDialog(false);
    }

    private String formatTimestamp(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(timestamp));
    }

    private void jointShareStyleWithPermissionRequest(final Runnable task) {
        RxPermissions rxPermissions = new RxPermissions((Activity)mContext);
        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean result) throws Exception {
                if (result) {
                    task.run();
                } else {
                    ArmsUtils.makeText(mContext, "未授权权限，部分功能不能使用");
                }
            }
        });
    }
}
