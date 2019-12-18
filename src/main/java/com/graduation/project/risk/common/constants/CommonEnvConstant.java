package com.graduation.project.risk.common.constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class CommonEnvConstant {

    public static final int CHANGE_EXECUTE_TIMEOUT = 500;

    public static final String CRTL = "\r\n";

    public static final String PATH_SPLIT = File.pathSeparator;

    public static final Map<String, String> prop = new HashMap<>();

    public static final String DEFAULT_ENCODING = "utf-8";

    static {
        prop.putAll(System.getenv());
    }

    public static final String showEnv() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : prop.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(CRTL);
        }

        return sb.toString();
    }

}
