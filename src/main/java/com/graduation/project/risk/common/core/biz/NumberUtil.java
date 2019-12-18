package com.graduation.project.risk.common.core.biz;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

    /**
     *
     * @param s
     * @return
     */
    public static boolean isNumeric(String s) {

        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(s).toString();
        } catch (Exception e) {
            return false;
        }

        Matcher isNum = pattern.matcher(bigStr);
        return isNum.matches();
    }

}
