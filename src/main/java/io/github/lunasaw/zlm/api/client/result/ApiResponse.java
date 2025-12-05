package io.github.lunasaw.zlm.api.client.result;

/**
 * ZLM API 返回响应接口
 * <p>
 * 所有 ZLM API 返回响应类均应实现此接口
 *
 * @author CHEaN
 */
public interface ApiResponse {
    /**
     * 状态码
     */
    Integer code();
    /**
     * 提示信息
     */
    String msg();
}
