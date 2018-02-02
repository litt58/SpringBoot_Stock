package com.jzli.service;

import com.jzli.bean.StockInfo;
import com.jzli.repository.StockInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    private StockInfoRepository stockInfoRepository;
    @Autowired
    private HttpService httpService;

    private final String sinaStockInfoUrl = "http://hq.sinajs.cn/list";
    /**
     * 新浪股票上海市场标识
     */
    private final String sinaStockShanghai = "sh";
    /**
     * 新浪股票深圳市场标识
     */
    private final String sinaStockShenzhen = "sz";

    public void addStockInfo(StockInfo stockInfo) {
        stockInfoRepository.add(stockInfo);
    }

    public StockInfo getStockInfo(String id) {
        return stockInfoRepository.get(id);
    }

    public void deleteStockInfo(String id) {
        stockInfoRepository.delete(id);
    }

    /**
     * 搜索指定的股票信息
     *
     * @param stockId
     * @param stockMarket
     * @return
     * @throws IOException
     */
    @Async
    public StockInfo searchStock(String stockId, String stockMarket) {
        StockInfo stockInfo = null;
        StringBuilder sb = new StringBuilder();
        //拼接访问地址
        sb.append(sinaStockInfoUrl).append("=").append(stockMarket).append(stockId);
        //访问接口
        try {
            String result = httpService.get(sb.toString());
            //处理接口返回
            stockInfo = dealStockInfoResult(result);
            if (!ObjectUtils.isEmpty(stockInfo)) {
                stockInfoRepository.add(stockInfo);
                logger.info(stockInfo.toString());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return stockInfo;
    }

    @Async
    public void excute(int i) {
        logger.info("测试:" + i);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void test() {
        for (int i = 1; i < 101; i++) {
            excute(i);
        }
    }

    /**
     * 处理返回结果
     *
     * @param result
     * @return
     */
    public static StockInfo dealStockInfoResult(String result) {
        StockInfo stockInfo = null;
        String[] split = result.split("=");
        if (!ObjectUtils.isEmpty(split)
                && split.length == 2) {
            //获取股票代码
            String code = split[0].substring(split[0].length() - 6);
            //检查股票代码是否存在
            String[] split1 = split[1].replace("\"", "").split(",");
            if (!ObjectUtils.isEmpty(split1)
                    && split1.length == 33) {
                //检查股票代码是否已经退市
                boolean isOk = false;
                String name = split1[0];
                for (int i = 1; i < 4; i++) {
                    double v = Double.parseDouble(split1[i]);
                    if (v != 0) {
                        isOk = true;
                        break;
                    }
                }

                if (isOk
                        && !ObjectUtils.isEmpty(code)
                        && !ObjectUtils.isEmpty(name)) {
                    stockInfo = new StockInfo();
                    stockInfo.setId(code);
                    stockInfo.setName(name);
                }
            }

        }
        return stockInfo;
    }

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
            searchStock(sb.toString(), sinaStockShanghai);
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
        for (int i = 0; i < 1000; i++) {
            sb = new StringBuilder();
            String s = Integer.toString(i);
            for (int j = 0; j < 6 - s.length(); j++) {
                sb.append("0");
            }
            sb.append(s);
            searchStock(sb.toString(), sinaStockShenzhen);
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
        for (int i = 0; i < 800; i++) {
            sb = new StringBuilder("3");
            String s = Integer.toString(i);
            for (int j = 0; j < 5 - s.length(); j++) {
                sb.append("0");
            }
            sb.append(s);
            searchStock(sb.toString(), sinaStockShanghai);
        }
        logger.info("同步创业板股票信息:" + (System.currentTimeMillis() - start) + "毫秒");
    }

}
