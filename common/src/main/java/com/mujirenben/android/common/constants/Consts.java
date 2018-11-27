package com.mujirenben.android.common.constants;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/14.Best Wishes to You!  []~(~▽~)~* Cheers!

/**
 * 尽量后面路由化
 */
public class Consts {

    // 服务器协议
    public static final String PROTOCROL = "http://";
    // 公司外网正式服务器
    public static String OFFICIAL_SERVER = "apix2.hongrenzhuang.com";
    // 公司外网测试服务器
    public static String TEST_SERVER = "192.168.0.4:9000";

    //支付宝拉新
    public static String VIP_PULL_NEW_URL = "hrzrouter://hrzapp/app/webSearch?url=http://apihelper.hongrenzhuang.com/liehuo_4_180831/#/&titleName=烈火计划";
    //京东购物协议
    public static String HRZ_JD_READ_AGGREEMENT="hrzrouter://hrzapp/app/webSearch?url=http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2#/ShoppingNotes/ShoppingNotes&titleName=京东用户协议";
    /**红人装用户协议*/
    public static String HRZ_USER_AGREE_URL = "http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2#/my/protocol/protocol";
    /**微信提现教程*/
    public static String HRZ_WITHDRAW_WX_URL = "http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2#/my/Towithdraw/Towithdrawwx";
    /**支付宝提现教程*/
    public static String HRZ_WITHDRAW_ALIPAY_URL = "http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2#/my/Towithdraw/Towithdrawzfb";
    /**提现说明*/
    public static String HRZ_WITHDRAW_DES_URL = "http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2#/my/deposit/deposit";
    /**体验馆入场*/
    public static String HRZ_TI_YAN_GUAN_URL = "http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2#/my/ibon/ibon";
    /**商学院*/
    public static String HRZ_SCHOOL_URL = "http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2#/my/Video/Video";
    /**常见问题*/
    public static String HRZ_FAQ_URL = "http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2#/CommonQuestion/CommonQuestion";
    /**我的活动*/
    public static String HRZ_MY_ACTIVITY_URL = "http://"+Consts.OFFICIAL_SERVER +"/index.html?id=2#/index";
    /**邀请二维码*/
    public static String HRZ_INVITE_QR_CODE_URL = "http://imgcdn.tlgn365.com/2018-08-31/26da276b-c520-4c38-a9b5-65349f3c521d.png";

    public static final String REQUEST_SUCCESS = "00000";
    public static final String REQUEST_FAIL = "11111";

    //包装成路由模式
    public static String getHrzRouterUrl(String url,String titleName){
        return "hrzrouter://hrzapp/app/webSearch?url="+url+"&titleName="+titleName;
    }

    //支付宝支付切换沙箱环境
    public static final boolean USE_SAND_BOX_ALIPAY = false;
    //进行灰度测试
    public static final boolean USE_GRAY_TEST = false;


    public static class PHONE_SYSTEM{
        public static final int ANDROID=1;
        public static final int IOS=2;
    }


    //主页TAB
    public static class HOME_TAB{
        public static final String TAB_HOME="主页";
        public static final String TAB_VIP="会员";
        public static final String TAB_DISCOVER="发现";
        public static final String TAB_SHOPALLIANCE="商家联盟";
        public static final String TAB_MINE="我的";
        public static final String TAB_CATEGORY_RESULT="分类";
    }

    //缺省页样式
    public static class STAND_BY_VIEW{
        public static final String STATE_NODATA="无数据";
        public static final String STATE_NETERROR="网络错误";
        public static final String STATE_NETOUTTIME="网络超时";
    }

    /**
     *   搜索商品列表请求参数
     */
    public static class SORT_GOODS_TYPE{
        public static final int SORTTYPE_ABOVE=1;//综合排序
        public static final int SORTTYPE_SALE=2;//销量
        public static final int SORTTYPE_NEW=3;//新品
        public static final int SORTTYPE_PRICE=4;//价格
    }

    public static class SORT_GROUP_BY{
        public static final int SORT_UP=2;//升序
        public static final int SORT_DOWN=1;//降序
    }

    public static class ERROR_STR{
        //自己定义无数据
        public static final String DATA_EMPTY="数据为空";
        public static final String DATA_PARSE_ERROR="数据解析错误";
        public static final String NETWORK_PROBLEM="网络好像出了点问题";
        public static final String NETWORK_UNENABLE="当前网络不可用";
        public static final String NETWORK_TIMEOUT="请求网络超时";
        public static final String NETWORK_CONNECTION_EXCEPTION="服务器连接异常";

        public static final String DATA_GET_ERROR="数据获取异常";
        public static final String SERVER_ERROR="服务器出了点问题";
        public static final String REQUEST_ADDRESS_NOTEXIST="请求地址不存在";
        public static final String REQUEST_REJECT_BY_SERVER="请求被服务器拒绝";
        public static final String REQUEST_REDIRECT="请求被重定向到其他页面";
    }

    public static class  HRZ_SELF_ORDER_STATUS{
        public static final int PAY_ING=0;
        public static final int ALREADY_PAY=1;
        public static final int PAY_FAIL=2;
    }

    public static class  HRZ_SELF_ORDER_STATUS_STR{
        public static final String PAY_ING="支付中";
        public static final String ALREADY_PAY="已支付";
        public static final String PAY_FAIL="支付失败";
    }

    public static class HRZ_ORDER_STATUS{
        public static final int ORDER_SUCCESS=1;
        public static final int ORDER_FINISH=2;
        public static final int ORDER_INVALID=3;
    }

    public static class HRZ_ORDER_STATUS_STR{
        public static final String ORDER_SUCCESS="订单成功";
        public static final String ORDER_FINISH="订单结算";
        public static final String ORDER_INVALID="订单失效";
    }

    public static final String SET_TABLAYOUT_INDEX ="set_tabLayout_index";
    public static final String SHOW_UPGRADE_DIALOG="show_upgrade_dialog";
    public static final String DISMISS_UPGRADE_DIALOG="dismiss_upgrade_dialog";

    public static final String GOODS_ID_INTENT_STR = "itemId";
    public static final String GOODS_NUM_IID_INTENT_STR = "num_iids";
    public static final String PLATFORM_ID_INTENT_STR = "platformId";
    public static final String ROUTER_FROM="routerFrom";

    public static final String CAN_WITHDRAW_KEY = "can_withdraw_key";
    public static final String QRCODE_HOST_ORDER="qr.hdyl.net.cn";
    public static final String QRCODE_HOST_DETAIL="share.hdyl.net.cn";
    public static final String QRCODE_HOST_TIYAN = "hongrenzhuang.com";
    public static final String QRCODE_HOST_WEB = Consts.OFFICIAL_SERVER;
    public static final String CHENHOGN_TAG="CHENHONG";
    public static final String QR_CODE_PRICE_KEY = "qrCodePrice";
    public static final String QR_CODE_CONTENT_KEY = "qrCodeContent";
    public final static String WEB_TYPE_KEY = "webType";//网页类型
    public final static String WEB_TITLE_TAG="titleName";
    public final static String PARAM_URL = "param_url"; // 打开的url
    public final static String PARAM_INJECT_JS = "param_inject_js"; //是否需要注入jsbridge
    public static final String HRZ_ROUTER_BUNDLE="HrzRouterBundle";

    /**登录来源*/
    public static final String LOGIN_SOURCE_KEY="login_source";
    /**手机正则*/
    public static final String PHONE_NUMBER_REG ="^1(3[0-9]|4[4-9]|5[0-35-9]|7[0135-8]|8[0-9])\\d{8}$";

    public static final String MINI_PROGRAM_ID = "gh_5b42e98cd45e";

    public static final String MINI_PROGRAM_PATH = "pages/Router/Router";

    //sp
    public static final String NOT_FIRST_TIME_SHARE="notFirstTimeShare";

}
