package io.github.lunasaw.zlm.config.props;

import io.github.lunasaw.zlm.hook.ZlmHook;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ZLMediaKit Hook 配置属性
 *
 * @author CHEaN
 */
@Data
@ConfigurationProperties(prefix = "zlm.hook")
public class ZlmHookProperties implements InitializingBean {

    /**
     * Hook 地址前缀
     */
    private String path = ZlmHook.Paths.DEFAULT_PREFIX;

    /**
     * 是否启用 Hook 功能
     */
    private boolean enable = false;

    /**
     * Hook 线程池配置：zlm.hook.thread-pool.*
     */
    private final ThreadPool threadPool = new ThreadPool();


    @Override
    public void afterPropertiesSet() {
        path = normalizePrefix(path);
    }

    /**
     * 规范化 Hook 地址前缀
     *
     * @param path 原始路径
     * @return 规范化后的路径
     */
    private static String normalizePrefix(String path) {
        if (path == null || path.isBlank()) return ZlmHook.Paths.DEFAULT_PREFIX;
        String p = path.trim();
        if (!p.startsWith("/")) p = "/" + p;
        if (p.endsWith("/")) p = p.substring(0, p.length() - 1);
        return p;
    }

    @Data
    public static class ThreadPool {

        /**
         * 核心线程数（默认线程数）
         */
        private int corePoolSize = 5;

        /**
         * 最大线程数
         */
        private int maxPoolSize = 30;

        /**
         * 允许线程空闲时间（秒）
         */
        private int keepAliveTime = 30;

        /**
         * 缓冲队列大小
         */
        private int queueCapacity = 100;

        /**
         * 线程名前缀
         */
        private String threadNamePrefix = "zlm-hook-";
    }

}
