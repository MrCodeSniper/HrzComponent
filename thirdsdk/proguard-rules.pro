# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

##对外类

#-keep public class com.mujirenben.android.thirdsdk.alibabaSDK.AlibabaConstant
#-keep public class com.mujirenben.android.thirdsdk.alibabaSDK.AlibabaSDK
#-keep public class com.mujirenben.android.thirdsdk.JdSdk.JdSdkRouter
#-keep public class com.mujirenben.android.thirdsdk.weipinhuiSDK.WeiPinHuiSDK
-keep class com.kepler.jd.Listener.OpenAppAction

##京东开普勒相关混淆代码开始##
-keep class com.kepler.**{*;}
-dontwarn com.kepler.**
-keep class com.jingdong.jdma.**{*;}
-dontwarn com.jingdong.jdma.**
-keep class com.jingdong.crash.**{*;}
-dontwarn com.jingdong.crash.**
-keep class com.kepler.jd.Listener.**{*;}
##京东开普勒相关混淆代码结束##



#阿里百川混淆

-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.alibaba.** {*;}
-dontwarn com.alibaba.**
-keep class com.alipay.** {*;}
-dontwarn com.alipay.**
-keep class com.taobao.** {*;}
-dontwarn com.taobao.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
-keep class mtopsdk.** {*;}
-dontwarn mtopsdk.**
-keep class org.json.** {*;}
-keep class com.ali.auth.**  {*;}

##支付相关混淆代码开始##
-dontwarn com.alimama.**
-dontwarn com.ali.auth.**
-dontwarn com.alibaba.**
-keep class com.alimama.** {*;}
-keeppackagenames com.alimama.tunion.sdk.**
-keeppackagenames com.alimama.tunion.sdk.**
-keep class com.alimama.tunion.sdk.** {
    public <fields>;
    public <methods>;
}

#支付宝支付混淆代码
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
##支付相关混淆代码结束##