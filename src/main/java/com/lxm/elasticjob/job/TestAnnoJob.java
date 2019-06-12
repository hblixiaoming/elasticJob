package com.lxm.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lxm.elasticjob.annotation.EsSimpleJob;
import org.springframework.stereotype.Component;

@Component
@EsSimpleJob(cron = "0/5 * * * * ?", shardingTotalCount = 3, shardingItemParameters = "0=0,1=1,2=2", jobName = "testJob")
public class TestAnnoJob implements SimpleJob {

    public void execute(ShardingContext shardingContext) {
        System.out.println("hello world");
    }
}
