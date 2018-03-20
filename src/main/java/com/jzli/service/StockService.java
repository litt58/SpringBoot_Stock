package com.jzli.service;

import com.jzli.bean.PageInfo;
import com.jzli.bean.StockInfo;
import com.jzli.bean.StockRecord;
import com.jzli.repository.StockInfoRepository;
import com.jzli.repository.StockRecordRepository;
import com.jzli.util.JodaTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
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
        Date endDate =  JodaTimeUtils.parseDate(end);
        return stockRecordRepository.list(code, startDate, endDate);
    }

    public StockRecord getHigh(String code, String start, String end) throws ParseException {
        Date startDate = JodaTimeUtils.parseDate(start);
        Date endDate =  JodaTimeUtils.parseDate(end);
        return stockRecordRepository.getHigh(code, startDate, endDate);
    }

    public StockRecord getLow(String code, String start, String end) throws ParseException {
        Date startDate = JodaTimeUtils.parseDate(start);
        Date endDate = JodaTimeUtils.parseDate(end);
        return stockRecordRepository.getLow(code, startDate, endDate);
    }

    public PageInfo<StockInfo> paginationQuery(int pageNo, int pageSize) {
        return stockInfoRepository.paginationQuery(pageNo, pageSize);
    }

    public List<StockInfo> query() {
        return stockInfoRepository.query();
    }
}
