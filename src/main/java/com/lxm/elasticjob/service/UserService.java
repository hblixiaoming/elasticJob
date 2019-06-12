package com.lxm.elasticjob.service;

import com.lxm.elasticjob.mapper.UserMapper;
import com.lxm.elasticjob.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public User queryById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
