package com.graduation.project.risk.project.biz.util;

import org.apache.commons.lang3.StringUtils;

public class VersionUtil {

    // beta
    private static final String BETA_VER_SUFFIX = "_beta";

    //
    private final static int VERSION_NUMBER_DIGITS = 3;

    public final static String VERSION_0_1 = "0.1";

    public final static String VERSION_0_2 = "0.2";

    public static void main(String[] args) {
        System.out.println(isSupportMinVersion("0.1", "0.2"));
    }

    public static boolean isSupportMinVersion(String verString, String minVersion) {
        String tmpVerString = verString;
        boolean isBeta = StringUtils.endsWithIgnoreCase(tmpVerString, BETA_VER_SUFFIX);
        if (isBeta) {
            tmpVerString = StringUtils.replace(verString, BETA_VER_SUFFIX, "");
        }
        return isSupportVersion(tmpVerString, minVersion, null);
    }

    public static boolean isSupportVersion(String currentUserVersion, String minVersion, String latestVersion) {
        try {
            if (StringUtils.isBlank(currentUserVersion) || StringUtils.isBlank(minVersion)) {
                return false;
            }

            boolean compareLatestVersion = !StringUtils.isBlank(latestVersion);

            String[] currentUserVersionNum = currentUserVersion.split("\\.");

            if (VERSION_NUMBER_DIGITS != currentUserVersionNum.length) {
                currentUserVersion += ".0";
                currentUserVersionNum = currentUserVersion.split("\\.");
            }

            String[] minVersionNum = minVersion.split("\\.");
            if (VERSION_NUMBER_DIGITS != minVersionNum.length) {
                minVersion += ".0";
                minVersionNum = minVersion.split("\\.");
            }

            String[] latestVersionNum = compareLatestVersion ? latestVersion.split("\\.") : null;

            if (VERSION_NUMBER_DIGITS == currentUserVersionNum.length && VERSION_NUMBER_DIGITS == minVersionNum.length) {
                if (compareLatestVersion && VERSION_NUMBER_DIGITS != latestVersionNum.length) {
                    return false;
                }
                for (int i = 0; i < VERSION_NUMBER_DIGITS; i++) {
                    int resultMin = Integer.valueOf(currentUserVersionNum[i]).compareTo(Integer.valueOf(minVersionNum[i]));
                    if (resultMin > 0 || (resultMin == 0 && i == (VERSION_NUMBER_DIGITS - 1))) {
                        if (!compareLatestVersion) {
                            return true;
                        }
                        for (int j = 0; j < VERSION_NUMBER_DIGITS; j++) {
                            int resultMax = Integer.valueOf(currentUserVersionNum[j]).compareTo(Integer.valueOf(latestVersionNum[j]));
                            if (resultMax < 0 || (resultMax == 0 && j == (VERSION_NUMBER_DIGITS - 1))) {
                                return true;
                            } else if (resultMax > 0) {
                                return false;
                            }
                        }
                    } else if (resultMin < 0) {
                        return false;
                    }
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

}
