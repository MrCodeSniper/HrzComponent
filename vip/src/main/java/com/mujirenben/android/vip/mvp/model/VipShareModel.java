package com.mujirenben.android.vip.mvp.model;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.vip.mvp.contract.VipShareContract;

import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VipShareModel extends BaseModel implements VipShareContract.Model {

    @Inject
    public VipShareModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void saveSharingImage(Bitmap image, SaveCallback cb, boolean toShare) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                try {
                    String path = saveBitmap(image, toShare);
                    emitter.onNext(path);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (cb != null) {
                    cb.onSaveStarted();
                }
            }

            @Override
            public void onNext(String path) {
                if (cb != null) {
                    cb.onSaveCompleted(path);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (cb != null) {
                    cb.onSaveFailed();
                }
            }

            @Override
            public void onComplete() {

            }
        });
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
            return targetFile.getAbsolutePath();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
