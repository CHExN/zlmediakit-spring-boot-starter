package io.github.lunasaw.zlm.config.props;

import io.github.lunasaw.zlm.node.LoadBalancer;
import io.github.lunasaw.zlm.node.ZlmNode;
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

    private Map<String, ZlmNode> nodes = new ConcurrentHashMap<>();
    private transient List<ZlmNode> nodeList = new CopyOnWriteArrayList<>();

    private LoadBalancer.Type balance = LoadBalancer.Type.ROUND_ROBIN;

    @Override
    public void afterPropertiesSet() {
        nodes.forEach((nodeId, node) -> node.setNodeId(nodeId));
        nodeList = new CopyOnWriteArrayList<>(nodes.values());
    }

}
