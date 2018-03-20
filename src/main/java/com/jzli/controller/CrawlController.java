package com.jzli.controller;

import com.jzli.bean.StockInfo;
import com.jzli.service.CrawlService;
import com.jzli.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/crawl")
@Api(value = "/crawl", description = "抓取股票")
public class CrawlController {
    @Autowired
    private CrawlService crawlService;

    @Autowired
    private StockService stockService;

    /**
     * 默认开始时间
     */
    private final String start = "20150101";
    /**
     * 默认结束时间
     */
    private final String end = "20190101";

    /**
     * 新浪股票上海市场标识
     */
    private final String sinaStockShanghai = "sh";
    /**
     * 新浪股票深圳市场标识
     */
    private final String sinaStockShenzhen = "sz";

    @RequestMapping("/crawlStock")
    @ApiOperation(value = "搜索个股信息", httpMethod = "GET", notes = "搜索个股信息")
    public StockInfo crawlStock(@RequestParam("code") String code, @RequestParam("stockMarketCode") String stockMarketCode) throws IOException {
        return crawlService.searchStock(code, stockMarketCode);
    }


    @RequestMapping("/loopStockInfo")
    @ApiOperation(value = "遍历股票信息", httpMethod = "GET", notes = "遍历股票信息")
    public void loop() throws IOException {
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
        StringBuilder sb;
        for (int i = 0; i < 2000; i++) {
            sb = new StringBuilder("6");
            String s = Integer.toString(i);
            for (int j = 0; j < 5 - s.length(); j++) {
                sb.append("0");
            }
            sb.append(s);
            crawlService.searchStock(sb.toString(), sinaStockShanghai);
        }
    }

    /**
     * 同步深圳市场股票信息
     *
     * @throws IOException
     */
    public void loopShenzhen() throws IOException {
        StringBuilder sb;
        for (int i = 0; i < 2100; i++) {
            sb = new StringBuilder();
            String s = Integer.toString(i);
            for (int j = 0; j < 6 - s.length(); j++) {
                sb.append("0");
            }
            sb.append(s);
            crawlService.searchStock(sb.toString(), sinaStockShenzhen);
        }
    }

    /**
     * 同步创业板股票信息
     *
     * @throws IOException
     */
    public void loopchuangye() throws IOException {
        StringBuilder sb;
        for (int i = 0; i < 1000; i++) {
            sb = new StringBuilder("3");
            String s = Integer.toString(i);
            for (int j = 0; j < 5 - s.length(); j++) {
                sb.append("0");
            }
            sb.append(s);
            crawlService.searchStock(sb.toString(), sinaStockShenzhen);
        }
    }

    @RequestMapping("/crawlStockHistory")
    @ApiOperation(value = "抓取个股信息历史信息", httpMethod = "GET", notes = "抓取个股信息历史信息")
    public void crawlStockHistory(@RequestParam("code") String code, @ApiParam(name = "start", value = "格式为yyyyMMdd") @RequestParam("start") String start, @ApiParam(name = "end", value = "格式为yyyyMMdd") @RequestParam("end") String end) throws Exception {
        crawlService.crawlStockHistory(code, start, end);
    }

    @RequestMapping("/loopStockHistory")
    @ApiOperation(value = "遍历股票信息", httpMethod = "GET", notes = "遍历股票信息")
    public void loopStockHistory() throws Exception {
        List<StockInfo> query = stockService.query();
        for (StockInfo stockInfo : query) {
            crawlService.crawlStockHistory(stockInfo.getId(), start, end);
        }
    }

    @RequestMapping("/crawlNewStockHistory")
    @ApiOperation(value = "遍历最新的股票历史信息", httpMethod = "GET", notes = "遍历最新的股票历史信息")
    public void crawlNewStockHistory() throws Exception {
        List<StockInfo> query = stockService.query();
        for (StockInfo stockInfo : query) {
            crawlService.crawlStockHistory(stockInfo.getId(), start, end);
        }
    }
}
