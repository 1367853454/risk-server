package com.graduation.project.risk.common.core.biz;

import com.graduation.project.risk.common.security.jwt.JWTConfigurer;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    /**
     * check is valid ip
     *
     * @param ip
     * @return
     */
    public static boolean isIp(String ip) {

        if (StringUtils.isBlank(ip)) {
            return false;
        }

        if (ip.length() < 7 || ip.length() > 15 || "".equals(ip)) {
            return false;
        }

        String regex = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(regex);

        Matcher mat = pat.matcher(ip);

        return mat.find();
    }

    public static boolean verifyEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        String regex = "[\\w-]+@([\\w-]+\\.)+[a-z]{2,3}";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(email);

        return mat.find();
    }

    /**
     * @param s
     * @return
     */
    public static String stampToUtcDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * Get token from request
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);

        //return bearerToken;

        if (org.springframework.util.StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt;
        }

        return bearerToken;
    }

    /**
     * upper --> Upper
     *
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        if (StringUtils.isBlank(str)) return null;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    /**
     * @param str
     * @param cr
     * @return
     */
    public static String getBehindChar(String str, String cr) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(cr)) return null;
        return str.substring(str.indexOf(cr) + 1);
    }

    /**
     * Get random code
     *
     * @return
     */
    public static String genVCode() {
        StringBuilder buf = new StringBuilder();

        buf.append((int) (Math.random() * 10));
        buf.append((int) (Math.random() * 10));
        buf.append((int) (Math.random() * 10));
        buf.append((int) (Math.random() * 10));
        return buf.toString();
    }


    /**
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * @param array
     * @param index
     * @return
     */
    public static String[] getListArray(List<String[]> array, int index) {

        if (array == null) {
            return null;
        }


        String[] b = new String[array.size()];

        for (int i = 0; i < array.size(); i++) {
            String[] a = array.get(i);
            if (a.length > index) {
                b[i] = a[index];
            }

        }

        List<String> tmp = new ArrayList<>();
        for (String str : b) {
            if (!StringUtils.equals(str, "null") && str != null && str.length() != 0) {
                tmp.add(str);
            }
        }
        b = tmp.toArray(new String[0]);

        return b;
    }



    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(Object str) {
        if (str == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(Long str) {
        if (str == null) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isEmpty(List<?> list) {
        if (list == null || list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isEmpty(Set<?> set) {
        if (set == null || set.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isEmpty(String[] arr) {
        if (arr == null || arr.length == 0) {
            return true;
        } else {
            return false;
        }
    }



}
