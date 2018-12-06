

-keep class com.mujirenben.android.vip.mvp.model.** {*;}
-keep class com.mujirenben.android.vip.mvp.ui.widget.** {*;}


-keep class cn.bingoogolapple.bgabanner.BGABanner
-keep class com.gcssloop.widget.RCImageView
-keep class com.mujirenben.android.common.widget.HrzHeadersView
-keep class com.mujirenben.android.common.widget.indicatorLayout.IndicatorLayout
-keep class com.mujirenben.android.common.widget.progress.CircleProgress
-keep class com.mujirenben.android.common.widget.switcher.AdvTextSwitcher
-keep class me.jessyan.retrofiturlmanager.**
-keep class cn.bingoogolapple.bgabanner.BGAViewPager

-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# If you use the byType method to obtain Service, add the following rules to protect the interface:
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# If single-type injection is used, that is, no interface is defined to implement IProvider, the following rules need to be added to protect the implementation
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider
