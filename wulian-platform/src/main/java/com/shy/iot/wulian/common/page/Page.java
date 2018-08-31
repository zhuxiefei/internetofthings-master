package com.shy.iot.wulian.common.page;

import java.util.List;

/**
 * Created by cxx on 2018/7/19.
 */
public class Page<T> {
    private Long total;
    private List<T> rows;
    private int page;
    private int pageSize;

    public Page() {
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
