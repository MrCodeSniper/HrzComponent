package com.mujirenben.android.common.datapackage.bean;

/**
 * Created by mac on 2018/5/12.
 */

public class Const {

    public static String WECHAT_APPID="wxb82c21dcc13d3fa7";
    public static String WECHAT_SECRET="8fc3258d803db2fa6150a410b7a8b318";

    public static String SAVE_USER_DATA_SUCCESS="SAVE_USER_DATA_SUCCESS";
    public static String SAVE_SHIP_ADDRESS_SUCCESS="SAVE_SHIP_ADDRESS_SUCCESS";
    public static String DELETE_SHIP_ADDRESS_SUCCESS="DELETE_SHIP_ADDRESS_SUCCESS";

    public static int  TEMPLATE_BANNER=1;
    public static int  TEMPLATE_ICONS=2;
    public static int  TEMPLATE_TOUFU=3;
    public static int  TEMPLATE_NEWUSEREVENT=4;
    public static int  TEMPLATE_ACTIVITY_RANK=5;
    public static int  TEMPLATE_ACTIVITY_TICKET=6;

    public static int TEMPLATE_EMPTY=1000;
    public static int TEMPLATE_GOODS_DETAIL_BANNER=1001;
    public static int TEMPLATE_GOODS_DETAIL_DISCOUNT_PRICE=1002;
    public static int TEMPLATE_GOODS_DETAIL_PRE_PRICE=1003;
    public static int TEMPLATE_GOODS_DETAIL_DESCIPTION=1004;
    public static int TEMPLATE_GOODS_DETAIL_SERVICE=1005;
    //同一个模版
    public static int TEMPLATE_GOODS_DETAIL_TICKET=1006;
    public static int TEMPLATE_GOODS_DETAIL_SPEC=1007;
    public static int TEMPLATE_GOODS_DETAIL_PARAMS=1008;
    public static int TEMPLATE_GOODS_DETAIL_SHOPINFO=1009;
    public static int TEMPLATE_GOODS_DETAIL_GOODSPICS=1010;
    public static int TEMPLATE_GOODS_DETAIL_PRICE_TYPE_TWO=1011;
    public static int TEMPLATE_SEARCH_FAIL=100001;

    // 平台列表字符串
    public class PlatformName {
        public final static String ALL = "全部";
        public final static String HRZ = "自营";
        public final static String JINGDONG = "京东";
        public final static String TAOBAO = "淘宝";
        public final static String WEIPINHUI = "唯品会";
        public final static String MOGUJIE = "蘑菇街";
        public final static String ZIYING="自营";
        public final static String OFFLINE="线下";
    }

    /**
     * 目前正确的平台枚举
     */
    public class Platform {
        public final static int ALL = 0;
        public final static int HRZ = 1;
        public final static int TAOBAO = 2;
        public final static int JD = 3;
        public final static int VIP = 4;
        public final static int OFFLINE = 5;
        public final static int MGJ = 6;
    }

    /**
     * 用户等级
     */
    public class UserLevel {
        public final static int LEVEL_FANS = 0; // 粉丝
        public final static int LEVEL_VIP = 1; // 皇冠
        public final static int LEVEL_SHOP = 2; // 皇冠
    }

    /**
     * 数据库
     */
    public static final String DB_TYPE_CITY="DB_TYPE_CITY";
    public static final String DB_TYPE_MAIN="DB_TYPE_MAIN";

    /**
     * 收货地址
     */
    public static final int CHOOSE_SHIP_ADDRESS_RESULT_CODE=11;
    public static final String CHOOSE_SHIP_ADDRESS="选择收货地址";
    public static final int CHOOSE_SHIP_ADDRESS_REQUEST_CODE=10;
    public static final String CHOOSE_ADDRESS_STATE="choose_address_state";

}
