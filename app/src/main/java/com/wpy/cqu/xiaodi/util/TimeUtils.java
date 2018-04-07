package com.wpy.cqu.xiaodi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangpeiyu on 2016/7/3.
 */
public class TimeUtils {

    public static String getFormatTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String str = format.format(date);
        return str;
    }

    public static String getFormatTime(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = format.format(time);
        return str;
    }


    public static String getHour(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String str = format.format(time);
        return str;
    }

    public static Long getLongTime(String strtime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(strtime);
            return date.getTime();
        } catch (ParseException e) {
            /**
             * 时间装换失败
             */
        }
        return date.getTime();
    }

    public static Boolean TimeOverFiveMinute(String oldTime, String currentTime) {
        Long long_currentTome = getLongTime(currentTime);
        Long long_oldTome = getLongTime(oldTime);
        if (long_currentTome - long_oldTome > 5 * 60 * 1000) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean TimeOverTenMinute(String oldTime, String currentTime) {
        Long long_currentTome = getLongTime(currentTime);
        Long long_oldTome = getLongTime(oldTime);
        if (long_currentTome - long_oldTome > 10 * 60 * 1000) {
            return true;
        } else {
            return false;
        }

    }

    public static Boolean TimeOverNow(String time) {
        Long lTime = getLongTime(time);
        Long lNowTime = System.currentTimeMillis();
        if (lTime < lNowTime) {
            return false;
        } else {
            return true;
        }
    }
}
