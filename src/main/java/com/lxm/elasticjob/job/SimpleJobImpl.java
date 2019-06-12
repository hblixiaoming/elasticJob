package com.lxm.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lxm.elasticjob.mapper.UserMapper;
import com.lxm.elasticjob.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SimpleJobImpl implements SimpleJob {
    private final static Logger logger = LoggerFactory.getLogger(DataFlowJobImpl.class);

    @Autowired
    private UserMapper userMapper;

    public void execute(ShardingContext shardingContext) {
        logger.info("execute, shardingContext={}", shardingContext);
        String parameter = shardingContext.getShardingParameter();
        Integer mod = Integer.parseInt(parameter);
        List<User> users = userMapper.selectWithMod(mod);

        for (User user : users) {
            User newUser = new User();
            newUser.setAge(user.getAge() + 1);
            newUser.setId(user.getId());
            userMapper.updateByPrimaryKeySelective(newUser);
        }
    }
}
