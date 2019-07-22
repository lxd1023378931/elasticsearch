package com.uzak.elasticsearch.highlevelrestclient.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        request.indices("user");
        //指定查询的文档库中的文档类型
        request.types("manage");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询所有内容
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //==
//        searchSourceBuilder.query(QueryBuilders.termQuery("id", 1));
        // in()
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("userName", "uzak1")
                            //设置开启模糊查询
                            .fuzziness(Fuzziness.AUTO)
                            );
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1000);
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request);
        Arrays.stream(response.getHits().getHits()).forEach(one -> {
            Map<String, Object> map = one.getSourceAsMap();
            System.out.println(map.get("userName"));
        });
    }

}