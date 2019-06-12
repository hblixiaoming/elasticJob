package com.lxm.elasticjob.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsSimpleJob {
    String jobName() default "";

    String cron() default "";

    int shardingTotalCount() default 0;

    String shardingItemParameters() default "";
}
