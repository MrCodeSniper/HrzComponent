package com.mujirenben.android.common.util.wxHelper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.ch.android.common.R;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.widget.DialogUtils;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoFileObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mujirenben.android.common.util.wxHelper.WxBitmapUtil.SHARE_IMG_TEMP_PATH;

/**
 * @author: panrongfu
 * @date: 2018/7/12 9:43
 * @describe: 第三方微信工具栏 分享图片大小不能大于32kb,在分享图片前需要检测是否已经获取了存储权限
 */
public class WeiXinHelper {

    /**上下文*/
    private Context mContext;
    /**第三方App和微信通信的openApi接口*/
    private IWXAPI wxAPI;
    /**分享标题*/
    private String title;
    /**分享描述*/
    private String description;
    /**分享多图url集合*/
    private String[] pictureThumbPaths;
    /**分享音乐URL*/
    private String musicUrl;
    /**分享音乐缩略图本地或者是网络图片*/
    private String musicThumbPath;
    /**分享视频URL*/
    private String videoUrl;
    /**分享视频缩略图本地或者是网络图片*/
    private String videoThumbPath;
    /**分享网页URL*/
    private String webPageUrl;
    /**分享网页缩略图本地或者是网络图片*/
    private String webPageThumbPath;
    /**小程序原始id*/
    private String miniUserName;
    /**小程序页面路径*/
    private String miniPath;
    /**加载框*/
    private DialogUtils dialogUtils;
    private boolean compress;
    /**分享图片*/
    private Bitmap shareBitmap;

    /**分享到哪个场景*/
    public enum ShareToType {
        /**会话页面*/
        SESSION,
        /**朋友圈*/
        TIMELINE,
        /**收藏*/
        FAVORITE
    }
    /**已经保存的图片个数*/
    private int saveImageCount=0;

    /**
     * 判断 用户是否安装微信客户端
     * @param context
     * @return 是否
     */
    public static boolean isWeixinAvilible(Context context) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 拉起微信登录
     */
    public void wxLogin() {

        if(isWeixinAvilible(mContext)){
            Logger.e("EVENTBUS发送微信信息");
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = UUID.randomUUID().toString();
            wxAPI.sendReq(req);
        }else {
            ArmsUtils.makeText(mContext,"请先安装微信客户端");
        }

    }

    /**
     * 分享文本类型
     * @param toType
     */
    public void shareTextTo(ShareToType toType) {

        //初始化一个WXTextObject对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = this.description;

        //用WXTextObject对象，初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        sendMessageToWx(msg, null, toType);
    }

    /**
     * 分享图片
     * @param toType
     */
    public void sharePictureTo(ShareToType toType) {

        if(!isWeixinAvilible(mContext)){
            ArmsUtils.makeText(mContext,"没有安装微信,不能使用分享功能");
            if(dialogUtils != null){
                dialogUtils.hide();
            }
            return;
        }
        //每次分享把原来保存的零时文件删除
        WxBitmapUtil.deleteDir();
//        if(null == pictureThumbUrls && pictureThumb!= null){
//            Bitmap thumbBmp = Bitmap.createScaledBitmap(pictureThumb, thumbWidth, thumbHeight, true);
//            //初始化WXImageObject和WXMediaMessage对象
//            WXImageObject imgObj = new WXImageObject(thumbBmp);
//            WXMediaMessage msg = new WXMediaMessage();
//            msg.mediaObject = imgObj;
//            sendMessageToWx(msg, thumbBmp, toType);
//            return;
//        }
        Observable
                .fromArray(pictureThumbPaths)
                .map(url -> WxBitmapUtil.getLocalOrNetBitmap(url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    //先进行图片压缩
                   // Bitmap compressBitmap = WxBitmapUtil.compressImage(bitmap);
                    //保存图片到本地
                    WxBitmapUtil.saveBitmap(mContext,bitmap,System.currentTimeMillis()+"");
                    saveImageCount++;
                    if(saveImageCount == pictureThumbPaths.length){
                        saveImageCount = 0;
                        startSharePictureToWx(toType);
                    }
                });
    }

    /**
     * 分享图片（bitmap形式）
     * @param toType
     */
    public void shareBitmapTo(ShareToType toType){
            Bitmap thumbBmp =  WxBitmapUtil.compressImage(shareBitmap);
            //初始化WXImageObject和WXMediaMessage对象
            WXImageObject imgObj = new WXImageObject(thumbBmp);
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;

            if (thumbBmp != null) {
                // msg.thumbData = WxBitmapUtil.getByteFromBitmap(thumbBitmap,true);
                msg.setThumbImage(thumbBmp);
            }
            //构造一个req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            //transaction字段用于唯一标识一个请求
            req.transaction = UUID.randomUUID().toString();
            req.message = msg;
            shareToType(req, toType);
            //调用api接口发送数据到微信
            wxAPI.sendReq(req);

            if(dialogUtils != null){
                //关闭对话框
                dialogUtils.hide();
            }
    }

    /**
     * 开始分享图片
     * @param toType
     */
    private void startSharePictureToWx(ShareToType toType) {
        ArrayList<Uri> uris = WxBitmapUtil.getFileUriArray(mContext,SHARE_IMG_TEMP_PATH);
        Log.e("pictureToWx",uris.toString());
        //创建用于存放图片的Uri数组
        switch (toType){
            //分享到朋友圈
            case TIMELINE:
                nativeShareToTimeLine(uris);
                break;
            //分享到指定好友
            case SESSION:
                nativeShareToSession(uris);
                break;
            default:
                break;
        }
    }

    /**
     * 拉起微信朋友圈发送多张图片
     * @param uri 图片本地路径转成uri
     */
    private void nativeShareToTimeLine(ArrayList<Uri> uri){
        if(dialogUtils != null){
            dialogUtils.hide();
        }
        Intent shareIntent = new Intent();
        //1调用系统分析
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra("Kdescription", description);
        //单张图片
        //shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        //shareIntent.setType("image/jpeg");
        //多张图片
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uri);
        shareIntent.setType("image/*");
        //指定选择微信
        ComponentName componentName = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareToTimeLineUI");
        shareIntent.setComponent(componentName);
        //开始分享
        mContext.startActivity(shareIntent);

    }

    /**
     * 拉起微信发送多张图片给好友
     * @param uris 图片本地路径转成uris
     * */
    private void nativeShareToSession(ArrayList<Uri> uris){
        if(dialogUtils != null){
            dialogUtils.hide();
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        //单图分享
        //shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        //shareIntent.setType("image/jpeg");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
       // intent.putExtra(Intent.EXTRA_TEXT, description);
        mContext.startActivity(intent);

    }

    /**
     * 分享音乐类型
     * 分享至微信的音乐，直接点击好友会话或朋友圈下的分享内容会跳转至第三方APP，
     * 点击会话列表顶部的音乐分享内容将跳转至微信原生音乐播放器播放
     * @param toType
     */
    public void shareMusicTo(ShareToType toType) {
        //初始化WXMusicObject和WXMediaMessage对象
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = this.musicUrl;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        createBitmap(msg,musicThumbPath,toType);
    }

    /**
     * 分享视频类型(暂时不支持分享视频)
     * @param toType
     */
    public void shareVideoTo(ShareToType toType) {
        //初始化WXVideoFileObject对象 填写视频地址
        WXVideoFileObject video = new WXVideoFileObject();
        video.filePath = this.videoUrl;

        //用WXVideoFileObject对象 初始化WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(video);
        msg.mediaObject = video;
        createBitmap(msg,videoThumbPath,toType);
    }

    /**
     * 分享网页类型
     * @param toType
     */
    public void shareWebPageTo(final ShareToType toType) {
        //初始化WXWebpageObject对象 填写网页URL
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = this.webPageUrl;

        //用WXVideoFileObject对象 初始化WXMediaMessage对象
        final WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = webPage;
        createBitmap(msg,webPageThumbPath,toType);
    }

    /**
     * 创建bitmap从网络或是本地bitmap超过32k需要压缩
     * @param msg
     * @param bitmapPath
     * @param toType
     */
    private void createBitmap(final WXMediaMessage msg,String bitmapPath, final ShareToType toType) {

        //如果没有bitmap,且图片URL不为空，则获取网络图片
        if(!TextUtils.isEmpty(bitmapPath)){
            Observable.just(bitmapPath)
                      .map(url->WxBitmapUtil.getLocalOrNetBitmap(url))
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(bitmap -> {
                          Bitmap thumbBmp  = WxBitmapUtil.compressImage(bitmap);
                          bitmap = null;
                          sendMessageToWx(msg, thumbBmp, toType);
                      });
        }else {
            sendMessageToWx(msg, null, toType);
        }
    }

    /**
     * 分享小程序
     * 目前支持分享到会话
     */
    public void shareMiniProgram() {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        //兼容低版本的网页链接
        miniProgramObj.webpageUrl = this.webPageUrl;
        //正式版:0，测试版:1，体验版:2
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;
        // 小程序原始id
        miniProgramObj.userName = this.miniUserName;
        //小程序页面路径
        miniProgramObj.path = this.miniPath;

        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.mediaObject = miniProgramObj;

        sendMessageToWx(msg, null, ShareToType.SESSION);
    }

    /**
     *打开小程序
     */
    public void openMiniProgram(){
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = miniUserName; // 填小程序原始id
        req.path = miniPath;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        wxAPI.sendReq(req);
    }

    /**
     * 发送数据到微信
     * @param msg
     * @param thumbBitmap
     * @param toType
     */
    private void sendMessageToWx(WXMediaMessage msg, Bitmap thumbBitmap, ShareToType toType) {
        if(dialogUtils != null){
            //关闭对话框
            dialogUtils.hide();
        }
        msg.title = this.title;
        msg.description = this.description;
        if (thumbBitmap != null) {
            msg.thumbData = WxBitmapUtil.bitmap2Bytes(thumbBitmap,32000);
           // msg.setThumbImage(thumbBitmap);
        }
        thumbBitmap = null;
        //构造一个req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求
        req.transaction = UUID.randomUUID().toString();
        req.message = msg;
        shareToType(req, toType);
        //调用api接口发送数据到微信
        wxAPI.sendReq(req);

    }

    /**
     * 根据toType判断用户分享到的场景
     * @param req
     * @param toType
     */
    private void shareToType(SendMessageToWX.Req req, ShareToType toType) {

        switch (toType) {
            case SESSION:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case TIMELINE:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case FAVORITE:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            default:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
        }
    }

    private WeiXinHelper(Builder builder) {
        wxAPI = WXAPIFactory.createWXAPI(builder.mContext, Const.WECHAT_APPID, true);
        wxAPI.registerApp(Const.WECHAT_APPID);
        this.mContext = builder.mContext;
        this.title = builder.title;
        this.description = builder.description;
        this.pictureThumbPaths = builder.pictureThumbPaths;
        this.musicUrl = builder.musicUrl;
        this.musicThumbPath = builder.musicThumbPath;
        this.videoUrl = builder.videoUrl;
        this.videoThumbPath = builder.videoThumbPath;
        this.webPageUrl = builder.webPageUrl;
        this.webPageThumbPath = builder.webPageThumbPath;
        this.miniUserName = builder.miniUserName;
        this.miniPath = builder.miniPath;
        this.shareBitmap = builder.shareBitmap;
        dialogUtils = new DialogUtils(mContext, R.layout.common_loading_toast,"");
        dialogUtils.show();
    }

    public static Builder getBuilder(Context cxt) {
        return new Builder(cxt);
    }

    public static class Builder {

        /**上下文*/
        private final Context mContext;
        /**分享标题*/
        private String title;
        /**分享描述*/
        private String description;
        /**分享多图url集合*/
        private String[] pictureThumbPaths;
        /**分享音乐URL*/
        private String musicUrl;
        /**分享音乐缩略图本地或者是网络图片*/
        private String musicThumbPath;
        /**分享视频URL*/
        private String videoUrl;
        /**分享视频缩略图本地或者是网络图片*/
        private String videoThumbPath;
        /**分享网页URL*/
        private String webPageUrl;
        /**分享网页缩略图本地或者是网络图片*/
        private String webPageThumbPath;
        /**小程序原始id*/
        private String miniUserName;
        /**小程序页面路径*/
        private String miniPath;
        /**分享图片*/
        private Bitmap shareBitmap;

        public Builder(Context cxt) {
            this.mContext = cxt;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPictureThumbPaths(String[] pictureThumbPaths) {
            this.pictureThumbPaths = pictureThumbPaths;
            return this;
        }

        public Builder setMusicUrl(String musicUrl) {
            this.musicUrl = musicUrl;
            return this;
        }

        public Builder setMusicThumbPath(String musicThumbPath) {
            this.musicThumbPath = musicThumbPath;
            return this;
        }

        public Builder setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public Builder setVideoThumbPath(String videoThumbPath) {
            this.videoThumbPath = videoThumbPath;
            return this;
        }

        public Builder setWebPageUrl(String webPageUrl) {
            this.webPageUrl = webPageUrl;
            return this;
        }

        public Builder setWebPageThumbPath(String webPageThumbPath) {
            this.webPageThumbPath = webPageThumbPath;
            return this;
        }

        public Builder setMiniUserName(String miniUserName) {
            this.miniUserName = miniUserName;
            return this;
        }

        public Builder setMiniPath(String miniPath) {
            this.miniPath = miniPath;
            return this;
        }

        public Builder setShareBitmap(Bitmap shareBitmap) {
            this.shareBitmap = shareBitmap;
            return this;
        }

        public WeiXinHelper build() {
            return new WeiXinHelper(this);
        }
    }
}
