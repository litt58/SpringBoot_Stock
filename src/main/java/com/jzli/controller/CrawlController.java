package com.jzli.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/welcome")
    @ApiOperation(value = "欢迎", httpMethod = "GET", notes = "欢迎")
    public String welcome() {
        return "welcome";
    }
}
