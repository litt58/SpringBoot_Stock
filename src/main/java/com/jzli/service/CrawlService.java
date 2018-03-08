package com.jzli.service;

import com.jzli.bean.StockInfo;
import com.jzli.bean.StockRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SinaStockService sinaStockService;

    /**
     * 新浪股票上海市场标识
     */
    private final String sinaStockShanghai = "sh";
    /**
     * 新浪股票深圳市场标识
     */
    private final String sinaStockShenzhen = "sz";


    public void loopStockMarket() throws IOException {
        loopShanghai();
        loopShenzhen();
        loopchuangye();
    }

    /**
     * 同步上海市场股票信息
     *
     * @throws IOException
     */
    public void loopShanghai() throws IOException {
        long start = System.currentTimeMillis();
        StringBuilder sb;
        for (int i = 0; i < 2000; i++) {
            sb = new StringBuilder("6");
            String s = Integer.toString(i);
            for (int j = 0; j < 5 - s.length(); j++) {
                sb.append("0");
            }
            sb.append(s);
            sinaStockService.searchStock(sb.toString(), sinaStockShanghai);
        }
        logger.info("同步上海市场股票信息总用时:" + (System.currentTimeMillis() - start) + "毫秒");
    }

    /**
     * 同步深圳市场股票信息
     *
     * @throws IOException
     */
    public void loopShenzhen() throws IOException {
        long start = System.currentTimeMillis();
        StringBuilder sb;
        for (int i = 0; i < 2100; i++) {
            sb = new StringBuilder();
            String s = Integer.toString(i);
            for (int j = 0; j < 6 - s.length(); j++) {
                sb.append("0");
            }
            sb.append(s);
            sinaStockService.searchStock(sb.toString(), sinaStockShenzhen);
        }
        logger.info("同步深圳市场股票信息:" + (System.currentTimeMillis() - start) + "毫秒");

    }

    /**
     * 同步创业板股票信息
     *
     * @throws IOException
     */
    public void loopchuangye() throws IOException {
        long start = System.currentTimeMillis();
        StringBuilder sb;
        for (int i = 0; i < 1000; i++) {
            sb = new StringBuilder("3");
            String s = Integer.toString(i);
            for (int j = 0; j < 5 - s.length(); j++) {
                sb.append("0");
            }
            sb.append(s);
            sinaStockService.searchStock(sb.toString(), sinaStockShenzhen);
        }
        logger.info("同步创业板股票信息:" + (System.currentTimeMillis() - start) + "毫秒");
    }

    public StockInfo crawlStock(String stockId, String stockMarket) {
        return sinaStockService.searchStock(stockId, stockMarket);
    }

    public List<StockRecord> crawlStockHistory(String stockId, String start, String end) throws Exception {
        return sinaStockService.crawlStockHistory(stockId, start, end);
    }
}
