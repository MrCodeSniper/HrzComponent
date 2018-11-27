package com.mujirenben.android.vip.mvp.ui.fragment.state;

import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;
import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;

import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/7/17 10:39
 * @describe: 用户状态管理
 */

public class UserStateManager {

    private UserState state;

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public void showUniqueInfoLayout(FanHeaderInfo vipInfo){
        state.showUniqueInfoLayout(vipInfo);
    }
    public void showAnnounceLayout(List<NoticeBean.DataBean> noticeList){
        state.intAnnounceLayout(noticeList);
    }
    public void showBannerLayout(VipBannerBean bannerBean){
        state.showBannerLayout(bannerBean);
    }
    /**
     * 页面状态变化可见,不可见
     * @param loginViewHidden
     * @param unLoginViewHidden
     */
    public void onHiddenChanged(boolean loginViewHidden,boolean unLoginViewHidden){
        state.onHiddenChanged(loginViewHidden, unLoginViewHidden);
    }
}
