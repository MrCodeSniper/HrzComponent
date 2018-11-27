package com.mujirenben.android.mine.mvp.model.service;

import com.mujirenben.android.mine.mvp.model.bean.AccountInfoBean;
import com.mujirenben.android.mine.mvp.model.bean.IdCardUploadBean;
import com.mujirenben.android.mine.mvp.model.bean.LockFanBean;
import com.mujirenben.android.mine.mvp.model.bean.UpdateAccountBean;
import com.mujirenben.android.mine.mvp.model.bean.VerifyCodeBean;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawListBean;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawResultBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author: panrongfu
 * @date: 2018/8/6 16:16
 * @describe:
 */

public interface WithdrawService {

    /**
     * 获取账户信息
     * @return
     */
     @GET("/hrz/api/wallet/withdraw/info")
     @Headers({"Domain-Name:mock_app_withdraw_info"})// 加上 Domain-Name header
     Observable<AccountInfoBean> getWithdrawInfo(@QueryMap Map<String, String> map);

    @Multipart
    @POST("/hrz/rpc/common/img/upload")
    @Headers({"Domain-Name:mock_upload_file"})// 加上 Domain-Name header
    Observable<IdCardUploadBean> uploadIdCardPic(@Part MultipartBody.Part part);

    /**
     * 更新账户信息
     * @return
     */
    @POST("/hrz/api/wallet/update/withdraw/info")
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json",
            "Domain-Name:mock_app_update_withdraw_info"})// 加上 Domain-Name header
     Observable<UpdateAccountBean> updateWithDrawInfo(@Body RequestBody requestBody);

    /**
     * 申请提现
     * @return
     */
    @POST("/hrz/api/wallet/withdraw")
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json",
            "Domain-Name:mock_app_withdraw"})// 加上 Domain-Name header
     Observable<WithdrawResultBean> applyWithdraw(@Body RequestBody requestBody);

    /**
     * 提现列表
     * @param map
     * @return
     */
    @GET("/hrz/api/wallet/withdraw/list")
    @Headers({"Domain-Name:mock_app_withdraw_Record"})// 加上 Domain-Name header
    Observable<WithdrawListBean> getWithdrawRecord(@QueryMap HashMap<String, String> map);

    /**
     * 绑定邀请人
     * @param requestBody
     * @return
     */
    @POST("/hrz/api/accountMember/lockfanApp")
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json",
            "Domain-Name:mock_app_lock_fan"})// 加上 Domain-Name header
    Observable<LockFanBean> applyLockFan(@Body RequestBody requestBody);

    /**
     * 发送手机验证码
     * @param map
     * @return
     */
    @GET("/hrz/api/wallet/withdraw/code ")
    @Headers({"Domain-Name:mock_app_send_verify"})// 加上 Domain-Name header
    Observable<VerifyCodeBean> sendPhoneVerify(@QueryMap HashMap<String, String> map);

    /**
     * 验证手机验证码
     * @param requestBody
     * @return
     */
    @POST()
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json",
            "Domain-Name:mock_app_verify_vCode"})// 加上 Domain-Name header
    Observable<Object> verifyVcode(@Body RequestBody requestBody);
}
