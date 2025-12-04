package io.github.lunasaw.zlm.hook.service;

import io.github.lunasaw.zlm.hook.entity.*;

/**
 * ZLM Hook 服务接口
 * <p>
 * <a href="https://github.com/zlmediakit/ZLMediaKit/wiki/MediaServer%E6%94%AF%E6%8C%81%E7%9A%84HTTP-HOOK-API">ZLMediaKit Hook 文档</a>
 *
 * @author CHEaN
 */
public interface ZlmHookService {

    /**
     * 流量统计事件，播放器或推流器断开时并且耗用流量超过特定阈值时会触发此事件，
     * 阈值通过配置文件 general.flowThreshold 配置。
     *
     * @apiNote 此事件对回复不敏感
     */
    void onFlowReport(HookParamForOnFlowReport param);

    /**
     * 访问 HTTP 文件服务器上 HLS 之外的文件时触发。
     */
    HookResultForOnHttpAccess onHttpAccess(HookParamForOnHttpAccess param);

    /**
     * 播放器鉴权事件，rtsp/rtmp/http-flv/ws-flv/hls 的播放都将触发此鉴权事件；
     * 如果流不存在，那么先触发 on_play 事件然后触发 on_stream_not_found 事件。
     * 播放 rtsp 流时，如果该流启动了 rtsp 专属鉴权(on_rtsp_realm)那么将不再触发 on_play 事件。
     */
    HookResultForGeneral onPlay(HookParamForOnPlay param);

    /**
     * rtsp/rtmp/rtp 推流鉴权事件。
     */
    HookResultForOnPublish onPublish(HookParamForOnPublish param);

    /**
     * 录制 MP4 完成后通知事件。
     *
     * @apiNote 此事件对回复不敏感
     */
    void onRecordMp4(HookParamForOnRecordMP4 param);

    /**
     * RTSP 专用的鉴权事件，先触发 {@link #onRtspRealm} 事件然后才会触发本事件。
     */
    HookResultForOnRtspAuth onRtspAuth(HookParamForOnRtspAuth param);

    /**
     * 该 RTSP 流是否开启 RTSP 专用方式的鉴权事件，开启后才会触发 {@link #onRtspAuth} 事件。
     * <p>
     * 需要指出的是 RTSP 也支持 URL 参数鉴权，它支持两种方式鉴权。
     */
    HookResultForOnRtspRealm onRtspRealm(HookParamForOnRtspRealm param);

    /**
     * Shell 登录鉴权，ZLMediaKit 提供简单的 telnet 调试方式。
     * 使用 <code>telnet 127.0.0.1 9000</code> 能进入 MediaServer 进程的 shell 界面。
     */
    HookResultForGeneral onShellLogin(HookParamForOnShellLogin param);

    /**
     * rtsp/rtmp 流注册或注销时触发此事件。
     *
     * @apiNote 此事件对回复不敏感
     */
    void onStreamChanged(HookParamForOnStreamChanged param);

    /**
     * 流无人观看时事件，用户可以通过此事件选择是否关闭无人看的流。
     * 一个直播流注册上线了，如果一直没人观看也会触发一次无人观看事件，
     * 触发时的协议 schema 是随机的，看哪种协议最晚注册 (一般为 hls)。
     * 后续从有人观看转为无人观看，触发协议 schema 为最后一名观看者使用何种协议。
     * 目前 mp4/hls 录制不当做观看人数(mp4 录制可以通过配置文件 mp4_as_player 控制，
     * 但是 rtsp/rtmp/rtp 转推算观看人数，也会触发该事件。
     */
    HookResultForOnStreamNoneReader onStreamNoneReader(HookParamForOnStreamNoneReader param);

    /**
     * 流未找到事件，用户可以在此事件触发时，立即去拉流，这样可以实现按需拉流。
     *
     * @apiNote 此事件对回复不敏感
     */
    void onStreamNotFound(HookParamForOnStreamNotFound param);

    /**
     * 服务器启动事件，可以用于监听服务器崩溃重启。
     *
     * @apiNote 此事件对回复不敏感
     */
    void onServerStarted(HookParamForOnServerStarted param);

    /**
     * 服务器退出报告，当服务器正常退出时触发
     *
     * @apiNote 此事件对回复不敏感
     */
    void onServerExited(HookParamForOnServerExited param);

    /**
     * 服务器定时上报时间，上报间隔可配置，默认 10s 上报一次
     *
     * @apiNote 此事件对回复不敏感
     */
    void onServerKeepLive(HookParamForOnServerKeepalive param);

    /**
     * 调用 startSendRtp 接口，rtp 推流停止时触发
     *
     * @apiNote 此事件对回复不敏感
     */
    void onSendRtpStopped(HookParamForOnSendRtpStopped param);

    /**
     * 调用 openRtpServer 接口，rtp server 长时间未收到数据时触发
     *
     * @apiNote 此事件对回复不敏感
     */
    void onRtpServerTimeout(HookParamForOnRtpServerTimeout param);

}
