package com.graduation.project.risk.common.core.web;

import io.swagger.annotations.ApiModelProperty;

public class CommonRequestData {

    /**
     * header prefix
     */
    public static final String HEADER_PREFIX = "x-c-";

    /**
     * sensor prefix
     */
    public static final String SENSOR_PREFIX = "x-sensor-";

    /**
     * type of platform
     */
    private String platform;

    /**
     * token
     */
    private String token;

    /**
     * version like 1.2.3
     */
    private String version;

    /**
     * channel id
     */
    private String cid;

    /**
     * device id
     */
    private String did;

    /**
     * user id
     */
    private String uid;

    /**
     * device brand
     */
    private String dbd;

    /**
     * device model
     */
    private String dml;

    /**
     *
     */
    private String lng;

    /**
     *
     */
    private String lat;

    /**
     * Satellite positioning city
     */
    private String ip;

    /**
     *
     */
    private String location;

    /**
     * package
     */
    @ApiModelProperty(value = "package", dataType = "string", example = "google")
    private String from;

    /**
     * package info
     */
    private String shadow;

    /**
     * request time
     */
    private String timestamp;

    /**
     *
     */
    private String country;

    /**
     *
     */
    private String utm_source;

    /**
     *
     */
    private String utm_medium;

    /**
     *
     */
    private String utm_term;

    /**
     *
     */
    private String utm_content;

    /**
     *
     */
    private String utm_campaign;

    /**
     *
     */
    private SensorsCommonParam sensors;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDbd() {
        return dbd;
    }

    public void setDbd(String dbd) {
        this.dbd = dbd;
    }

    public String getDml() {
        return dml;
    }

    public void setDml(String dml) {
        this.dml = dml;
    }

    public String getShadow() {
        return shadow;
    }

    public void setShadow(String shadow) {
        this.shadow = shadow;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUtm_source() {
        return utm_source;
    }

    public void setUtm_source(String utm_source) {
        this.utm_source = utm_source;
    }

    public String getUtm_medium() {
        return utm_medium;
    }

    public void setUtm_medium(String utm_medium) {
        this.utm_medium = utm_medium;
    }

    public String getUtm_term() {
        return utm_term;
    }

    public void setUtm_term(String utm_term) {
        this.utm_term = utm_term;
    }

    public String getUtm_content() {
        return utm_content;
    }

    public void setUtm_content(String utm_content) {
        this.utm_content = utm_content;
    }

    public String getUtm_campaign() {
        return utm_campaign;
    }

    public void setUtm_campaign(String utm_campaign) {
        this.utm_campaign = utm_campaign;
    }

    public SensorsCommonParam getSensors() {
        return sensors;
    }

    public void setSensors(SensorsCommonParam sensors) {
        this.sensors = sensors;
    }

    public static class SensorsCommonParam {

        private String anonymous_id;

        private String product;

        private String module;

        private String os;

        private String os_version;

        private Integer screen_height;

        private Integer screen_width;

        private Boolean wifi;

        private String browser;

        private String browser_version;

        private String carrier;

        private String network_type;

        public String getAnonymous_id() {
            return anonymous_id;
        }

        public void setAnonymous_id(String anonymous_id) {
            this.anonymous_id = anonymous_id;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOs_version() {
            return os_version;
        }

        public void setOs_version(String os_version) {
            this.os_version = os_version;
        }

        public Integer getScreen_height() {
            return screen_height;
        }

        public void setScreen_height(Integer screen_height) {
            this.screen_height = screen_height;
        }

        public Integer getScreen_width() {
            return screen_width;
        }

        public void setScreen_width(Integer screen_width) {
            this.screen_width = screen_width;
        }

        public Boolean getWifi() {
            return wifi;
        }

        public void setWifi(Boolean wifi) {
            this.wifi = wifi;
        }

        public String getBrowser() {
            return browser;
        }

        public void setBrowser(String browser) {
            this.browser = browser;
        }

        public String getBrowser_version() {
            return browser_version;
        }

        public void setBrowser_version(String browser_version) {
            this.browser_version = browser_version;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public String getNetwork_type() {
            return network_type;
        }

        public void setNetwork_type(String network_type) {
            this.network_type = network_type;
        }
    }

}
