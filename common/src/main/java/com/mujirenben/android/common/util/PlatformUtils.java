package com.mujirenben.android.common.util;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/29.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.text.TextUtils;
import android.widget.ImageView;

import com.ch.android.common.R;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.entity.SearchPlatformItem;

import java.util.ArrayList;
import java.util.List;

public class PlatformUtils {

    public static String changePlatformIdToStr(int platformId) {
        String platformName = "";
        switch (platformId) {
            case Const.Platform.ALL:
                platformName = Const.PlatformName.ALL;
                break;
            case Const.Platform.HRZ:
                platformName = Const.PlatformName.HRZ;
                break;
            case Const.Platform.TAOBAO:
                platformName = Const.PlatformName.TAOBAO;
                break;
            case Const.Platform.JD:
                platformName = Const.PlatformName.JINGDONG;
                break;
            case Const.Platform.VIP:
                platformName = Const.PlatformName.WEIPINHUI;
                break;
            case Const.Platform.MGJ:
                platformName = Const.PlatformName.MOGUJIE;
                break;
        }
        return platformName;
    }


    public static String changePlatformIdToStrForCategory(int platformId) {

        String platformName = "";
        switch (platformId) {
            case Const.Platform.ALL:
                platformName = Const.PlatformName.ALL;
                break;
            case Const.Platform.HRZ:
                platformName = Const.PlatformName.HRZ;
                break;
            case Const.Platform.TAOBAO:
                platformName = Const.PlatformName.TAOBAO;
                break;
            case Const.Platform.JD:
                platformName = Const.PlatformName.JINGDONG;
                break;
            case Const.Platform.VIP:
                platformName = Const.PlatformName.WEIPINHUI;
                break;
            case Const.Platform.OFFLINE:
                platformName = Const.PlatformName.OFFLINE;
                break;
            case Const.Platform.MGJ:
                platformName = Const.PlatformName.MOGUJIE;
                break;
        }
        return platformName;
    }

    /**
     * 平台代码
     * 平台（0：全部，1:自营(红人装) 2：淘宝，3：京东，4：唯评会，5：线下 6:蘑菇街）
     * @param platform
     * @return
     */
    public static int getPlatformCode(String platform) {
        if (Const.PlatformName.TAOBAO.equals(platform)) {
            return Const.Platform.TAOBAO;
        } else if (Const.PlatformName.JINGDONG.equals(platform)) {
            return Const.Platform.JD;
        } else if (Const.PlatformName.WEIPINHUI.equals(platform)) {
            return Const.Platform.VIP;
        } else if (Const.PlatformName.MOGUJIE.equals(platform)){
            return Const.Platform.MGJ;
        }
        return Const.Platform.ALL;
    }

    public static String getPlantForm(int plantformId) {
        String plantform = "";
        switch (plantformId) {
            case Const.Platform.TAOBAO:
                plantform = Const.PlatformName.TAOBAO;
                break;
            case Const.Platform.JD:
                plantform = Const.PlatformName.JINGDONG;
                break;
            case Const.Platform.VIP:
                plantform = Const.PlatformName.WEIPINHUI;
                break;
            case Const.Platform.MGJ:
                plantform = Const.PlatformName.MOGUJIE;
                break;
            default:
                plantform = Const.PlatformName.HRZ;
                break;
        }
        return plantform;
    }


    /**
     * 获取平台信息列表，用于下拉菜单
     * @return
     */
    public static List<SearchPlatformItem> getSearchPlatformList() {
        List<SearchPlatformItem> platformItemList = new ArrayList<SearchPlatformItem>();
        SearchPlatformItem platformAll = new SearchPlatformItem(Const.PlatformName.ALL,
                R.drawable.search_platform_all_large, R.drawable.search_platform_all_small);
        SearchPlatformItem platformJD = new SearchPlatformItem(Const.PlatformName.JINGDONG,
                R.drawable.search_platform_jingdong_large, R.drawable.search_platform_jingdong_small);
        SearchPlatformItem platformTaobao = new SearchPlatformItem(Const.PlatformName.TAOBAO,
                R.drawable.search_platform_taobao_large, R.drawable.search_platform_taobao_small);
        SearchPlatformItem platformWeipinhui = new SearchPlatformItem(Const.PlatformName.WEIPINHUI,
                R.drawable.search_platform_weipinhui_large, R.drawable.search_platform_weipinhui_small);
        SearchPlatformItem platformMgj = new SearchPlatformItem(Const.PlatformName.MOGUJIE,
                R.drawable.search_platform_mgj_large, R.drawable.search_platform_mgj_small);

        platformItemList.add(platformAll);
        platformItemList.add(platformJD);
        platformItemList.add(platformTaobao);
        platformItemList.add(platformWeipinhui);
        platformItemList.add(platformMgj);

        return platformItemList;
    }


    /**
     * 根据平台设置tag图片
     * @param platform
     */
    public static void initPlatformTag(String platform, ImageView iv_platform_icon){
        if(TextUtils.equals(platform, Const.Platform.HRZ + "")){
            iv_platform_icon.setImageResource(R.drawable.icon_ziying);
        }else if(TextUtils.equals(platform, Const.Platform.TAOBAO + "")){
            iv_platform_icon.setImageResource(R.drawable.icon_taobao);
        }else if(TextUtils.equals(platform, Const.Platform.JD + "")){
            iv_platform_icon.setImageResource(R.drawable.icon_jingdong);
        }else if(TextUtils.equals(platform, Const.Platform.VIP + "")){
            iv_platform_icon.setImageResource(R.drawable.icon_weipinhui);
        }else if(TextUtils.equals(platform, Const.Platform.MGJ + "")){
            iv_platform_icon.setImageResource(R.drawable.icon_mogujie);
        }
    }

}
