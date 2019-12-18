package com.graduation.project.risk.common.core.biz;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final DateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

    public static final DateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final DateFormat yyyy = new SimpleDateFormat("yyyy");

    public static final DateFormat yymmdd = new SimpleDateFormat("yyyyMMdd");

    public static final DateFormat ymdhms2 = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final DateFormat mdhms = new SimpleDateFormat("MMddHHmmss");

    private static TimeZone getTimezone(String country) {

        if (StringUtils.isBlank(country)) {
            return TimeZone.getTimeZone("GMT-7");
        }

        if (StringUtils.equalsIgnoreCase(country, "ID")) {
            return TimeZone.getTimeZone("GMT-7");
        } else {
            return TimeZone.getTimeZone("GMT-8");
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss --> yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date format2SimpleDate(Date date) {

        String d = ymd.format(date);

        Date time;

        try {
            time = ymd.parse(d);
        } catch (ParseException e) {
            logger.error("Parse date failed, param={}", date);
            return null;
        }

        return time;
    }

    /**
     * yyyy-MM-dd HH:mm:ss --> yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String format2Date(Date date) {

        return ymdhms.format(date);
    }

    /**
     * string --> date
     *
     * @param date java.lang.String
     * @return java.util.Date
     */
    public static Date parseDate(String date) {
        try {
            return ymd.parse(date);
        } catch (ParseException e) {
            logger.error("Parse date failed, param={}", date);
        }
        return null;
    }

    /**
     * string --> date
     *
     * @param date
     * @return
     */
    public static Date parseFullDate(String date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(date + "000"));
            return calendar.getTime();
        } catch (Exception e) {
            logger.error("Parse date failed, param={}", date);
        }
        return null;
    }

    public static String formatJakartaDate(Date date) {
        ymdhms2.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return ymdhms2.format(date);
    }

    public static String format4Date(Date date) {
        return mdhms.format(date);
    }

    public static long transToTimestamp(String date) {
        try {
            return ymdhms2.parse(date).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date date, int seconds) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setTimeZone(TimeZone.getTimeZone("GMT-7"));

        cal.add(Calendar.SECOND, seconds);

        return cal.getTime();
    }

    /**
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date, int minutes) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.MINUTE, minutes);

        return cal.getTime();
    }

    /**
     * @param date
     * @param days
     * @return
     */
    public static Date addDate(Date date, int days) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    /**
     * Get last seconds by day
     * <p>
     *
     * @param date 2018-02-01 13:49:44
     * @return 2018-02-01 23:59:59
     */
    public static Date latestOfDay(Date date) {

        try {
            Date d = ymd.parse(ymd.format(date));
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);

            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.add(Calendar.MILLISECOND, -1);

            return cal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param d1
     * @param d2
     * @return difference
     */
    public static long dateDiff(Date d1, Date d2) {

        String t1 = ymd.format(d1);
        String t2 = ymd.format(d2);

        try {
            d1 = ymd.parse(t1);
            d2 = ymd.parse(t2);

            long diff = d1.getTime() - d2.getTime();

            return diff / (1000 * 60 * 60 * 24);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     *
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }


    /**
     * Calculate age
     *
     * @param birthday
     * @return
     */
    public static Integer getAge(Date birthday) {

        Integer currentYear = Integer.valueOf(yyyy.format(new Date()));
        Integer birthYear = Integer.valueOf(yyyy.format(birthday));

        return currentYear - birthYear;
    }

    /**
     * parse ktp date
     *
     * @param date
     * @return
     */
    public static Date ktpParseDate(String date) {
        try {
            return yymmdd.parse(date);
        } catch (ParseException e) {
            logger.error("Parse date failed，param={}", date);
        }
        return null;
    }

    /**
     * @param d1
     * @param d2
     * @return
     */
    public static String getDateDiffer(Date d1, Date d2) {
        try {
            long l = d2.getTime() - d1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            return "Use：" + day + "Day" + hour + "Hour" + min + "Min" + s + "Sec";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Long transForMilliSecond(Date date) {
        if (date == null) return null;
        return (date.getTime());
    }

    /**
     * @param d1
     * @param day
     * @return
     */
    public static Date getApplyAgainDate(Date d1, Integer day) {

        String a = ymd.format(d1);
        Date b = parseDate(a);
        Calendar cal = Calendar.getInstance();
        cal.setTime(b);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * @param age
     * @return
     */
    public static Long getMilliByAge(int age) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - age);
        int year = calendar.get(Calendar.YEAR);
        String time = String.valueOf(year) + "/01/01";
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy/MM/dd").parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long Timestamp = date1.getTime();
        return Timestamp;
    }

    /**
     * calculate overdue days
     *
     * @param
     * @return
     */
    public static Integer getOverdueDays(Long dueTime, Long repaymentTime) {

        Date dueDate = latestOfDay(new Date(dueTime));
        Date repaymentDate = latestOfDay(new Date(repaymentTime));

        if (repaymentDate.getTime()  - dueDate.getTime() <= 0) {
            return 0;
        }

        Long diff = (repaymentDate.getTime() - dueDate.getTime()) / (1000 * 3600 * 24);
        return diff.intValue();
    }

    public static boolean isSameDay(Long time1, Long time2) {
        if (time1 == null || time2 == null) {
            return true;
        }
        Date date1 = new Date(time1);
        Date date2 = new Date(time2);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return DateUtils.isSameDay(cal1, cal2);
    }

    public static void main(String[] args) {

        System.out.println(exportDate(1538633756000L));
    }

    /**
     * format timestamp to yyyy-mm-dd(String)
     *
     * @param
     * @return
     */
    public static String exportDate(Long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return String.valueOf(cal.get(Calendar.YEAR)) + "-" + String.valueOf(cal.get(Calendar.MONTH) + 1) + "-" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    }
}
