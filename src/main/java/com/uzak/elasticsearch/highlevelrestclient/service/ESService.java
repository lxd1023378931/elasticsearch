package com.uzak.elasticsearch.highlevelrestclient.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ESService
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/16 23:33
 */
@Slf4j
@Service
public class ESService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void indexApi() throws Exception{
        for (int i = 2; i < 1000; i ++){
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("userName", "uzak"+i);
            map.put("password", "123");
            map.put("createTime", LocalDateTime.now());
            map.put("message", "High level restclient test"+i);
            IndexRequest indexRequest = new IndexRequest("user", "manage", ""+i).source(map);
            IndexResponse response = restHighLevelClient.index(indexRequest);
            log.info(response.toString());
        }
    }

    public void getApi() throws Exception{
        GetRequest request = new GetRequest("user", "manage", "999");
        GetResponse response = restHighLevelClient.get(request);
        log.info(response.toString());
    }

    public void deleteApi() throws Exception{
        DeleteRequest request = new DeleteRequest("user", "manage", "1");
        DeleteResponse response = restHighLevelClient.delete(request);
        log.info(response.toString());
    }

    /**
     * 同步
     * @throws Exception
     */
    public void bulkApi() throws Exception{
        BulkResponse response = restHighLevelClient.bulk(getBulkRequest(1000));
        log.info(response.toString());
    }

    /**
     * 异步（未调通）
     * @throws Exception
     */
    public void bulkAsyncApi() throws Exception{
        restHighLevelClient.bulkAsync(getBulkRequest(2000), new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {
                log.info(bulkItemResponses.toString());
            }

            @Override
            public void onFailure(Exception e) {
                log.error("", e);
            }
        });
    }

    private BulkRequest getBulkRequest(int startIndex){
        BulkRequest request = new BulkRequest();
        for (int i = startIndex; i < startIndex+1000; i ++){
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("userName", "uzak"+i);
            map.put("password", "123");
            map.put("createTime", LocalDateTime.now());
            map.put("message", "High level restclient test"+i);
            //ES 变更版本后, setSource()的参数不能是json串了, 但是可以转化成 map 来使用
            //request.add(new IndexRequest("user", "manage", ""+i).source(XContentType.JSON, map));
            request.add(new IndexRequest("user", "manage", ""+i).source(map));
        }
        return request;
    }

}