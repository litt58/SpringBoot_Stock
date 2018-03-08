package com.jzli.controller;

import com.jzli.bean.StockInfo;
import com.jzli.service.CrawlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

    @RequestMapping("/crawlStock")
    @ApiOperation(value = "搜索个股信息", httpMethod = "GET", notes = "搜索个股信息")
    public StockInfo crawlStock(@RequestParam("code") String code, @RequestParam("stockMarketCode") String stockMarketCode) throws IOException {
        return crawlService.crawlStock(code, stockMarketCode);
    }


    @RequestMapping("/loop")
    @ApiOperation(value = "遍历股票信息", httpMethod = "GET", notes = "遍历股票信息")
    public void loop() throws IOException {
        crawlService.loopStockMarket();
    }

    @RequestMapping("/crawlStockHistory")
    @ApiOperation(value = "抓取个股信息历史信息", httpMethod = "GET", notes = "抓取个股信息历史信息")
    public void crawlStockHistory(@RequestParam("code") String code, @RequestParam("start") String start, @RequestParam("end") String end) throws Exception {
        crawlService.crawlStockHistory(code, start, end);
    }

}
