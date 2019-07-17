package com.uzak.elasticsearch.highlevelrestclient;

import com.uzak.elasticsearch.highlevelrestclient.service.BulkProcessorService;
import com.uzak.elasticsearch.highlevelrestclient.service.ESService;
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

    @Autowired
    private BulkProcessorService bulkProcessorService;

    @Test
    public void createIndexApi() throws Exception{
        esService.createIndexApi();
    }

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
    public void updateApi() throws Exception{
        esService.updateApi();
    }

    @Test
    public void upsertApi() throws Exception{
        esService.upsertApi();
    }

    @Test
    public void bulkApi() throws Exception{
        esService.bulkApi();
    }

    @Test
    public void bulkAsyncApi() throws Exception{
        esService.bulkAsyncApi();
    }

    @Test
    public void add(){
        for (int i = 1; i < 3000; i ++){
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("userName", "uzak"+i);
            map.put("password", "123");
            map.put("createTime", LocalDateTime.now());
            map.put("message", "High level restclient test"+i);
            IndexRequest indexRequest = new IndexRequest("agent", "person", ""+i).source(map);
            bulkProcessorService.add(indexRequest);
        }
    }

}