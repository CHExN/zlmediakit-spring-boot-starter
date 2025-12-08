package io.github.lunasaw.zlm.api.client.resp;

/**
 * ZLM API 结果 - 创建 GB28181 RTP 接收端口
 *
 * @param code 状态码
 * @param port 接收端口，方便获取随机端口号
 */
public record ApiResponseForOpenRtpServer(Integer code, String msg, Integer port) implements ApiResponse {
}
