package com.graduation.project.risk.common.core.biz;

import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.biz.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;

@Component
public class RestBusinessTemplate {

    @Value("${environment}")
    private String environment;

    private static RestBusinessTemplate restBusinessTemplate;

    @PostConstruct
    public void init() {
        restBusinessTemplate = this;
        restBusinessTemplate.environment = this.environment;
    }

    public static <T> CommonRestResult<T> execute(Callback<T> callback) {
        return doExecute(callback, null);
    }

    public static <T> CommonRestResult<T> transaction(Callback<T> callback) {

        TransactionTemplate transactionTemplate = ApplicationContextUtil.getBean(TransactionTemplate.class);

        return doExecute(callback, transactionTemplate);

    }

    private static <T> CommonRestResult<T> doExecute(Callback<T> callback, TransactionTemplate transactionTemplate) {

        String env = restBusinessTemplate.environment;
        boolean test = false;
        if (!StringUtils.equalsIgnoreCase(env, "prod")) {
            test = true;
        }

        CommonRestResult<T> restResult = new CommonRestResult<>();

        try {
            restResult.setStatus(CommonRestResult.SUCCESS);

            T obj;

            if (null == transactionTemplate) {
                obj = callback.doExecute();
            } else {
                obj = transactionTemplate.execute(status -> callback.doExecute());
            }
            restResult.setContent(obj);

        } catch (BizCoreException e) {

            restResult.setStatus(CommonRestResult.FAIL);

            ErrorCode code = e.getCode();

            String msg;

            if (code != null) {
                restResult.setCode(code.getCode());
                restResult.setMessage(code.getDesc());
            } else {
                msg = e.getMessage();
                restResult.setCode("999999");
                restResult.setMessage(msg);
            }

        } catch (Exception e) {
            LoggerUtil.error("catch system error : ", e);
            restResult.setCode("999999");
            restResult.setStatus(CommonRestResult.FAIL);
            restResult.setMessage(ErrorCode.SYSTEM_EXCEPTION.getDesc());
        }

        return restResult;
    }

    /**
     * @param <T>
     */
    public interface Callback<T> {
        T doExecute();
    }

}
