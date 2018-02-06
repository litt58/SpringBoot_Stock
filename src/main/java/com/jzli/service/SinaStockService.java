package com.jzli.service;

import com.jzli.bean.StockInfo;
import com.jzli.repository.StockInfoRepository;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
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
public class SinaStockService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private StockInfoRepository stockInfoRepository;
    @Autowired
    private HttpService httpService;

    private HtmlCleaner cleaner;

    private final String sinaStockInfoUrl = "http://hq.sinajs.cn/list";

    private final String sinaStockHistoryUrl = "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/";
    private final String YEAR = "year";
    private final String QUARTER = "jidu";
    private final String POSTFIX = ".phtml";

    @PostConstruct
    public void init() {
        cleaner = new HtmlCleaner();
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

    public String getHistory(String code, String year, String quarter) throws IOException, XPatherException {
        StringBuilder sb = new StringBuilder();
        sb.append(sinaStockHistoryUrl).append(code).append(POSTFIX)
                .append("?").append(YEAR).append("=").append(year)
                .append("&").append(QUARTER).append("=").append(quarter);
        String s = httpService.get(sb.toString());
        System.out.println(s);
        TagNode root = cleaner.clean(s);
        String xpath = "/body/div[@id='wrap']/div[@id='main']/div[@id='center']/div[@class='centerImgBlk']/div[@class='tagmain']/table[@class='table']/tbody";
        xpath = "//div[@id='wrap']/div[@id='main']/div[@id='center']/div[@class='centerImgBlk']/div[@class='tagmain']/table[@id='FundHoldSharesTable']";
        Object[] objects = root.evaluateXPath(xpath);
        for (Object obj : objects) {
            if (obj instanceof TagNode) {
                TagNode target = (TagNode) obj;
                System.out.println(target);
            }
        }
        return s;
    }

}
