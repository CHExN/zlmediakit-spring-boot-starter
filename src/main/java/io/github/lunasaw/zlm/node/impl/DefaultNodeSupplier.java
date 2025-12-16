package io.github.lunasaw.zlm.node.impl;

import io.github.lunasaw.zlm.node.ZlmNode;
import io.github.lunasaw.zlm.config.props.ZlmProperties;
import io.github.lunasaw.zlm.node.NodeSupplier;

import java.util.List;

/**
 * 默认节点提供器
 * <p>
 * 从 {@link ZlmProperties 配置文件} 中获取节点列表
 *
 * @author CHEaN
 */
public record DefaultNodeSupplier(ZlmProperties zlmProperties) implements NodeSupplier {

    @Override
    public String getName() {
        return "DefaultNodeSupplier";
    }

    @Override
    public List<ZlmNode> getNodes() {
        return zlmProperties.getNodeList();
    }

    @Override
    public ZlmNode getNode(String nodeId) {
        if (nodeId == null) {
            return null;
        }

        return zlmProperties.getNodes().get(nodeId);
    }
}