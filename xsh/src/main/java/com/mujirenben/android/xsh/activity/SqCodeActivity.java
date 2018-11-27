package com.mujirenben.android.xsh.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.SqCodeEntity;
import com.mujirenben.android.xsh.entity.WeixinSoftEntity;
import com.mujirenben.android.xsh.service.AllianceService;
import com.mujirenben.android.xsh.utils.NetServiceUtils;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.QrCodeUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.wxHelper.WeiXinHelper;
import com.mujirenben.android.common.util.wxHelper.WxBitmapUtil;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Thinkpad on 2018/3/31.
 */

public class SqCodeActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_share;
    private ImageView iv_code;
    private TextView tv_save;
    private List<File> mFileList = new ArrayList<>();
    private Retrofit retrofit;
    private AllianceService allianceService;
    private Bitmap qrCode;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                        ArmsUtils.makeText(SqCodeActivity.this,getResources().getString(R.string.save_success));
                    break;
                case 2:
                    try {
                        shareMultiplePictureToFriend(SqCodeActivity.this, "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private ImageView iv_icon;
    private TextView tv_name;
    private ArrayList<Uri> imageUris = new ArrayList<Uri>();
    private String username;
    private TextView tv_watch;
    private SqCodeEntity sqCodeEntity;
    private RelativeLayout rl_top;
    private WeixinSoftEntity weixinSoftEntitys;

    private ImageView tv_back;
    private TextView tv_title_bar;

    RxPermissions rxPermissions;

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
        username = getIntent().getStringExtra(Constants.IntentConstant.FENLEI_NAME);
        return R.layout.hrz_activity_sqcode;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initView();
        prepareInit();
        initData();
    }

    private void prepareInit() {
        tv_title_bar.setText("我的对接信息");
        tv_back.setOnClickListener(v -> finish());
        retrofit = NetServiceUtils.getRetrofitNew();
        allianceService = retrofit.create(AllianceService.class);
        rxPermissions = new RxPermissions(this);
    }

    private void initData() {
        getTopData();
    }

    private void getTopData(){

        Observable<WeixinSoftEntity> call = allianceService.getCode(HttpParamUtil.getCommonSignParamMap(this, null));
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weixinSoftEntity -> {
                    if (weixinSoftEntity != null &&weixinSoftEntity.getData()!=null ) {
                        Logger.e(weixinSoftEntity.toString());
                        weixinSoftEntitys=weixinSoftEntity;
                        iv_code.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        iv_code.setDrawingCacheEnabled(true);
                        iv_code.setImageBitmap(qrCode=QrCodeUtils.createQRImage(weixinSoftEntity.getData(),300,300)); //创建二维码
                        WxBitmapUtil.saveBitmap(qrCode,true);// 保存图片
                        qrCode=null;
                        if (!"".equals(LoginDataManager.getsInstance(SqCodeActivity.this).getAvatarUrl())) {
                            Glide.with(SqCodeActivity.this).load(LoginDataManager.getsInstance(SqCodeActivity.this).getAvatarUrl()).into(iv_icon);
                        }
                    }
                },throwable -> {
                    Logger.e(throwable.getMessage());
                });

    }

    private void initView() {
        tv_back=findViewById(R.id.tv_back);
        tv_title_bar=findViewById(R.id.tv_titlebar);
        rl_top = (RelativeLayout) findViewById(R.id.rl_top);
        tv_watch = (TextView) findViewById(R.id.tv_watch);
        tv_watch.setOnClickListener(this);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(this);
        iv_code = (ImageView) findViewById(R.id.iv_code);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText("Hi,[" + username + "]");
        TextView dockCode = (TextView) findViewById(R.id.dockCode);
        dockCode.setText("对接人编码:"+LoginDataManager.getsInstance(this).getUserId()+"");
    }

    public List<File> getFile(File file) {
        File[] fileArray = file.listFiles();
        if (fileArray != null) {
            for (File f : fileArray) {
                if (f.isFile()) {
                    mFileList.add(f);
                } else {
                    getFile(f);
                }
            }
        }
        return mFileList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if (new File(BaseApplication.SDIMGFILE).exists()) {
            File file = new File(BaseApplication.SDIMGFILE);
            List<File> fileList = getFile(file);
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i) != null) {
                    File fileFild = fileList.get(i);
                    Log.i(Constants.TAG, "fileFild\t" + fileFild.getPath());
                    if (fileFild.getPath().contains("sharehrz") && isShare||!fileFild.getPath().contains("jpg")) {
                        fileFild.delete();
                    }
                }
            }
        }
    }

    private boolean isShare;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        rl_top.setDrawingCacheEnabled(true);
        Bitmap shareBitmap = rl_top.getDrawingCache();
        if (i == R.id.tv_share) {
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(result -> {
                        if(result){
                            isShare = true;
                            Bitmap bitmap = imageZoom(shareBitmap);   //压缩bitmap
                            WeiXinHelper.getBuilder(SqCodeActivity.this)
                                    .setShareBitmap(bitmap)
                                    .setTitle("分享图片")
                                    .setDescription("描述")
                                    .build()
                                    .shareBitmapTo(WeiXinHelper.ShareToType.SESSION);
                        }else {
                            ArmsUtils.makeText(SqCodeActivity.this,"未授权权限，部分功能不能使用");
                        }
                    });

        } else if (i == R.id.tv_save) {
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(result -> {
                        if(result){
                            isShare = false;
                            new Thread(() -> {
                                try {
                                    String targetFilePath = WxBitmapUtil.saveBitmap(shareBitmap, false);// 保存图片
                                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(targetFilePath))));
                                  //iv_code.destroyDrawingCache(); // 保存过后释放资源
                                  //iv_code.setDrawingCacheEnabled(false);
                                    handler.sendEmptyMessage(1);
                                } catch (Exception e) {
                                    Logger.e(e.getMessage());
                                }
                            }).start();
                        }else {
                            ArmsUtils.makeText(SqCodeActivity.this,"未授权权限，部分功能不能使用");
                        }
                    });
        } else if (i == R.id.tv_watch) {
            MyLinkStoreListActivity.startSelf(SqCodeActivity.this);
        }
    }

    /**
     * 微信分享到好友(多张图片及描述)
     */
    public void shareMultiplePictureToFriend(Context context, String description) throws Exception {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        intent.putExtra(Intent.EXTRA_TEXT, description);
        context.startActivity(intent);
    }


    /**
     * 分享图片大小不能大于32kb
     */
    private Bitmap imageZoom(Bitmap bitMap) {
        //图片允许最大空间   单位：KB
        double maxSize =32;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length/1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }

        return  bitMap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public  Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

}
