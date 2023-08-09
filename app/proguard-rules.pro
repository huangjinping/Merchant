# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\studio-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-ignorewarnings                # 抑制警告
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose             # true 混淆时是否记录日志
-dontskipnonpubliclibraryclasses

# 注解
-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# native 方法 For native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#序列化
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class * implements java.io.Serializable { *; }

#友盟统计混淆
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class [com.hdfex.merchantqrshow].R$*{
public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Hawk 混淆配置
-keep class com.orhanobut.**{ *;}

#aidl文件
-dontwarn android.content.pm.**
-keep class android.content.pm.** { *; }

# webView
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# okhttp
-keep class okhttp3.**{ *;}
-dontwarn okhttp3.**
-keep class okio.**{ *;}
-dontwarn okio.**

# glide 混淆配置
#-keep class  com.bumptech.**{ *;}
#-dontwarn com.bumptech.**
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#    **[] $VALUES;
#    public *;
#}

#Gson zxing 混淆配置
-keep class com.google.**{ *;}
-dontwarn com.google.**
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keepattributes Signature


#友盟
-keep class com.hdfex.merchantqrshow.bean.** { *; }
-dontwarn com.hdfex.merchantqrshow.bean.**


-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}



-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn org.apache.thrift.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}
-keep class com.megvii.** {*;}

-keep public class com.turui.bank.ocr.** { *; }
-dontwarn com.turui.bank.ocr.**


-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

#-keep public class com.umeng.message.example.example.R$*{
#   public static final int *;
#}

#避免log打印输出
 -assumenosideeffects class android.util.Log {
      public static *** v(...);
      public static *** d(...);
      public static *** i(...);
      public static *** w(...);
 }


 #友盟
 -keep class com.umeng.** { *; }
 -dontwarn com.umeng.**

 -keep class u.aly.** { *; }
 -dontwarn u.aly.**


-dontwarn com.tencent.mm.**
-keep class com.tencent.mm.**{*;}



-keep class com.hdfex.merchantqrshow.wxapi.WXEntryActivity
