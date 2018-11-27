package com.mujirenben.android.common.util;

import android.content.Context;
import android.text.TextUtils;

import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.user.LoginDataManager;

/**
 * @author: panrongfu
 * @date: 2018/9/11 11:25
 * @describe:
 */

public class MiniProgramHelper {

    public static String getParameters(Context context,String shopId,String otherParams){

        String mediaUserId = LoginDataManager.getsInstance(context.getApplicationContext()).getMediaId();
        String mid = "88888888";
        String mTags = LoginDataManager.getsInstance(context.getApplicationContext()).getWeixinUnionId();
        String miniPath ;
        if(!TextUtils.isEmpty(shopId)){
            miniPath = Consts.MINI_PROGRAM_PATH+"?sid="+shopId+"&mediaUserId="+mediaUserId+"&mid="+mid+"&mTags="+mTags;
        }else {
            miniPath = Consts.MINI_PROGRAM_PATH+otherParams+"&mediaUserId="+mediaUserId+"&mid="+mid+"&mTags="+mTags;
        }
        return miniPath;
    }
}
