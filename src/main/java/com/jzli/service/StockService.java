package com.jzli.service;

import com.jzli.bean.StockInfo;
import com.jzli.bean.StockRecord;
import com.jzli.repository.StockInfoRepository;
import com.jzli.repository.StockRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");


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

    public List<StockRecord> getStockHistory(String code, String start, String end) {
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(start);
        } catch (Exception e) {
        }
        try {
            endDate = sdf.parse(end);
        } catch (Exception e) {
        }
        return stockRecordRepository.list(code, startDate, endDate);
    }
}
