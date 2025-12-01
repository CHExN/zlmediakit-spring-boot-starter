package io.github.lunasaw.zlm.config;

import lombok.Data;

/**
 * ZLM 节点配置
 * @author luna
 * @version 1.0
 */
@Data
public class ZlmNode {

    /**
     * The id of this node.
     */
    private String serverId = "zlm";

    /**
     * The host of this node. eg: <a href="http://127.0.0.1">node</a>
     */
    private String host = "http://127.0.0.1";

    /**
     * The secret of this host.
     */
    private String secret;

    /**
     * Whether enable this host.
     */
    private boolean enabled = true;

    /**
     * The weight of this host.
     */
    private int weight = 100;
}
