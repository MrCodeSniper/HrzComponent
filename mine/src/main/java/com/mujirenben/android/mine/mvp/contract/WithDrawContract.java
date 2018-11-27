package com.mujirenben.android.mine.mvp.contract;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.mvp.model.bean.AccountInfoBean;
import com.mujirenben.android.mine.mvp.model.bean.LockFanBean;
import com.mujirenben.android.mine.mvp.model.bean.VerifyCodeBean;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawResultBean;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author: panrongfu
 * @date: 2018/8/6 15:47
 * @describe:
 */

public interface WithDrawContract {

    interface View extends IView {
        /**
         * 账户信息
         * @param accountInfoBean
         */
       void showAccountInfo(AccountInfoBean.DataBean accountInfoBean);

        /**
         * 获取账户信息错误
         * @param errorMsg
         */
       void showAccountInfoError(String errorMsg);

        /**
         * 申请提现成功
         */
        void withdrawOnSuccess();

        /**
         * 申请提现失败
         * @param errorMsg
         */
        void withdrawOnFail(String errorMsg);

        /**
         * 锁粉成功
         * @param msg
         */
        void lockFanOnSuccess(String msg);

        /**
         * 锁粉失败
         * @param msg
         */
        void lockFanOnFail(String msg);

        /**
         * 发送验证码成功
         * @param msg
         */
        void verifyCodeOnSuccess(VerifyCodeBean msg);

        /**
         * 发送验证码失败
         * @param smg
         */
        void verifyCodeOnFail(String smg);

//        /**
//         * 验真码验证成功
//         * @param msg
//         */
//        void vCodeVerifyOnSuccess(String msg);
//
//        /**
//         * 验证码验证失败
//         * @param msg
//         */
//        void vCodeVerifyOnFail(String msg);

    }

    interface Model extends IModel {
        /**
         * 获取账户信息
         * @param map
         * @return
         */
        Observable<AccountInfoBean> getWithdrawInfo(HashMap<String, String> map);

        /**
         * 发起提现
         * @param requestBody
         * @return
         */
        Observable<WithdrawResultBean> applyWithdraw(RequestBody requestBody);


        /**
         * 锁粉
         * @param requestBody
         * @return
         */
        Observable<LockFanBean> applyLockFan(RequestBody requestBody);


        /**
         * 发送手机验证码
         * @param map
         * @return
         */

        Observable<VerifyCodeBean> sendPhoneVerify(HashMap<String, String> map);


        /**
         * 验证手机验证码
         * @param requestBody
         * @return
         */
        Observable<Object> verifyVcode(RequestBody requestBody);
    }
}
