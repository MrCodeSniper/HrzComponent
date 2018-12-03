
-dontwarn java.lang.invoke.**
-dontwarn java.lang.invoke.**

-dontwarn com.mujirenben.android.xsh.constant.**
-keep class com.mujirenben.android.xsh.constant.** {*;}


-dontwarn com.mujirenben.android.xsh.**
-keep class com.mujirenben.android.xsh.entity.** {*;}
-keep class com.mujirenben.android.xsh.utils.** {*;}
-keep class com.mujirenben.android.xsh.widget.** {*;}
-keep class com.mujirenben.android.xsh.service.** {*;}
-keep class com.mujirenben.android.xsh.activity.** {*;}
-keep class com.mujirenben.android.xsh.fragment.** {*;}
-keep class com.mujirenben.android.xsh.adapter.** {*;}

#amap
-keep class com.amap.api.maps.**{*;}
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
-keep class com.amap.api.services.**{*;}
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}

-keep class org.apache.**{*;}

#-libraryjars libs/xUtils-2.6.14.jar
-keep class com.lidroid.** { *; }

#xutils混淆
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }
-keep public class * extends com.lidroid.xutils.**
-keep public interface org.xutils.** {*;}
-dontwarn org.xutils.**

# Retrofit
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
# okhttp
-dontwarn okio.**

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

#ijkplayer
-keep class tv.danmaku.ijk.media.player.** {*;}
-keep class tv.danmaku.ijk.media.player.IjkMediaPlayer{*;}
-keep class tv.danmaku.ijk.media.player.ffmpeg.FFmpegApi{*;}

-keepattributes Signature
    -keep class sun.misc.Unsafe { *; }
    -keep class com.taobao.** {*;}
    -keep class com.alibaba.** {*;}
    -keep class com.alipay.** {*;}
    -dontwarn com.taobao.**
    -dontwarn com.alibaba.**
    -dontwarn com.alipay.**
    -keep class com.ut.** {*;}
    -dontwarn com.ut.**
    -keep class com.ta.** {*;}
    -dontwarn com.ta.**
    -keep class org.json.** {*;}
    -keep class com.ali.auth.**  {*;}


   # 微信支付
   -dontwarn com.tencent.mm.**
   -dontwarn com.tencent.wxop.stat.**
   -keep class com.tencent.mm.** {*;}
   -keep class com.tencent.wxop.stat.**{*;}

   -dontwarn org.apache.http.**
   -keep class org.apache.http.** {*;}


