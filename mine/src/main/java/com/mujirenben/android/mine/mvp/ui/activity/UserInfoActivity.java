package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.ShipAddress;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.bean.MessageEventGlobal;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.rxtools.RxPhotoTool;
import com.mujirenben.android.common.widget.CustomPopupWindow;
import com.mujirenben.android.common.widget.dialog.ChooseDialog;
import com.mujirenben.android.common.widget.dialog.RxDialogEditSureCancel;
import com.mujirenben.android.mine.di.component.DaggerMineComponent;
import com.mujirenben.android.mine.di.module.MineModule;
import com.mujirenben.android.mine.mvp.contract.MineContract;
import com.mujirenben.android.mine.mvp.model.InviteCodeBean;
import com.mujirenben.android.mine.mvp.model.bean.UserInfo;
import com.mujirenben.android.mine.mvp.model.bean.UserRewardInfo;
import com.mujirenben.android.mine.mvp.presenter.MinePresenter;
import com.mujirenben.android.mine.mvp.ui.view.AddressPicker;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac on 2018/5/29.
 */

public class UserInfoActivity extends BaseActivity<MinePresenter> implements MineContract.View {

    @BindView(R2.id.tv_back)
    ImageView tvBack;

    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;

    @BindView(R2.id.iv_users_icon)
    RCImageView ivUsersIcon;

    @BindView(R2.id.tv_user_info_name)
    TextView tvUserInfoName;

    @BindView(R2.id.iv_qrcode)
    ImageView ivQrcode;

    @BindView(R2.id.tv_location)
    TextView tvLocation;

    @BindView(R2.id.tv_birthday)
    TextView tvBirthday;

    @BindView(R2.id.tv_user_phone)
    TextView tvUserPhone;

    @BindView(R2.id.rl_address)
    RelativeLayout rl_address;

    @BindView(R2.id.rl_user_qrcode)
    RelativeLayout rlUserQrCode;

    @BindView(R2.id.tv_level)
    TextView tv_level;

    RxDialogEditSureCancel rxDialogSure;
    @BindView(R2.id.mine_enter_phone_layout)
    RelativeLayout mineEnterPhoneLayout;
    private ChooseDialog chooseDialog;
    private UserBean userBean;
    private Uri resultUri;
    private AddressPicker addressPicker;

    @Inject
    RxPermissions mRxPermissions;

    private LoginDataManager loginDataManager;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent
                .builder()
                .appComponent(appComponent)
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.mine_activity_user_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        tvTitlebar.setText(R.string.user_self_info);
//        mPresenter.getUserInfofromDB();
        loginDataManager = LoginDataManager.getsInstance(this);
        prepareListener();
        refreshView();
    }

    private void prepareListener() {
        rlUserQrCode.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(ARouterPaths.VIP_QR_CODE_ACTIVITY)
                        .withString("source", "我的")
                        .navigation(this));
        tvBack.setOnClickListener(v -> finish());
        rl_address.setOnClickListener( v ->
                ARouter.getInstance()
                        .build(ARouterPaths.ADDRESS_INFOS)
                        .navigation(this));
        tvUserInfoName.setOnClickListener( v -> {
            // showDialog(loginDataManager.getNickName());
        });
        ivUsersIcon.setOnClickListener( v -> {
            // showPopupWindow();
        });
        tvLocation.setOnClickListener( v -> showAddressPicker());

        mineEnterPhoneLayout.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(ARouterPaths.BIND_PHONE_ACTIVITY)
                        .withTransition(R.anim.slide_in_right, R.anim.slide_out_left)//跳转到的界面动画，自己退场动画
                        .navigation(this)
        );
    }

    public void showPopupWindow() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.photo_selected_pop_layout, null);
        CustomPopupWindow.builder(this)
                .contentView(popupView)
                .isFocus(true)
                .customListener((contentView, popupWindow) -> intiClickEvent(contentView, popupWindow))
//                .animationStyle(R.style.AnimBottom)
                .isOutsideTouch(true)
                .build()
                .show();
    }

    /**
     * 监听事件
     *
     * @param contentView
     */
    private void intiClickEvent(View contentView, CustomPopupWindow popupWindow) {
        TextView takePhotoTv = contentView.findViewById(R.id.takePhotoTv);
        TextView selectPhotoTv = contentView.findViewById(R.id.selectPhotoTv);
        TextView cancelTv = contentView.findViewById(R.id.cancelTv);

        takePhotoTv.setOnClickListener(v -> {
            mPresenter.requestCamera();
            popupWindow.dismiss();
        });
        selectPhotoTv.setOnClickListener(v -> {
            mPresenter.requestStorage();
            RxPhotoTool.openLocalImage(this);
            popupWindow.dismiss();
        });
        cancelTv.setOnClickListener(v -> popupWindow.dismiss());
    }

    private void showAddressPicker() {

        if (userBean == null || userBean.getFromProvince() == null || userBean.getFromProvince().equals("")) {
            addressPicker = new AddressPicker(this, 2);
        } else {
            ShipAddress shipAddress = new ShipAddress();
            shipAddress.setAreaId1(userBean.getFromProvince() == null ? "" : userBean.getFromProvince());
            shipAddress.setAreaId2(userBean.getFromCity() == null ? "" : userBean.getFromCity());
            shipAddress.setAreaId3(userBean.getFromZone() == null ? "" : userBean.getFromZone());
            addressPicker = new AddressPicker(this, 2, shipAddress);
        }


        addressPicker.setTitleInfo(getString(R.string.choose_your_city));
        addressPicker.setOnContentItemClickListener(new AddressPicker.OnContentItemClickListener() {

            @Override
            public void onLevelOneClick(Long ProId) {
                mPresenter.getCitiesByProvinceId(ProId);
            }

            @Override
            public void onLevelTwoClick(Long cityId) {
                userBean.setFromProvince(addressPicker.getmShipAddress().getAreaId1() == null ? "" : addressPicker.getmShipAddress().getAreaId1());
                userBean.setFromZone(addressPicker.getmShipAddress().getAreaId3() == null ? "" : addressPicker.getmShipAddress().getAreaId3());
                userBean.setFromCity(addressPicker.getmShipAddress().getAreaId2() == null ? "" : addressPicker.getmShipAddress().getAreaId2());
                mPresenter.saveUserData(userBean);
                addressPicker.cancel();
            }

            @Override
            public void onLevelThreeClick() {

            }

            @Override
            public void onTabCitySelect(Long proId) {
                mPresenter.getCitiesByProvinceId(proId);
            }
        });
        addressPicker.show();

        mPresenter.getAllProvince();


    }


    private void showDialog(String tvname) {
        if (rxDialogSure == null) {
            rxDialogSure = new RxDialogEditSureCancel(this);//提示弹窗
        }
        if (!TextUtils.isEmpty(tvname)) {
            rxDialogSure.getEditText().setText(tvname);
            rxDialogSure.getEditText().setSelection(tvname.length());
            rxDialogSure.getSureView().setEnabled(true);
            rxDialogSure.getSureView().setSelected(true);
        }

        rxDialogSure.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 1) {
                    rxDialogSure.getSureView().setEnabled(false);
                    rxDialogSure.getSureView().setSelected(false);
                } else {
                    rxDialogSure.getSureView().setEnabled(true);
                    rxDialogSure.getSureView().setSelected(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rxDialogSure.getSureView().setOnClickListener((View v) -> {
                    userBean.setUser_name(rxDialogSure.getEditText().getText().toString());
                    mPresenter.saveUserData(userBean);
                    rxDialogSure.cancel();
                }
        );


        rxDialogSure.getCancelView().setOnClickListener((View v) -> rxDialogSure.cancel());
        rxDialogSure.show();
    }


    public static void gotoActivity(Activity activity) {
        Intent intent = new Intent(activity, UserInfoActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showUserInfo(UserBean userBean) {
    }


    private void refreshView() {
        if (loginDataManager.getDisplayName() == null) {
            tvUserInfoName.setText(R.string.please_input_nickname);
        } else {
            tvUserInfoName.setText(loginDataManager.getDisplayName());
        }

        if (tvUserPhone != null) {
            tvUserPhone.setText(loginDataManager.getPhone());
        }

        if(LoginDataManager.getsInstance(this).getLevel()==Const.UserLevel.LEVEL_SHOP){
            tv_level.setText("店主");
        }else if(LoginDataManager.getsInstance(this).getLevel()==Const.UserLevel.LEVEL_FANS){
            tv_level.setText("粉丝");
        }else if(LoginDataManager.getsInstance(this).getLevel()==Const.UserLevel.LEVEL_VIP){
            tv_level.setText("皇冠");
        }else {
            tv_level.setText("粉丝");
        }



//        if (tvLocation != null) {
//            tvLocation.setText(userBean.getFromProvince() + userBean.getFromCity() + userBean.getFromZone());
//            if (userBean.getFromProvince() == null && userBean.getFromZone() == null && userBean.getFromCity() == null) {
//                tvLocation.setText(R.string.select_city);
//            }
//        }

        if (!TextUtils.isEmpty(loginDataManager.getAvatarUrl())) {
            Glide.with(getActivity()).load(loginDataManager.getAvatarUrl()).into(ivUsersIcon);
        } else {
            ivUsersIcon.setImageResource(R.drawable.ic_luncher);
        }
    }


    @Override
    public void noUserInfoDB() {

    }

    @Override
    public void saveSucess(Long itemId) {
    }

    @Override
    public void saveFail(Long itemId) {

    }

    @Override
    public void requestPermissionSuccess(String permissionType) {
        RxPhotoTool.openCameraImage(this);
        chooseDialog.cancel();
    }

    @Override
    public void getProvinceSuccess(List<Province> provinceList) {
        addressPicker.setProvinceTags(provinceList, true);
    }

    @Override
    public void getCitiesSuccess(List<City> cityList) {
        addressPicker.setCityTags(cityList, true);
    }

    @Override
    public void getLocalDbFail() {
        Logger.e("获取本地数据库数据失败");
    }

    @Override
    public void showUserRewardInfo(UserRewardInfo userRewardInfo) {
    }


    @Override
    public void getUserRewardError() {

    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public void showUserInfo(UserInfo loginResultBean) {

    }

    @Override
    public void onLoadInviteCodeInfoCompleted(InviteCodeBean info) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }


    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEventGlobal messageEvent) {
        if (messageEvent.getMessage().equals(Const.SAVE_USER_DATA_SUCCESS)) {
            refreshView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
//                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
                    initUCrop(data.getData());
                }

                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    /* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                }

                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.ic_luncher)
                        //异常占位图(当加载异常的时候出现的图片)
                        .error(R.drawable.ic_luncher)
                        //禁止Glide硬盘缓存缓存
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                Glide.with(this).
                        load(RxPhotoTool.cropImageUri).
                        apply(options).
                        thumbnail(0.5f).
                        into(ivUsersIcon);
//                RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    resultUri = UCrop.getOutput(data);
                    Logger.e(resultUri.toString());
                    //roadImageView(resultUri, ivUsersIcon);
                    //   userBean.setNickLocalUri(resultUri.toString());
                    //RxSPTool.putContent(this, "AVATAR", resultUri.toString());

                    loginDataManager.setAvatarUrl(resultUri.toString());
                    EventBus.getDefault().post(new MessageEventGlobal(Const.SAVE_USER_DATA_SUCCESS));
                    //mPresenter.saveUserData(userBean);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    //从Uri中加载图片 并将其转化成File文件返回
//    private File roadImageView(Uri uri, ImageView imageView) {
//        Glide.with(this).
//                load(uri).
//                thumbnail(0.5f).
//                into(imageView);
//        return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
//    }

    private void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.main_color));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.main_color));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
