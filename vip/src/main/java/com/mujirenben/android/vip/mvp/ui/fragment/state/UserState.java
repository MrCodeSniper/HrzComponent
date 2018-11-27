package com.mujirenben.android.vip.mvp.ui.fragment.state;

import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;


import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/7/17 9:38
 * @describe: 状态模式，用户登录和未登录两种状态，通过控制这种状态，改变vip的布局
 */
public interface UserState {

    /**
     * 展示会员个人信息
     * 1）会员已登录
     * 2）会员未登录
     * @param fanHeaderInfo vip信息对象
     */
    void showUniqueInfoLayout(FanHeaderInfo fanHeaderInfo);

    /**
     * 页面状态
     * @param loginViewHidden
     * @param unLoginViewHidden
     */
    void onHiddenChanged(boolean loginViewHidden, boolean unLoginViewHidden);

    /**
     * 公告栏
     * @param noticeList
     */
    void intAnnounceLayout(List<NoticeBean.DataBean> noticeList);

    /**
     * 轮播图
     * @param bannerBean
     */
    void showBannerLayout(VipBannerBean bannerBean);
}
