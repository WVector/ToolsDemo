package com.vector.libtools.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
