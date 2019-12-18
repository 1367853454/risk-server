package com.graduation.project.risk.project.biz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private LoggerUtil() {
    }

    public static void debug(Logger logger, Object... obj) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(obj));
        }

    }

    public static void info(Logger logger, Object... obj) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(obj));
        }

    }

    public static void warn(Logger logger, Object... obj) {
        if (logger.isWarnEnabled()) {
            logger.warn(getLogString(obj));
        }

    }

    public static void debug(Object... obj) {
        Logger logger = currentLogger();
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(obj));
        }

    }

    public static void info(Object... obj) {
        Logger logger = currentLogger();
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(obj));
        }

    }

    public static void warn(String message) {
        Logger logger = currentLogger();
        logger.warn(getLogString(message));
    }

    public static void warn(String message, Throwable e) {
        Logger logger = currentLogger();
        logger.warn(getLogString(message), e);
    }

    public static void error(String message) {
        Logger logger = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
        logger.error(getLogString(message));
    }

    public static void error(String message, Throwable e) {
        Logger logger = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
        logger.error(getLogString(message), e);
    }

    public static String getLogString(Object... obj) {
        StringBuilder log = new StringBuilder();
        Object[] var2 = obj;
        int var3 = obj.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            log.append(o);
        }

        return log.toString();
    }

    public static Logger currentLogger() {
        return LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
    }
}

