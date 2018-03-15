package com.jzli.service;

import com.jzli.bean.StockInfo;
import com.jzli.bean.StockRecord;
import com.jzli.repository.StockInfoRepository;
import com.jzli.repository.StockRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


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
        Date startDate = sdf.parse(start);
        Date endDate = sdf.parse(end);
        return stockRecordRepository.list(code, startDate, endDate);
    }

    public StockRecord getHigh(String code, String start, String end) throws ParseException {
        Date startDate = getDate(start);
        Date endDate = getDate(end);
        return stockRecordRepository.getHigh(code, startDate, endDate);
    }

    public StockRecord getLow(String code, String start, String end) throws ParseException {
        Date startDate = getDate(start);
        Date endDate = getDate(end);
        return stockRecordRepository.getLow(code, startDate, endDate);
    }

    public Date getDate(String time) throws ParseException {
        Date date = null;
        if (!ObjectUtils.isEmpty(time)) {
            date = sdf.parse(time);
        }
        return date;
    }
}
