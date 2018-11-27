package com.mujirenben.android.common.util;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/14.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.text.TextPaint;

public class TextDivideRowUtils {

    /**
     * 截取文本相对于画板来说第一行,第二行的文本
     * @param inText
     * @param lineWidth
     * @param paint
     * @return 返回第一行、第二行的文本
     */
    public static String[] CurStringToTwoPartByTextPaint(String inText, int lineWidth, TextPaint paint) {

        if (inText != null && inText.length() > 0 && paint != null) {
            String[] strs = new String[2];
            int widthPostion = paint.breakText(inText, true, lineWidth, null);
            strs[0] = inText.substring(0, widthPostion);
            if (widthPostion < inText.length()) {
                strs[1] = inText.substring(widthPostion, inText.length());
            }
            else {
                strs[1] = "";
            }
            return strs;
        }
        return null;
    }



}
