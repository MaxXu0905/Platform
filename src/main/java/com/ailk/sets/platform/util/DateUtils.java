/**
 * author :  lipan
 * filename :  DateUtils.java
 * create_time : 2014年4月23日 下午3:12:44
 */
package com.ailk.sets.platform.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ailk.sets.platform.util.sparseArray.SparseIntArray;

/**
 * @author : lipan
 * @create_time : 2014年4月23日 下午3:12:44
 * @desc : 日期工具类
 * @update_time :
 * @update_desc :
 * 
 */
public class DateUtils
{

    public static final String DATE_FORMAT_1 = "HH:mm yyyy-MM-dd";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_4 = "yyyy-MM-dd 23:59";

    public static final int MAX_DAYS_BIG_MONTH = 31; // 大月天数
    public static final int MAX_DAYS_SMALL_MONTH = 30; // 小月天数
    public static final int MAX_DAYS_LEAP_YEAR = 29; // 闰年2月最大天数
    public static final int MAX_DAYS_NON_LEAP_YEAR = 28; // 平年2月最大天数

    /**
     * 获得指定格式的时间字符串
     * 
     * @param timestamp
     *            时间戳
     * @param pattern
     *            时间表达式
     * @return 时间字符串
     */
    public static String getMonthStr(long timestamp, String pattern)
    {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTimeInMillis(timestamp);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.CHINA);
        return formatter.format(cal.getTime());
    }
    
    /**
     * 获得指定格式的时间字符串
     * 
     * @param timestamp
     *            时间戳
     * @param pattern
     *            时间表达式
     * @return 时间字符串
     */
    public static String getDateStr(Date date, String pattern)
    {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(date);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.CHINA);
        return formatter.format(cal.getTime());
    }

    /**
     * 获得指定格式的时间字符串
     * 
     * @param pattern
     *            时间表达式
     * @return 时间字符串
     */
    public static String getDateStr(String pattern)
    {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.CHINA);
        return formatter.format(cal.getTime());
    }

    /**
     * 判断指定日期是否为闰年
     * 
     * @param date
     *            指定的日期，null时返回 false
     * @return 是否为闰年
     */
    public static boolean isLeap(Date date)
    {
        boolean isLeap = false;
        if (null != date)
        {
            Calendar cal = Calendar.getInstance(Locale.CHINA);
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
                isLeap = true;
        }
        return isLeap;
    }

    /**
     * 获得时间单位的值
     * 
     * @param calendar 日期
     * @return
     */
    public static int getCalVal(Calendar calendar, int type)
    {
        if (type == Calendar.MONTH) // 月份+1
        {
            return calendar.get(type) + 1;
        }
        return calendar.get(type);
    }

    /**
     * 获得指定日期所在年的每个月的最大天数
     * 
     * @return
     */
    public static SparseIntArray getMonthMaxDays(Calendar calendar)
    {
        SparseIntArray maxDays = new SparseIntArray();

        Integer[] big = { 1, 3, 5, 7, 8, 10, 12 };
        Integer[] small = { 4, 6, 9, 11 };
        List<Integer> bigList = Arrays.asList(big); // 大月
        List<Integer> smallList = Arrays.asList(small); // 小月

        if (calendar == null)
        {
            calendar = Calendar.getInstance();
        }

        for (int i = 1; i <= 12; i++)
        {
            if (bigList.contains(i))
            {
                maxDays.put(i, MAX_DAYS_BIG_MONTH);
            } else if (smallList.contains(i))
            {
                maxDays.put(i, MAX_DAYS_SMALL_MONTH);
            } else
            {
                if (isLeap(calendar.getTime()))
                {
                    maxDays.put(i, MAX_DAYS_LEAP_YEAR);
                } else
                {
                    maxDays.put(i, MAX_DAYS_NON_LEAP_YEAR);
                }
            }
        }
        return maxDays;
    }

    /**
     * 获得当前时间戳
     * 
     * @return 当前时间戳
     */
    public static Timestamp getCurrentTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * 获得当前毫秒数
     * 
     * @return 当前毫秒数
     */
    public static long getCurrentMillis()
    {
        return System.currentTimeMillis();
    }
    
    /**
     * 获得指定的时间戳
     * 
     * @return 当前时间戳
     */
    public static Timestamp getTimestamp(String dateStr , String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        try
        {
            return new Timestamp(formatter.parse(dateStr).getTime());
        } catch (Exception e)
        {
            return null;
        }
    }
    
    
    /**
     * Date ——> Millis
     * @param dateStr
     * @param format
     * @return
     */
    public static long getDateMillis(String dateStr , String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        try
        {
            return formatter.parse(dateStr).getTime();
        } catch (ParseException e)
        {
            return 0;
        }
    }
    
    /**
     * Timestamp 转 Date
     * @param timestamp
     * @return
     */
    public static Date timestamp2Date(Timestamp timestamp)
    {
       return new Date(timestamp.getTime());
    }
    
    /**
     * Date 转  Timestamp 
     * @param date
     * @return
     */
    public static Timestamp date2Timestamp(Date date)
    {
        return new Timestamp(date.getTime());
    }
    
    
    /**
     * Millis 转  Timestamp 
     * @param date
     * @return
     */
    public static Timestamp millis2Timestamp(Long millis)
    {
        return new Timestamp(millis);
    }
    
    /**
     * 获得指定日期 偏移天数的日期
     * @param date 指定日期
     * @param offsetDay 偏移天数(正数为向前，负数为向后)
     * @return
     */
    public static Date getOffsetDate(Date date , int offsetDay)
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, offsetDay);
        return calendar.getTime();
    }
    
    /**
     * 获得指定日期 偏移天数的日期
     * @param date 指定日期
     * @param offsetDay 偏移天数(正数为向前，负数为向后)
     * @return
     */
    public static Timestamp getOffsetDate(Timestamp timestamp , int offsetDay)
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(timestamp2Date(timestamp));
        calendar.add(Calendar.DAY_OF_MONTH, offsetDay);
        return date2Timestamp(calendar.getTime());
    }
    
    /**
     * 获得指定日期 偏移小时的日期
     * @param date 指定日期
     * @param offsetDay 偏移小时数(正数为向前，负数为向后)
     * @return
     */
    public static Timestamp getOffsetDate(Timestamp timestamp , int offsetDay , int offsetHour)
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(timestamp2Date(timestamp));
        calendar.add(Calendar.DAY_OF_MONTH, offsetDay);
        calendar.add(Calendar.HOUR_OF_DAY, offsetHour);
        return date2Timestamp(calendar.getTime());
    }
    
    /**
     * 比较两个日期
     * @param date1 日期字符串
     * @param format1 格式
     * @param date2 日期字符串
     * @param format2 格式
     * @param type 比较的单位
     * @return
     */
    public static Long compare(String date1 , String format1 , String date2 ,  String format2 , int type)
    {
        Calendar calendar1 = Calendar.getInstance(Locale.CHINA);
        calendar1.setTime(timestamp2Date(getTimestamp(date1, format1)));
        Calendar calendar2 = Calendar.getInstance(Locale.CHINA);
        calendar2.setTime(timestamp2Date(getTimestamp(date2, format2)));
        
        long offMillis = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
        if(type == Calendar.YEAR)
        {
           return null;
        }else if (type == Calendar.MONTH) // 月...
        {
//            return offMillis / (1000*3600*24*);
        }else if (type == Calendar.DAY_OF_YEAR) // 天
        {
            return offMillis / (1000*3600*24);
        }else if (type == Calendar.HOUR)
        {
            return offMillis / (1000*3600); // 相隔小时数
        }
        return null;
    }
    
    public static void main(String[] args)
    {
//        System.out.println(compare("20140717", "yyyyMMdd", "20140719", "yyyyMMdd", Calendar.HOUR));
//        System.out.println(millis2Timestamp(System.currentTimeMillis()));
          System.out.println(getOffsetDate(getCurrentTimestamp(), 0, 0));
          Calendar cal = Calendar.getInstance(Locale.CHINA);
          cal.setTimeInMillis(System.currentTimeMillis());
          
          System.out.println(cal.get(cal.MINUTE));
          
          System.out.println(getCurrentTimestamp().compareTo(getTimestamp("2014-10-13 00:00:00", "yyyy-MM-dd HH:mm")));
    }
}
