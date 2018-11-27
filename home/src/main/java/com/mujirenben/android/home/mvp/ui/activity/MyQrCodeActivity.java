package com.mujirenben.android.home.mvp.ui.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.google.zxing.client.result.ParsedResultType;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.home.R;
import com.mujirenben.android.home.dagger.component.DaggerMyLocalUserComponent;
import com.mujirenben.android.home.dagger.module.MyLocalUserModule;
import com.mujirenben.android.home.mvp.contract.MyLocalUserContract;
import com.mujirenben.android.home.mvp.presenter.MyLocalUserPresenter;
import com.mylhyl.zxing.scanner.encode.QREncode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 *
 */
@Route(path = ARouterPaths.QR_CODE)
public class MyQrCodeActivity extends BaseActivity<MyLocalUserPresenter> implements MyLocalUserContract.View{

    private ImageView iv_myinvitecode;
    private Bitmap bitmap;
    private ImageView tv_back;
    private TextView tv_save_qrcode;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyLocalUserComponent
                .builder()
                .appComponent(appComponent)
                .myLocalUserModule(new MyLocalUserModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_myinvitecode;
    }

    private int statusBarHeight;

    private void initStatusBar(){
        Window window = this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }



    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mPresenter.getUserInfofromDB();


        initStatusBar();
        iv_myinvitecode=findViewById(R.id.iv_myinvitecode);
        tv_back=findViewById(R.id.tv_scan_back);
        tv_save_qrcode=findViewById(R.id.tv_save_qrcode);
        tv_back.setOnClickListener((View v)->finish());






        tv_save_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveBitmap(bitmap,"my_hrz_qrcode.png");
                } catch (IOException e) {
                    e.printStackTrace();
                    ArmsUtils.makeText(MyQrCodeActivity.this,"保存二维码失败");
                }
                ArmsUtils.makeText(MyQrCodeActivity.this,"保存二维码成功");
            }
        });




    }


    private void showQrCode(UserBean userBean){

        Resources res = MyQrCodeActivity.this.getResources();
        Observable.just(R.drawable.ic_luncher)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Bitmap>() {
                    @Override
                    public Bitmap apply(Integer integer) throws Exception {
                        Bitmap bmp=null;
                        if(userBean==null||userBean.getNickLocalUri()==null){
                             bmp= setImgSize(BitmapFactory.decodeResource(res, R.drawable.ic_luncher),152,152);
                        }else {
                             bmp=setImgSize(BitmapFactory.decodeFile(getRealFilePath(MyQrCodeActivity.this,Uri.parse(userBean.getNickLocalUri()))),152,152);
                        }

                        bitmap = new QREncode.Builder(MyQrCodeActivity.this)
                                .setColor(getResources().getColor(R.color.black))//二维码颜色
                                .setParsedResultType(ParsedResultType.TEXT)//默认是TEXT类型
                                .setContents("hrzscheme://add_family?user_id=10086")//二维码内容
                                //自定义用户协议
                                .setLogoBitmap(bmp)//二维码中间logo
                                .build()
                                .encodeAsBitmap();
                        return bitmap;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        Glide.with(MyQrCodeActivity.this).load(bitmap).into(iv_myinvitecode);
                    }
                });
    }


    public  String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    private void saveBitmap(Bitmap bitmap,String bitName) throws IOException
    {
        File file = new File("/sdcard/hongrenzhuang/"+bitName);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public int getStatusBarHeight(){
        if(statusBarHeight==0){
            Class<?> c;
            try
            {
                c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }


    public Bitmap setImgSize(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showUserInfo(UserBean userBean) {
        showQrCode(userBean);
    }

    @Override
    public void noUserInfoDB() {
        showQrCode(null);
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
}
