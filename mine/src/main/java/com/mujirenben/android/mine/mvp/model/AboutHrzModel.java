package com.mujirenben.android.mine.mvp.model;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.common.util.AppInfoUtils;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.mine.mvp.contract.AboutHrzContract;
import com.mujirenben.android.mine.mvp.model.bean.AppUpgradeInfo;
import com.mujirenben.android.mine.mvp.model.service.AppUpgradeService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AboutHrzModel extends BaseModel implements AboutHrzContract.Model {

    private static final String TAG = "AboutHrzModel";

    @Inject
    Application mApplication;

    @Inject
    OkHttpClient mOkHttpClient;

    private DownloadTask mDownloadTask;

    @Inject
    public AboutHrzModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Bitmap getAppQrCode() {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(bitmap);
        c.drawColor(Color.GREEN);
        return bitmap;
    }

    @Override
    public String getAppVersion() {
        return AppInfoUtils.getVersionName(mApplication);
    }

    @Override
    public void checkUpgrade() {

    }

    @Override
    public String getHotline() {
        return "111111";
    }

    @Override
    public Bitmap getOfficialAccountQrCode() {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(bitmap);
        c.drawColor(Color.RED);
        return bitmap;
    }

    @Override
    public Observable<AppUpgradeInfo> getAppUpgradeInfo() {
        return mRepositoryManager.obtainRetrofitService(AppUpgradeService.class).getAppUpgradeInfo();
    }

    @Override
    public void startDownload(String uri, DownloadListener listener) {
        LogUtil.i(TAG, "startDownload");
        try {
            downloadFile(uri, isExistDir("hrz_temp2"), "hrz.apk", listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopDownload() {
        LogUtil.i(TAG, "stopDownload");
        if (mDownloadTask != null) {
            mDownloadTask.cancel();
        }
    }

    @Override
    public void pauseDownload() {

    }

    @Override
    public void resumeDownload() {

    }

    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    public interface DownloadListener {
        void onDownloadStart();
        void onDownloadCompleted(String apkPath);
        void onDownloadFailed();
        void onDownloadUpgrade(long downloadedSize, long totalSize);
        void onDownloadCanceled();
    }

    private void downloadFile(String url, String parentPath, String fileName,
                              DownloadListener listener) {
        LogUtil.i(TAG, "downloadFile");

        mDownloadTask = new DownloadTask.Builder(url, parentPath, fileName)
                .setMinIntervalMillisCallbackProcess(50)
                .setPassIfAlreadyCompleted(false)
                .build();
        mDownloadTask.enqueue(new com.liulishuo.okdownload.DownloadListener() {

            private long mDownloadedSize = 0;
            private long mTotalSize = 0;

            @Override
            public void taskStart(@NonNull DownloadTask task) {
                LogUtil.i(TAG, "taskStart");
                listener.onDownloadStart();
            }

            @Override
            public void connectTrialStart(@NonNull DownloadTask task,
                                          @NonNull Map<String, List<String>> requestHeaderFields) {
                LogUtil.i(TAG, "connectTrialStart");
            }

            @Override
            public void connectTrialEnd(@NonNull DownloadTask task, int responseCode,
                                        @NonNull Map<String, List<String>> responseHeaderFields) {
                LogUtil.i(TAG, "connectTrialEnd");
            }

            @Override
            public void downloadFromBeginning(@NonNull DownloadTask task,
                                              @NonNull BreakpointInfo info,
                                              @NonNull ResumeFailedCause cause) {
                mDownloadedSize = 0;
                mTotalSize = info.getTotalLength();

                LogUtil.i(TAG, "downloadFromBeginning >> mTotalSize=" + mTotalSize);
            }

            @Override
            public void downloadFromBreakpoint(@NonNull DownloadTask task,
                                               @NonNull BreakpointInfo info) {
                mDownloadedSize = info.getTotalOffset();
                mTotalSize = info.getTotalLength();

                LogUtil.i(TAG, "downloadFromBeginning >> mTotalSize=" + mTotalSize + ", mDownloadedSize=" + mDownloadedSize);

                listener.onDownloadUpgrade(mDownloadedSize, info.getTotalLength());
            }

            @Override
            public void connectStart(@NonNull DownloadTask task, int blockIndex,
                                     @NonNull Map<String, List<String>> requestHeaderFields) {
                LogUtil.i(TAG, "connectStart >> blockIndex=" + blockIndex);
            }

            @Override
            public void connectEnd(@NonNull DownloadTask task, int blockIndex,
                                   int responseCode,
                                   @NonNull Map<String, List<String>> responseHeaderFields) {
                LogUtil.i(TAG, "connectEnd >> blockIndex=" + blockIndex);
            }

            @Override
            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {
                LogUtil.i(TAG, "fetchStart >> blockIndex=" + blockIndex);

            }

            @Override
            public void fetchProgress(@NonNull DownloadTask task,
                                      int blockIndex, long increaseBytes) {
                mDownloadedSize += increaseBytes;
                listener.onDownloadUpgrade(mDownloadedSize, mTotalSize);
                LogUtil.i(TAG, "fetchProgress >> blockIndex=" + blockIndex + ", mDownloadedSize = " + mDownloadedSize);
            }

            @Override
            public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {
                LogUtil.i(TAG, "fetchEnd >> blockIndex=" + blockIndex);
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task,
                                @NonNull EndCause cause,
                                @Nullable Exception realCause) {
                LogUtil.i(TAG, "taskEnd >> cause=" + cause.name() + " realCase=" + (realCause == null ? null : realCause.getMessage()));
                if (cause.equals(EndCause.COMPLETED)) {
                    listener.onDownloadCompleted(task.getFile().getAbsolutePath());
                } else if (cause.equals(EndCause.CANCELED)) {
                    listener.onDownloadCanceled();
                } else {
                    listener.onDownloadFailed();
                }

                mDownloadTask = null;
            }
        });
    }

}
