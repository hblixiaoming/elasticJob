package com.lxm.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.lxm.elasticjob.mapper.UserMapper;
import com.lxm.elasticjob.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class DataFlowJobImpl implements DataflowJob<User> {
    @Autowired
    private UserMapper userMapper;
    private final static Logger logger = LoggerFactory.getLogger(DataFlowJobImpl.class);


    public List<User> fetchData(ShardingContext shardingContext) {
        logger.info("fetchData, shardingContext={}", shardingContext);
        String parameter = shardingContext.getShardingParameter();
        Integer mod = Integer.parseInt(parameter);
        return userMapper.selectWithMod(mod);
    }

    public void processData(ShardingContext shardingContext, List<User> data) {
        for (User user : data) {
            User newUser = new User();
            newUser.setAge(user.getAge() + 1);
            newUser.setId(user.getId());
            userMapper.updateByPrimaryKeySelective(newUser);
        }
    }
}
