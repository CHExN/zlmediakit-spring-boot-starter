package io.github.lunasaw.zlm.hook;

import lombok.experimental.UtilityClass;

public interface ZlmHook {

    @UtilityClass
    final class Paths {
        // Hook 基础路径
        public static final String DEFAULT_PREFIX = "/index/hook";
        // 流量上报
        public static final String ON_FLOW_REPORT = "/on_flow_report";
        // HTTP 访问事件
        public static final String ON_HTTP_ACCESS = "/on_http_access";
        // 播放器鉴权事件
        public static final String ON_PLAY = "/on_play";
        // 推流鉴权事件
        public static final String ON_PUBLISH = "/on_publish";
        // MP4 录制事件
        public static final String ON_RECORD_MP4 = "/on_record_mp4";
        // RTSP 专用鉴权事件
        public static final String ON_RTSP_AUTH = "/on_rtsp_auth";
        // RTSP 域鉴权事件
        public static final String ON_RTSP_REALM = "/on_rtsp_realm";
        // Shell 登录鉴权
        public static final String ON_SHELL_LOGIN = "/on_shell_login";
        // 流状态变化事件
        public static final String ON_STREAM_CHANGED = "/on_stream_changed";
        // 流无人观看事件
        public static final String ON_STREAM_NONE_READER = "/on_stream_none_reader";
        // 流未找到事件
        public static final String ON_STREAM_NOT_FOUND = "/on_stream_not_found";
        // 服务器启动事件
        public static final String ON_SERVER_STARTED = "/on_server_started";
        // 服务器退出事件
        public static final String ON_SERVER_EXITED = "/on_server_exited";
        // 服务器心跳上报
        public static final String ON_SERVER_KEEPALIVE = "/on_server_keepalive";
        // RTP 发送停止事件
        public static final String ON_SEND_RTP_STOPPED = "/on_send_rtp_stopped";
        // RTP 服务器超时事件
        public static final String ON_RTP_SERVER_TIMEOUT = "/on_rtp_server_timeout";
    }

}
