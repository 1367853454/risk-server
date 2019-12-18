package com.graduation.project.risk.common.core.web;

public class CommonRequestDataHolder {

    private static final ThreadLocal<CommonRequestData> context = new ThreadLocal<>();

    public static void set(CommonRequestData requestData) {
        context.set(requestData);
    }

    public static void clear() {
        context.set(null);
    }

    public static CommonRequestData get() {
        return context.get();
    }

    public static final String getCid() {
        return get() == null ? "" : get().getCid();
    }

    public static final String getPlatform() {
        return get() == null ? "" : get().getPlatform();
    }

    public static final String getToken() {
        return get() == null ? "" : get().getToken();
    }

    public static final String getVersion() {
        return get() == null ? "" : get().getVersion();
    }

    public static final String getDid() {
        return get() == null ? "" : get().getDid();
    }

    public static final String getUid() {
        return get() == null ? "" : get().getUid();
    }

    public static final String getDbd() {
        return get() == null ? "" : get().getDbd();
    }

    public static final String getDml() {
        return get() == null ? "" : get().getDml();
    }

    public static final String getLng() {
        return get() == null ? "" : get().getLng();
    }

    public static final String getLat() {
        return get() == null ? "" : get().getLat();
    }

    public static final String getIp() {
        return get() == null ? "" : get().getIp();
    }

    public static final String getLocation() {
        return get() == null ? "" : get().getLocation();
    }

    public static final String getFrom() {
        return get() == null ? "" : get().getFrom();
    }

    public static final String getShadow() {
        return get() == null ? "" : get().getShadow();
    }

    public static final String getTimestamp() {
        return get() == null ? "" : get().getTimestamp();
    }

    public static final String getCountry() {
        return get() == null ? "" : get().getCountry();
    }

    public static final String getUtm_source() {
        return get() == null ? "" : get().getUtm_source();
    }

    public static final String getUtm_medium() {
        return get() == null ? "" : get().getUtm_medium();
    }

    public static final String getUtm_term() {
        return get() == null ? "" : get().getUtm_term();
    }

    public static final String getUtm_content() {
        return get() == null ? "" : get().getUtm_content();
    }

    public static final String getUtm_campaign() {
        return get() == null ? "" : get().getUtm_campaign();
    }


}
