package com.mujirenben.android.xsh.constant;

public class Constants {

    public static final int TYPE_ALLIANCE_SHOPDETAIL_EMPTY=10000;
    public static final int TYPE_ALLIANCE_SHOPDETAIL_BANNER=10001;
    public static final int TYPE_ALLIANCE_SHOPDETAIL_SHOPNAME=10002;
    public static final int TYPE_ALLIANCE_SHOPDETAIL_SHOPDETAILPARAM=10003;
    public static final int TYPE_ALLIANCE_SHOPDETAIL_BUSSINESS_SITUATION=10004;
    public static final int TYPE_ALLIANCE_SHOPDETAIL_SHOPPICS=10005;
    public static final int TYPE_ALLIANCE_SHOPDETAIL_LOCATION=10006;
    public static final int TYPE_ALLIANCE_SHOPDETAIL_COMMONTICKET=10007;
    public static final int TYPE_ALLIANCE_SHOPDETAIL_REDPOCKET=10008;
    public static final int TYPE_ALLIANCE_SHOPDETAIL_SHOPMENU=10009;
    public static final int TYPE_ALLIANCEE_SHOPDETAIL_DISCOUNT_SHOPS=10010;


    public static final int TYPE_ALLIANCE_LINKLIST_HEADER = -1; // 顶部banner
    public static final int TYPE_ALLIANCE_LINKLIST_TO_CONFIRM_RATE = 1; // 对接人未确认佣金
    public static final int TYPE_ALLIANCE_LINKLIST_REFUSE_RATE = 2; // 对接人不同意佣金
    public static final int TYPE_ALLIANCE_LINKLIST_TO_VERIFY = 3; // 未审核
    public static final int TYPE_ALLIANCE_LINKLIST_VERIFY_SUCCESS = 4; // 审核通过
    public static final int TYPE_ALLIANCE_LINKLIST_VERIFY_FAILURE = 5; // 审核不通过
    public static final int TYPE_ALLIANCE_LINKLIST_TO_SIGN = 6; // 未签约
    public static final int TYPE_ALLIANCE_LINKLIST_TO_ACTIVITY = 7; // 未激活
    public static final int TYPE_ALLIANCE_LINKLIST_ACTIVITY_SUCCESS = 8; // 激活成功
    public static final int TYPE_ALLIANCE_LINKLIST_ACTIVITY_FAILURE = 9; // 激活失败
	public static final int TYPE_ALLIANCE_LINKLIST_HAS_REWARD = 10; // 开始收益
    public static final int TYPE_ALLIANCE_LINKLIST_UNKNOWN = 11; // 无法解析的类型


    public static final String ALLIANCE_REQUEST_SUCCESS ="10000";
    public static final String ALLIANCE_REQUEST_FAIL ="10001";
    public static final String ALLIANCE_AUTHORIAZE_FAIL ="10002";
    public static final String ALLIANCE_NEED_AUTHORIAZE  ="10003";
    public static final String ALLIANCE_PARAMS_INVALID ="10004";
    public static final String ALLIANCE_LOGIN_INVALID ="10005";
    public static final String ALLIANCE_SESSION_INVALID ="10006";
    public static final String ALLIANCE_NOFIND_SHOPS ="10007";
    public static final String ALLIANCE_NOFIND_PAYCODE ="10008";
    public static final String ALLIANCE_SYSTEM_ERROR ="90001";






    /**
     * intent传递常量
     */
    public final static class IntentConstant
    {
        public static final String	GOODS_LIST					= "com.mujirenben.goodslist";
        public static final String	JPUSH_ID					= "com.mujirenben.jpushreceiver";
        public static final String	SHOUYI_TITLE				= "com.mujirenben.shouyititle";
        public static final String	IS_ORDER_SUBMIT_SUCCESS		= "com.mujirenben.isordersubmitsuccess";
        public static final String	IS_SEARCH					= "com.mujirenben.IS_SEARCH";
        public static final String	CAT_ID						= "com.mujirenben.CAT_ID";
        public static final String	IS_FRISRFABU_JINRU			= "com.mujirenben.IS_FRISRFABU_JINRU";
        public static final String	CAMERA_ADD_HRD				= "com.mujirenben.CAMERA_ADD_HRD";
        public static final String	CAMERA_GOODS_ID				= "com.mujirenben.camera_goods_id";
        public static final String	XD_GOODS_ID					= "com.mujirenben.xd_goods_id";
        public static final String	EXTENDID					= "com.mujirenben.extendid";
        public static final String	IS_CAMERA_TYPE				= "com.mujirenben.isgoodstype";
        public static final String	HAVE_CAMERA_GOODS			= "com.mujirenben.ishavegoods";
        public static final String	CAMERA_GOODS				= "com.mujirenben.goods";
        public static final String	CAMERA_IMGLIST				= "com.mujirenben.cameraimglist";
        public static final String	IS_FROM_BUYSUCCESS			= "com.mujirenben.frombuysuccess";
        public static final String	PAY_SUCCESS					= "com.mujirenben.qrwebviewactivity.paysuccess";
        public static final String	PAY_FAIL					= "com.mujirenben.qrwebviewactivity.payfail";
        public static final String	QR_PRICE					= "com.mujirenben.qrwebviewactivity.price";
        public static final String	IS_QR_JINRU					= "com.mujirenben.qrjinru";
        public static final String	IS_TYG_JINRU				= "com.mujirenben.tygjinru";
        public static final String	DELETE_VIDEO				= "com.mujirenben.videodetaildelete";
        public static final String	BRAND_ID					= "com.mujirenben.brandfenleiactivity.storeid";
        public static final String	REMINDISHAVEHUIFU			= "com.mujirenben.smallbrandedit.selectcategory";
        public static final String	SMALLBRANDADDPROFORMBRAND	= "com.mujirenben.smallbrandedit.formbrand";
        public static final String	SMALLBRANDFENLEI_ISSELECT	= "com.mujirenben.smallbrandedit.selectcategory";
        public static final String	SMALLBRANDFENLEI_CATEGORY	= "com.mujirenben.fenleiactivty.category";
        public static final String	SMALLBRANDFENLEI_ISADD		= "com.mujirenben.fenleiactivty.add";
        public static final String	MSG_NAME					= "com.mujirenben.mefrag.name";								// 用户头像
        public static final String	avatar						= "com.mujirenben.mefrag.avatar";							// 用户头像
        public static final String	IS_LOGIN					= "com.mujirenben.mefrag.islogin";							// 是否登录
        public static final String	IS_PERSON					= "com.mujirenben.isPerson";								// 从个人中心进入
        public static final String	IS_TONGKUAN					= "com.mujirenben.istongkuan";								// 从同款视频进入
        public static final String	IS_BRAND_JINRU				= "com.mujirenben.firstfabuactivity";						// 店铺页进入
        public static final String	VIDEO_DETAIL				= "com.mujirenben.videodetailactivity.video";				// videoid
        public static final String	REMIND_JUMP					= "com.mujirenben.remind.jump";								// 消息jump
        public static final String	FENLEI						= "com.muirenben.fenleidetailactivity.fenlei";				// 分类id
        public static final String	FENLEI_ID					= "com.muirenben.fenleidetailactivity.fenleiid";			// 分类id
        public static final String	FENLEI_NAME					= "com.mujirenben.fenleidetailactivity.fenleiname";			// 分类name
        public static final String	USER_ID						= "com.mujirenben.persondetailactivity.userid";				// 用户id
        public static final String	SONCATEGORY					= "com.mujirenben.cantegorydetailActivity.sonCategoryList";	// 子分类集合
        public static final String	STOREID						= "com.mujirenben.storeActivity.storeid";					// 子分类集合
        public static final String	GOODID						= "com.mujirenben.goodActivity.goodsid";					// 商品ID
        public static final String	LINKURL						= "com.mujirenben.videoDetailActivity.linkurl";
        public static final String	IS_TK						= "com.mujirenben.AddProTbActivity.ISTK";
        public static final String	IS_URL						= "com.mujirenben.qr_url";
        public static final String	DELETE_FULI					= "com.mujirenben.fulivideodetailactivity.delete";			// 是否刷新个人页
        public static final String	VIDEO_PATH					= "com.mujirenben.cameraactivity.videopath";
        public static final String	IMG_PATH					= "com.mujirenben.cameraactivity.imagepath";
        public static final String	PERSON_JIESHAO				= "com.mujirenben.personsetting.jieshao";					// 个人介绍
        public static final String	PERSON_GENDER				= "com.mujirenben.personsetting.gender";					// 个人性别
        public static final String	TB_ORDERID					= "com.mujirenben.taobao.oderid";							// 淘宝订单ID
        public static final String	MESSAGE_USER_ID				= "com.mujirenben.message.userid";
        public static final String	MESSAGE_NAME				= "com.mujirenben.message.name";
        public static final String	ISPUTONG					= "com.mujirenben.cameraactivity.putongshipin";
        public static final String	SETTING_WEBNAME				= "com.mujirenben.webview.name";
        public static final String	NOTIFATION_NUM				= "com.mujirenben.index.notifation";						// 消息数量
        public static final String	MESSAGE_NUM					= "com.mujirenben.index.messaegnumn";						// 私信数量
        public static final String	SEARCH_TITLE				= "com.mujirneben.finalsearch.serchtitle";
        public static final String	STARTVIDEO_TYPE				= "com.mujirenben.videodetailActivity.type";
        public static final String	MY_CLOTHES					= "com.mujirenben.videodetailActivity.type";				// 自己衣服视频发布
        public static final String	SEARCH_TB					= "com.mujirenben.searchtaobaoActivity";					// 搜索淘宝
        public static final String	LINK_URL					= "com.mujirenben.searchtaobaoActivity.linkurl";			// 商品url
        public static final String	TB_PRICE					= "com.mujirenben.searchtaobaoActivity.price";
        public static final String	TB_URL						= "com.mujirenben.searchtaobaoActivity.url";
        public static final String	TB_NAME						= "com.mujirenben.searchtaobaoActivity.name";
        public static final String	SIZE						= "com.mujirenben.seearchtaobaoSize";						// 上传尺码
        public static final String	SIZE_ID						= "com.mujirenben.seearchtaobaoSize_id";					// 上传尺码
        public static final String	INTRODUCE					= "com.mujirenben.introduce";								// 介绍
        public static final String	IS_PRO_HIDE					= "com.mujirenben.is_pro_hide";								// 介绍
        public static final String	IS_CAMERA_ADDPRO			= "com.mujirenben.is_camera_addpro";						// 介绍
        public static final String	TYPE						= "com.mujirenben.uploadtype";								// 上传分类
        public static final String	CAMERA_TYPE					= "com.mujirenben.searchtaobaoActivity.cameratype";			// 上传视频类型
        public static final String	VIDEO_THUMB					= "com.mujirenben.moneyorderActivity.videothumb";
        public static final String	VIDEO_INTRODUCE				= "com.mujirenben.moneyorderActivity.introduce";
        public static final String	MONEY_ALL					= "com.mujirenben.getmoney.allmoney";						// 提现总额
        public static final String	DOWN_PRO					= "com.mujirenben.download";								// 下载进度
        public static final String	BUY_CAMERA					= "com.mujirenben.selectcameraactivity.buy";				// 进入购买列表是通过拍摄界面进入的
        public static final String	BUY_THUMB					= "com.mujirenben.cameraactivity.buygoods.thumb";
        public static final String	BUY_MIAOSHU					= "com.mujirenben.camearactitvity.buygoods.miaoshu";
        public static final String	IS_HONGREN					= "com.mujirenben.hongrenactivity";
        public static final String	IS_YUQI						= "com.mujirenben.yuqiactivity";							// 预期收益
        public static final String	UPDATE_TYPE					= "com.mujirenben.update.title";							// 修改类型，0为昵称，1位介绍
        public static final String	UPDATE_MESSAGE				= "com.mujirenben.update.message";
        public static final String	INTENT_TYPE					= "com.mujinrebn.itnent.type";								// 跳转类型
        public static final String	INTENT_ID					= "com.mujirenben.intent.id";								// 跳转ID
        public static final String	DETAITYPE					= "com.mujirenben.intent.DETAITYPE";						// 跳转类型
        public static final String	DETAURL						= "com.mujirenben.intent.DETAURL";							// 跳转链接
        public static final String	IS_TYG						= "com.mujirenben.intent.istiyanugan";						// 跳转ID
        public static final String	SEARCH_TXT					= "com.mujirenben.search.txt";
        public static final String	ZHUANTI_TYPE				= "com.mujirrenben.morezhuanti";							// 专题列表类型//0店铺专题，1:一级店铺推荐更多状体，2:网红更多专题
        public static final String	USERLIST_TYPE				= "com.mujirenben.userlisttype";							// 跳转用户列表类型，0,，网红更多用户，1关注列表。2热门用户,3粉丝列表
        public static final String	USERLIST_TITLE				= "com.mujirenben.usertitle";
        public static final String	TITLE						= "com.mujirenben.title";
        public static final String	USER_FANS					= "com.userlistactivity.fans";
        public static final String	IS_REGISTER					= "com.mujirenebn.register";
        public static final String	REGISTER_USERNAME			= "com.mujirenebn.register.username";
        public static final String	REGISTER_USERPWD			= "com.mujirenebn.register.pwd";
        public static final String	IS_FIRSTBANGDING			= "com.mujirenben.firstbangding";
        public static final String	IS_BANGDING					= "com.mujirenben.bangding";
        public static final String	IS_SETTING					= "com.mujirenben.setting";
        public static final String	IS_PWD_SET					= "com.mujirenben.settingpwd";
        public static final String	SMALLSHOPEDITSTATUS			= "com.mujirenben.shopeditstauts";
        public static final String	SMALLBRANDGOODS				= "com.mujirenben.smallshop.goods";
        public static final String	ZHUANI_SHARETITLE			= "com.mujirenben.shretitle";
        public static final String	ZHUANTI_SHARETHUMB			= "com.mujirenben.sharethumb";
        public static final String	ZHUANTI_SHARELINKURL		= "com.mujirenben.sharelinkurl";
        public static final String	ZHUANI_SHARETXT				= "com.mujirenben.sharetxt";
        public static final String	SHANGXIN_VIDEO_DETAIL		= "com.mujirenben.shangxinvideo.detail";
        public static final String	SHANGXIN_VIDEO_GOODS		= "com.mujirenben.shagnxinvideo.goods";
        public static final String	SMALLBRANDEDITPROADD		= "com.mujirenben.smallbrandeditproactivity.addpro";
        public static final String	SMALLBRANDPROLIYOU			= "com.muijirenben.smallbrandproliyou";
        public static final String	SMALLBRANDPRODETAIL			= "com.mujirenben.smallbrandprodetail";
        public static final String	XUANHUOPROTHUMB				= "com.mujirenben.submitxuanhuo.thumb";
        public static final String	XUANHUOPRODETAIL			= "com.mujirenben.submitxuanhuo.detail";
        public static final String	XUANHUOPROFANLI				= "com.mujirenben.submitxuanhuo.fanli";
        public static final String	XUANHUOPROFANLIHEIGHT		= "com.mujirenben.submitxuanhuo.fanliheight";
        public static final String	XUANHUOPROFILEP				= "com.mujirenben.submitxuanhuo.profilep";
        public static final String	XUANHUOPROFANLIALL			= "com.mujirenben.submitxuanhuo.fanliAll";
        public static final String	XUANHUOPROPRICE				= "com.mujirenben.submitxuanhuo.price";
        public static final String	TIXIANISSUCCESS				= "com.mujirenben.tixian";									// 提现
        public static final String	IS_MOKA						= "com.mujirenben.ismoka";									// 提现
        public static final String	WX_URL						= "com.mujirenben.wx.wxurl";								// 微信二维码链接
        public static final String	ISTAOKOULINGADD				= "com.mujirenben.ISTAOKOULINGADD";							// 淘口令跳转
        public static final String	ISTAOKOULINGBACK			= "com.mujirenben.ISTAOKOULINGBACK";						// 淘口令返回
        public static final String	ISWXIMG						= "com.mujirenben.ISWXIMG";									//
        public static final String	SEARCHRESULT				= "com.mujirenben.SEARCHRESULT";							//
        public static final String	ISCHANGEADDRESS				= "com.mujirenben.ISCHANGEADDRESS";							// 是否修改收货地址
        public static final String	ISTOBUY						= "com.mujirenben.ISTOBUY";									// 是否从购买跳转
        public static final String	MONEY						= "com.mujirenben.MONEY";									// 购买金额
        public static final String	ISHGPOP						= "com.mujirenben.ISHGPOP";									// 是皇冠pop
        public static final String	ISDZPOP						= "com.mujirenben.ISDZPOP";									// 是店主pop
        public static final String	ISKTDP						= "com.mujirenben.ISKTDP";									// 是店主开通店铺pop
        public static final String	MIAOSHU						= "com.mujirenben.MIAOSHU";									// 上传视频的描述
        public static final String	Flage						= "com.mujirenben.Flage";									// 是否从mainactivity和扫码进入
        public static final String	XINTIYANGUAN				= "com.mujirenben.XINTIYANGUAN";							// 是否从新体验馆进入
        public static final String	ALACCOUNT					= "com.mujirenben.ALACCOUNT";								// 用户支付宝账号
        public static final String	ALNAME						= "com.mujirenben.ALNAME";									// 用户姓名
        public static final String	REALNAME					= "com.mujirenben.REALNAME";								// 真实姓名
        public static final String	IDENTITYCARD				= "com.mujirenben.IDENTITYCARD";							// 身份证号码
        public static final String	ZFBJIAOCHENG				= "com.mujirenben.ZFBJIAOCHENG";							// 支付宝教程
        public static final String	WXJIAOCHENG					= "com.mujirenben.WXJIAOCHENG";								// 微信教程
        public static final String	TAOBAOOPENID				= "com.mujirenben.TAOBAOOPENID";							// 淘宝openID
        public static final String	WXUNIONID					= "com.mujirenben.WXUNIONID";								// 微信unionid
        public static final String	WXOPENID					= "com.mujirenben.WXOPENID";								// 微信openid
        public static final String	USERGUID					= "com.mujirenben.USERGUID";								// 用户guid
        public static final String	USERTOKEN					= "com.mujirenben.USERTOKEN";								// 用户usertoken
        public static final String	FIRSTNICK					= "com.mujirenben.FIRSTNICK";								// 用户firstlogin
        public static final String	UPDATEVERSON				= "com.mujirenben.UPDATEVERSON";							// version
        public static final String	DOWNLOADURL					= "com.mujirenben.DOWNLOADURL";								// download
        // url
        public static final String	WXHEAD						= "com.mujirenben.WXHEAD";									// 微信头像
        public static final String	WXNICK						= "com.mujirenben.WXNICK";									// 微信昵称
        public static final String	WXSEX						= "com.mujirenben.WXSEX";									// 微信性别
        public static final String	JDMAINURL					= "com.mujirenben.JDMAINURL";								// 京东链接
        public static final String	UPCity						= "com.mujirenben.UPCity";									// 京东链接
        public static final String	INVTYPE						= "com.mujirenben.INVCODE";									// 邀请码
        public static final String	THUMB1						= "com.mujirenben.THUMB1";									// 邀请码
        public static final String	THUMB2						= "com.mujirenben.THUMB2";									// 邀请码
        public static final String	THUMB3						= "com.mujirenben.THUMB3";									// 邀请码
        public static final String	JS							= "com.mujirenben.js";										// js
        public static final String	SQUSERNAME					= "com.mujirenben.SQUSERNAME";								// 扫码的用户名
        public static final String	SQHEADURL					= "com.mujirenben.SQHEADURL";								// 扫码人头像
        public static final String	SQMSG						= "com.mujirenben.SQMSG";									// 扫码信息
        public static final String	latitude						= "com.mujirenben.latitude";									// 经度
        public static final String	longitude						= "com.mujirenben.longitude";									// 纬度
        public static final String	MediaID						= "com.mujirenben.MediaID";									// 媒体ID

    }

    /**
     * 广播传递常量
     */
    public final static class BroadCast
    {
        public static final String	VIDEO_COMPLETE			= "com.mujirenben.jcvideoplayer.complete";
        public static final String	REFRESH_FAXIAN			= "com.mujirenben.faxianfragment.refreshfaxian";
        public static final String	MESSAGE_NUM				= "com.mujirenben.faxianfragment.message";		// 首页消息数量
        public static final String	SIZE_CLICK				= "com.mujirenben.sizeactivity.item";			// 尺寸点击
        public static final String	TYPE_SIZE				= "com.mujirenben.typeactivity.item";			// 类型点击
        public static final String	UPDATE_PRO				= "com.mujirenben.updatepro";					// 刷新数据
        public static final String	SON_TYPE				= "com.mujirenben.sontype";
        public static final String	UPDATE_DOW				= "com.mujirenben.main.download";				// 下载任务
        public static final String	IS_LOGIN				= "com.mujirenben.mefrag.login";				// 登录还是登出
        public static final String	READ_MSG				= "com.mujirenben.mefrag.read";
        public static final String	WHAT					= "com.mujirenben.mainactivity.waht";
        public static final String	ADD_LOGIN				= "com.mujirenben.usermanager";
        public static final String	UPLOAD_ERROR			= "com.mujirenben.upload_error";
        public static final String	SHANGXINVIDEO_REFRESH	= "com.mujirenben.shangxin.camera";
        public static final String	SMALLBRAND_ADDFENLEI	= "com.mujirenben.smallbrandfenlei.add";
        public static final String	MIAOSHU					= "com.mujirenben.cameraActivity.MIAOSHU";
    }






    public static final String MEDIA_ID="1000000";

    public static final String TAG="CHENHONG";

    public static final String BASE_PROTOCOL="https://";
//    public static final String BASE_HOST_TEST_CHUWEN="https://experiment.hdyl.net.cn";
    public static final String BASE_ALLIANCE_HOST="https://xdz.hdyl.net.cn";

    public static final String BASE_HOST_CHUWEN="xdzapi.hongrenzhuang.com";

    public static final String BASE_PORT=":8080";
    public static final String QRCODE_HOST="qr.hdyl.net.cn";

    public static int EXACT_SCREEN_HEIGHT;
    public static int EXACT_SCREEN_WIDTH;

    public static String		REQUEST_URL				= "http://apiv2.tlgn365.com/v1/";

    public static String		REQUEST_TAG				= "";
    public static String		REQUEST_PATH			= REQUEST_URL + REQUEST_TAG;













}
