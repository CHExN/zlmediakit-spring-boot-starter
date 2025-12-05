package io.github.lunasaw.zlm.api.client;

import io.github.lunasaw.zlm.config.ZlmNode;
import io.github.lunasaw.zlm.node.LoadBalancer;
import io.github.lunasaw.zlm.node.NodeSupplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 节点感知的 ZLM 客户端管理器
 * <p>
 * Starter 引入后直接注入本类，即可按节点 ID 获取客户端，或按负载均衡策略选择一个客户端
 * 内部缓存客户端实例，自动携带对应 host/secret
 */
@Slf4j
public class ZlmClientManager implements AutoCloseable {

    private final LoadBalancer loadBalancer;
    private final NodeSupplier nodeSupplier;

    /**
     * 按 nodeId 缓存客户端，避免重复创建 HttpClient
     */
    private final Map<String, ZlmClient> clientCache = new ConcurrentHashMap<>();

    /**
     * 记录节点快照，用于在 host/secret 变化时重建客户端
     */
    private final Map<String, NodeDescriptor> nodeSnapshot = new ConcurrentHashMap<>();

    public ZlmClientManager(LoadBalancer loadBalancer, NodeSupplier nodeSupplier) {
        this.loadBalancer = Objects.requireNonNull(loadBalancer, "LoadBalancer must not be null");
        this.nodeSupplier = Objects.requireNonNull(nodeSupplier, "NodeSupplier must not be null");
    }

    /**
     * 根据节点 ID 获取对应的客户端
     *
     * @param nodeId 节点 ID
     * @return 客户端实例
     * @throws IllegalArgumentException 当节点不存在或不可用时抛出
     */
    public ZlmClient getClient(String nodeId) {
        ZlmNode node = nodeSupplier.getNode(nodeId);
        Assert.notNull(node, "ZLM Node with id " + nodeId + " not found");
        return getClient(node);
    }

    /**
     * @see #getOrCreateClient(ZlmNode)
     */
    public ZlmClient getClient(ZlmNode node) {
        return getOrCreateClient(node);
    }

    /**
     * 带业务 key 的负载均衡选择
     *
     * @param key 负载均衡选择 key，通常用于标识业务类型
     * @throws IllegalArgumentException 当节点不存在或不可用时抛出
     */
    public ZlmClient selectClient(String key) {
        ZlmNode node = loadBalancer.selectNode(key);
        return getOrCreateClient(node);
    }

    /**
     * 按负载均衡策略选择一个节点并返回客户端
     *
     * @throws IllegalArgumentException 当节点不存在或不可用时抛出
     */
    public ZlmClient selectClient() {
        ZlmNode node = loadBalancer.selectNode(null);
        return getOrCreateClient(node);
    }


    @Override
    public void close() {
        clientCache.values().forEach(this::closeQuietly);
        clientCache.clear();
        nodeSnapshot.clear();
    }

    /**
     * 获取已缓存的客户端，或根据节点信息创建新的客户端。
     * <p>
     * 如果节点的 host 或 secret 发生变化，则重新创建客户端实例。
     *
     * @throws IllegalArgumentException 当节点为 null 或节点 ID 为 null 时抛出
     */
    private ZlmClient getOrCreateClient(ZlmNode node) {
        Assert.notNull(node, "ZLM Node must not be null");
        Assert.notNull(node.getNodeId(), "ZLM Node ID must not be null");

        String nodeId = node.getNodeId();
        NodeDescriptor descriptor = new NodeDescriptor(node.getHost(), node.getSecret());

        return clientCache.compute(nodeId, (key, existing) -> {
            NodeDescriptor previous = nodeSnapshot.put(key, descriptor);
            // host/secret 未变化则复用
            if (existing != null && descriptor.equals(previous)) {
                return existing;
            }
            // 配置变化，重建客户端
            closeQuietly(existing);
            return new DefaultZlmClient(node.getHost(), node.getSecret());
        });
    }

    /**
     * 安静地关闭客户端，忽略异常
     */
    private void closeQuietly(ZlmClient client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch (Exception e) {
            log.warn("Failed to close ZlmClient cleanly", e);
        }
    }

    /**
     * 节点描述符（用于比较 host/secret 变化）
     *
     * @param host
     */
    private record NodeDescriptor(String host, String secret) {
    }

}
