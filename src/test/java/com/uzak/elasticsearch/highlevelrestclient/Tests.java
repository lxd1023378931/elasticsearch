package com.uzak.elasticsearch.highlevelrestclient;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Tests
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/16 22:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {

    @Autowired
    private ElasticsearchConfiguration configuration;

    @Test
    public void indexApi() throws Exception{
        for (int i = 2; i < 1000; i ++){
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("userName", "uzak"+i);
            map.put("password", "123");
            map.put("createTime", LocalDateTime.now());
            map.put("message", "High level restclient test"+i);
            IndexRequest indexRequest = new IndexRequest("user", "manage", ""+i).source(map);
            IndexResponse response = configuration.getObject().index(indexRequest);
            System.out.println(response);
        }
    }

    @Test
    public void getApi() throws Exception{
        GetRequest request = new GetRequest("user", "manage", "999");
        GetResponse response = configuration.getObject().get(request);
        System.out.println(response);
    }

    @Test
    public void deleteApi() throws Exception{
        DeleteRequest request = new DeleteRequest("user", "manage", "1");
        DeleteResponse response = configuration.getObject().delete(request);
        System.out.println(response);
    }
}