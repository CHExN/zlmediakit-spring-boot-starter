package io.github.lunasaw.zlm.node;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ZLM 服务器节点配置
 *
 * @author CHEaN
 */
@Data
@NoArgsConstructor
public class ZlmNode {

    /**
     * 服务器节点 ID
     */
    private String nodeId = "zlm";
    /**
     * 服务器 API 请求地址
     */
    private String host = "http://127.0.0.1";
    /**
     * 服务器 API 密钥
     */
    private String secret;
    /**
     * 是否启用该节点
     */
    private boolean enable = true;
    /**
     * 节点权重
     */
    private int weight = 100;

    /**
     * 复制构造函数
     */
    public ZlmNode(ZlmNode node) {
        this.nodeId = node.nodeId;
        this.host = node.host;
        this.secret = node.secret;
        this.enable = node.enable;
        this.weight = node.weight;
    }

}
