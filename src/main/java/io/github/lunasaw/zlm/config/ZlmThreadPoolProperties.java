package io.github.lunasaw.zlm.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ZLMediaKit ThreadPoolTask 配置类
 *
 * @author luna
 */
@ConfigurationProperties("zlm.thread-pool")
@Getter
public class ZlmThreadPoolProperties {

    private final int cpuNum = Runtime.getRuntime().availableProcessors();

    /*
     * 默认情况下，在创建了线程池后，线程池中的线程数为 0，当有任务来之后，就会创建一个线程去执行任务，
     * 当线程池中的线程数目达到 corePoolSize 后，就会把到达的任务放到缓存队列当中；
     * 当队列满了，就继续创建线程，当线程数量大于等于 maxPoolSize 后，开始使用拒绝策略拒绝
     */

    /**
     * 核心线程数（默认线程数）
     */
    private final int corePoolSize = 5;
    /**
     * 最大线程数
     */
    private final int maxPoolSize = 30;
    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private final int keepAliveTime = 30;
    /**
     * 缓冲队列大小
     */
    private final int queueCapacity = 100;
    /**
     * 线程池名前缀
     */
    private final String threadNamePrefix = "zlm-hook-";

}
