package com.jzli.repository;

import com.jzli.bean.StockRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
@Repository
public class StockRecordRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void add(StockRecord record) {
        mongoTemplate.insert(record);
    }

    public List<StockRecord> list(String id, Date before, Date after) {
        Query query = new Query(Criteria.where("id").is(id));
        List<StockRecord> stockRecords = mongoTemplate.find(query, StockRecord.class);
        return stockRecords;
    }
}
