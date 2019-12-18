package com.graduation.project.risk.common.base.entity;


import io.swagger.annotations.ApiModelProperty;

public class PageParams {

	@ApiModelProperty(example = "1")
	private Integer pageSize;

	@ApiModelProperty(example = "5")
	private Integer pageNumber;

	public Integer getPageSize() {
		if(pageSize == null) {
			pageSize = 10;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		if(pageNumber == null) {
			pageNumber = 1;
		}
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
}
