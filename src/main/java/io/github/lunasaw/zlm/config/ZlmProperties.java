package io.github.lunasaw.zlm.config;

import io.github.lunasaw.zlm.node.LoadBalancer;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ZLMediaKit 配置属性
 *
 * @author CHEaN
 */
@ConfigurationProperties(prefix = "zlm")
@Data
public class ZlmProperties implements InitializingBean {

    /**
     * ZLM 服务器配置映射，key 为 {@link ZlmNode#getNodeId() nodeId}
     */
    private Map<String, ZlmNode> nodeMap = new ConcurrentHashMap<>();
    private List<ZlmNode> nodes = new CopyOnWriteArrayList<>();

    @Deprecated
    private boolean enable = true;
    @Deprecated
    private boolean hookEnable = false;

    private LoadBalancer.Type balance = LoadBalancer.Type.ROUND_ROBIN;

    @Override
    public void afterPropertiesSet() {
        for (ZlmNode node : nodes) {
            nodeMap.put(node.getNodeId(), node);
        }
    }

}
