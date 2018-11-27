package com.mujirenben.android.common.util.wxHelper;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.common.internal.ByteStreams.copy;

/**
 * @author: panrongfu
 * @date: 2018/7/13 20:13
 * @describe: 微信分享图片工具类
 */

public class WxBitmapUtil {

    public static String SHARE_IMG_TEMP_PATH = Environment.getExternalStorageDirectory()+ "/hrz/sharePicTemp/";
    public static int BITMAP_SIZE = 32;
    /**
     * 创建文件夹
     * @param dirName 文件夹名称
     * @return 文件夹路径
     * @throws IOException
     */
    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SHARE_IMG_TEMP_PATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    /**
     * 将图片压缩保存到文件夹
     * @param bm
     * @param picName
     */
    public static void saveBitmap(Context context, Bitmap bm, String picName) {
        try {
            // 如果没有文件夹就创建一个程序文件夹
//            if (!isFileExist("")) {
//                File tempf = createSDDir("");
//            }
            File fileDir = new File(SHARE_IMG_TEMP_PATH);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            File f = new File(fileDir.getAbsolutePath(), picName + ".jpg");
            Log.e("filepath",f.getAbsolutePath());
            //如果该文件夹中有同名的文件，就先删除掉原文件
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);



            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

//  MediaStore.Images.Media.insertImage(context.getContentResolver(),f.getPath(),f.getName(),"");
//
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                Uri uri = FileProvider.getUriForFile(
//                        context,
//                        context.getPackageName() + ".fileprovider",
//                        f);
//                //保存图片后发送广播通知更新数据库
//                MediaStore.Images.Media.insertImage(context.getContentResolver(),f.getPath(),f.getName(),"");
//                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
//            }else {
//                Uri uri = Uri.fromFile(f);
//                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
//            }
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(f);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定路径下的图片
     * @param dirPath
     * @return
     */
    public static List<String> getFilePaths(String dirPath){
        File fileDir = new File(dirPath);
        File[] subFile = fileDir.listFiles();
        List<String> paths = new ArrayList<>();
        for(File file:subFile){
            paths.add(file.getAbsolutePath());
        }
        return paths;
    }
    /**
     * 获取指定路径下文件的URI
     * @param dirPath
     * @param context
     * @return uriArrays
     */
    public static ArrayList<Uri> getFileUriArray(Context context,String dirPath){
        File fileDir = new File(dirPath);
        File[] subFile = fileDir.listFiles();
        ArrayList<Uri> uriArray = new ArrayList<>();
        for(File file:subFile){
            Log.e("Uri",file.getAbsolutePath());
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//               Uri uri = FileProvider.getUriForFile(
//                        context,
//                        context.getPackageName() + ".fileprovider",
//                        file);
//               uriArray.add(uri);
//            }else {
//                Uri uri = Uri.fromFile(file);
//               // Log.e(">>>Uri",u)
//                uriArray.add(uri);
//            }

            Uri uri = Uri.fromFile(file);
            uriArray.add(uri);
        }
        return uriArray;
    }

    /**
     * 把Bitmap转成byte[]
     * @param bmp
     * @return
     */
    public static byte[] getByteFromBitmap(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        }  else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas =  new Canvas(localBitmap);
        while ( true) {
            localCanvas.drawBitmap(bmp,  new Rect(0, 0, i, j),  new Rect(0, 0,i, j),  null);
            if (needRecycle){
                bmp.recycle();
            }
            ByteArrayOutputStream localByteArrayOutputStream =  new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                Log.e("getByteFromBitmap",arrayOfByte.length+"");
                return arrayOfByte;
            }  catch (Exception e) {
                // F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     * @param bitmap
     * @param maxkb
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb&& options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    /**
     * 获取本地或服务器图片
     * @param url
     * @return
     */
    public static Bitmap getLocalOrNetBitmap(String url){

        Bitmap bitmap;
        InputStream in;
        BufferedOutputStream out;
        try{
            in = new BufferedInputStream(new URL(url).openStream(), 32);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 32);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 质量压缩 并返回Bitmap
     * @param image 要压缩的图片
     * @return 压缩后的图片
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > BITMAP_SIZE) {
            baos.reset();// 重置baos即清空baos
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        Log.e("compressImage",baos.toByteArray().length+"");
        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 质量压缩
     * @param bitmap
     * @param picName
     */
    public static void compressImageByQuality(final Bitmap bitmap,String picName) {
        // 如果没有文件夹就创建一个程序文件夹
        if (!isFileExist("")) {
            try {
                File tempf = createSDDir("");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        File f = new File(SHARE_IMG_TEMP_PATH, picName + ".JPEG");
        // 如果该文件夹中有同名的文件，就先删除掉原文件
        if (f.exists()) {
            f.delete();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 500) {
            // 重置baos即让下一次的写入覆盖之前的内容
            baos.reset();
            // 图片质量每次减少5
            options -= 5;
            // 如果图片质量小于10，则将图片的质量压缩到最小值
            if (options < 0) {
                options = 0;
            }
            // 将压缩后的图片保存到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 如果图片的质量已降到最低则，不再进行压缩
            if (options == 0) {
                break;
            }
        }
        // 将压缩后的图片保存的本地上指定路径中
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(SHARE_IMG_TEMP_PATH, picName + ".JPEG"));
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * 通过uri获取图片
     *
     * @param uri
     */
    public Bitmap getBitmapFormUri(Activity ac, Uri uri) throws IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1)){
            return null;
        }
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    /**
     * 判断改文件是否是一个标准文件
     * @param fileName 判断的文件路径
     * @return 判断结果
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(SHARE_IMG_TEMP_PATH + fileName);
        file.isFile();
        return file.exists();
    }

    /**
     * 删除指定文件夹中的所有文件
     */
    public static void deleteDir() {
        File dir = new File(SHARE_IMG_TEMP_PATH);
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteDir();
            }
        }
        dir.delete();
    }

    /**
     * 删除指定文件
     * @param file
     */
    public static void deleteFile(File file) {
        // 判断文件是否存在
        if (file.exists()) {
            // 判断是否是文件
            if (file.isFile()) {
                // delete()方法 你应该知道 是删除的意思;
                file.delete();
                // 否则如果它是一个目录
            } else if (file.isDirectory()) {
                // 声明目录下所有的文件 files[];
                File files[] = file.listFiles();
                // 遍历目录下所有的文件
                for (int i = 0; i < files.length; i++) {
                    // 把每个文件 用这个方法进行迭代
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.i("TAG", "文件不存在！");
        }
    }

    /**
     * 删除指定文件
     * @param fileName
     */
    public static void delFile(String fileName) {
        File file = new File(SHARE_IMG_TEMP_PATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    /**
     * 通过uri获取file
     * @param uri
     * @param context
     * @return
     */
    public static File getFileByUri(Uri uri,Context context) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
            //Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }



    public static String saveBitmap(Bitmap bmp, boolean toShare) throws Exception {

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "hongrenzhuang" + File.separator + "sharePic";

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
