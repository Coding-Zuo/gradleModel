package com.zuo.model.utils.common.db;

import java.util.List;

/**
 * 分页查询输出结果定义
 */
public class PageResult<T> {
    /**
     * 当前第几页
     */
    private Integer page;
    /**
     * 每页行数
     */
    private Integer size;
    /**
     * 总记录数
     */
    private Integer total;
    /**
     * 数据List
     */
    private List<T> dataList;

    public PageResult() {
        this.page = -1;
        this.size = -1;
    }

    public PageResult(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
