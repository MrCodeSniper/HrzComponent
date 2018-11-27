package com.mujirenben.android.common.datapackage.resource;

import android.content.Context;
import android.util.Log;

import com.mujirenben.android.common.base.greendao.DaoMaster;
import com.mujirenben.android.common.base.greendao.DaoSession;
import com.mujirenben.android.common.datapackage.bean.Const;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by admin on 2017/3/10.
 * 简单封装数据库处理 准备中 默认为greendao支持
 */
@Singleton
public class DataBaseHelper {

    private static  final String  TAG = DataBaseHelper.class.getSimpleName();

    private   Context mContext;
    //留着涉及到资源的统一关闭回收
    private   DaoMaster.DevOpenHelper mHelper;
    private   DaoSession mDaoSession;


    private static HashMap<String,DaoSession> hashMap=new HashMap<>();
    private static  final String  DB_NAME_SHIP="ChinaCity.db";//数据库名称
    private static  final String  DB_NAME_MAIN="hrz-db";//数据库名称
    private static final String DB_BASE_PATH="/data/data/" + "com.mujirenben.liangchenbufu"
            + "/databases/";

    @Inject
    public DataBaseHelper(Context context) {
        this.mContext = context;
        copyAssetsSingleFile(DB_BASE_PATH,DB_NAME_SHIP);
     }

    private boolean copyAssetsSingleFile(String outPath, String fileName) {
        File file = new File(outPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("--Method--", "copyAssetsSingleFile: cannot create directory.");
                return false;
            }
        }
        try {
            InputStream inputStream = mContext.getAssets().open(fileName);
            File outFile = new File(file, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            // Transfer bytes from inputStream to fileOutputStream
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = inputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            inputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    public DaoSession getDaoSessionByType(String type){
        if(hashMap.get(type)==null){
            DaoMaster.DevOpenHelper mHelper =  new DaoMaster.DevOpenHelper(mContext,type== Const.DB_TYPE_CITY?DB_NAME_SHIP:DB_NAME_MAIN);
            Database db=mHelper.getWritableDb();
            hashMap.put(type,new DaoMaster(db).newSession());
        }
        return hashMap.get(type);
    }


    /**
     * 初始化Context对象
     * @param context
     */
    public void init(Context context){
        this.mContext = context;
    }

    /**
     * 设置debug模式开启或关闭，默认关闭
     * @param flag
     */
    public void setDebug(boolean flag){
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase(){
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession(){
        if (null != mDaoSession){
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public  void  closeHelper(){
        if (mHelper!=null){
            mHelper.close();
            mHelper = null;
        }
    }














}
