package com.graduation.project.risk.common.core.biz;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public class MD5Util {
    public MD5Util() {
    }

    public static String digest(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buff = md.digest(input.getBytes("utf-8"));
            String md5str = bytesToHex(buff);
            return md5str;
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static String digest(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buff = md.digest(input);
            String md5str = bytesToHex(buff);
            return md5str;
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder md5str = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            int digital = bytes[i];
            if (digital < 0) {
                digital += 256;
            }

            if (digital < 16) {
                md5str.append("0");
            }

            md5str.append(Integer.toHexString(digital));
        }

        return md5str.toString();
    }

    public static String encode(String str) {
        try {
            byte[] bytes = null;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
            bytes = md.digest();
            StringBuilder stringBuilder = new StringBuilder("");

            for(int i = 0; i < bytes.length; ++i) {
                int v = bytes[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } catch (GeneralSecurityException var7) {
            return "";
        } catch (UnsupportedEncodingException var8) {
            throw new RuntimeException(var8);
        }
    }
}
