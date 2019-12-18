package com.graduation.project.risk.common.core.dal.mongdb.query;

import org.springframework.stereotype.Service;

//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Query;


@Service
public class ManualDAO {

//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    /**
//     *
//     * @param queryForm   the queryGroup form
//     * @param entityClass the entity class
//     * @return the page
//     */
//    public <T> Page<T> queryByPage(Object queryForm, Class<T> entityClass) {
//
//        Query query = QueryUtil.buildQuery(queryForm, entityClass);
//
//        long count = mongoTemplate.count(query, entityClass);
//
//        List<T> list = mongoTemplate.find(query, entityClass);
//
//        int pageNumber = 1;
//        if (queryForm instanceof PageQuery) {
//            PageQuery pageQuery = (PageQuery) queryForm;
//            pageNumber = pageQuery.getPageNumber();
//        }
//
//        Pageable pageable = new PageRequest(pageNumber - 1 < 0 ? 0 : pageNumber - 1,
//            query.getLimit());
//
//        Page<T> page = new PageImpl<>(list, pageable, count);
//
//        return page;
//
//    }
//
//    /**
//     *
//     * @param queryForm   the queryGroup form
//     * @param entityClass the entity class
//     * @return the page
//     */
//    public <T> List<T> query(Object queryForm, Class<T> entityClass) {
//
//        Query query = QueryUtil.buildQuery(queryForm, entityClass);
//
//        long count = mongoTemplate.count(query, entityClass);
//
//        List<T> list = mongoTemplate.find(query, entityClass);
//
//        return list;
//
//    }

}
