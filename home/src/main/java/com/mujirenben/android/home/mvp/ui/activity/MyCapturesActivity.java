package com.mujirenben.android.home.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.http.api.ShopNumberService;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.entity.AllianceDetailBeans;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.LightSensorManager;
import com.mujirenben.android.common.util.MiniProgramHelper;
import com.mujirenben.android.common.util.NetWorkUtils;
import com.mujirenben.android.common.util.PermissionUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.imgCompress.ImageUtils;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.common.util.wxHelper.WeiXinHelper;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.dialog.SureDialog;
import com.mujirenben.android.home.R;
import com.mujirenben.android.home.dagger.component.DaggerQrCodeComponent;
import com.mujirenben.android.home.dagger.module.QrCodeModule;
import com.mujirenben.android.home.mvp.contract.QrCodeContract;
import com.mujirenben.android.home.mvp.model.entity.GoodsEntity;
import com.mujirenben.android.home.mvp.model.entity.ScanQrOrderBean;
import com.mujirenben.android.home.mvp.model.entity.ScanQrTakeBean;
import com.mujirenben.android.home.mvp.model.service.ScanService;
import com.mujirenben.android.home.mvp.presenter.QrCodePresenter;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.decode.QRDecode;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mujirenben.android.common.constants.Consts.QRCODE_HOST_TIYAN;


/**
 *extends BaseActivity<UserPresenter> implements UserContract.View
 */
public class MyCapturesActivity extends BaseActivity<QrCodePresenter> implements OnScannerCompletionListener,
        View.OnClickListener,QrCodeContract.View,SensorEventListener, LightSensorManager.LightStateCallback,Handler.Callback  {
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public final static String INTENT_EXTRA_NAME = "name";
    public final static String INTENT_EXTRA_PLATFORM = "platform";
    private ScannerView mScannerView;
    private ImageView tv_back;
    private TextView tv_album;
    private TextView tv_light;
    private TextView tv_mycode;
    private boolean isTourch=false;
    private String pattern = "^\\d{13}$";
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private SureDialog rxDialogSure;
    private LinearLayout ll_click_light;
    private ImageView iv_light;

    @Inject
    RxPermissions mRxPermissions;

    private IRepositoryManager mRepositoryManager;

    private RxErrorHandler mErrorHandler;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerQrCodeComponent
                .builder()
                .appComponent(appComponent)
                .qrCodeModule(new QrCodeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setStatusBarTransparent(this);
        initRetrofit();
        return R.layout.activity_qrcode;
    }

    /**
     * 初始化网络请求
     */
    private void initRetrofit() {
         mRepositoryManager = BaseApplication.getApplication().getAppComponent().repositoryManager();
         mErrorHandler = BaseApplication.getApplication().getAppComponent().rxErrorHandler();
    }

    private void initPreView(){
        mScannerView=findViewById(R.id.scanner_view);
        tv_back=findViewById(R.id.tv_scan_back);
        tv_album=findViewById(R.id.tv_scan_alblum);
        tv_light=findViewById(R.id.touch_light_view);
        iv_light=findViewById(R.id.iv_light);
        tv_mycode=findViewById(R.id.zxing_myqrcode);
        ll_click_light=findViewById(R.id.ll_click_light);
        mScannerView.setOnScannerCompletionListener(this);
        tv_back.setOnClickListener(this);
        tv_album.setOnClickListener(this);
        tv_mycode.setOnClickListener(this);
        ll_click_light.setOnClickListener(this);
    }


    private void initPermission(){
        mRxPermissions.requestEach(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d(TAG, permission.name + " is granted.");
                            ScannerOptions.Builder builder = new ScannerOptions.Builder();
                            builder.setTipTextColor(R.color.transparent);
                            builder.setTipTextSize(0);
                            builder.setFrameCornerColor(getResources().getColor(R.color.main_color));
                            builder.setLaserLineColor(getResources().getColor(R.color.main_color));
                            mScannerView.setScannerOptions(builder.build());
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                            ArmsUtils.makeText(MyCapturesActivity.this,"未授权权限，部分功能不能使用");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                            ArmsUtils.makeText(MyCapturesActivity.this,"未授权权限，部分功能不能使用");
                        }
                    }
                });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initPreView();
        initPermission();
    }

    public static void gotoActivity(Activity activity) {
        activity.startActivity(new Intent(activity,MyCapturesActivity.class));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        Logger.e("onResume");
        super.onResume();
        mScannerView.onResume();
        LightSensorManager.getInstance(this).start(this);
    }

    @Override
    protected void onStart() {
        Logger.e("onStart");
        super.onStart();
        mScannerView.onResume();
        LightSensorManager.getInstance(this).start(this);
    }

    @Override
    protected void onRestart() {
        Logger.e("onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.e("onPause");
        mScannerView.onPause();
        LightSensorManager.getInstance(this).stop();
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    //周期性扫码
    @Override
    public void onScannerCompletion(Result result, ParsedResult parsedResult, Bitmap bitmap) {

         if(result==null || TextUtils.isEmpty(result.getText())){
             showDialog("识别不到/二维码无效","重新识别");
             return;
         }

        Logger.e("扫描结果:"+result.getText());

         if(!NetWorkUtils.checkNetWork(this)){
             showDialog("联网超时,请检查网络","确定");
             return;
         }

         switchQrCode(result.getText());
    }

    /*
     * 筛选二维码条件
     * @param qrCode
     */
    private void switchQrCode(String qrCode){
        if(Pattern.matches(pattern,qrCode)){
            showDialog("当前版本暂不支持扫描条形码","重新识别");
        }else if(qrCode.contains(Consts.QRCODE_HOST_DETAIL)&&qrCode.contains("id")){
            scanQrIntoShop(qrCode);
        }else if(qrCode.contains(Consts.QRCODE_HOST_ORDER) && qrCode.contains("sid")){
            scanQrPay(qrCode);
        }else if (qrCode.contains(QRCODE_HOST_TIYAN)&& qrCode.contains("M.4")){
            scanQrTake(qrCode);
        }else if (qrCode.contains(QRCODE_HOST_TIYAN)&& qrCode.contains("M.3")){
            scanQrOrder(qrCode);
        }else if(qrCode.contains(Consts.QRCODE_HOST_WEB)||qrCode.contains("hrzrouter")) {
            //扫描到活动页
            HrzRouter.getsInstance(this).navigation(qrCode);
            finish();
        }else if (qrCode.startsWith("http")){
            HrzRouter.getsInstance(this).navigation(qrCode);
            finish();
        }else{
            showDialog("识别不到/二维码无效","重新识别");
        }
    }
    
    
    

    /**
     * 扫码进店
     * @param result
     */
    private void scanQrIntoShop(String result) {
        //扫码进店
        if(LoginDataManager.getsInstance(this).isLogin()){
            String newResult = result;
            String shopId = newResult.substring(newResult.indexOf("=")+1,newResult.indexOf("&"));
            if(TextUtils.isEmpty(shopId))return;
            ARouter.getInstance()
                    .build(ARouterPaths.ALLIANCE_SHOPDETAIL)
                    .withInt("shopId",Integer.parseInt(shopId))
                    .navigation(MyCapturesActivity.this);

        }else {
            routeToLogin();
        }
    }

    /**
     * 扫码支付
     * @param result
     */
    private void scanQrPay(String result) {
        //扫码支付
        String resultStr = result;
        String parameters = resultStr.substring(resultStr.indexOf("?"),resultStr.length());
        String miniPath = MiniProgramHelper.getParameters(this,null,parameters);
        WeiXinHelper.getBuilder(this)
                .setMiniUserName(Consts.MINI_PROGRAM_ID)
                .setMiniPath(miniPath)
                .build()
                .openMiniProgram();
        MyCapturesActivity.this.finish();
    }

    /**
     * 扫码带走
     * @param result
     */
    private void scanQrTake(String result) {
        //扫码带走
        String requestTakeURL = result;
        String token = "77cbc257e66302866cf6191754c0c8e3";
        String userid = "10";
        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("userid",userid);
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mRepositoryManager.obtainRetrofitService(ScanService.class).scanQrTake(requestTakeURL,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<ScanQrTakeBean>(mErrorHandler) {
                    @Override
                    public void onNext(ScanQrTakeBean bean) {
                        if(bean != null && bean.getStatus().equals("201")){
                            String taobaoURL = bean.getData().getTaobaourl();
                            String price = bean.getData().getPrice();
                            String hrzrouterURL = "hrzrouter://hrzapp/app/webSearch?url="+taobaoURL+"&titleName=体验馆商品"+"&webType=100"+"&qrCodePrice="+price+"&qrCodeContent="+requestTakeURL;
                            HrzRouter.getsInstance(MyCapturesActivity.this).navigation(hrzrouterURL);
                            MyCapturesActivity.this.finish();
                        }else {
                            ArmsUtils.makeText(MyCapturesActivity.this,"商品获取失败");
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                ArmsUtils.makeText(MyCapturesActivity.this,msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                ArmsUtils.makeText(MyCapturesActivity.this,msg);
                            }
                        });
                    }
                });
        MyCapturesActivity.this.finish();
    }

    /**
     * 扫码下单
     * @param result
     */
    private void scanQrOrder(String result) {
        //扫码下单
        String requestOrderURL =  result;
        // RetrofitUrlManager.getInstance().putDomain("scan_order", requestOrderURL);
        String token = "77cbc257e66302866cf6191754c0c8e3";
        String userid = "10";
        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("userid",userid);
        String postJson = new Gson().toJson(map);
        Logger.e("JSON:"+postJson);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mRepositoryManager.obtainRetrofitService(ScanService.class).scanQrOrder(requestOrderURL,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<ScanQrOrderBean>(mErrorHandler) {
                    @Override
                    public void onNext(ScanQrOrderBean bean) {
                        if(null !=bean && bean.getStatus().equals("200")){
                            String num_iids = bean.getData().getNum_iids();
                            Bundle bundle=new Bundle();
                            bundle.putString(Consts.GOODS_NUM_IID_INTENT_STR,num_iids);
                            bundle.putString(Consts.GOODS_ID_INTENT_STR,"");
                            bundle.putString(Consts.ROUTER_FROM,"扫一扫");
                            ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL)
                                    .withBundle(Consts.HRZ_ROUTER_BUNDLE,bundle)
                                    .navigation(MyCapturesActivity.this);
                            MyCapturesActivity.this.finish();
                        }else {
                            ArmsUtils.makeText(MyCapturesActivity.this,"商品获取失败");
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
                            @Override
                            public void dataException(String msg) {
                                ArmsUtils.makeText(MyCapturesActivity.this,msg);
                            }

                            @Override
                            public void networkException(String msg) {
                                ArmsUtils.makeText(MyCapturesActivity.this,msg);
                            }
                        });
                    }
                });
        MyCapturesActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
      int id=v.getId();
      if(id==R.id.tv_scan_back){
          finish();
      }else if(id==R.id.tv_scan_alblum){
          gallery();
      }else if(id==R.id.ll_click_light){
            if(isTourch){
                mScannerView.toggleLight(false);
                iv_light.setSelected(false);
                tv_light.setText("轻触照亮");
                isTourch=false;
            }else {
                iv_light.setSelected(true);
                tv_light.setText("轻触熄灭");
                mScannerView.toggleLight(true);
                isTourch=true;
            }
      }else if(id==R.id.zxing_myqrcode){
          if(LoginDataManager.getsInstance(this).isLogin()){
              ARouter.getInstance().build(ARouterPaths.VIP_QR_CODE_ACTIVITY).navigation(this);
          }else {
             routeToLogin();
          }
      }
    }

    /*
     * 登陆
     */
    private void routeToLogin(){
        ARouter.getInstance()
                .build(ARouterPaths.LOGIN_MAIN_MINE)
                .withString(Consts.LOGIN_SOURCE_KEY,"扫一扫")
                .navigation(this);
    }


    /*
    * 从相册获取
    */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();//解析图片  得到的是图片地址
                //crop(uri);
                QRDecode.decodeQR(ImageUtils.getRealPathFromUri(MyCapturesActivity.this,uri),this);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loadSuccess(GoodsEntity homeIndex) {
        Logger.e(homeIndex.toString());
        if(homeIndex!=null){
            if(homeIndex.getError_code()==0){
                showDialog(homeIndex.getResult().getSummary().getName(),"确定");
                Logger.e(homeIndex.getResult().getSummary().getName());
                Bundle bundle = new Bundle();
                bundle.putString(INTENT_EXTRA_NAME, homeIndex.getResult().getSummary().getName());
                bundle.putInt(INTENT_EXTRA_PLATFORM, 0);
                ARouter.getInstance().build(ARouterPaths.SEARCH_RESULT).with(bundle).navigation(this);
            }else {
                showDialog("识别不到"+homeIndex.getReason(),"重新识别");
            }
        }else {
            showDialog("识别不到/二维码无效","重新识别");
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void noNetDialog() {
        showDialog("联网超时,请检查网络","确定");
    }

    @Override
    public void requestOutTime() {
        showDialog("识别超时","重新识别");
    }


    private void showDialog(String content,String check){
        if(rxDialogSure==null){
            rxDialogSure = new SureDialog(this);//提示弹窗
        }
        rxDialogSure.getLogoView().setVisibility(View.GONE);
        rxDialogSure.getTitleView().setVisibility(View.GONE);
        rxDialogSure.setContent(content);
        rxDialogSure.setSure(check);
        rxDialogSure.getSureView().setOnClickListener(v -> {
            rxDialogSure.cancel();
            mScannerView.onResume();
        });
        rxDialogSure.show();
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
    public void lightWeak() {
        ll_click_light.setVisibility(View.VISIBLE);
    }

    @Override
    public void lightStrong() {
        ll_click_light.setVisibility(View.GONE);
    }
}
