package com.graduation.project.risk.common.core.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpConvertUtil {

    private Logger logger = LoggerFactory.getLogger(IpConvertUtil.class);

    private static IpConvertUtil instance = null;

    private IpConvertUtil(){}

    public static IpConvertUtil getInstance(){
        if (instance == null) {
            instance = new IpConvertUtil();
        }
        return instance;
    }

    /**
     * ip --> country
     *
     * @param ip
     * @param url
     * @return     country
     */
    public String getCountryByIp(String ip, String url) {

        String head = "Get country by ip, ";

        logger.info(head + "start, ip={}", ip);

        JSONObject json = convertFrom(ip, url);

        if (null == json) {
            logger.info(head + "failed，no response, ip={}", ip);
            return null;
        }

        logger.info(head + "end, ip={}", ip);

        return json.getString(country_code);
    }

    /**
     * Get location by ip
     *
     * result：
     * {
     *  "continent_name":"Asia",
     *  "city":"Hangzhou",
     *  "ip":"218.108.151.13",
     *  "latitude":45.0000,
     *  "continent_code":"AS",
     *  "type":"ipv4",
     *  "country_code":"ID",
     *  "country_name":"Indonesia",
     *  "region_name":"Jakarta",
     *  "location":
     *      {
     *          "capital":"Jakarta",
     *          "calling_code":"62",
     *          "languages":
     *              [
     *                  {
     *                      "code":"id",
     *                      "native":"id",
     *                      "name":"jakarta"
     *                  }
     *              ],
     *          "country_flag_emoji_unicode":"",
     *          "is_eu":false,
     *          "country_flag_emoji":"",
     *          "country_flag":"http://assets.ipstack.com/flags/id.svg",
     *          "geoname_id":1808926
     *      },
     *  "region_code":"",
     *  "longitude": 100.0000
     * }
     *
     * @param ip
     * @param url
     * @return
     */
    private JSONObject convertFrom(String ip, String url) {

        if (!CommonUtil.isIp(ip)) {
            return null;
        }

        String response = null;
        JSONObject result = null;

        try {
            response = HttpUtil.getInstance().doGet(url);
            if (StringUtils.isBlank(response)) {
                return null;
            }

            result = JSON.parseObject(response);
        } catch (Exception e) {
            logger.error("Get location by ip failed，ip={}, url={}, result={}",
                    ip, url, response, e);
        }

        return result;

    }

    /**
     *
     */
    private static final String city = "city";
    /**
     *
     */
    private static final String country_code = "country_code";
    /**
     *
     */
    private static final String country_name = "country_name";

    /**
     * 
     */
    private static final String region_name = "region_name";

}
