package com.mujirenben.android.vip.mvp.contract;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.FanListBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;

public interface MyFanContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
       void showFanInfo(FanHeaderInfo headerInfo);
       void showFanList(List<FanListBean.DataBean> dataList);
       void fanInfoException(String msg);
       void fanListNetworkException(String msg);
       void fanListDataErrorLayout(String msg);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<FanHeaderInfo> getMyFanInfo(RequestBody requestBody);
        Observable<FanListBean> getMyFanList(RequestBody requestBody);
    }
}
