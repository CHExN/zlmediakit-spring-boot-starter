package io.github.lunasaw.zlm.hook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZLM Hook 回调参数 - Shell 登录鉴权 (on_shell_login)
 *
 * @param mediaServerId 服务器 ID
 * @param id            TCP 链接唯一 ID
 * @param ip            telnet 终端 IP
 * @param passwd        telnet 终端登录用户密码
 * @param port          telnet 终端端口号
 * @param username      telnet 终端登录用户名
 * @author CHEaN
 */
public record HookParamForOnShellLogin(
        @JsonProperty("mediaServerId") String mediaServerId,
        @JsonProperty("id") String id,
        @JsonProperty("ip") String ip,
        @JsonProperty("passwd") String passwd,
        @JsonProperty("port") int port,
        @JsonProperty("user_name") String username
) implements HookParam {
}
