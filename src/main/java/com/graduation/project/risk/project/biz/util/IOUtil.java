package com.graduation.project.risk.project.biz.util;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

public class IOUtil {

    public static String readResource(Resource resource) {
        try{

            return IOUtils.toString(resource.getInputStream(),"utf-8");
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

}
