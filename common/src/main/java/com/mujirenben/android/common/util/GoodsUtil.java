package com.mujirenben.android.common.util;

import com.mujirenben.android.common.datapackage.bean.Const;

public class GoodsUtil {

    public static String getPlatformById(int platformId) {
        switch (platformId) {
            case Const.Platform.ALL:
                return Const.PlatformName.ALL;
            case Const.Platform.JD:
                return Const.PlatformName.JINGDONG;
            case Const.Platform.TAOBAO:
                return Const.PlatformName.TAOBAO;
            case Const.Platform.VIP:
                return Const.PlatformName.WEIPINHUI;
            case Const.Platform.HRZ:
                return Const.PlatformName.HRZ;
            case Const.Platform.MGJ:
                return Const.PlatformName.MOGUJIE;
        }
        return Const.PlatformName.ALL;
    }
}
