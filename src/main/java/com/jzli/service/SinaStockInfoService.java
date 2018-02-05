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

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/2/5
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
@Service
public class SinaStockInfoService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private StockInfoRepository stockInfoRepository;
    @Autowired
    private HttpService httpService;

    private final String sinaStockInfoUrl = "http://hq.sinajs.cn/list";

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
}
