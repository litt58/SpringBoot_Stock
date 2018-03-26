package com.jzli.bean;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/3/26
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class RecommendStockInfo {
    private StockInfo stockInfo;
    private StockRecord low;
    private StockRecord high;
    private StockRecord current;

    private double highRate;
    private double lowRate;

    public StockInfo getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(StockInfo stockInfo) {
        this.stockInfo = stockInfo;
    }

    public StockRecord getLow() {
        return low;
    }

    public void setLow(StockRecord low) {
        this.low = low;
    }

    public StockRecord getHigh() {
        return high;
    }

    public void setHigh(StockRecord high) {
        this.high = high;
    }

    public StockRecord getCurrent() {
        return current;
    }

    public void setCurrent(StockRecord current) {
        this.current = current;
    }

    public double getHighRate() {
        return highRate;
    }

    public void setHighRate(double highRate) {
        this.highRate = highRate;
    }

    public double getLowRate() {
        return lowRate;
    }

    public void setLowRate(double lowRate) {
        this.lowRate = lowRate;
    }

    @Override
    public String toString() {
        return "RecommendStockInfo{" +
                "stockInfo=" + stockInfo +
                ", low=" + low +
                ", high=" + high +
                ", current=" + current +
                ", highRate=" + highRate +
                ", lowRate=" + lowRate +
                '}';
    }

    public int compareLow(RecommendStockInfo o1, RecommendStockInfo o2) {
        if (o1.getLowRate() == o2.getLowRate()) {
            return o1.getStockInfo().getId().compareTo(o2.getStockInfo().getId());
        } else {
            return o1.getLowRate() > o2.getLowRate() ? 1 : -1;
        }
    }

    public int compareHigh(RecommendStockInfo o1, RecommendStockInfo o2) {
        if (o1.getHighRate() == o2.getHighRate()) {
            return o1.getStockInfo().getId().compareTo(o2.getStockInfo().getId());
        } else {
            return o1.getHighRate() > o2.getHighRate() ? -1 : 1;
        }
    }

}
