package io.github.lunasaw.zlm.config;

import io.github.lunasaw.zlm.node.LoadBalancer;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * ZLMediaKit 配置属性
 *
 * @author CHEaN
 */
@ConfigurationProperties(prefix = "zlm")
@Data
public class ZlmProperties implements InitializingBean {

    private Map<String, ZlmNode> nodeMap = new ConcurrentHashMap<>();
    private List<ZlmNode> nodes = new CopyOnWriteArrayList<>();

    private boolean enable = true;
    private boolean hookEnable = false;

    private LoadBalancer.Type balance = LoadBalancer.Type.ROUND_ROBIN;

    @Override
    public void afterPropertiesSet() {
        nodeMap = nodes.stream()
                .filter(ZlmNode::isEnabled)
                .collect(Collectors.toMap(ZlmNode::getServerId, node -> node));
    }

}
