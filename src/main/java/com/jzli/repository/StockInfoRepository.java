package com.jzli.repository;

import com.jzli.bean.StockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

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
@Repository
public class StockInfoRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void add(StockInfo stockInfo) {
        mongoTemplate.insert(stockInfo);
    }

}
