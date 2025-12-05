package io.github.lunasaw.zlm.node;

import io.github.lunasaw.zlm.config.ZlmNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 负载均衡器
 * <p>
 * 每次选择节点时直接从 {@link NodeSupplier} 获取最新节点列表，不维护本地节点缓存
 */
public interface LoadBalancer {

    /**
     * 设置节点提供器
     *
     * @param nodeSupplier 节点提供器
     */
    void setNodeSupplier(NodeSupplier nodeSupplier);

    /**
     * 根据 key 选择节点
     * 每次选择时直接从 NodeSupplier 获取最新节点列表
     *
     * @param key 选择 key
     * @return 选中的节点
     */
    ZlmNode selectNode(String key);

    /**
     * 获取负载均衡器类型
     *
     * @return 类型标识
     */
    String getType();

    /**
     * 负载均衡器类型枚举
     */
    @Getter
    @AllArgsConstructor
    @Accessors(fluent = true)
    enum Type {
        RANDOM("Random"),
        ROUND_ROBIN("RoundRobin"),
        CONSISTENT_HASHING("ConsistentHashing"),
        WEIGHT_ROUND_ROBIN("WeightRoundRobin"),
        WEIGHT_RANDOM("WeightRandom");

        private final String type;
    }

}
