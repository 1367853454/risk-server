package com.graduation.project.risk.common.base.aop.mvc;

import com.graduation.project.risk.common.core.web.errors.ErrorVM;
import com.graduation.project.risk.common.core.web.errors.ExceptionTranslator;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.biz.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by zhouqin on 13/10/2016.
 */
@ControllerAdvice("com.graduation.project.risk.project.web.rest")
public class RestResourceAdvice extends ExceptionTranslator implements ResponseBodyAdvice<Object> {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestResourceAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        //        String requestPath = request.getURI().getPath();
        //        if(requestPath.startsWith("/swagger") || requestPath.startsWith("/v2/api-docs")) {
        //            return body;
        //        }

        // envelope is default true.

        try {

            List<String> _envelope = request.getHeaders().get("envelope");
            boolean do_not_envelope = _envelope != null && _envelope.size() == 1
                    && _envelope.get(0).equals("false");

            if (body instanceof CommonRestResult || body instanceof ErrorVM || body instanceof String || do_not_envelope) {

                if (body instanceof CommonRestResult
                        && ((CommonRestResult) body).getContent() instanceof Page) {

                    Page page = (Page) ((CommonRestResult) body).getContent();

                    ((CommonRestResult) body).setContent(page.getContent());
                    ((CommonRestResult) body).setMetadata(pageMetadata(page));
                }
                return body;
            } else {

                throw new RuntimeException(
                        "process error,Rest Controller method return CommonRestResult , please use RestBusinessTemplate。 【body is "
                                + body == null ? "null" : body.toString() + "】");
            }
        } catch (RuntimeException e) {
            LoggerUtil.error("request process error", e);

            throw e;
        }

    }

    @SuppressWarnings("unchecked")
    private Map pageMetadata(Page page) {
        Map metadata = new HashMap();
        Map pageInfo = new HashMap();

        pageInfo.put("currentItemCount", page.getNumberOfElements());
        pageInfo.put("totalItems", page.getTotalElements());
        pageInfo.put("totalPages", page.getTotalPages());
        pageInfo.put("pageIndex", page.getNumber() + 1);
        pageInfo.put("itemsPerPage", page.getSize());

        metadata.put("page", pageInfo);
        return metadata;
    }
}
