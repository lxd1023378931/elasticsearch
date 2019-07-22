package com.uzak.elasticsearch.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName Service
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/22 21:36
 */
public interface UserService {
    Optional<UserBean> findById(Long id);
    UserBean save(UserBean userBean);
    void delete(UserBean userBean);
    Iterable<UserBean> findAll();
    Page<UserBean> findByUserName(String userName, Pageable pageable);
}