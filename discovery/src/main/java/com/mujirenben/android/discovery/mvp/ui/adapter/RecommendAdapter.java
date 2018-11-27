package com.mujirenben.android.discovery.mvp.ui.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.misc.KoulingHelper;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.CommonUtils;
import com.mujirenben.android.common.util.GoodsUtil;
import com.mujirenben.android.common.util.sensorHelper.SensorHelper;
import com.mujirenben.android.common.util.wxHelper.ShareDialogHelper;
import com.mujirenben.android.common.util.wxHelper.WeiXinHelper;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickLinkListener;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickSessionListener;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickTimeLineListener;
import com.mujirenben.android.common.widget.DialogUtils;

import com.mujirenben.android.discovery.R;
import com.mujirenben.android.discovery.mvp.model.entity.DiscoveryBean;
import com.mujirenben.android.discovery.mvp.presenter.DiscoveryPresenter;
import com.mujirenben.android.discovery.mvp.ui.activity.ImageBrowseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecommendAdapter extends BaseQuickAdapter<DiscoveryBean.Data, BaseViewHolder> {

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
    private ArrayList<String> imagePathUrls;

    public RecommendAdapter(Context context, int layoutResId,
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
                (ViewGroup.MarginLayoutParams) helper.itemView.getLayoutParams();
        int pos = helper.getAdapterPosition();
        if (pos != 0) {
            params.topMargin = mItemsGapPx;
        } else {
            params.topMargin = 0;
        }
        helper.itemView.setLayoutParams(params);

//        Glide.with(helper.itemView)
//                .load("http://img5.duitang.com/uploads/item/201508/09/20150809005334_rxVJH.jpeg")
//                .into((ImageView)helper.getView(R.id.iv_portrait));

        if (item.getProduct() == null) {
            helper.getView(R.id.rl_goods).setVisibility(View.GONE);
        } else {
            Glide.with(helper.itemView).load(item.getProduct().getImg()).into((ImageView) helper.getView(R.id.iv_goods_image));

            ((TextView) helper.getView(R.id.tv_content)).setText(item.getContent());
            ((TextView) helper.getView(R.id.tv_item_action)).setText("分享￥" + CommonUtils.takeOutLastZero(item.getProduct().getShareProfit()));
            ((TextView) helper.getView(R.id.tv_goods_name)).setText(item.getProduct().getName());
            ((TextView) helper.getView(R.id.tv_platform)).setText(GoodsUtil.getPlatformById(item.getProduct().getPlatform()));
            ((TextView) helper.getView(R.id.tv_coupon)).setText("￥" + CommonUtils.takeOutLastZero(item.getProduct().getCoupon()));
            ((TextView) helper.getView(R.id.tv_publish_time)).setText(formatTimestamp(item.getCreateTime() * 1000));

            ImageView iv_platforms_icon = helper.getView(R.id.iv_platforms_icon);
            initPlatformTag(item.getProduct().getPlatform() + "", iv_platforms_icon);


            if (Float.compare(item.getProduct().getCoupon(), 0.0f) == 0) {
                helper.getView(R.id.ll_coupon).setVisibility(View.GONE);
                ((TextView) helper.getView(R.id.tv_after_price)).setText("￥" + CommonUtils.takeOutLastZero(item.getProduct().getAfterPrice()));
            } else {
                helper.getView(R.id.ll_coupon).setVisibility(View.VISIBLE);
                ((TextView) helper.getView(R.id.tv_after_price)).setText("券后￥" + CommonUtils.takeOutLastZero(item.getProduct().getAfterPrice()));
            }

            helper.getView(R.id.rl_goods).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Consts.GOODS_ID_INTENT_STR, item.getProduct().getProductId() + "");
                    bundle.putString(Consts.PLATFORM_ID_INTENT_STR, item.getProduct().getPlatform() + "");
                    bundle.putString(Consts.ROUTER_FROM, "推荐位");
                    ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL)
                            .withBundle(Consts.HRZ_ROUTER_BUNDLE, bundle)
                            .navigation(mContext);

                    HashMap<String, Object> sensorMap = new HashMap<>();
                    sensorMap.put("name", "商品");
                    SensorHelper.uploadTrack("FindClick", sensorMap);
                }
            });

            helper.setOnClickListener(R.id.tv_item_action_ll, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isLogin = LoginDataManager.getsInstance(mContext).isLogin();
                    if (!isLogin) { // 进入登录页面
                        ARouter.getInstance()
                                .build(ARouterPaths.LOGIN_MAIN_MINE)
                                .withString(Consts.LOGIN_SOURCE_KEY, "商品分享")
                                .navigation(mContext);
                        return;
                    }

                    share(item);

                    HashMap<String, Object> sensorMap = new HashMap<>();
                    sensorMap.put("ID", item.getId());
                    sensorMap.put("category", "爆款");
                    SensorHelper.uploadTrack("FindShare", sensorMap);
                }
            });
        }

        for (int i = 0; i < mImageIds.length; i++) {
            ImageView iv = helper.getView(mImageIds[i]);

            // 动态设置各个ImageView的大小和间距
            ViewGroup.MarginLayoutParams ivParams = (ViewGroup.MarginLayoutParams) iv.getLayoutParams();
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

    private void generateKouling(DiscoveryBean.Product product, final KoulingHelper.Callback callback) {

        final DialogUtils loadingDialog = new DialogUtils(mContext, R.layout.common_loading_toast, "");
        loadingDialog.show();

        KoulingHelper koulingHelper = KoulingHelper.get(mContext);
        koulingHelper.generateKouling(product.getProductId(), product.getPlatformId(), product.getIdType(),
                product.getPlatform(),
                new KoulingHelper.Callback() {
                    @Override
                    public void onKoulingGenerated(String kouling, String shareUrl) {
                        loadingDialog.hide();

                        if (callback != null) {
                            callback.onKoulingGenerated(kouling, shareUrl);
                        }
                    }

                    @Override
                    public void onKoulingFailed() {
                        loadingDialog.hide();
                    }
                });
    }

    private void share(final DiscoveryBean.Data item) {
        if (item == null || item.getProduct() == null) return;

        mPresenter.clickShare(item.getId());
        imagePathUrls = new ArrayList<>(item.getImgs());
        if(imagePathUrls.size() == 0){
            String coverURL = item.getProduct().getImg();
            imagePathUrls.add(coverURL);
        }
        ShareDialogHelper.getBuilder(mContext)
                .setDialogTitle("标题")
                .setDialogContent("内容")
                .setOnLinkListener(new OnClickLinkListener() {
                    @Override
                    public void onClick() {
                        generateKouling(item.getProduct(), new KoulingHelper.Callback() {
                            @Override
                            public void onKoulingGenerated(String kouling, String shareUrl) {
                                final ClipboardManager cm = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData data = ClipData.newPlainText(null, kouling);
                                if (cm != null) {
                                    cm.setPrimaryClip(data);
                                }
                                ArmsUtils.makeText(mContext, "复制成功");
                            }
                        });
                    }
                })
                .setOnSessionListener(new OnClickSessionListener() {
                      @Override
                      public void onClick() {
                            generateKouling(item.getProduct(), new KoulingHelper.Callback() {
                                @Override
                                public void onKoulingGenerated(String kouling, String shareUrl) {

                                    if (item.getProduct().getPlatform() == Const.Platform.JD) {
                                        WeiXinHelper.getBuilder(mContext)
                                                .setWebPageUrl(kouling)
                                                .setTitle(item.getProduct().getName())
                                                .setWebPageThumbPath(imagePathUrls.get(0))
                                                .setDescription(mContext.getString(R.string.jd_share_text))
                                                .build()
                                                .shareWebPageTo(WeiXinHelper.ShareToType.SESSION);
                                        return;
                                    }

                                    ARouter.getInstance().build(ARouterPaths.GOODS_SHARE)
                                            .withInt("type", 0)
                                            .withBoolean("joint_share_style", true)
                                            .withString("copy_writing", item.getContent())
                                            .withString("taokouling", kouling)
                                            .withStringArrayList("image_urls", imagePathUrls)
                                            .withFloat("commission", item.getProduct().getShareProfit())
                                            .withString("label", GoodsUtil.getPlatformById(item.getProduct().getPlatform()))
                                            .withString("goods_name", item.getProduct().getName())
                                            .withFloat("pre_price", item.getProduct().getOrgPrice())
                                            .withFloat("after_price", item.getProduct().getAfterPrice())
                                            .withFloat("coupons", item.getProduct().getCoupon())
                                            .withString("qr_code_url", shareUrl)
                                            .withFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                            .navigation(mContext);
                                }
                            });
                      }
                 })
                .setOnTimeLineListener( new OnClickTimeLineListener() {
                        @Override
                        public void onClick() {
                            generateKouling(item.getProduct(), new KoulingHelper.Callback() {
                                @Override
                                public void onKoulingGenerated(String kouling, String shareUrl) {

                                    if (item.getProduct().getPlatform() == Const.Platform.JD) {
                                        WeiXinHelper.getBuilder(mContext)
                                                .setWebPageUrl(kouling)
                                                .setTitle(item.getProduct().getName())
                                                .setWebPageThumbPath(imagePathUrls.get(0))
                                                .setDescription(mContext.getString(R.string.jd_share_text))
                                                .build()
                                                .shareWebPageTo(WeiXinHelper.ShareToType.TIMELINE);
                                        return;
                                    }

                                    ARouter.getInstance().build(ARouterPaths.GOODS_SHARE)
                                            .withInt("type", 1)
                                            .withBoolean("joint_share_style", true)
                                            .withString("copy_writing", item.getContent())
                                            .withString("taokouling", kouling)
                                            .withStringArrayList("image_urls", imagePathUrls)
                                            .withFloat("commission", item.getProduct().getShareProfit())
                                            .withString("label", GoodsUtil.getPlatformById(item.getProduct().getPlatform()))
                                            .withString("goods_name", item.getProduct().getName())
                                            .withFloat("pre_price", item.getProduct().getOrgPrice())
                                            .withFloat("after_price", item.getProduct().getAfterPrice())
                                            .withFloat("coupons", item.getProduct().getCoupon())
                                            .withString("qr_code_url", shareUrl)
                                            .withFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                            .navigation(mContext);
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


    /**
     * 根据平台设置tag图片
     *
     * @param platform
     */
    private void initPlatformTag(String platform, ImageView iv_platform_icon) {
        if (TextUtils.equals(platform, Const.Platform.HRZ + "")) {
            iv_platform_icon.setImageResource(R.drawable.icon_ziying);
        } else if (TextUtils.equals(platform, Const.Platform.TAOBAO + "")) {
            iv_platform_icon.setImageResource(R.drawable.icon_taobao);
        } else if (TextUtils.equals(platform, Const.Platform.JD + "")) {
            iv_platform_icon.setImageResource(R.drawable.icon_jingdong);
        } else if (TextUtils.equals(platform, Const.Platform.VIP + "")) {
            iv_platform_icon.setImageResource(R.drawable.icon_weipinhui);
        } else if (TextUtils.equals(platform, Const.Platform.MGJ + "")) {
            iv_platform_icon.setImageResource(R.drawable.icon_mogujie);
        }
    }

}
