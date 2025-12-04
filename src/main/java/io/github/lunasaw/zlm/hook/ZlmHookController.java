package io.github.lunasaw.zlm.hook;

import io.github.lunasaw.zlm.hook.param.*;
import io.github.lunasaw.zlm.hook.service.ZlmHookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.lunasaw.zlm.hook.ZlmHook.Paths.*;

/**
 * ZLM Hook 控制器
 *
 * @author CHEaN
 */
@Slf4j
@Tag(name = "ZLM Hook", description = "ZLMediaKit 钩子回调接口，用于处理各种媒体流事件")
@RestController
@RequestMapping(ZLM_HOOK)
public class ZlmHookController {

    private final ZlmHookService zlmHookService;
    private final AsyncTaskExecutor executor;

    public ZlmHookController(ZlmHookService zlmHookService, @Qualifier("zlmTaskExecutor") AsyncTaskExecutor executor) {
        this.zlmHookService = zlmHookService;
        this.executor = executor;
    }

    @Operation(summary = "流量统计事件", description = "流量统计事件，播放器或推流器断开时并且耗用流量超过特定阈值时会触发此事件；此事件对回复不敏感")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_FLOW_REPORT)
    public HookResult onFlowReport(@Parameter(description = "流量统计参数") @RequestBody HookParamForOnFlowReport param) {
        return handleAsyncHookEvent("onFlowReport", param, zlmHookService::onFlowReport);
    }

    @Operation(summary = "HTTP 访问事件", description = "访问 HTTP 文件服务器上 HLS 之外的文件时触发，结果会被缓存")
    @ApiResponse(responseCode = "200", description = "访问成功", content = @Content(schema = @Schema(implementation = HookResultForOnHttpAccess.class)))
    @PostMapping(ON_HTTP_ACCESS)
    public HookResultForOnHttpAccess onHttpAccess(@Parameter(description = "HTTP 访问参数") @RequestBody
                                                  HookParamForOnHttpAccess param) {
        return handleSyncHookEvent("onHttpAccess", param, zlmHookService::onHttpAccess);
    }

    @Operation(summary = "播放器鉴权事件", description = "rtsp/rtmp/http-flv/ws-flv/hls 播放触发的鉴权事件")
    @ApiResponse(responseCode = "200", description = "鉴权成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_PLAY)
    public HookResult onPlay(@Parameter(description = "播放鉴权参数") @RequestBody
                             HookParamForOnPlay param) {
        return handleSyncHookEvent("onPlay", param, zlmHookService::onPlay);
    }

    @Operation(summary = "推流鉴权事件", description = "rtsp/rtmp/rtp 推流时触发的鉴权事件")
    @ApiResponse(responseCode = "200", description = "鉴权成功", content = @Content(schema = @Schema(implementation = HookResultForOnPublish.class)))
    @PostMapping(ON_PUBLISH)
    public HookResultForOnPublish onPublish(@Parameter(description = "推流鉴权参数") @RequestBody
                                            HookParamForOnPublish param) {
        return handleSyncHookEvent("onPublish", param, zlmHookService::onPublish);
    }

    @Operation(summary = "MP4 录制事件", description = "MP4 录制完成时触发的事件；此事件对回复不敏感")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_RECORD_MP4)
    public HookResult onRecordMp4(@Parameter(description = "MP4 录制参数") @RequestBody
                                  HookParamForOnRecordMP4 param) {
        return handleAsyncHookEvent("onRecordMp4", param, zlmHookService::onRecordMp4);
    }

    @Operation(summary = "RTSP 专用鉴权事件", description = "RTSP 专用鉴权，先触发 on_rtsp_realm 事件后才会触发此事件")
    @ApiResponse(responseCode = "200", description = "鉴权成功", content = @Content(schema = @Schema(implementation = HookResultForOnRtspAuth.class)))
    @PostMapping(ON_RTSP_AUTH)
    public HookResultForOnRtspAuth onRtspAuth(@Parameter(description = "RTSP 鉴权参数") @RequestBody
                                              HookParamForOnRtspAuth param) {
        return handleSyncHookEvent("onRtspAuth", param, zlmHookService::onRtspAuth);
    }

    @Operation(summary = "RTSP 域鉴权事件", description = "判断 RTSP 流是否开启专用鉴权方式，开启后会触发 on_rtsp_auth 事件")
    @ApiResponse(responseCode = "200", description = "鉴权成功", content = @Content(schema = @Schema(implementation = HookResultForOnRtspRealm.class)))
    @PostMapping(ON_RTSP_REALM)
    public HookResultForOnRtspRealm onRtspRealm(@Parameter(description = "RTSP 域鉴权参数") @RequestBody
                                                HookParamForOnRtspRealm param) {
        return handleSyncHookEvent("onRtspRealm", param, zlmHookService::onRtspRealm);
    }

    @Operation(summary = "Shell 登录鉴权", description = "Shell 登录鉴权，ZLMediaKit 提供简单的 telnet 调试方式")
    @ApiResponse(responseCode = "200", description = "鉴权成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_SHELL_LOGIN)
    public HookResult onShellLogin(@Parameter(description = "Shell 鉴权参数") @RequestBody
                                   HookParamForOnShellLogin param) {
        return handleSyncHookEvent("onShellLogin", param, zlmHookService::onShellLogin);
    }

    @Operation(summary = "流状态变化事件", description = "rtsp/rtmp 流注册或注销时触发；此事件对回复不敏感")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_STREAM_CHANGED)
    public HookResult onStreamChanged(@Parameter(description = "流状态变化参数") @RequestBody
                                      HookParamForOnStreamChanged param) {
        return handleAsyncHookEvent("onStreamChanged", param, zlmHookService::onStreamChanged);
    }

    @Operation(summary = "流无人观看事件", description = "rtsp/rtmp 流无人观看时触发，可以在此事件中关闭无人观看的流")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResultForOnStreamNoneReader.class)))
    @PostMapping(ON_STREAM_NONE_READER)
    public HookResultForOnStreamNoneReader onStreamNoneReader(@Parameter(description = "流无人观看参数") @RequestBody
                                                              HookParamForOnStreamNoneReader param) {
        return handleSyncHookEvent("onStreamNoneReader", param, zlmHookService::onStreamNoneReader);
    }

    @Operation(summary = "流未找到事件", description = "流未找到时触发，可在此时进行按需拉流；此事件对回复不敏感")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_STREAM_NOT_FOUND)
    public HookResult onStreamNotFound(@Parameter(description = "流未找到参数") @RequestBody
                                       HookParamForOnStreamNotFound param) {
        return handleAsyncHookEvent("onStreamNotFound", param, zlmHookService::onStreamNotFound);
    }

    @Operation(summary = "服务器启动事件", description = "服务器启动时触发，可用于监听服务器崩溃重启；此事件对回复不敏感")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_SERVER_STARTED)
    public HookResult onServerStarted(@Parameter(description = "服务器配置参数") @RequestBody
                                      HookParamForOnServerStarted param) {
        return handleAsyncHookEvent("onServerStarted", param, zlmHookService::onServerStarted);
    }

    @Operation(summary = "服务器退出事件", description = "服务器退出时触发的事件")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_SERVER_EXITED)
    public HookResult onServerExited(@Parameter(description = "钩子参数") @RequestBody
                                     HookParamForOnServerExited param) {
        return handleAsyncHookEvent("onServerExited", param, zlmHookService::onServerExited);
    }

    @Operation(summary = "服务器心跳上报", description = "服务器定时上报时间，上报间隔可配置，默认 10s 上报一次")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_SERVER_KEEPALIVE)
    public HookResult onServerKeepalive(@Parameter(description = "服务器心跳参数") @RequestBody
                                        HookParamForOnServerKeepalive param) {
        return handleAsyncHookEvent("onServerKeepalive", param, zlmHookService::onServerKeepLive);
    }

    @Operation(summary = "RTP 发送停止事件", description = "发送 RTP(startSendRtp) 被动关闭时的回调事件；对回复不敏感")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_SEND_RTP_STOPPED)
    public HookResult onSendRtpStopped(@Parameter(description = "RTP 发送停止参数") @RequestBody
                                       HookParamForOnSendRtpStopped param) {
        return handleAsyncHookEvent("onSendRtpStopped", param, zlmHookService::onSendRtpStopped);
    }

    @Operation(summary = "RTP 服务器超时事件", description = "RTP 服务器长时间未收到数据时触发；对回复不敏感")
    @ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = HookResult.class)))
    @PostMapping(ON_RTP_SERVER_TIMEOUT)
    public HookResult onRtpServerTimeout(@Parameter(description = "RTP 服务器超时参数") @RequestBody
                                         HookParamForOnRtpServerTimeout param) {
        return handleAsyncHookEvent("onRtpServerTimeout", param, zlmHookService::onRtpServerTimeout);
    }

    /**
     * 处理同步 Hook 事件的通用方法
     *
     * @param hookName Hook 事件名称，用于日志输出
     * @param param    Hook 参数
     * @param function Hook 处理函数
     * @param <T>      参数类型
     * @param <R>      返回类型
     * @return Hook响应结果
     */
    private <T, R> R handleSyncHookEvent(String hookName, T param, Function<T, R> function) {
        try {
            log.trace("{} param = {}", hookName, param);
            R result = function.apply(param);
            log.trace("{} success, result = {}", hookName, result);
            return result;
        } catch (Exception e) {
            log.trace("{} fail, param = {}", hookName, param, e);
            throw e;
        }
    }

    /**
     * 处理异步 Hook 事件的通用方法
     *
     * @param hookName Hook 事件名称，用于日志输出
     * @param param    Hook 参数
     * @param consumer Hook 处理函数
     * @param <T>      参数类型
     * @return Hook响应结果（异步处理总是返回 SUCCESS）
     */
    private <T> HookResult handleAsyncHookEvent(String hookName, T param, Consumer<T> consumer) {
        try {
            log.trace("{} param = {}", hookName, param);
            executor.execute(() -> {
                try {
                    consumer.accept(param);
                    log.trace("{} async success", hookName);
                } catch (Exception e) {
                    log.trace("{} async fail", hookName, e);
                }
            });
            return HookResult.success();
        } catch (Exception e) {
            log.trace("{} fail, param = {}", hookName, param, e);
            return HookResult.success();
        }
    }

}
