package io.github.lunasaw.zlm.hook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调参数 - HTTP 访问事件 (on_http_access)
 *
 * @param mediaServerId                 服务器 ID
 * @param headerAccept                  HTTP 头部 Accept 字段
 * @param headerAcceptEncoding          HTTP 头部 Accept-Encoding 字段
 * @param headerAcceptLanguage          HTTP 头部 Accept-Language 字段
 * @param headerCacheControl            HTTP 头部 Cache-Control 字段
 * @param headerConnection              HTTP 头部 Connection 字段
 * @param headerHost                    HTTP 头部 Host 字段
 * @param headerUpgradeInsecureRequests HTTP 头部 Upgrade-Insecure-Requests 字段
 * @param headerUserAgent               HTTP 头部 User-Agent 字段
 * @param id                            TCP 链接唯一 ID
 * @param ip                            HTTP 客户端 IP
 * @param isDir                         HTTP 访问路径是文件还是目录
 * @param params                        HTTP URL 参数
 * @param path                          请求访问的文件或目录
 * @param port                          HTTP 客户端端口号
 * @author CHEaN
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HookParamForOnHttpAccess(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("header.Accept") String headerAccept,
        @JsonProperty("header.Accept-Encoding") String headerAcceptEncoding,
        @JsonProperty("header.Accept-Language") String headerAcceptLanguage,
        @JsonProperty("header.Cache-Control") String headerCacheControl,
        @JsonProperty("header.Connection") String headerConnection,
        @JsonProperty("header.Host") String headerHost,
        @JsonProperty("header.Upgrade-Insecure-Requests") String headerUpgradeInsecureRequests,
        @JsonProperty("header.User-Agent") String headerUserAgent,
        @JsonProperty("id") String id,
        @JsonProperty("ip") String ip,
        @JsonProperty("is_dir") boolean isDir,
        @JsonProperty("params") String params,
        @JsonProperty("path") String path,
        @JsonProperty("port") int port
) implements HookParam {
}
