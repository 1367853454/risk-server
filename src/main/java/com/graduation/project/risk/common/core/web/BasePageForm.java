package com.graduation.project.risk.common.core.web;

import com.graduation.project.risk.common.core.dal.mongdb.query.KeywordQuery;
import com.graduation.project.risk.common.core.dal.mongdb.query.PageQuery;
import com.graduation.project.risk.common.core.dal.mongdb.query.SortQuery;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BasePageForm implements PageQuery, KeywordQuery, SortQuery {

    protected String       keyword;

    protected Integer      pageNumber = 0;

    protected Integer      pageSize   = Integer.MAX_VALUE;

    @ApiModelProperty(value = "[,ASC|DESC]", example = "ID,DESC")
    protected List<String> orderBys   = new ArrayList<>();

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     *
     * @return
     */
    public Sort sort() {

        if (CollectionUtils.isEmpty(orderBys)) {
            return null;
        }

        List<Sort.Order> orderList = orderBys
            .stream()
            .filter(item -> StringUtils.isNotBlank(item))
            .map(
                item -> {

                    String orders[] = item.split(",");

                    if (orders.length == 1) {
                        return new Sort.Order(orders[0]);
                    } else {
                        return new Sort.Order(
                            orders[1].toUpperCase().equals(Sort.Direction.DESC) ? Sort.Direction.DESC
                                : Sort.Direction.ASC, orders[0]);
                    }
                }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(orderList)) {
            return null;
        }

        return new Sort(orderList);

    }

    public BasePageForm orderBy(String property, Sort.Direction direction) {
        orderBys.add(property + "," + direction.name());
        return this;
    }

    public BasePageForm orderBy(String property) {
        return orderBy(property, Sort.Direction.ASC);
    }

}
