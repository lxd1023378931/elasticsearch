package com.uzak.elasticsearch.highlevelrestclient;

import com.uzak.elasticsearch.lowlevelrestclient.ESClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName ElasticsearchConfiguration
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/16 22:41
 */
@Slf4j
@Component
public class ElasticsearchConfiguration implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {

    @Autowired
    private ESClient esClient;

    private RestHighLevelClient restHighLevelClient;

    @Override
    public void destroy() throws Exception {
        try {
           if (restHighLevelClient != null){
               restHighLevelClient.close();
           }
        }catch (Exception e){
            log.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public RestHighLevelClient getObject() throws Exception {
        return restHighLevelClient;
    }

    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        restHighLevelClient = new RestHighLevelClient(esClient.getRestClientBuilder());
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}