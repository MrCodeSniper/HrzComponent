package com.mujirenben.android.vip.mvp.contract;
import com.mujirenben.android.common.entity.SearchResult;
import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;
import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface VipContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void showVipInfo(FanHeaderInfo headerInfo);//会员信息
        void vipInfoException(String msg);
        void showGoods(SearchResult data);
        void searchResultNodata();

        /**
         * 轮播图获取成功
         * @param bannerBean
         */
        void vipBannerOnSuccess(VipBannerBean bannerBean);

        /**
         * 轮播图获取成功
         * @param msg
         */
        void vipBannerOnFail(String msg);

        /**
         * 公告列表获取成功
         * @param noticeBean
         */
        void noticeListOnSuccess(List<NoticeBean.DataBean> noticeBean);

        /**
         * 公告列表获取失败
         * @param msg
         */
        void noticeListOnFail(String msg);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取vip信息
         * @param requestBody
         * @return
         */
        Observable<FanHeaderInfo> getMyFanInfo(RequestBody requestBody);


        /**
         * 获取轮播图
         * @param requestBody
         * @return
         */
        Observable<VipBannerBean> getVipBanner(RequestBody requestBody);

        /**
         * 获取公告数据
         * @param map
         * @return
         */
        Observable<NoticeBean> getNoticeList(HashMap<String, String> map);

        Observable<SearchResult> getSearchGoods(Map<String, String> params);
    }
}
