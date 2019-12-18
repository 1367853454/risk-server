package com.graduation.project.risk.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page<T> implements Serializable, Cloneable {

	/**
	 *
	 */
	private int page = 1;
	/**
	 *
	 */
	private int pageSize = 16;
	/**
	 *
	 */
	private long records = 1;
	/**
	 *
	 */
	private long total = 1;
	/**
	 *
	 */
	private List<T> rows;
	/**
	 *
	 */
	private Map<String, Object> params = new HashMap<>();

	/**
	 *
	 *
	 * @return int
	 */
	public int getPage() {
		return this.page;
	}

	/**
	 *
	 *
	 * @param page
	 */
	public void setPage(int page) {
		if (page >= 1) {
			this.page = page;
		}
	}

	/**
	 *
	 *
	 * @return int
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 *
	 *
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 *
	 *
	 * @return int
	 */
	public long getRecords() {
		return this.records;
	}

	/**
	 *
	 *
	 * @param records
	 */
	public void setRecords(long records) {
		this.records = records;
		//在设置总记录数的时候计算出对应的总页数
		long totalPage = (records % pageSize == 0) ? (records / pageSize) : (records / pageSize + 1);
		this.setTotal(totalPage);
	}

	/**
	 *
	 *
	 * @return int
	 */
	public long getTotal() {
		return this.total;
	}

	/**

	 *
	 * @param total
	 */
	private void setTotal(long total) {
		this.total = total;
	}

	/**
	 *
	 *
	 * @return java.util.List<T>
	 */
	public List<T> getRows() {
		return this.rows;
	}

	/**
	 *
	 *
	 * @param rows
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 *  Map
	 *
	 * @return java.util.Map
	 */
	public Map<String, Object> getParams() {
		return this.params;
	}

	/**
	 *
	 *
	 * @param params
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 *
	 */
	public int prevPageNo() {
		return this.page - 1;
	}

	/**
	 *
	 */
	public int nextPageNo() {
		return this.page + 1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [pageNo=").append(page).append(", pageSize=")
				.append(pageSize).append(", rows=").append(rows)
				.append(", total=").append(total)
				.append(", records=").append(records).append("]");
		return builder.toString();
	}

	/**
	 *
	 *
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
