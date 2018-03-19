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

    public List<StockInfo> query() {
        return mongoTemplate.find(new Query().with(new Sort(Sort.Direction.ASC, "id")), StockInfo.class);
    }

    public PageInfo<StockInfo> paginationQuery(int pageNo, int pageSize) {
        PageInfo<StockInfo> pageInfo = new PageInfo<>(pageNo, pageSize);
        Query query = new Query();
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(pageInfo.getPageNo() - 1, pageInfo.getPageSize(), sort);
        query.with(pageable);
        List<StockInfo> stockInfoList = mongoTemplate.find(query, StockInfo.class);
        pageInfo.setData(stockInfoList);
        long count = mongoTemplate.count(new Query(), StockInfo.class);
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

}
