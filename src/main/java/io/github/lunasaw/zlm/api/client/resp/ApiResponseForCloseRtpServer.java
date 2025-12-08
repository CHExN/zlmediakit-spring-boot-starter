package io.github.lunasaw.zlm.api.client.resp;

/**
 * ZLM API 结果 - 关闭 GB28181 RTP 接收端口
 *
 * @param hit 是否找到记录并关闭
 */
public record ApiResponseForCloseRtpServer(Integer code, String msg, Integer hit) implements ApiResponse {
}
