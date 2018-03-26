package com.jzli.service;

import com.jzli.bean.PageInfo;
import com.jzli.bean.RecommendStockInfo;
import com.jzli.bean.StockInfo;
import com.jzli.bean.StockRecord;
import com.jzli.repository.StockInfoRepository;
import com.jzli.repository.StockRecordRepository;
import com.jzli.util.JodaTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/3/2
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
@Service
public class StockService {
    @Autowired
    private StockInfoRepository stockInfoRepository;
    @Autowired
    private StockRecordRepository stockRecordRepository;

    public StockInfo getStockInfo(String id) {
        return stockInfoRepository.get(id);
    }

    public void deleteStockInfo(String id) {
        stockInfoRepository.delete(id);
    }

    public List<StockRecord> getStockHistory(String code, String start, String end) throws ParseException {
        Date startDate = JodaTimeUtils.parseDate(start);
        Date endDate = JodaTimeUtils.parseDate(end);
        return stockRecordRepository.list(code, startDate, endDate);
    }

    public StockRecord getHigh(String code, String start, String end) throws ParseException {
        Date startDate = JodaTimeUtils.parseDate(start);
        Date endDate = JodaTimeUtils.parseDate(end);
        return stockRecordRepository.getHigh(code, startDate, endDate);
    }

    public StockRecord getLow(String code, String start, String end) throws ParseException {
        Date startDate = JodaTimeUtils.parseDate(start);
        Date endDate = JodaTimeUtils.parseDate(end);
        return stockRecordRepository.getLow(code, startDate, endDate);
    }

    public StockRecord getLastHistoryDate(String code) {
        return stockRecordRepository.getLastHistoryDate(code);
    }

    public PageInfo<StockInfo> paginationQuery(StockInfo stockInfo, Integer pageNo, Integer pageSize) {
        return stockInfoRepository.paginationQuery(stockInfo, pageNo, pageSize);
    }

    public List<StockInfo> query(StockInfo stockInfo) {
        return stockInfoRepository.query(stockInfo);
    }

    public StockInfo star(String id) {
        return stockInfoRepository.star(id);
    }

    public List<RecommendStockInfo> recommend(StockInfo stockInfo, String start, String end) throws ParseException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<StockInfo> query = query(stockInfo);
        List<RecommendStockInfo> list = new LinkedList<>();
        for (StockInfo stock : query) {
            StockRecord low = getLow(stock.getId(), start, end);
            if (!ObjectUtils.isEmpty(low)) {
                StockRecord record = getLastHistoryDate(stock.getId());
                RecommendStockInfo recommendStockInfo = new RecommendStockInfo();
                recommendStockInfo.setStockInfo(stock);
                recommendStockInfo.setCurrent(record);
                recommendStockInfo.setLow(low);
                recommendStockInfo.setLowRate(record.getLow() / low.getLow());
                list.add(recommendStockInfo);
            }
        }
        list.sort((o1, o2) -> o1.compareTo(o2));
        stopWatch.stop();
        double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        System.out.println("总用时：" + totalTimeSeconds);
        return list;
    }
}
