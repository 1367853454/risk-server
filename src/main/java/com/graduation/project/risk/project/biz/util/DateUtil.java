package com.graduation.project.risk.project.biz.util;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String YMD = "yyyy-MM-dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static void main(String[] args) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ms:ss");

        Date d1 = df.parse("2018-02-28 12:00:21");

        System.out.println(dateAdd(d1, 1));
    }

    /**
     * d2 - d1
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int dayDiff(Date d1, Date d2) {

        if (null == d1 || null == d2) {
            return 0;
        }

        LocalDate date1 = convertFrom(d1);
        LocalDate date2 = convertFrom(d2);

        Period p = Period.between(date1, date2);

        return p.getDays();
    }

    /**
     * 2018-09-11 + 1 --> 2018-09-12
     *
     * @param date
     * @param days
     * @return
     */
    public static Date dateAdd(Date date, int days) {

        if (null == date) {
            return null;
        }

        if (0 == days) {
            return date;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    /**
     * date --> string
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {

        if (null == date || StringUtils.isBlank(format)) {
            return null;
        }

        return getFormat(format).format(date);
    }

    /**
     * string --> date
     *
     * @param date
     * @param format
     * @return
     */
    public static Date format(String date, String format) {

        if (StringUtils.isBlank(date) || StringUtils.isBlank(format)) {
            return null;
        }

        try {
            return getFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * date --> date
     *
     * @param date
     * @param format
     * @return
     */
    public static Date dateFormat(Date date, String format) {

        if (null == date || StringUtils.isBlank(format)) {
            return null;
        }

        try {

            String s = getFormat(format).format(date);

            return getFormat(format).parse(s);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Date --> LocalDate
     *
     * @param date
     * @return
     */
    private static LocalDate convertFrom(Date date) {
        if (null == date) {
            return null;
        }

        Instant instant = date.toInstant();

        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }
}
