package com.jzli.service;

import com.jzli.bean.StockInfo;
import com.jzli.repository.StockInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class CrawlService {
    @Autowired
    private StockInfoRepository stockInfoRepository;

    public void add(StockInfo stockInfo) {
        stockInfoRepository.add(stockInfo);
    }

    public StockInfo get(String id) {
        return stockInfoRepository.get(id);
    }
}
