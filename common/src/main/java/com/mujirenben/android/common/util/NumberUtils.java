package com.mujirenben.android.common.util;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/22.Best Wishes to You!  []~(~▽~)~* Cheers!


import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {

    public static String doubleToString(double number) {
        return String.valueOf(number);
    }


    public static String floatToString(float number) {
        return String.valueOf(number);
    }

    //两个Double数相减
    public static String sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return doubleToString(b1.subtract(b2).doubleValue());
    }


    //两个float数相减
    public static String sub(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return doubleToString(b1.subtract(b2).floatValue());
    }

    /**
     * double 转 string 去掉后面锝0
     * @param i
     * @return
     */
    public static String doubleToStr(double i){
        String s = String.valueOf(i);
        if(s.indexOf(".") > 0){
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return s;
    }


    public static String subForFloat(Float v1, Float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return floatToString(b1.subtract(b2).floatValue());
    }

    public static String subForDif(Double v1, Float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return floatToString(b1.subtract(b2).floatValue());
    }


    public static String intChange2Str(int number) {
        String str = "";
        if (number <= 0) {
            str = "";
        } else if (number < 10000) {
            str = number + "";
        } else {
            double d = (double) number;
            double num = d / 10000;//1.将数字转换成以万为单位的数字

            BigDecimal b = new BigDecimal(num);
            double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//2.转换后的数字四舍五入保留小数点后一位;
            str = f1 + "万";
        }
        return str;
    }


    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * double保留两位小数
     */
    public static String doubleTwoDot(double num){
        DecimalFormat df = new DecimalFormat("#.00");
        return  df.format(num);
    }




}
