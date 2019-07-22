package com.uzak.elasticsearch.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName UserRepository
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/22 20:59
 */
public interface UserRepository extends ElasticsearchRepository<UserBean, Long>{
    Page<UserBean> findByUserName(String userName, Pageable pageable);
}
