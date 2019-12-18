package com.graduation.project.risk.common.core.dal.mongdb.query;

import com.graduation.project.risk.common.model.Page;
import com.graduation.project.risk.common.model.WebservicePageQueryResult;

import java.util.List;


public class PageUtil {

    /**
     * list --> page
     *
     * @return
     */
    public static final <T> Page toPage(List<T> list) {

        if (list instanceof com.github.pagehelper.Page) {

            com.github.pagehelper.Page<T> mybatisPage = (com.github.pagehelper.Page<T>) list;

            Page<T> page = new Page<>();
            page.setPage(mybatisPage.getPageNum());
            page.setPageSize(mybatisPage.getPageSize());
            page.setRecords(mybatisPage.getTotal());
            page.setRows(list);

            return page;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static final void fillPageInfo(Page page, WebservicePageQueryResult result) {
        result.setContent(page.getRows());
        result.setItemsPerPage(page.getPageSize());
        result.setPageIndex(page.getPage() + 1);
        result.setTotalPages((int) page.getTotal());
        result.setSuccess(true);
        result.setTotalItems(page.getRecords());
    }
}
