package com.uzak.elasticsearch.highlevelrestclient.service;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName BulkProcessorService
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/17 21:30
 */
@Service
public class BulkProcessorService {

    @Autowired
    private BulkProcessor processor;

    public void add(DocWriteRequest request){
        processor.add(request);
    }
}