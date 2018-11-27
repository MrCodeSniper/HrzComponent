package com.mujirenben.android.common.util;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/23.Best Wishes to You!  []~(~▽~)~* Cheers!


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {


    private DateTimeUtil() {
    }

    public static final String yyyyMM = "yyyy-MM";
    public static final String yyMMdd = "yy-MM-dd";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyYearMMMonthddDay = "yyyy年MM月dd日";

    /**
     * String转DATE型 支持yy-MM-dd; yyyy-MM-dd; yyyy-MM-dd HH:mm; yyyy-MM-dd
     * HH:mm:ss
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {
        if (date == null || date.length() == 0)
            throw new IllegalArgumentException("Date must not be null");
        Date date2 = null;
        date = date.trim();
        if (date.length() == 10) {
            date2 = parseDate(date, DateTimeUtil.yyyyMMdd);
        } else if (date.length() == 19) {
            date2 = parseDate(date, DateTimeUtil.yyyyMMddHHmmss);
        } else if (date.length() == 16) {
            date2 = parseDate(date, DateTimeUtil.yyyyMMddHHmm);
        } else if (date.length() == 8) {
            date2 = parseDate(date, DateTimeUtil.yyMMdd);
        } else {
            throw new IllegalArgumentException("日期格式不对，必须为：yyyy-MM-dd或者yyyy-MM-dd HH:mm:ss");
        }
        return date2;
    }


    public static String parseTimestamp(long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date(timeStamp));   // 时间戳转换成时间
        return sd;
    }

    public static String parseTimestamp(long timeStamp,String pattern){
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date(timeStamp));   // 时间戳转换成时间
        return sd;
    }


    /**
     * 自定义日期格式把String转换成DATE型
     *
     * @param date
     * @param parsePattern
     *            自定义格式
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date, String parsePattern) throws ParseException {
        if (date == null || parsePattern == null)
            throw new IllegalArgumentException("Date and parsePattern must not be null");
        SimpleDateFormat parser = new SimpleDateFormat(parsePattern);
        Date date1 = parser.parse(date);
        return date1;
    }

    /**
     * Date转String,支持格式:yyyy-MM-dd
     *
     * @param d
     * @return
     */
    public static String formatMonth(Date d) {
        return formatDate(d, DateTimeUtil.yyyyMM);
    }

    /**
     * Date转String,支持格式:yyyy-MM-dd
     *
     * @param d
     * @return
     */
    public static String formatDate(Date d) {
        return formatDate(d, DateTimeUtil.yyyyMMdd);
    }


    /**
     * Date转String,支持格式:yyyy-MM-dd HH:mm
     *
     * @param d
     * @return
     */
    public static String formatShortDateTime(Date d) {
        return formatDate(d, DateTimeUtil.yyyyMMddHHmm);
    }


    /**
     * Date转String,支持格式:yyyy-MM-dd HH:mm:ss
     *
     * @param d
     * @return
     */
    public static String formatDateTime(Date d) {
        return formatDate(d, DateTimeUtil.yyyyMMddHHmmss);
    }


    /**
     * Date转String
     *
     * @param d
     * @param parsePattern
     *            自定义格式
     * @return
     */
    public static String formatDate(Date d, String parsePattern) {
        if (d == null)
            throw new IllegalArgumentException("Date and parsePattern must not be null");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(parsePattern);
        String sd = simpleDateFormat.format(d);
        return sd;
    }


    /**
     * 获取当前日期
     *
     * @return Date
     */
    public static Date getTime() {
        Calendar now = Calendar.getInstance();
        return now.getTime();
    }


    /**
     * 获取当前年
     *
     * @return int
     */
    public static int getYear() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        return now.get(Calendar.YEAR);
    }


    /**
     * 获取当前月
     *
     * @return int
     */
    public static int getMonth() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        return now.get(Calendar.MONTH) + 1;
    }


    /**
     * 获取当前日期
     *
     * @return String
     */
    public static String getNowTime() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtil.yyyyMMddHHmmss);
        String str = formatter.format(now.getTime());
        return str;
    }


    /**
     * 获取当前日期
     *
     * @return String
     */
    public static String getNowTime(String fmt) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        String str = formatter.format(now.getTime());
        return str;
    }

    /**
     * /**
     * 获取当前月份，如：2018-08
     *
     * @return
     */
    public static String getMonthTime() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(yyyyMM);
        String str = formatter.format(now.getTime());
        return str;
    }


    /**
     * 获取"上午好", "下午好", "晚上好"
     *
     * @return String
     */
    public static String getAMPM() {
        String[] hello = { "上午好", "下午好", "晚上好" };
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int index = 0;
        if (hour >= 12 && hour < 18)
            index = 1;
        if (hour >= 18 && hour <= 23)
            index = 2;
        return hello[index];
    }
    /**
     * 获取星期
     *
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /* 日期操作方法 */
    public static Date addYears(Date date, int amount) {
        return add(date, 1, amount);
    }
    public static Date addMonths(Date date, int amount) {
        return add(date, 2, amount);
    }
    public static Date addWeeks(Date date, int amount) {
        return add(date, 3, amount);
    }
    public static Date addDays(Date date, int amount) {
        return add(date, 5, amount);
    }
    public static Date addHours(Date date, int amount) {
        return add(date, 11, amount);
    }
    public static Date addMinutes(Date date, int amount) {
        return add(date, 12, amount);
    }
    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, 14, amount);
    }
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }
    public static Date setYears(Date date, int amount) {
        return set(date, 1, amount);
    }
    public static Date setMonths(Date date, int amount) {
        return set(date, 2, amount);
    }
    public static Date setDays(Date date, int amount) {
        return set(date, 5, amount);
    }
    public static Date setHours(Date date, int amount) {
        return set(date, 11, amount);
    }
    public static Date setMinutes(Date date, int amount) {
        return set(date, 12, amount);
    }
    public static Date setSeconds(Date date, int amount) {
        return set(date, 13, amount);
    }
    public static Date setMilliseconds(Date date, int amount) {
        return set(date, 14, amount);
    }
    private static Date set(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setLenient(false);
            c.setTime(date);
            c.set(calendarField, amount);
            return c.getTime();
        }
    }
    /**
     * 比较两个日期相差天、分、秒
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String compare(Date startDate, Date endDate) {
        StringBuilder sb = new StringBuilder();
        if (null != startDate && null != endDate) {
            long beginTime = startDate.getTime();
            long endTime = endDate.getTime();
            long diff = endTime - beginTime;
            long dayMS = 86400000; // 一天=毫秒
            long hourMS = 3600000; // 小时=毫秒
            long minuteMS = 60000; // 分钟=毫秒
            long secondMS = 1000; // 秒=毫秒
            if (diff >= dayMS) {
                sb.append((diff / dayMS) + "天");
                long modNum = diff % dayMS; // 余数
                if (modNum >= 0) {
                    diff = modNum;
                }
            }
            if (diff >= hourMS) {
                sb.append((diff / hourMS) + "小时");
                long modNum = diff % hourMS; // 余数
                if (modNum >= 0) {
                    diff = modNum;
                }
            }
            if (diff >= minuteMS) {
                sb.append((diff / minuteMS) + "分");
                long modNum = diff % minuteMS; // 余数
                if (modNum > 0) {
                    sb.append((modNum / secondMS) + "秒");
                }
            } else {
                sb.append((diff / secondMS) + "秒");
            }
        }
        return sb.toString();
    }





}