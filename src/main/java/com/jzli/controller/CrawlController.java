package com.jzli.controller;

import com.jzli.bean.StockInfo;
import com.jzli.service.CrawlService;
import com.jzli.service.HttpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private HttpService httpService;

    @RequestMapping("/welcome")
    @ApiOperation(value = "欢迎", httpMethod = "GET", notes = "欢迎")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加股票信息")
    public String add(@RequestBody @ApiParam(value = "股票信息", required = true) StockInfo stockInfo) {
        crawlService.add(stockInfo);
        return "ok";
    }

    @RequestMapping("/crawl")
    @ApiOperation(value = "爬取网页", httpMethod = "GET", notes = "爬取网页")
    public String crawl(@RequestParam("url") String url) throws IOException {
        return httpService.get(url);
    }
}
