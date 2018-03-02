package com.jzli.repository;

import com.jzli.bean.StockRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

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


    /**
     * 获取指定的股票历史信息
     *
     * @param id
     * @return
     */
    public StockRecord get(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, StockRecord.class);
    }

    public void add(StockRecord record) {
        StockRecord stockRecord = get(record.getId());
        if (ObjectUtils.isEmpty(stockRecord)) {
            mongoTemplate.insert(record);
        }
    }

    public void addAll(List<StockRecord> records) {
//        mongoTemplate.insertAll(records);
        records.forEach(this::add);
    }

    public List<StockRecord> list(String code, Date start, Date end) {
        Query query = new Query(Criteria.where("code").is(code));
        List<StockRecord> stockRecords = mongoTemplate.find(query, StockRecord.class);
        return stockRecords;
    }
}
