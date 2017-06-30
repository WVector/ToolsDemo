package com.vector.libtools.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.vector.libtools.timer.TimeUtils.formatTime;

/**
 * Created by fengjunming_t on 2017/6/15 0015.
 */
public class TimeUtil {

    public static final SimpleDateFormat mYyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static final SimpleDateFormat mYyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat mYyyy_MM_ddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat mYyyyMMddHH = new SimpleDateFormat("yyyyMMddHH");
    public static final SimpleDateFormat mYyyyMM = new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat mYyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat mMd = new SimpleDateFormat("M.d");

    public static int diffDay(long currentTime, long preBillTime) {
        return (int) ((currentTimeDay(preBillTime) - currentTimeDay(currentTime)) / TimeConstants.DAY);
    }

    public static long monthPlusDay(int day, long currentTime) {
//        return currentTimeMonth(currentTime) + (long) (day - 1) * TimeConstants.DAY;
        Date date = new Date(currentTime);
        date.setDate(day);
        return date.getTime();
    }

    public static long currentTimeMonth(long currentTime) {
        try {
            return mYyyyMM.parse(mYyyyMMddHHmmss.format(currentTime).substring(0, 6)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    public static long currentTimeDay(long currentTime) {
        try {
            return mYyyyMMdd.parse(mYyyyMMddHHmmss.format(currentTime).substring(0, 8)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    public static long currentTimeHour(long currentTime) {
        try {
            return mYyyyMMddHH.parse(mYyyyMMddHHmmssSSS.format(currentTime).substring(0, 10)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    public static long currentTimePlusMonth(long currentTime, int months) {
        Date date = new Date(currentTime);
        date.setMonth(date.getMonth() + months);
        return date.getTime();
    }

    public static long currentTimePlusDay(long currentTime, int days) {
        Date date = new Date(currentTime);
        date.setDate(date.getDate() + days);
        return date.getTime();
    }

    public static long monthPlusDay2(int day, long currentTime) {
        return monthPlusDay(day, currentTimeMonth(currentTime));
    }

    public static boolean currentHourBetweenAB(int a, int b) {
        Date currentDate = new Date();
        long currentDateTime = currentDate.getTime();
        currentDate.setHours(a);
        long aTime = currentTimeHour(currentDate.getTime());
        currentDate.setHours(b);
        long bTime = currentTimeHour(currentDate.getTime());

//        System.out.println(mYyyyMMddHHmmssSSS.format(currentDateTime));
//        System.out.println(mYyyyMMddHHmmssSSS.format(aTime));
//        System.out.println(mYyyyMMddHHmmssSSS.format(bTime));


        return aTime > bTime ? currentDateTime < aTime && currentDateTime > bTime : currentDateTime < bTime && currentDateTime > aTime;
    }

    public static double getIntervalTime(long string) {
        try {
            return (string - new Date().getTime()) * 1D / TimeConstants.DAY;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 以友好的方式显示时间
     *
     * @param time 要格式化的时间
     * @return 友好提示
     */
    public static String friendlyTime(Date time) {
        if (time == null) {
            return "Unknown";
        }
        String resultTime = "";
        Calendar calendar = Calendar.getInstance();
        long lDay = time.getTime() / (24 * 60 * 60 * 1000);
        long cDay = calendar.getTimeInMillis() / (24 * 60 * 60 * 1000);

        int days = (int) (cDay - lDay);

        int lYear = Integer.parseInt(formatTime("yyyy", time.getTime()));
        int cYear = Integer.parseInt(formatTime("yyyy", calendar.getTimeInMillis()));

        int years = cYear - lYear;

        if (years == 0) {
            //一年之内
            if (days == 0) {
                //一天之内
                int hour = (int) ((calendar.getTimeInMillis() - time.getTime()) / (60 * 60 * 1000));
                if (hour == 0) {
                    //一小时之内
                    int minute = (int) ((calendar.getTimeInMillis() - time.getTime()) / (60 * 1000));
                    if (minute == 0) {
                        //一分钟之内
                        resultTime = "刚刚";
                    } else {
                        resultTime = minute + "分钟前";
                    }
                } else {
                    resultTime = hour + "小时前";
                }
            } else if (days == 1) {
                resultTime = "昨天";
            } else if (days == 2) {
                resultTime = "前天";
            } else if (days > 2) {
                //	        } else if (days > 2 && days <= 10) {
                //		    resultTime = days + "天前";
                //	        } else if (days > 10) {
                resultTime = formatTime("M月d日", time.getTime());
            }
        } else {
            resultTime = formatTime("yy年M月d日", time.getTime());
        }
        return resultTime;
    }
}
