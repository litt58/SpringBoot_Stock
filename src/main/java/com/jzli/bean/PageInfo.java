package com.jzli.bean;

import org.springframework.util.ObjectUtils;

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
    private final Integer DEFAULT_PAGE_SIZE = 50;
    private final Integer DEFAULT_PATE_NO = 1;

    private Integer pageNo;
    private Integer pageSize;
    private Long totalPageNo;
    private Long total;
    private List<T> data;

    public PageInfo(Integer pageNo, Integer pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (ObjectUtils.isEmpty(pageNo) || pageNo <= 0) {
            this.pageNo = DEFAULT_PATE_NO;
        } else {
            this.pageNo = pageNo;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (ObjectUtils.isEmpty(pageSize) || pageSize <= 0) {
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
