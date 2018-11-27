package com.mujirenben.android.common.util.wxHelper;

import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * @author: panrongfu
 * @date: 2018/7/12 14:44
 * @describe:
 */

public interface WeiXinDelegate {

    void onCreate(Bundle savedInstanceState);
    void onReq(BaseReq req);
    void onResp(BaseResp resp);
}
