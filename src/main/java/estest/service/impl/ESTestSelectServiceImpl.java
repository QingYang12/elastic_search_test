package estest.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import estest.dao.PersionsDao;
import estest.entity.Item;
import estest.entity.Persions;
import estest.service.ESTestSelectService;
import estest.service.ESTestService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

//@RunWith(SpringRunner.class)

//@SpringBootTest(classes = ElasticApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
@Service("eSTestSelectServiceImpl")
public class ESTestSelectServiceImpl implements ESTestSelectService {
    //private static Logger logger=Logger.getLogger(ESTestServiceImpl.class);

    @Resource
    public PersionsDao persionsDao;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void select_string_Data() {
        // 构建一个普通的查询
        System.out.println(1);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery("张无忌"))
                .build();
        List<Item> resultItemLists =elasticsearchTemplate.queryForList(searchQuery,Item.class);
        System.out.println(2);

    }

    @Override
    public void select_term_Data() {

        System.out.println(1);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("name.keyword","张无忌"))
                .build();
        List<Item> resultItemLists =elasticsearchTemplate.queryForList(searchQuery,Item.class);
        System.out.println(2);

    }
//
    @Override
    public void select_range_Data() {
        System.out.println(1);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.rangeQuery("age").gte(18).lte(22))
                .build();
        List<Item> resultItemLists =elasticsearchTemplate.queryForList(searchQuery,Item.class);
        System.out.println(2);
    }

    @Override
    public void select_wildcard_Data() {
        System.out.println(1);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.wildcardQuery("name","张*忌"))
                .build();
        List<Item> resultItemLists =elasticsearchTemplate.queryForList(searchQuery,Item.class);
        System.out.println(2);
    }

    @Override
    public void select_match_Data() {
        System.out.println(1);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name.keyword","张*忌"))
                .build();
        List<Item> resultItemLists =elasticsearchTemplate.queryForList(searchQuery,Item.class);
        System.out.println(2);
    }

    @Override
    public void select_bool_Data() {

        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        List <QueryBuilder> queryarr=new ArrayList<>();
        queryarr.add(QueryBuilders.termQuery("sect.keyword","明教"));
        queryarr.add(QueryBuilders.termQuery("sex.keyword","女"));
        boolQueryBuilder.must().addAll(queryarr);
        System.out.println(1);
        SearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
        List<Item> resultItemLists =elasticsearchTemplate.queryForList(searchQuery,Item.class);
        System.out.println(2);
    }
    @Override
    public void select_cardinality_Data() {
        System.out.println(1);
        CardinalityAggregationBuilder agg = AggregationBuilders.cardinality("sect_name").field("sect.keyword");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .addAggregation(agg)
                .build();
        Aggregations resultItemLists =elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });
        System.out.println(2);
        // 获取聚合结果
        InternalCardinality teamAgg = (InternalCardinality) resultItemLists.asMap().get("sect_name");
        Long num=teamAgg.getValue();
        System.out.println("num "+ num);
        System.out.println(2);
    }
    @Override
    public void select_max_Data() {
        //
        //由于是聚合，这里使用的是AggregationBuilder。maxage是自己定义的给查询出来的最大值起的名字，age是elasticsearch中的index里面我们放进去的数据里面的字段名，也就是要在该字段中聚合出最大值
        MaxAggregationBuilder agg = AggregationBuilders.max("maxage").field("age");
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .addAggregation(agg)
                .build();
        Aggregations resultItemLists =elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });
        System.out.println(2);
        // 获取聚合结果
        //StringTerms teamAgg = (StringTerms) resultItemLists.asMap().get("maxage");
        List<Aggregation> list =resultItemLists.asList();
        if(!CollectionUtils.isEmpty(list)){
            InternalMax resultMap= (InternalMax) list.get(0);
            Double maxage=resultMap.value();
            System.out.println(maxage);
        }else{
            System.out.println("NULL");
        }
        //实例
        /*List<StringTerms.Bucket> bucketList = teamAgg.getBuckets();
        for(StringTerms.Bucket bucket:bucketList) {
            // 结构化地址
            String formatAddress = bucket.getKeyAsString();
            System.out.println(formatAddress);

            Aggregations signAggs = bucket.getAggregations();
            StringTerms signTerms = (StringTerms) signAggs.asMap().get("sign_org_aggs");
            List<StringTerms.Bucket> signBucketList = signTerms.getBuckets();
            // 签收网点只能一个
            if(signBucketList==null || signBucketList.size() >1) {
                continue;
            }

            StringTerms.Bucket signBucket = signBucketList.get(0);
            // 签收频次需要5次以上
            if(signBucket.getDocCount() >= 5) {

                // 满足条件的网点放入prepareList
//                HistoryIndexDocument entity = new HistoryIndexDocument();
//                entity.setFormatAddress(formatAddress);
//                entity.setSignOrgCode(signBucket.getKeyAsString());
//                prepareList.add(entity);
            }
        }*/
    }

    @Override
    public void select_aggregations_Data() {
        TermsAggregationBuilder agg = AggregationBuilders.terms("sect_count").field("sect.keyword");
        TermsAggregationBuilder agg2 = AggregationBuilders.terms("sex_count").field("sex.keyword");
        agg.subAggregation(agg2);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .addAggregation(agg)
                .build();
        Aggregations resultItemLists =elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });
        System.out.println(2);
        // 获取聚合结果
        StringTerms teamAgg = (StringTerms) resultItemLists.asMap().get("sect_count");
        List<StringTerms.Bucket> bucketList = teamAgg.getBuckets();
        JSONArray reslist=new JSONArray();
        for(StringTerms.Bucket bucket:bucketList) {

            String Key = bucket.getKeyAsString();
            Aggregations bucketAggregations=bucket.getAggregations();
            StringTerms teamAggsect = (StringTerms) bucketAggregations.asMap().get("sex_count");
            List<StringTerms.Bucket> bucket2List = teamAggsect.getBuckets();
            for(StringTerms.Bucket bucket2:bucket2List){
                String Key2 = bucket2.getKeyAsString();
                JSONObject jsonitem=new JSONObject();
                jsonitem.put("sect",Key);
                jsonitem.put("sex",Key2);
                jsonitem.put("num",bucket2.getDocCount());
                reslist.add(jsonitem);
            }


        }
        System.out.println(reslist.toString());
    }

    @Override
    public void select_aggregations_two_Data() {
        TermsAggregationBuilder aggsex = AggregationBuilders.terms("sex_count").field("sex.keyword");
        TermsAggregationBuilder aggsect = AggregationBuilders.terms("sect_count").field("sect.keyword");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .addAggregation(aggsex)
                .addAggregation(aggsect)
                .build();
        Aggregations resultItemLists =elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });
        System.out.println(2);
        // 获取聚合结果
        StringTerms teamAggsex = (StringTerms) resultItemLists.asMap().get("sex_count");
        List<StringTerms.Bucket> bucketListsex = teamAggsex.getBuckets();
        JSONArray reslistsex=new JSONArray();
        for(StringTerms.Bucket bucket:bucketListsex) {
            // Key
            String Key = bucket.getKeyAsString();
            System.out.println(Key);
            Long num=bucket.getDocCount();
            JSONObject jsonitem=new JSONObject();
            jsonitem.put(Key,num);
            reslistsex.add(jsonitem);

        }
        System.out.println(reslistsex.toString());

        StringTerms teamAggsect = (StringTerms) resultItemLists.asMap().get("sect_count");
        List<StringTerms.Bucket> bucketList = teamAggsect.getBuckets();
        JSONArray reslistsect=new JSONArray();
        for(StringTerms.Bucket bucket:bucketList) {
            // Key
            String Key = bucket.getKeyAsString();
            System.out.println(Key);
            Long num=bucket.getDocCount();
            JSONObject jsonitem=new JSONObject();
            jsonitem.put(Key,num);
            reslistsect.add(jsonitem);

        }
        System.out.println(reslistsect.toString());
    }

}
