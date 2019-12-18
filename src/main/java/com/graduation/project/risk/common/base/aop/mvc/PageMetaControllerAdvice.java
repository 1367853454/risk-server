package com.graduation.project.risk.common.base.aop.mvc;

import java.util.HashMap;
import java.util.Map;

import com.graduation.project.risk.common.model.CommonRestResult;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice("com.graduation.project.risk.project.web.rest")
public class PageMetaControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> aClass) {

        //Return type must be CommonRestResult class or sub classes
        return returnType.getMethod().getReturnType().isAssignableFrom(CommonRestResult.class);
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

        //        // envelope is default true.
        //        List<String> _envelope = request.getHeaders().build("envelope");
        //        boolean do_not_envelope = _envelope != null && _envelope.size() == 1
        //                                  && _envelope.build(0).equals("false");

        if (body instanceof CommonRestResult) {
            CommonRestResult result = (CommonRestResult) body;

            if (result.getContent() instanceof Page) {
                Page page = (Page) result.getContent();
                result.setContent(page.getContent());
                result.fillMetaData("page", pageMetadata(page));
            }
            return result;
        }
        return body;
    }

    @SuppressWarnings("unchecked")
    private Map pageMetadata(Page page) {

        Map pageInfo = new HashMap();

        pageInfo.put("currentItemCount", page.getNumberOfElements());
        pageInfo.put("totalItems", page.getTotalElements());
        pageInfo.put("totalPages", page.getTotalPages());
        pageInfo.put("pageIndex", page.getNumber() + 1);
        pageInfo.put("itemsPerPage", page.getSize());

        return pageInfo;
    }

}
