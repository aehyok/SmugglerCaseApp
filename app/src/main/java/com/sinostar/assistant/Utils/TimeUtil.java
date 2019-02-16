package com.sinostar.assistant.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    /**
     * 日期格式化
     *
     * @return
     */
    public static String DateformatTime(Date date) {
        if (isThisYear(date)) {//今年
            if (isToday(date)) { //今天
                return isMorning(date);
            } else {
                if (isYestYesterday(date)) {//昨天，显示昨天
                    return "昨天 " +isMorning(date);

                } else if (isThisWeek(date)) {//本周,显示周几
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "星期一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "星期二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "星期三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "星期四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "星期五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "星期六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "星期日";
                    }
                    return weekday + " " +isMorning(date);
                } else {
                    SimpleDateFormat sdf;
                    if(date.getHours()<=12){
                        sdf = new SimpleDateFormat("MM-dd 上午hh:mm");
                    }else{
                        sdf = new SimpleDateFormat("MM-dd 下午hh:mm");
                    }
                    return sdf.format(date);
                }
            }
        } else {
            SimpleDateFormat sdf;
            if(date.getHours()<=12){
                sdf= new SimpleDateFormat("yyyy-MM-dd 上午hh:mm");
            }else{
                sdf= new SimpleDateFormat("yyyy-MM-dd 下午hh:mm");
            }
            return sdf.format(date);
        }

    }


    private static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }

    private static boolean isToday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() == now.getDate());
    }

    private static boolean isYestYesterday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() + 1 == now.getDate());
    }

    private static boolean isThisWeek(Date date) {
        Date now = new Date();
        if ((date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth())) {
            if (now.getDay() - date.getDay() < now.getDay() && now.getDate() - date.getDate() > 0 && now.getDate() - date.getDate() < 7) {
                return true;
            }
        }
        return false;
    }



    private static boolean isThisYear(Date date) {
        Date now = new Date();
        return date.getYear() == now.getYear();
    }

    private static String isMorning(Date date) {
        String time;
        if(date.getHours()<=12){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("上午 hh:mm");
            time=simpleDateFormat.format(date);

        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("下午 hh:mm");
            time=simpleDateFormat.format(date);
        }
      return time;
    }


}
