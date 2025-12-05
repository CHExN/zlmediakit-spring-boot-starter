package io.github.lunasaw.zlm.api.client.http;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * HTTP 执行抽象，封装底层 HTTP 调用、连接池、超时与重试。
 */
public interface HttpExecutor extends Closeable {

    /**
     * 以 application/x-www-form-urlencoded 发送 POST 请求。
     *
     * @param url    完整的 URL
     * @param params 表单参数，允许为空
     * @return 响应字符串
     * @throws IOException 网络层异常
     */
    String postForm(String url, Map<String, String> params) throws IOException;

    /**
     * 以 application/x-www-form-urlencoded 发送 POST 请求并返回二进制。
     *
     * @param url    完整的 URL
     * @param params 表单参数，允许为空
     * @return 响应二进制
     * @throws IOException 网络层异常
     */
    byte[] postFormForBytes(String url, Map<String, String> params) throws IOException;

    /**
     * 以 application/json 发送 POST 请求。
     *
     * @param url  完整的 URL
     * @param json 序列化后的 JSON 字符串
     * @return 响应字符串
     * @throws IOException 网络层异常
     */
    String postJson(String url, String json) throws IOException;

    @Override
    void close() throws IOException;
}
