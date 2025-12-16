package io.github.lunasaw.zlm.node;

import java.util.List;

/**
 * 节点提供器接口
 * <p>
 * 用于动态获取节点列表的 hook 机制，支持外部实现自定义的节点获取逻辑
 *
 * @author CHEaN
 */
public interface NodeSupplier {

    /**
     * 获取当前可用的节点列表
     * <p>
     * 该方法会被 LoadBalancer 定期调用以获取最新的节点信息
     *
     * @return 节点列表，如果没有可用节点则返回空列表
     */
    List<ZlmNode> getNodes();

    /**
     * 根据服务器节点 ID 获取指定节点
     *
     * @param nodeId 服务器节点 ID
     * @return 节点信息，如果不存在则返回 null
     */
    ZlmNode getNode(String nodeId);

    /**
     * 获取节点提供器的名称标识
     *
     * @return 提供器名称
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }

}