package io.github.lunasaw.zlm.node.service.impl;

import io.github.lunasaw.zlm.config.ZlmNode;
import io.github.lunasaw.zlm.node.LoadBalancer;
import io.github.lunasaw.zlm.node.service.NodeService;
import org.springframework.util.Assert;

/**
 * ZLM 节点服务实现类
 * <p>
 * 整合负载均衡器和节点提供器的功能，提供统一的节点管理服务
 *
 * @author luna
 */
public record NodeServiceImpl(LoadBalancer loadBalancer) implements NodeService {

    @Override
    public ZlmNode getAvailableNode(String nodeKey) {
        try {
            ZlmNode zlmNode = selectNode(nodeKey);
            if (zlmNode != null) {
                return zlmNode;
            }
        } catch (IllegalArgumentException ignored) {}

        return selectNode();
    }

    @Override
    public ZlmNode selectNode(String key) {
        Assert.hasText(key, "负载均衡选择 key 不能为空");
        ZlmNode node = loadBalancer.selectNode(key);
        Assert.notNull(node, "未找到可用的 ZLM 节点");
        return node;
    }
}