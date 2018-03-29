package com.jzli.service;

import com.jzli.bean.StockInfo;
import com.jzli.bean.StockRecord;
import com.jzli.client.HttpClient;
import com.jzli.repository.StockInfoRepository;
import com.jzli.repository.StockRecordRepository;
import com.jzli.util.JodaTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
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
    private StockInfoRepository stockInfoRepository;
    @Autowired
    private StockRecordRepository stockRecordRepository;
    @Autowired
    private HttpClient httpClient;

    private final String sinaStockInfoUrl = "http://hq.sinajs.cn/list";

    //http://quotes.money.163.com/service/chddata.html?code=CODE&start=START&end=END&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;VOTURNOVER
    private final String sotckHIstoryCsvUrl = "http://quotes.money.163.com/service/chddata.html?code=CODE&start=START&end=END&fields=TCLOSE;HIGH;LOW;TOPEN";

    /**
     * 搜索指定的股票信息
     *
     * @param stockId
     * @param stockMarket
     * @return
     * @throws IOException
     */
    @Async
    public StockInfo searchStock(String stockId, String stockMarket) throws IOException {
        StockInfo stockInfo;
        StringBuilder sb = new StringBuilder();
        //拼接访问地址
        sb.append(sinaStockInfoUrl).append("=").append(stockMarket).append(stockId);
        //访问接口
        String result = httpClient.get(sb.toString());
        //处理接口返回
        stockInfo = dealStockInfoResult(result);
        if (!ObjectUtils.isEmpty(stockInfo)) {
            stockInfoRepository.add(stockInfo);
            logger.info(stockInfo.toString());
        }
        return stockInfo;
    }

    /**
     * 处理返回结果
     *
     * @param result
     * @return
     */
    public StockInfo dealStockInfoResult(String result) {
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
                    stockInfo.setStar(Boolean.FALSE);
                }
            }

        }
        return stockInfo;
    }

    /**
     * 获取指定时间范围的股票历史信息
     *
     * @param code
     * @return
     * @throws Exception
     */
    @Async
    public List<StockRecord> crawlAllStockHistory(String code) throws Exception {
        return crawlStockHistory(code, JodaTimeUtils.DEFAULT_START, JodaTimeUtils.DEFAULT_END);
    }

    /**
     * 获取最新的股票历史信息
     *
     * @param code
     * @return
     * @throws Exception
     */
    @Async
    public List<StockRecord> crawlNewestStockHistory(String code) throws Exception {
        StockRecord stockRecord = stockRecordRepository.getLastHistoryDate(code);
        return crawlStockHistory(code, stockRecord.getTime().replace("-",""), JodaTimeUtils.getDateString2(new Date()));
    }

    public List<StockRecord> crawlStockHistory(String code, String start, String end) throws Exception {
        String url = sotckHIstoryCsvUrl;
        StringBuilder sb = new StringBuilder();
        if (code.startsWith("6")) {
            //代表上海
            sb.append("0").append(code);
        } else {
            //代表深圳
            sb.append("1").append(code);
        }
        url = url.replace("CODE", sb.toString());
        url = url.replace("START", start);
        url = url.replace("END", end);
        String result = httpClient.get(url);
        List<StockRecord> stockRecords = dealStockHistoryInfoResult(result);
        stockRecordRepository.addAll(stockRecords);
        logger.info("保存" + code + "历史信息成功");
        return stockRecords;
    }

    /**
     * 处理历史数据
     *
     * @param result
     */
    private List<StockRecord> dealStockHistoryInfoResult(String result) {
        List list = null;
        if (!ObjectUtils.isEmpty(result)) {
            list = new LinkedList<StockRecord>();
            String[] split = result.split("\r\n");
            for (int n = split.length, i = 1; i < n; i++) {
                String s = split[i];
                String[] strings = s.split(",");
                if (!ObjectUtils.isEmpty(strings) && strings.length == 7) {
                    StockRecord record;
                    record = new StockRecord();
                    record.setTime(strings[0]);
                    record.setDate(JodaTimeUtils.parseDate(strings[0]));
                    record.setCode(strings[1].replace("'", ""));
                    record.setEnd(Double.parseDouble(strings[3]));
                    record.setHigh(Double.parseDouble(strings[4]));
                    record.setLow(Double.parseDouble(strings[5]));
                    record.setStart(Double.parseDouble(strings[6]));
                    record.setId(record.getCode() + "-" + record.getDate().getTime());
                    list.add(record);
                }
            }
        }
        return list;
    }
}
