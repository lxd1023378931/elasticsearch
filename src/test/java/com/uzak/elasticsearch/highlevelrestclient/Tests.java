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
    private ESService esService;

    @Test
    public void indexApi() throws Exception{
        esService.indexApi();
    }

    @Test
    public void getApi() throws Exception{
        esService.getApi();
    }

    @Test
    public void deleteApi() throws Exception{
        esService.deleteApi();
    }

    @Test
    public void bulkApi() throws Exception{
        esService.bulkApi();
    }

    @Test
    public void bulkAsyncApi() throws Exception{
        esService.bulkAsyncApi();
    }

}