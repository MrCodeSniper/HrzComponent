
##红人装推送sdk
-dontwarn ccom.hrz.push.**
-keep class com.hrz.push.** {*;}

##个推设置

-dontwarn com.igexin.**
-keep class com.igexin.** { *; }
-keep class org.json.** { *; }

-keep class android.support.v4.app.NotificationCompat {*;}
-keep class android.support.v4.app.NotificationCompat$Builder {*;}


##华为推送通道
-dontwarn com.huawei.hms.**
-keep class com.huawei.hms.** { *; }
-keep class com.huawei.android.** { *; }
-dontwarn com.huawei.android.**
-keep class com.hianalytics.android.** { *; }
-dontwarn com.hianalytics.android.**
-keep class com.huawei.updatesdk.** { *; }
-dontwarn com.huawei.updatesdk.**
-ignorewarning
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

##魅族推送通道
-keep class com.meizu.** { *; }
-dontwarn com.meizu.**

##小米推送通道
-keep class com.xiaomi.** { *; }
-dontwarn com.xiaomi.push.**
-keep class org.apache.thrift.** { *; }

##oppo推送通道
-keep class com.coloros.mcssdk.** { *; }
-dontwarn com.coloros.mcssdk.**