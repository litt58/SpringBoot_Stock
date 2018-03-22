package com.jzli.repository;

import com.jzli.bean.PageInfo;
import com.jzli.bean.StockInfo;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

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
public class StockInfoRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<StockInfo> query(StockInfo stockInfo) {
        Criteria criteria = buildCriteria(stockInfo);
        return mongoTemplate.find(new Query(criteria).with(new Sort(Sort.Direction.ASC, "id")), StockInfo.class);
    }

    public PageInfo<StockInfo> paginationQuery(StockInfo stockInfo, Integer pageNo, Integer pageSize) {
        Criteria criteria = buildCriteria(stockInfo);
        PageInfo<StockInfo> pageInfo = new PageInfo<>(pageNo, pageSize);
        Query query = new Query(criteria);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(pageInfo.getPageNo() - 1, pageInfo.getPageSize(), sort);
        query.with(pageable);
        List<StockInfo> stockInfoList = mongoTemplate.find(query, StockInfo.class);
        pageInfo.setData(stockInfoList);
        long count = mongoTemplate.count(new Query(criteria), StockInfo.class);
        pageInfo.setTotal(count);
        return pageInfo;
    }

    /**
     * 新增
     *
     * @param stockInfo
     */
    public void add(StockInfo stockInfo) {
        StockInfo stockInfo1 = get(stockInfo.getId());
        if (ObjectUtils.isEmpty(stockInfo1)) {
            mongoTemplate.insert(stockInfo);
        } else {
            //股票名称是否更新
            if (!stockInfo1.getName().equals(stockInfo.getName())) {
                update(stockInfo);
            }
        }
    }

    /**
     * 更新
     *
     * @param stockInfo
     */
    public void update(StockInfo stockInfo) {
        Query query = Query.query(Criteria.where("id").is(stockInfo.getId()));
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("name", stockInfo.getName());
        Update update = new BasicUpdate(new BasicDBObject("$set", basicDBObject));
        mongoTemplate.updateFirst(query, update, StockInfo.class);
    }

    /**
     * 获取指定的股票信息
     *
     * @param id
     * @return
     */
    public StockInfo get(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, StockInfo.class);
    }

    /**
     * 删除指定的股票信息
     *
     * @param id
     */
    public void delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, StockInfo.class);
    }


    private Criteria buildCriteria(StockInfo stockInfo) {
        Criteria criteria = new Criteria();
        if (!ObjectUtils.isEmpty(stockInfo)) {
            if (!ObjectUtils.isEmpty(stockInfo.getId())) {
                Criteria id = Criteria.where("id").is(stockInfo.getId());
                criteria = criteria.andOperator(id);
            }
            if (!ObjectUtils.isEmpty(stockInfo.getName())) {
                Criteria name = Criteria.where("name").regex(stockInfo.getName());
                criteria = criteria.andOperator(name);
            }
            if (!ObjectUtils.isEmpty(stockInfo.getStar())) {
                Criteria star = Criteria.where("star").is(stockInfo.getStar());
                criteria = criteria.andOperator(star);
            }

        }
        return criteria;
    }

    public StockInfo star(String id) {
        StockInfo stockInfo = get(id);
        if (stockInfo.getStar()) {
            stockInfo.setStar(Boolean.FALSE);
        } else {
            stockInfo.setStar(Boolean.TRUE);
        }
        Query query = new Query(Criteria.where("id").is(id));
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("star", stockInfo.getStar());
        Update update = new BasicUpdate(new BasicDBObject("$set", basicDBObject));
        mongoTemplate.updateFirst(query, update, StockInfo.class);
        stockInfo = get(id);
        return stockInfo;
    }
}
