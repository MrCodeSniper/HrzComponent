package com.mujirenben.android.mine.mvp.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mujirenben.android.common.util.ArmsUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by smartown on 2018/2/24 11:46.
 * <br>
 * Desc:
 * <br>
 * 相机预览封装
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {

    private static String TAG = CameraPreview.class.getName();
    private Camera camera;
    private int mScreenWidth;
    private int mScreenHeight;
    private Context mContext;
    private SurfaceHolder surfaceHolder;
    public CameraPreview(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
    }

    private void initView() {
        getScreenMetrix(mContext);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setKeepScreenOn(true);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /**
     * 获取屏幕的宽高
     * @param context
     */
    private void getScreenMetrix(Context context) {
        WindowManager WM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        WM.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (camera == null) {
            try {
                camera = Camera.open();//开启相机
                setCameraParams(camera,mScreenWidth,mScreenHeight);
                camera.setPreviewDisplay(holder);//摄像头画面显示在Surface上
               // camera.autoFocus(this);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        //因为设置了固定屏幕方向，所以在实际使用中不会触发这个方法
       // camera.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        //回收释放资源
        release();
    }

    private void setCameraParams(Camera camera, int width, int height) {
        Log.i(TAG,"setCameraParams  width="+width+"  height="+height);
        Camera.Parameters parameters = camera.getParameters();
        // 获取摄像头支持的PictureSize列表
        List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
        for (Camera.Size size : pictureSizeList) {
            Log.i(TAG, "pictureSizeList size.width=" + size.width + "  size.height=" + size.height);
        }
        /**从列表中选取合适的分辨率*/
        Camera.Size picSize = getProperSize(pictureSizeList, ((float) height / width));
        if (null == picSize) {
            Log.i(TAG, "null == picSize");
            picSize = parameters.getPictureSize();
        }
        Log.i(TAG, "picSize.width=" + picSize.width + "  picSize.height=" + picSize.height);
        // 根据选出的PictureSize重新设置SurfaceView大小
//        float w = picSize.width;
//        float h = picSize.height;
        float w = mScreenWidth;
        float h = mScreenHeight;
        parameters.setPictureSize(picSize.width,picSize.width);
        setLayoutParams(new FrameLayout.LayoutParams(mScreenWidth,mScreenHeight));

        // 获取摄像头支持的PreviewSize列表
        List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();

        for (Camera.Size size : previewSizeList) {
            Log.i(TAG, "previewSizeList size.width=" + size.width + "  size.height=" + size.height);
        }
        Camera.Size preSize = getProperSize(previewSizeList, ((float) height) / width);
        if (null != preSize) {
            Log.i(TAG, "preSize.width=" + preSize.width + "  preSize.height=" + preSize.height);
            parameters.setPreviewSize(preSize.width, preSize.height);
        }

        parameters.setJpegQuality(100); // 设置照片质量
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
        }

        camera.cancelAutoFocus();//自动对焦。
        camera.setDisplayOrientation(90);// 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
        camera.setParameters(parameters);

    }

    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     *            h对应屏幕的width<p/>
     */
    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Log.i(TAG, "screenRatio=" + screenRatio);
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {
            float currentRatio = ((float) size.width) / size.height;
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }

        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    result = size;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 释放资源
     */
    private void release() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    /**
     * 对焦，在CameraActivity中触摸对焦
     */
    public void focus() {
        if (camera != null) {
            camera.autoFocus(null);
        }
    }

    /**
     * 开关闪光灯
     *
     * @return 闪光灯是否开启
     */
    public boolean switchFlashLight() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                return true;
            } else {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                return false;
            }
        }
        return false;
    }

    /**
     * 拍摄照片
     *
     * @param pictureCallback 在pictureCallback处理拍照回调
     */
    public void takePhoto(Camera.PictureCallback pictureCallback) {
        if (camera != null) {
            setCameraParams(camera, mScreenWidth, mScreenHeight);
            camera.takePicture(null, null, pictureCallback);
        }
    }

    public void startPreview() {
        if (camera != null) {
            camera.startPreview();
        }
    }

    public void stopPreview(){
        if (camera != null) {
            camera.stopPreview();
        }
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {

    }
}
