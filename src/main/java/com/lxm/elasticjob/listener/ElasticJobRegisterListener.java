//package com.lxm.elasticjob.listener;
//
//import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//import com.dangdang.ddframe.job.config.JobCoreConfiguration;
//import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
//import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
//import com.dangdang.ddframe.job.event.JobEventConfiguration;
//import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
//import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
//import com.lxm.elasticjob.annotation.EsDataFlowJob;
//import com.lxm.elasticjob.annotation.EsSimpleJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Map;
//
//@Component
//public class ElasticJobRegisterListener implements ApplicationContextAware,BeanDefinitionRegistryPostProcessor {
//    private final static Logger logger = LoggerFactory.getLogger(ElasticJobRegisterListener.class);
//    @Resource
//    private ZookeeperRegistryCenter zookeeperRegistryCenter;
//
//    @Resource
//    private JobEventConfiguration jobEventConfiguration;
//
//    private ApplicationContext context;
//
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        logger.info("ElasticJobRegisterListener, register elasticJob start");
//        //注册所有simpleJob
//        Map<String, Object> simpleJobs = context.getBeansWithAnnotation(EsSimpleJob.class);
//        for (String jobName : simpleJobs.keySet()) {
//            Object job = simpleJobs.get(jobName);
//            EsSimpleJob anno = context.findAnnotationOnBean(jobName, EsSimpleJob.class);
//            SimpleJob simpleJob = (SimpleJob) job;
//            SpringJobScheduler springJobScheduler = registerSimpleJob(simpleJob, anno);
//            definitionBean(registry, springJobScheduler.getClass(), anno.jobName());
//        }
//
//        //注册所有dataFlowJob
//        Map<String, Object> dataFlowJobs = context.getBeansWithAnnotation(EsDataFlowJob.class);
//        for (String jobName : dataFlowJobs.keySet()) {
//            Object job = dataFlowJobs.get(jobName);
//            EsDataFlowJob anno = context.findAnnotationOnBean(jobName, EsDataFlowJob.class);
//            DataflowJob dataflowJob = (DataflowJob) job;
//            SpringJobScheduler springJobScheduler = registerDataFlowJob(dataflowJob, anno);
//            definitionBean(registry, springJobScheduler.getClass(), anno.jobName());
//        }
//    }
//
//    private void definitionBean(BeanDefinitionRegistry registry, Class<?> cls, String beanName) {
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
//        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//        definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
//        definition.setBeanClass(cls);
//        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME);
//        registry.registerBeanDefinition(beanName, definition);
//    }
//
//
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//
//    }
//
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.context=applicationContext;
//    }
//
//    /**
//     * 注册simple job
//     *
//     * @param job
//     * @param anno
//     */
//    private SpringJobScheduler registerSimpleJob(SimpleJob job, EsSimpleJob anno) {
//        Class jobClass = job.getClass();
//        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
//                jobClass.getName(), anno.cron(), anno.shardingTotalCount()).shardingItemParameters(anno.shardingItemParameters()).build(), jobClass.getCanonicalName())).overwrite(true).build();
//        return new SpringJobScheduler(job, zookeeperRegistryCenter, liteJobConfiguration, jobEventConfiguration);
//    }
//
//    /**
//     * 注册dataFlow job
//     *
//     * @param job
//     * @param anno
//     * @return
//     */
//    private SpringJobScheduler registerDataFlowJob(DataflowJob job, EsDataFlowJob anno) {
//        Class jobClass = job.getClass();
//        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(new DataflowJobConfiguration(JobCoreConfiguration.newBuilder(
//                jobClass.getName(), anno.cron(), anno.shardingTotalCount()).shardingItemParameters(anno.shardingItemParameters()).build(), jobClass.getCanonicalName(), true)).overwrite(true).build();
//        return new SpringJobScheduler(job, zookeeperRegistryCenter, liteJobConfiguration, jobEventConfiguration);
//    }
//
//}
