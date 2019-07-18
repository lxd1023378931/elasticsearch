package com.uzak.elasticsearch.highlevelrestclient.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SearchService
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/18 21:19
 */
@Service
public class SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void search() throws Exception{
        SearchRequest request = new SearchRequest();
        //查询多个文档库，其中多个文档库名之间用逗号隔开
        request.indices("user", "esindex-2019", "agent");
        //指定查询的文档库中的文档类型
        request.types("estype", "manage", "person");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询所有内容
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request);
        System.out.println(response.getHits().getTotalHits());
    }

}