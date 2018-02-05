package com.jzli.bean;

import java.util.Date;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/2/1
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class StockRecord {
    private String id;
    private Double start;
    private Double end;
    private Double high;
    private Double low;
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StockRecord{" +
                "id='" + id + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", high=" + high +
                ", low=" + low +
                ", date=" + date +
                '}';
    }
}
