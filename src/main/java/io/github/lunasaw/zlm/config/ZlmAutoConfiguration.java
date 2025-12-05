package io.github.lunasaw.zlm.config;

import io.github.lunasaw.zlm.api.client.ZlmClient;
import io.github.lunasaw.zlm.api.client.ZlmClientManager;
import io.github.lunasaw.zlm.hook.ZlmHookController;
import io.github.lunasaw.zlm.hook.service.ZlmHookService;
import io.github.lunasaw.zlm.hook.service.impl.DefaultZlmHookServiceImpl;
import io.github.lunasaw.zlm.node.LoadBalancer;
import io.github.lunasaw.zlm.node.NodeSupplier;
import io.github.lunasaw.zlm.node.impl.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ZLMediaKit 自动配置类
 * <p>
 * 根据配置条件自动装配 ZLMediaKit 相关的 Bean
 *
 * @author CHEaN
 */
@AutoConfiguration
@EnableConfigurationProperties({ZlmProperties.class, ZlmThreadPoolProperties.class})
@ConditionalOnProperty(prefix = "zlm", name = "enable", havingValue = "true", matchIfMissing = true)
public class ZlmAutoConfiguration {

    /**
     * 默认的 ZlmHookService 实现
     * <p>
     * 当 zlm.hook-enable = true 且容器中不存在自定义 ZlmHookService Bean 时，装配默认实现
     *
     * @return DefaultZlmHookServiceImpl 实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "zlm", name = "hook-enable", havingValue = "true")
    @ConditionalOnMissingBean
    public ZlmHookService zlmHookService() {
        return new DefaultZlmHookServiceImpl();
    }

    /**
     * ZLMediaKit 专用线程池
     * 当 zlm.hook-enable = true 且存在 ZlmHookService 时装配
     *
     * @param config 线程池配置
     * @return ThreadPoolTaskExecutor 实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "zlm", name = "hook-enable", havingValue = "true")
    @ConditionalOnBean(ZlmHookService.class)
    @ConditionalOnMissingBean(name = "zlmTaskExecutor")
    public ThreadPoolTaskExecutor zlmTaskExecutor(ZlmThreadPoolProperties config) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setKeepAliveSeconds(config.getKeepAliveTime());
        executor.setThreadNamePrefix(config.getThreadNamePrefix());

        // 等待所有任务完成后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);

        // 线程池对拒绝任务的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    /**
     * ZlmHookAPI 控制器
     * <p>
     * 当 zlm.hook-enable = true 时装配
     *
     * @param zlmHookService ZlmHookService 实例
     * @param executor       异步任务执行器
     * @return ZlmHookAPI 实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "zlm", name = "hook-enable", havingValue = "true")
    public ZlmHookController zlmHookAPI(ZlmHookService zlmHookService, @Qualifier("zlmTaskExecutor") AsyncTaskExecutor executor) {
        return new ZlmHookController(zlmHookService, executor);
    }

    /**
     * 默认的 NodeSupplier 实现
     * <p>
     * 当缺失时启用
     *
     * @param zlmProperties zml 配置属性
     * @return DefaultNodeSupplier 实例
     */
    @Bean
    @ConditionalOnMissingBean
    public NodeSupplier nodeSupplier(ZlmProperties zlmProperties) {
        return new DefaultNodeSupplier(zlmProperties);
    }

    /**
     * ZLM 客户端管理器
     * <p>
     * 提供按节点 ID/负载均衡获取 {@link ZlmClient} 的入口
     *
     * @param loadBalancer 负载均衡器
     * @param nodeSupplier 节点提供器
     * @return ZlmClientManager 实例
     */
    @Bean
    @ConditionalOnMissingBean
    public ZlmClientManager zlmClientManager(LoadBalancer loadBalancer, NodeSupplier nodeSupplier) {
        return new ZlmClientManager(loadBalancer, nodeSupplier);
    }

    /**
     * 负载均衡器 Bean
     * <p>
     * 根据配置的负载均衡策略创建相应的 LoadBalancer 实例
     *
     * @param zlmProperties zml 配置属性
     * @param nodeSupplier  NodeSupplier 实例
     * @return LoadBalancer 实例
     */
    @Bean
    @ConditionalOnMissingBean
    public LoadBalancer loadBalancer(ZlmProperties zlmProperties, NodeSupplier nodeSupplier) {
        LoadBalancer balancer = switch (zlmProperties.getBalance()) {
            case RANDOM -> new RandomLoadBalancer();
            case ROUND_ROBIN -> new RoundRobinLoadBalancer();
            case CONSISTENT_HASHING -> new ConsistentHashingLoadBalancer();
            case WEIGHT_RANDOM -> new WeightRandomLoadBalancer();
            case WEIGHT_ROUND_ROBIN -> new WeightRoundRobinLoadBalancer();
        };

        // 设置 NodeSupplier 到 LoadBalancer 中
        balancer.setNodeSupplier(nodeSupplier);

        return balancer;
    }
}
