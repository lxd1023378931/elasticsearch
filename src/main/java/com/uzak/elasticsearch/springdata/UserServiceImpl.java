package com.uzak.elasticsearch.springdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/22 21:39
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserBean> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserBean save(UserBean userBean) {
        return userRepository.save(userBean);
    }

    @Override
    public void delete(UserBean userBean) {
        userRepository.delete(userBean);
    }

    @Override
    public Iterable<UserBean> findAll() {
        return  userRepository.findAll();
    }

    @Override
    public Page<UserBean> findByUserName(String userName, Pageable pageable) {
        return userRepository.findByUserName(userName, pageable);
    }
}