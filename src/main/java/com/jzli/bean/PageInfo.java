package com.jzli.bean;

import java.util.List;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/3/19
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class PageInfo<T> {
    private final int DEFAULT_PAGE_SIZE = 50;
    private final int DEFAULT_PATE_NO = 1;

    private int pageNo;
    private int pageSize;
    private Long totalPageNo;
    private Long total;
    private List<T> data;

    public PageInfo(int pageNo, int pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if (pageNo <= 0) {
            this.pageNo = DEFAULT_PATE_NO;
        } else {
            this.pageNo = pageNo;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public Long getTotalPageNo() {
        return totalPageNo;
    }

    public void setTotalPageNo(Long totalPageNo) {
        this.totalPageNo = totalPageNo;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        if (total > 0) {
            this.total = total;
            Long pageSize = Long.parseLong(Integer.toString(this.getPageSize()));
            this.totalPageNo = total / pageSize + 1;
        } else {
            this.total = 0L;
            this.totalPageNo = 0L;
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
