package com.jzli.repository;

import com.jzli.bean.StockRecord;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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
        if (ObjectUtils.isEmpty(stockRecord)
                && record.getStart() != 0d
                && record.getEnd() != 0d
                && record.getHigh() != 0d
                && record.getLow() != 0d) {
            mongoTemplate.insert(record);
        }
    }

    public void addAll(List<StockRecord> records) {
//        mongoTemplate.insertAll(records);
        records.forEach(this::add);
    }

    public List<StockRecord> list(String code, Date start, Date end) {
        Criteria criteria = buildCriteria(code, start, end);
        Query query = new Query(criteria);
        List<StockRecord> stockRecords = mongoTemplate.find(query, StockRecord.class);
        return stockRecords;
    }

    public StockRecord getHigh(String code, Date start, Date end) {
        StockRecord stockRecord;
        Criteria criteria = buildCriteria(code, start, end);
        Aggregation agg = newAggregation(
                match(criteria),
                group().max("high")
                        .as("high")
        ).withOptions(newAggregationOptions().cursor(new BasicDBObject()).build());
        AggregationResults<Double> results = mongoTemplate.aggregate(agg, StockRecord.class, Double.class);
        Double result = getAggregateResult(results);
        Criteria high = criteria.and("high").is(result);
        Query query = new Query(high);
        stockRecord = mongoTemplate.findOne(query, StockRecord.class);
        return stockRecord;
    }

    public StockRecord getLow(String code, Date start, Date end) {
        StockRecord stockRecord;
        Criteria criteria = buildCriteria(code, start, end);
        Aggregation agg = newAggregation(
                match(criteria),
                group().min("low")
                        .as("low")
        ).withOptions(newAggregationOptions().cursor(new BasicDBObject()).build());
        AggregationResults<Double> results = mongoTemplate.aggregate(agg, StockRecord.class, Double.class);
        Double result = getAggregateResult(results);
        Criteria high = criteria.and("low").is(result);
        Query query = new Query(high);
        stockRecord = mongoTemplate.findOne(query, StockRecord.class);
        return stockRecord;
    }

    private Criteria buildCriteria(String code, Date start, Date end) {
        Criteria criteria = null;
        if (!ObjectUtils.isEmpty(code)) {
            criteria = Criteria.where("code").is(code);
            Criteria date = null;
            if (!ObjectUtils.isEmpty(start)) {
                date = Criteria.where("date").gte(start);
            }
            if (!ObjectUtils.isEmpty(end)) {
                if (!ObjectUtils.isEmpty(date)) {
                    date = date.lte(end);
                } else {
                    date = Criteria.where("date").lte(end);
                }
            }
            if (!ObjectUtils.isEmpty(date)) {
                criteria = criteria.andOperator(date);
            }
        }
        return criteria;
    }

    /**
     * 处理聚合结果
     *
     * @param results
     * @return
     */
    public Double getAggregateResult(AggregationResults<Double> results) {
        DBObject rawResults = results.getRawResults();
        BasicDBObject cursor = (BasicDBObject) rawResults.get("cursor");
        BasicDBList firstBatch = (BasicDBList) cursor.get("firstBatch");
        BasicDBObject obj = (BasicDBObject) firstBatch.get(0);
        Set<String> set = obj.keySet();
        String key = null;
        for (String s : set) {
            key = s;
        }
        Double value = (Double) obj.get(key);
        return value;
    }
}
