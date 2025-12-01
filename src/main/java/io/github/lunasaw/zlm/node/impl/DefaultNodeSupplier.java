package io.github.lunasaw.zlm.node.impl;

import io.github.lunasaw.zlm.config.ZlmNode;
import io.github.lunasaw.zlm.config.ZlmProperties;
import io.github.lunasaw.zlm.node.NodeSupplier;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 默认节点提供器
 * <p>
 * 从配置文件中获取节点列表
 *
 * @author CHEaN
 */
@Slf4j
public record DefaultNodeSupplier(ZlmProperties zlmProperties) implements NodeSupplier {

    @Override
    public String getName() {
        return "DefaultNodeSupplier";
    }

    @Override
    public List<ZlmNode> getNodes() {
        List<ZlmNode> nodes = zlmProperties.getNodes();
        log.debug("从配置获取节点列表，共{}个节点", nodes != null ? nodes.size() : 0);
        return nodes;
    }

    @Override
    public ZlmNode getNode(String serverId) {
        if (serverId == null) {
            return null;
        }

        ZlmNode node = zlmProperties.getNodeMap().get(serverId);
        log.debug("从配置获取节点[{}]：{}", serverId, node != null ? "找到" : "未找到");
        return node;
    }
}