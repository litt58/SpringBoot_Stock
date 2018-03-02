package com.jzli.controller;

import com.jzli.bean.StockInfo;
import com.jzli.bean.StockRecord;
import com.jzli.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/stock")
@Api(value = "/stock", description = "股票")
public class StockController {
    @Autowired
    private StockService stockService;

    @RequestMapping("/get")
    @ApiOperation(value = "获取个股信息", httpMethod = "GET", notes = "获取个股信息")
    public StockInfo get(@RequestParam("id") String id) throws IOException {
        return stockService.getStockInfo(id);
    }

    @RequestMapping("/delete")
    @ApiOperation(value = "删除个股信息", httpMethod = "GET", notes = "删除个股信息")
    public void delete(@RequestParam("id") String id) throws IOException {
        stockService.deleteStockInfo(id);
    }

    @RequestMapping("/getStockHistory")
    @ApiOperation(value = "获取个股信息历史信息", httpMethod = "GET", notes = "获取个股信息历史信息")
    public List<StockRecord> getStockHistory(@RequestParam("code") String code, @RequestParam("start") String start, @RequestParam("end") String end) throws Exception {
        return stockService.getStockHistory(code, start, end);
    }
}
