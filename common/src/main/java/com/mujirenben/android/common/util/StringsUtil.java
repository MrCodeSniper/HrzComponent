package com.mujirenben.android.common.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018\9\8 0008.
 */

public class StringsUtil {

    /**
     * JSON格式化工具
     * @param in
     * @return
     */
    public static String JSONTokener(String in) {
        // consume an optional byte order mark (BOM) if it exists
        if (in != null && in.startsWith("\ufeff")) {
            in = in.substring(1);
        }
        return in;
    }


    /**
     * 将淘宝的链接抽出商品的id
     * @param url
     * @return
     */
    public static String filterTaoBaoLink(String url){
        String pre_str=url.substring(0,url.indexOf(".htm?"));
        String regEx="[^0-9]";
        return pre_str.replaceAll(regEx, "");
    }


    /**
     * JSBRIDGE的文本 用来注入
     * @param context
     * @return
     */
    public static String getJsCode(Context context) {

        String str = "";
        try {
            InputStream is = context.getAssets().open("hrz_jsb_new.js");
            int lenght = 0;
            lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            str = new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }

}
