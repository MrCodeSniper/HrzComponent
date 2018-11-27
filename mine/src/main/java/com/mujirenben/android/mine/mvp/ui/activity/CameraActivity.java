package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.mine.mvp.ui.view.ClipCamera;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartown on 2018/2/24 11:46.
 * <br>
 * Desc:
 * <br>
 * 拍照界面
 */
public class CameraActivity extends Activity {
    private static final String TAG = "CameraActivity";
    @BindView(R2.id.surface_view)
    ClipCamera mCameraView;
    @BindView(R2.id.sign_guo_hui)
    ImageView signGuoHui;
    @BindView(R2.id.sign_head_picture)
    ImageView signHeadPicture;
    @BindView(R2.id.btn_shoot)
    ImageView takePhotoOkayIv;
    @BindView(R2.id.btn_cancle)
    ImageView takePhotoCloseIv;
    @BindView(R2.id.camera_scan_fl)
    FrameLayout cameraScanFl;

    /**
     * 拍摄类型-身份证正面
     */
    public final static int TYPE_IDCARD_FRONT = 100;
    /**
     * 拍摄类型-身份证反面
     */
    public final static int TYPE_IDCARD_BACK = 102;
    public final static int TAKE_PHOTO_REQUEST_CODE = 0X13;
    public final static int RESULT_CODE = 0X14;

    /**
     * @param type {@link #TYPE_IDCARD_FRONT}
     *             {@link #TYPE_IDCARD_BACK}
     */
    public static void openCertificateCamera(Activity activity, int type) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
    }

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_clip_camera);
        ButterKnife.bind(this);
        switch (type) {
            case TYPE_IDCARD_FRONT:
                signHeadPicture.setVisibility(View.VISIBLE);
                signGuoHui.setVisibility(View.GONE);
                break;
            case TYPE_IDCARD_BACK:
                signHeadPicture.setVisibility(View.GONE);
                signGuoHui.setVisibility(View.VISIBLE);
                break;
        }
        takePhotoOkayIv.setOnClickListener(v -> takePhoto());
        takePhotoCloseIv.setOnClickListener(v -> CameraActivity.this.finish());
        mCameraView.setOnClickListener(v -> mCameraView.setAutoFocus());
    }

    private void takePhoto() {
        mCameraView.takePicture(jpeg);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        mCameraView.stopPreview();
        super.onStop();
    }

    //创建jpeg图片回调数据对象
    private Camera.PictureCallback jpeg = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera Camera) {
            BufferedOutputStream bos = null;
            Bitmap bm = null;
            try {
                // 获得图片
                bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                int pic_width = bm.getWidth();//1280
                int  pic_height= bm.getHeight();//720
                int height,width,x_center,y_center;
                height = (int) (pic_height * 0.8);//屏幕宽的0.8,拍照取景框的宽为屏幕的0.8
                width = (int) (height * 1.6);
                x_center=pic_width/2;
                y_center=pic_height/2;

                Matrix matrix = new Matrix();
                matrix.postRotate(360,pic_width/2,pic_height/2);
                bm = Bitmap.createBitmap(bm, x_center - (width / 2) ,
                        y_center - (height / 2) ,
                        (int) (pic_height*0.8*1.6),
                        (int) (pic_height*0.8),
                        matrix,false);

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Log.i(TAG, "Environment.getExternalStorageDirectory()="+ Environment.getExternalStorageDirectory());
                    String path = Environment.getExternalStorageDirectory().getPath()+"/"+System.currentTimeMillis()+".jpg";
                    File file = new File(path);
                    if (!file.exists()){
                        file.createNewFile();
                    }
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将图片压缩到流中
                    String savePath = file.getAbsolutePath();
                    Intent intent = new Intent();
                    intent.putExtra("result",savePath);
                    setResult(TAKE_PHOTO_REQUEST_CODE,intent);
                    CameraActivity.this.finish();
                }else{
                    ArmsUtils.makeText(CameraActivity.this,"没有检测到内存卡");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.flush();//输出
                    bos.close();//关闭
                    bm.recycle();// 回收bitmap空间
                    mCameraView.stopPreview();// 关闭预览
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
