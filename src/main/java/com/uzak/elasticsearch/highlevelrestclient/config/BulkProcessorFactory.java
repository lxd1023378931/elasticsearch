package com.uzak.elasticsearch.highlevelrestclient.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.threadpool.ThreadPool;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BulkProcessorFactory
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/17 20:36
 */
@Slf4j
@Component
public class BulkProcessorFactory implements FactoryBean<BulkProcessor>{

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private BulkProcessor processor;

    @PostConstruct
    public void init(){
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest bulkRequest) {
                log.info("Executing bulk [{}] with {} requests", executionId, bulkRequest.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                if (bulkResponse.hasFailures()){
                    log.error("Bulk [{}] executed with failures,response = {}", executionId, bulkResponse);
                }else {
                    log.info("Bulk [{}] completed in {} milliseconds", executionId, bulkResponse.getTook().getMillis());
                }
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                log.error("Failed to execute bulk", throwable);
            }
        };
        this.processor = BulkProcessor.builder(restHighLevelClient::bulkAsync, listener)
                //100条执行一次bulk
                .setBulkActions(100)
                //1kb的数据刷新一次bulk
                .setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.KB))
                //并发请求数量, 0不并发, 1并发允许执行
                .setConcurrentRequests(0)
                //固定5s刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(5L))
                //失败重试3次，间隔2s
                .setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(2L), 3))
                .build();
    }

    @PreDestroy
    public void destory(){
        try {
            //执行关闭方法会把bulk剩余的数据都写入ES再执行关闭
            processor.awaitClose(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("Failed to close bulkProcessor", e);
        }
        log.info("bulkProcessor closed!");
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public BulkProcessor getObject() throws Exception {
        return this.processor;
    }

    @Override
    public Class<?> getObjectType() {
        return BulkProcessor.class;
    }
}