package com.uzak.elasticsearch.springdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName Test
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/22 21:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {

    @Autowired
    UserService userService;

    @Test
    public void findById(){
        System.out.println(userService.findById(1L));
    }

    @Test
    public void save(){
        UserBean userBean = new UserBean();
        userBean.setId(1156L);
        userBean.setUserName("liangxiudou");
        userBean.setPassword("lxd");
        userBean.setMessage("Test spring-boot-starter-data-elasticsearch");
        userBean.setCreateTime(LocalDateTime.now().toString());
        userService.save(userBean);
    }

    @Test
    public void findByUserName(){
        Page<UserBean> userBeanList = userService.findByUserName("liangxiudou", PageRequest.of(1, 1));
        userBeanList.forEach(one -> {
            System.out.println(one);
        });
    }

    @Test
    public void findAll(){
        Iterable<UserBean> userBeanList = userService.findAll();
        userBeanList.forEach(System.out::println);
    }

}