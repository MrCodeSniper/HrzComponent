package com.mujirenben.android.mine.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.JsonObject;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.mine.mvp.model.bean.LockFansResult;
import com.mujirenben.android.mine.mvp.model.service.LockFansService;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.io.File;
import java.io.FileOutputStream;
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

@Route(path = ARouterPaths.COUPONS_RECEIVE_ACTIVITY)
public class CouponsReceiveActivity extends BaseActivity {

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
        StatusBarUtil.setStatusBarWhite(this);
        return R.layout.activity_coupons_receive;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(this);
        mRepoMgr = mAppComponent.repositoryManager();
        mTitleTV.setText("领取优惠券");

        Glide.with(this)
                .load(Consts.HRZ_INVITE_QR_CODE_URL)
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

    @OnClick(R2.id.btn_give_up)
    public void onGiveUpClick(View view) {
        finish();
    }

    @OnClick(R2.id.btn_save_image)
    public void onSaveImageClick(View view) {
        Glide.with(this).asBitmap().load(Consts.HRZ_INVITE_QR_CODE_URL).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                try {
                    saveBitmap(resource,false);
                } catch (Exception e) {
                    ArmsUtils.makeText(CouponsReceiveActivity.this,"保存图片失败");
                }
            }
        });
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
            ArmsUtils.makeText(this, "验证码错误");
        } else {
            ArmsUtils.makeText(this, "验证成功");
            finish();
        }
    }
    private String saveBitmap(Bitmap bmp, boolean toShare) throws Exception {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "hongrenzhuang";

        // 必要时创建目录
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        // 创建目标文件
        File targetFile = new File(dir, System.currentTimeMillis() + (toShare ? "" : ".jpg"));
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }

        // 保存
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(targetFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100 /* quality */, fos);
            fos.flush();
            ArmsUtils.makeText(this,"保存成功");
        } finally {
            if (fos != null) {
                fos.close();
                bmp = null;
            }
        }
        return targetFile.getAbsolutePath();
    }
}
