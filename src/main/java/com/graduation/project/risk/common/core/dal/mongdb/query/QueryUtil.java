package com.graduation.project.risk.common.core.dal.mongdb.query;

//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.MongoRegexCreator;
//import org.springframework.data.mongodb.core.query.Query;


public class QueryUtil {

//    /**
//     * @param queryObject
//     * @return
//     */
//    public static Query buildQuery(Object queryObject, Class entityClass) {
//
//        Map<String, Criteria> queryConditionMap = new HashMap<>();
//
//        for (Field field : FieldUtils.getAllFields(queryObject.getClass())) {
//            Object fieldValue = FieldUtil.readField(queryObject, field.getName());
//            if (fieldValue == null || "".equals(fieldValue)) {
//                continue;
//            }
//
//            QueryCondition condition = AnnotationUtils.findAnnotation(field, QueryCondition.class);
//
//            if (condition != null) {
//                parpareCondition(queryObject, field, condition, queryConditionMap);
//            }
//        }
//
//        Query query = new Query();
//        queryConditionMap.values().forEach(criteria -> {
//            query.addCriteria(criteria);
//        });
//
//        if (queryObject instanceof PageQuery) {
//            PageQuery pageQuery = (PageQuery) queryObject;
//            query.skip((pageQuery.getPageNumber() - 1) * pageQuery.getPageSize());
//            query.limit(pageQuery.getPageSize());
//        } else {
//            query.skip(0);
//            query.limit(Integer.MAX_VALUE);
//        }
//
//        if (queryObject instanceof SortQuery) {
//            Sort sort = ((SortQuery) queryObject).sort();
//            if (sort != null) {
//                query.with(sort);
//            }
//        }
//
//        return query;
//
//    }
//
//    private static void parpareCondition(Object queryObject, Field field, QueryCondition condition,
//                                         Map<String, Criteria> queryConditionMap) {
//        String name = "";
//
//        if (StringUtils.isNotBlank(condition.name())) {
//            name = condition.name();
//        } else {
//            name = field.getName();
//        }
//
//        Object value = FieldUtil.readField(queryObject, field.getName());
//
//        // EQUALS, GT, LT, GTE, LTE, ;
//        switch (condition.condition()) {
//
//            case EQUALS:
//                getCriteria(queryConditionMap, name).is(value);
//                break;
//
//            case GT:
//
//                getCriteria(queryConditionMap, name).gt(value);
//                break;
//
//            case LT:
//                getCriteria(queryConditionMap, name).lt(value);
//                break;
//
//            case GTE:
//                getCriteria(queryConditionMap, name).gte(value);
//                break;
//
//            case LTE:
//                getCriteria(queryConditionMap, name).lte(value);
//                break;
//
//            case IN:
//
//                Object temp = FieldUtil.readField(queryObject, field.getName());
//
//                if (temp instanceof Collection) {
//
//                    if (!CollectionUtils.isEmpty((Collection) temp)) {
//                        getCriteria(queryConditionMap, name).in(((Collection) temp));
//                    }
//
//                }
//
//                break;
//
//            case NOT_IN:
//                getCriteria(queryConditionMap, name).nin(value);
//                break;
//
//            case EXISTS:
//                getCriteria(queryConditionMap, name).exists((Boolean) value);
//                break;
//            case LIKE:
//                getCriteria(queryConditionMap, name).regex(
//                    MongoRegexCreator.INSTANCE.toRegularExpression(((String) value).toLowerCase(),
//                        Part.Type.EXISTS));
//                break;
//            default:
//                getCriteria(queryConditionMap, name).is(value);
//                break;
//        }
//    }
//
//    private static Criteria getCriteria(Map<String, Criteria> queryConditionMap, String name) {
//
//        if (queryConditionMap.get(name) == null) {
//            Criteria criteria = Criteria.where(name);
//            queryConditionMap.put(name, criteria);
//        }
//
//        return queryConditionMap.get(name);
//    }

}
