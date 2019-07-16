package com.uzak.elasticsearch.lowlevelrestclient;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName ESClient
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/16 21:14
 */
@Slf4j
@Data
@Component
public class ESClient {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Value("${elasticsearch.scheme}")
    private String scheme;

    @Value("${elasticsearch.maxRetryTimeoutMillis}")
    private Integer maxRetryTimeoutMillis;

    @Bean
    public RestClient initRestClient(){
        return getRestClientBuilder().build();
    }

    public RestClientBuilder getRestClientBuilder(){
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, scheme));
        //请求头
        Header[] headers = new Header[]{new BasicHeader("header", "value")};
        builder.setDefaultHeaders(headers);
        //超时时间（毫秒级）
        builder.setMaxRetryTimeoutMillis(maxRetryTimeoutMillis);
        //失败监听
        builder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(HttpHost host) {
                log.error("ES["+host.toURI()+"] get failed!");
            }
        });
        //修改默认回调
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                return builder.setSocketTimeout(10000);
            }
        });
//        final CredentialsProvider credentialsProvider =
//                new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("user", "password"));

        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                //当设置了登录密码时
//                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                return httpClientBuilder.setDefaultIOReactorConfig(
                        IOReactorConfig.custom().setIoThreadCount(10).build());
            }
        });
        return builder;
    }
}