package com.mujirenben.android.mine.mvp.contract;
import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.mvp.model.bean.AccountInfoBean;
import com.mujirenben.android.mine.mvp.model.bean.IdCardUploadBean;
import com.mujirenben.android.mine.mvp.model.bean.UpdateAccountBean;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface WithdrawDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 图片上传成功
         */
        void uploadIdPicOnSuccess(String picURL);

        /**
         * 图片上传失败
         */
        void uploadIdPicOnFail(String errorMsg);

        /**
         * 保存账户信息成功
         */
        void updateAccountOnSuccess();

        /**
         * 保存账户信息错误
         * @param errorMsg
         */
        void updateAccountOnFail(String errorMsg);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 保存账户信息
         * @param requestBody
         * @return
         */
        Observable<UpdateAccountBean> updateWithDrawInfo(RequestBody requestBody);

        /**
         * 上传身份证
         * @param part
         * @return
         */
        Observable<IdCardUploadBean> uploadIdCardPic(MultipartBody.Part part);
    }
}
