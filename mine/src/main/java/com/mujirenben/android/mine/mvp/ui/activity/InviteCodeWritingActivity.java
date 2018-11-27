package com.mujirenben.android.mine.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.mine.mvp.model.bean.LockFansResult;
import com.mujirenben.android.mine.mvp.model.service.LockFansService;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.RequestBody;

@Route(path = ARouterPaths.INVITE_CODE_WRITING_ACTIVITY)
public class InviteCodeWritingActivity extends BaseActivity {

    private static final String TAG = "InviteCode";

    @BindView(R2.id.tv_title)
    TextView mTitleTV;

    @BindView(R2.id.tv_error_tip)
    TextView mErrorTipTV;

    @BindView(R2.id.et_invite_code)
    EditText mInviteCodeET;

    private AppComponent mAppComponent;
    private IRepositoryManager mRepoMgr;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        return R.layout.activity_invite_code_writing;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mAppComponent = ArmsUtils.obtainAppComponentFromContext(this);
        mRepoMgr = mAppComponent.repositoryManager();
        mTitleTV.setText("填写邀请码");

        Glide.with(this)
                .load("http://imgcdn.tlgn365.com/2018-08-31/26da276b-c520-4c38-a9b5-65349f3c521d.png")
                .into((ImageView)findViewById(R.id.iv_qr_code));
    }

    @OnClick(R2.id.ib_left_action)
    public void onBackClick(View view) {
        finish();
    }

    @OnClick(R2.id.btn_sure)
    public void onSureClick(View view) {
        lockFans(mInviteCodeET.getText().toString());
    }

    private void lockFans(String inviteCode) {
        Log.i(TAG, "lockFans >> inviteCode = " + inviteCode);

        RetrofitUrlManager.getInstance().putDomain("normal_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);

        HashMap<String, String> extraParams = new HashMap<>();
        extraParams.put("inviteCode", inviteCode);

        Map<String, String> params = HttpParamUtil.getCommonSignParamMap(this, extraParams);
        JsonObject jsonObj = new JsonObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            jsonObj.addProperty(entry.getKey(), entry.getValue());
        }
        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObj.toString());
        mRepoMgr.obtainRetrofitService(LockFansService.class).lockFans(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LockFansResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LockFansResult result) {
                        Log.i(TAG, "lockFans >> result = " + result);
                        handleLockFansResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "lockFans >> error = " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void handleLockFansResult(LockFansResult result) {
        if (!result.isSuccess()) {
            mErrorTipTV.setVisibility(View.VISIBLE);
            ArmsUtils.makeText(this, "未找到对应邀请人");
        } else {
            ArmsUtils.makeText(this, "锁定成功");
            finish();
        }
    }
}
