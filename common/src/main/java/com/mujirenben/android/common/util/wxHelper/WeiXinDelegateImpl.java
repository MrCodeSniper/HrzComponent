package com.mujirenben.android.common.util.wxHelper;

import android.os.Bundle;
import android.util.Log;

import com.mujirenben.android.common.event.WeiXinEvent;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;

/**
 * @author: panrongfu
 * @date: 2018/7/12 14:47
 * @describe:
 */

public class WeiXinDelegateImpl implements WeiXinDelegate{


    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onReq(BaseReq req) {
        Logger.e("微信分享");
    }

    @Override
    public void onResp(BaseResp resp) {
        //分享
        if(resp.getType()== ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){
            Log.i("ansen","微信分享操作.....");
            WeiXinEvent weiXinEvent =new WeiXinEvent(WeiXinEvent.WEIXIN_TYPE_SHARE, resp.errCode,"");
            EventBus.getDefault().post(weiXinEvent);
        //登陆
        }else if(resp.getType()==ConstantsAPI.COMMAND_SENDAUTH){
            Log.i("ansen", "微信登录操作.....");
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            WeiXinEvent weiXinEvent =new WeiXinEvent(WeiXinEvent.WEIXIN_TYPE_LOGIN, resp.errCode, authResp.code);
            EventBus.getDefault().post(weiXinEvent);
        }
    }
}
