package io.github.lunasaw.zlm.api;

import io.github.lunasaw.zlm.api.service.ZlmRestService;
import io.github.lunasaw.zlm.config.ZlmNode;
import io.github.lunasaw.zlm.api.entity.*;
import io.github.lunasaw.zlm.api.entity.req.CloseStreamsReq;
import io.github.lunasaw.zlm.api.entity.req.MediaReq;
import io.github.lunasaw.zlm.api.entity.req.RecordReq;
import io.github.lunasaw.zlm.api.entity.req.SnapshotReq;
import io.github.lunasaw.zlm.api.entity.rtp.*;
import io.github.lunasaw.zlm.node.NodeSupplier;
import io.github.lunasaw.zlm.node.service.NodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static io.github.lunasaw.zlm.constant.ApiConstants.HEADER_NODE_KEY;

/**
 * ZLM REST API 控制器
 *
 * @author luna
 */
@Slf4j
@RequiredArgsConstructor
@Tag(name = "ZLM 媒体服务器管理", description = "ZLMediaKit 流媒体服务器管理相关接口")
@ResponseBody
@RequestMapping("/zlm/api")
public class ZlmAPI {

    private final NodeSupplier nodeSupplier;
    private final NodeService nodeService;

    /**
     * 获取可用的 ZLM 节点
     *
     * @param nodeKey 节点 key，如果为空则使用负载均衡策略选择节点
     */
    private ZlmNode getAvailableNode(String nodeKey) {
        return (nodeKey != null && !nodeKey.isBlank())
                ? nodeService.getAvailableNode(nodeKey)
                : nodeService.selectNode();
    }

    // ==================== 系统信息接口 ====================

    @GetMapping("/version")
    @Operation(summary = "获取版本信息", description = "获取 ZLMediaKit 服务器的版本信息")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<Version> getVersion(@RequestHeader(HEADER_NODE_KEY) String nodeKey) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getVersion(node.getHost(), node.getSecret());
    }

    @GetMapping("/api/list")
    @Operation(summary = "获取 API 列表", description = "获取 ZLMediaKit 服务器支持的所有 API 接口列表")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<List<String>> getApiList(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "查询参数") @RequestParam(required = false) Map<String, String> params) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getApiList(node.getHost(), node.getSecret(), params);
    }

    @GetMapping("/threads/load")
    @Operation(summary = "获取网络线程负载", description = "获取 ZLMediaKit 服务器网络线程的负载情况")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<List<ThreadLoad>> getThreadsLoad(@RequestHeader(HEADER_NODE_KEY) String nodeKey) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getThreadsLoad(node.getHost(), node.getSecret());
    }

    @GetMapping("/statistic")
    @Operation(summary = "获取统计信息", description = "获取 ZLMediaKit 服务器主要对象的统计数量")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<ImportantObjectNum> getStatistic(@RequestHeader(HEADER_NODE_KEY) String nodeKey) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getStatistic(node.getHost(), node.getSecret());
    }

    @GetMapping("/work-threads/load")
    @Operation(summary = "获取后台线程负载", description = "获取 ZLMediaKit 服务器后台工作线程的负载情况")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<List<ThreadLoad>> getWorkThreadsLoad(@RequestHeader(HEADER_NODE_KEY) String nodeKey) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getWorkThreadsLoad(node.getHost(), node.getSecret());
    }

    @GetMapping("/server/config")
    @Operation(summary = "获取服务器配置", description = "获取 ZLMediaKit 服务器的配置信息")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<List<ServerNodeConfig>> getServerConfig(@RequestHeader(HEADER_NODE_KEY) String nodeKey) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getServerConfig(node.getHost(), node.getSecret());
    }

    @PostMapping("/server/config")
    @Operation(summary = "设置服务器配置", description = "修改 ZLMediaKit 服务器的配置参数")
    @ApiResponse(responseCode = "200", description = "设置成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> setServerConfig(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "配置参数") @RequestBody Map<String, String> params) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.setServerConfig(node.getHost(), node.getSecret(), params);
    }

    @PostMapping("/server/restart")
    @Operation(summary = "重启服务器", description = "重启 ZLMediaKit 服务器")
    @ApiResponse(responseCode = "200", description = "重启成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<Object> restartServer(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "重启参数") @RequestBody Map<String, String> params) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.restartServer(node.getHost(), node.getSecret(), params);
    }

    // ==================== 媒体流管理接口 ====================

    @PostMapping("/media/list")
    @Operation(summary = "获取流列表", description = "获取 ZLMediaKit 服务器中的媒体流列表")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<List<MediaData>> getMediaList(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "媒体查询条件") @RequestBody MediaReq mediaReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getMediaList(node.getHost(), node.getSecret(), mediaReq);
    }

    @PostMapping("/media/close")
    @Operation(summary = "关断单个流", description = "关闭指定的媒体流")
    @ApiResponse(responseCode = "200", description = "关闭成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> closeStream(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "媒体流信息") @RequestBody MediaReq mediaReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.closeStream(node.getHost(), node.getSecret(), mediaReq);
    }

    @PostMapping("/media/close-batch")
    @Operation(summary = "批量关断流", description = "批量关闭多个媒体流")
    @ApiResponse(responseCode = "200", description = "关闭成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<Void> closeStreams(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "批量关流请求") @RequestBody CloseStreamsReq closeStreamsReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.closeStreams(node.getHost(), node.getSecret(), closeStreamsReq.toMap());
    }

    @PostMapping("/media/online")
    @Operation(summary = "检查流是否在线", description = "检查指定媒体流是否在线")
    @ApiResponse(responseCode = "200", description = "检查成功",
            content = @Content(schema = @Schema(implementation = MediaOnlineStatus.class)))
    public MediaOnlineStatus isMediaOnline(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "媒体流信息") @RequestBody MediaReq mediaReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.isMediaOnline(node.getHost(), node.getSecret(), mediaReq);
    }

    @PostMapping("/media/player/list")
    @Operation(summary = "获取媒体流播放器列表", description = "获取指定媒体流的播放器列表")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<MediaPlayer> getMediaPlayerList(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "媒体流信息") @RequestBody MediaReq mediaReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getMediaPlayerList(node.getHost(), node.getSecret(), mediaReq);
    }

    @PostMapping("/media/info")
    @Operation(summary = "获取流信息", description = "获取指定媒体流的详细信息")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<MediaInfo> getMediaInfo(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "媒体流信息") @RequestBody MediaReq mediaReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getMediaInfo(node.getHost(), node.getSecret(), mediaReq);
    }

    @PostMapping("/media/play-urls")
    @Operation(summary = "获取播放地址", description = "获取指定媒体流的多协议播放地址")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<PlayUrl> getPlaybackUrls(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "媒体流信息") @RequestBody MediaReq mediaReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getPlaybackUrls(node.getHost(), node.getSecret(), mediaReq);
    }

    @PostMapping("/broadcast/message")
    @Operation(summary = "广播 WebRTC 消息", description = "广播 WebRTC datachannel 消息")
    @ApiResponse(responseCode = "200", description = "广播成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> broadcastMessage(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "消息参数") @RequestBody Map<String, String> params) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.broadcastMessage(node.getHost(), node.getSecret(), params);
    }

    // ==================== TCP会话管理接口 ====================

    @GetMapping("/session/list")
    @Operation(summary = "获取 TCP 会话列表", description = "获取 ZLMediaKit 服务器中所有 TCP 连接会话的列表")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<List<TcpLink>> getAllSession(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "本地端口") @RequestParam(required = false) String localPort,
            @Parameter(description = "对端IP") @RequestParam(required = false) String peerIp) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getAllSession(node.getHost(), node.getSecret(), localPort, peerIp);
    }

    @DeleteMapping("/session/{sessionId}")
    @Operation(summary = "断开 TCP 连接", description = "根据会话 ID 断开指定的 TCP 连接")
    @ApiResponse(responseCode = "200", description = "断开成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> kickSession(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "会话 ID") @PathVariable("sessionId") String sessionId) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.kickSession(node.getHost(), node.getSecret(), sessionId);
    }

    @PostMapping("/session/kick-batch")
    @Operation(summary = "批量断开 TCP 连接", description = "根据条件批量断开 TCP 连接")
    @ApiResponse(responseCode = "200", description = "断开成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> kickSessions(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "查询条件") @RequestBody Map<String, String> params) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.kickSessions(node.getHost(), node.getSecret(), params);
    }

    // ==================== 代理流管理接口 ====================

    @PostMapping("/proxy/add")
    @Operation(summary = "添加代理拉流", description = "添加一个拉流代理，用于从外部拉取媒体流")
    @ApiResponse(responseCode = "200", description = "添加成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<StreamKey> addStreamProxy(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "拉流代理配置") @RequestBody StreamProxyItem streamProxyItem) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.addStreamProxy(node.getHost(), node.getSecret(), streamProxyItem);
    }

    @DeleteMapping("/proxy/{key}")
    @Operation(summary = "关闭拉流代理", description = "根据代理 key 关闭指定的拉流代理")
    @ApiResponse(responseCode = "200", description = "关闭成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<StreamKey.StringDelFlag> delStreamProxy(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "代理 key") @PathVariable("key") String key) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.delStreamProxy(node.getHost(), node.getSecret(), key);
    }

    @PostMapping("/proxy/info")
    @Operation(summary = "获取拉流代理信息", description = "获取拉流代理的详细信息")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> getProxyInfo(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "查询参数") @RequestBody Map<String, String> params) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getProxyInfo(node.getHost(), node.getSecret(), params);
    }

    @PostMapping("/pusher/add")
    @Operation(summary = "添加推流代理", description = "添加一个推流代理，用于向外部推送媒体流")
    @ApiResponse(responseCode = "200", description = "添加成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<StreamKey> addStreamPusherProxy(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "推流代理配置") @RequestBody StreamPusherItem streamPusherItem) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.addStreamPusherProxy(node.getHost(), node.getSecret(), streamPusherItem);
    }

    @DeleteMapping("/pusher/{key}")
    @Operation(summary = "关闭推流代理", description = "根据代理 key 关闭指定的推流代理")
    @ApiResponse(responseCode = "200", description = "关闭成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<StreamKey.StringDelFlag> delStreamPusherProxy(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "代理key") @PathVariable("key") String key) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.delStreamPusherProxy(node.getHost(), node.getSecret(), key);
    }

    @PostMapping("/pusher/info")
    @Operation(summary = "获取推流代理信息", description = "获取推流代理的详细信息")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> getProxyPusherInfo(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "查询参数") @RequestBody Map<String, String> params) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getProxyPusherInfo(node.getHost(), node.getSecret(), params);
    }

    // ==================== FFmpeg 管理接口 ====================

    @PostMapping("/ffmpeg/add")
    @Operation(summary = "添加 FFmpeg 拉流代理", description = "添加一个 FFmpeg 拉流代理，用于从外部拉取媒体流")
    @ApiResponse(responseCode = "200", description = "添加成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<StreamKey> addFFmpegSource(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "FFmpeg配置") @RequestBody StreamFfmpegItem streamFfmpegItem) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.addFFmpegSource(node.getHost(), node.getSecret(), streamFfmpegItem);
    }

    @DeleteMapping("/ffmpeg/{key}")
    @Operation(summary = "关闭 FFmpeg 拉流代理", description = "根据代理 key 关闭指定的 FFmpeg 拉流代理")
    @ApiResponse(responseCode = "200", description = "关闭成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<StreamKey.StringDelFlag> delFFmpegSource(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "代理key") @PathVariable("key") String key) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.delFFmpegSource(node.getHost(), node.getSecret(), key);
    }

    // ==================== 录制管理接口 ====================

    /**
     * 获取录制文件列表
     */
    @PostMapping("/record/files")
    @Operation(summary = "获取录制文件列表", description = "获取指定媒体流的录制文件列表")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<Mp4RecordFile> getMp4RecordFile(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "录制查询条件") @RequestBody RecordReq recordReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getMp4RecordFile(node.getHost(), node.getSecret(), recordReq);
    }

    /**
     * 删除录像文件夹
     */
    @PostMapping("/record/delete-directory")
    @Operation(summary = "删除录像文件夹", description = "删除指定的录像文件夹")
    @ApiResponse(responseCode = "200", description = "删除成功",
            content = @Content(schema = @Schema(implementation = DeleteRecordDirectory.class)))
    public DeleteRecordDirectory deleteRecordDirectory(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "删除参数") @RequestBody Map<String, String> params) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.deleteRecordDirectory(node.getHost(), node.getSecret(), params);
    }

    @PostMapping("/record/start")
    @Operation(summary = "开始录制", description = "开始录制指定的媒体流")
    @ApiResponse(responseCode = "200", description = "录制开始成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> startRecord(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "录制配置") @RequestBody RecordReq recordReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.startRecord(node.getHost(), node.getSecret(), recordReq);
    }

    @PostMapping("/record/speed")
    @Operation(summary = "设置录像速度", description = "设置录像文件的播放速度")
    @ApiResponse(responseCode = "200", description = "设置成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> setRecordSpeed(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "录制配置") @RequestBody RecordReq recordReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.setRecordSpeed(node.getHost(), node.getSecret(), recordReq);
    }

    @PostMapping("/record/seek")
    @Operation(summary = "设置录像播放位置", description = "设置录像流的播放位置")
    @ApiResponse(responseCode = "200", description = "设置成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> seekRecordStamp(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "录制配置") @RequestBody RecordReq recordReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.seekRecordStamp(node.getHost(), node.getSecret(), recordReq);
    }

    @PostMapping("/record/stop")
    @Operation(summary = "停止录制", description = "停止录制指定的媒体流")
    @ApiResponse(responseCode = "200", description = "录制停止成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> stopRecord(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "录制配置") @RequestBody RecordReq recordReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.stopRecord(node.getHost(), node.getSecret(), recordReq);
    }

    @PostMapping("/record/status")
    @Operation(summary = "检查录制状态", description = "检查指定媒体流是否正在录制")
    @ApiResponse(responseCode = "200", description = "检查成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> isRecording(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "录制配置") @RequestBody RecordReq recordReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.isRecording(node.getHost(), node.getSecret(), recordReq);
    }

    // ==================== 截图接口 ====================

    @PostMapping("/snapshot")
    @Operation(summary = "获取截图", description = "获取指定媒体流的截图")
    @ApiResponse(responseCode = "200", description = "截图获取成功",
            content = @Content(schema = @Schema(implementation = String.class)))
    public String getSnap(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "截图配置") @RequestBody SnapshotReq snapshotReq) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getSnap(node.getHost(), node.getSecret(), snapshotReq);
    }

    // ==================== RTP服务器管理接口 ====================

    @GetMapping("/rtp/info/{streamId}")
    @Operation(summary = "获取RTP推流信息", description = "根据流ID获取RTP推流的详细信息")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = RtpInfoResult.class)))
    public RtpInfoResult getRtpInfo(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "流ID") @PathVariable("streamId") String streamId) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.getRtpInfo(node.getHost(), node.getSecret(), streamId);
    }

    @PostMapping("/rtp/server/open")
    @Operation(summary = "创建RTP服务器", description = "创建一个RTP服务器用于接收RTP推流")
    @ApiResponse(responseCode = "200", description = "创建成功",
            content = @Content(schema = @Schema(implementation = OpenRtpServerResult.class)))
    public OpenRtpServerResult openRtpServer(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "RTP服务器配置") @RequestBody OpenRtpServerReq req) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.openRtpServer(node.getHost(), node.getSecret(), req);
    }

    @PostMapping("/rtp/server/open-multiplex")
    @Operation(summary = "创建多路复用RTP服务器", description = "创建一个多路复用RTP服务器")
    @ApiResponse(responseCode = "200", description = "创建成功",
            content = @Content(schema = @Schema(implementation = OpenRtpServerResult.class)))
    public OpenRtpServerResult openRtpServerMultiplex(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "RTP服务器配置") @RequestBody OpenRtpServerReq req) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.openRtpServerMultiplex(node.getHost(), node.getSecret(), req);
    }

    @PostMapping("/rtp/server/connect")
    @Operation(summary = "连接RTP服务器", description = "连接到指定的RTP服务器")
    @ApiResponse(responseCode = "200", description = "连接成功",
            content = @Content(schema = @Schema(implementation = OpenRtpServerResult.class)))
    public OpenRtpServerResult connectRtpServer(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "RTP连接配置") @RequestBody ConnectRtpServerReq req) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.connectRtpServer(node.getHost(), node.getSecret(), req);
    }

    @DeleteMapping("/rtp/server/{streamId}")
    @Operation(summary = "关闭RTP服务器", description = "根据流ID关闭指定的RTP服务器")
    @ApiResponse(responseCode = "200", description = "关闭成功",
            content = @Content(schema = @Schema(implementation = CloseRtpServerResult.class)))
    public CloseRtpServerResult closeRtpServer(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "流ID") @PathVariable("streamId") String streamId) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.closeRtpServer(node.getHost(), node.getSecret(), streamId);
    }

    @PutMapping("/rtp/server/{streamId}/ssrc/{ssrc}")
    @Operation(summary = "更新RTP服务器SSRC", description = "更新RTP服务器的过滤SSRC")
    @ApiResponse(responseCode = "200", description = "更新成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> updateRtpServerSSRC(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "流ID") @PathVariable("streamId") String streamId,
            @Parameter(description = "SSRC值") @PathVariable("ssrc") String ssrc) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.updateRtpServerSSRC(node.getHost(), node.getSecret(), streamId, ssrc);
    }

    @PostMapping("/rtp/server/{streamId}/pause-check")
    @Operation(summary = "暂停RTP超时检查", description = "暂停指定RTP服务器的超时检查")
    @ApiResponse(responseCode = "200", description = "暂停成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> pauseRtpCheck(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "流ID") @PathVariable("streamId") String streamId) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.pauseRtpCheck(node.getHost(), node.getSecret(), streamId);
    }

    @PostMapping("/rtp/server/{streamId}/resume-check")
    @Operation(summary = "恢复RTP超时检查", description = "恢复指定RTP服务器的超时检查")
    @ApiResponse(responseCode = "200", description = "恢复成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> resumeRtpCheck(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "流ID") @PathVariable("streamId") String streamId) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.resumeRtpCheck(node.getHost(), node.getSecret(), streamId);
    }

    @GetMapping("/rtp/server/list")
    @Operation(summary = "获取RTP服务器列表", description = "获取所有正在运行的RTP服务器列表")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<List<RtpServer>> listRtpServer(@RequestHeader(HEADER_NODE_KEY) String nodeKey) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.listRtpServer(node.getHost(), node.getSecret());
    }

    // ==================== RTP发送管理接口 ====================

    @PostMapping("/rtp/send/start")
    @Operation(summary = "开始发送RTP", description = "开始向指定地址发送RTP流")
    @ApiResponse(responseCode = "200", description = "开始成功",
            content = @Content(schema = @Schema(implementation = StartSendRtpResult.class)))
    public StartSendRtpResult startSendRtp(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "RTP发送配置") @RequestBody StartSendRtpReq req) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.startSendRtp(node.getHost(), node.getSecret(), req);
    }

    @PostMapping("/rtp/send/start-passive")
    @Operation(summary = "开始被动发送RTP", description = "开始TCP passive模式被动发送RTP流")
    @ApiResponse(responseCode = "200", description = "开始成功",
            content = @Content(schema = @Schema(implementation = StartSendRtpResult.class)))
    public StartSendRtpResult startSendRtpPassive(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "RTP发送配置") @RequestBody StartSendRtpReq req) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.startSendRtpPassive(node.getHost(), node.getSecret(), req);
    }

    @PostMapping("/rtp/send/stop")
    @Operation(summary = "停止发送RTP", description = "停止向指定地址发送RTP流")
    @ApiResponse(responseCode = "200", description = "停止成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> stopSendRtp(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "RTP停止配置") @RequestBody CloseSendRtpReq req) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.stopSendRtp(node.getHost(), node.getSecret(), req);
    }

    // ==================== MP4文件管理接口 ====================

    @PostMapping("/mp4/load")
    @Operation(summary = "点播MP4文件", description = "加载并点播指定MP4文件")
    @ApiResponse(responseCode = "200", description = "加载成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<String> loadMP4File(
            @RequestHeader(HEADER_NODE_KEY) String nodeKey,
            @Parameter(description = "文件参数") @RequestBody Map<String, String> params) {
        ZlmNode node = getAvailableNode(nodeKey);
        return ZlmRestService.loadMP4File(node.getHost(), node.getSecret(), params);
    }

    // ==================== 存储管理接口 ====================

    // ==================== 指定节点操作接口 ====================

    /**
     * 指定节点获取版本信息
     */
    @GetMapping("/node/{nodeId}/version")
    @Operation(summary = "指定节点获取版本信息", description = "获取指定 ZLM 节点的版本信息")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<Version> getVersionByNode(
            @Parameter(description = "节点 ID") @PathVariable("nodeId") String nodeId) {
        ZlmNode node = nodeService.getAvailableNode(nodeId);
        return ZlmRestService.getVersion(node.getHost(), node.getSecret());
    }

    /**
     * 指定节点获取流列表
     */
    @PostMapping("/node/{nodeId}/media/list")
    @Operation(summary = "指定节点获取流列表", description = "获取指定ZLM节点中的媒体流列表")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = ServerResponse.class)))
    public ServerResponse<List<MediaData>> getMediaListByNode(
            @Parameter(description = "节点 ID") @PathVariable(value = "nodeId") String nodeId,
            @Parameter(description = "媒体查询条件") @RequestBody MediaReq mediaReq) {
        ZlmNode node = nodeService.getAvailableNode(nodeId);
        return ZlmRestService.getMediaList(node.getHost(), node.getSecret(), mediaReq);
    }

    /**
     * 获取所有节点列表
     */
    @GetMapping("/nodes")
    @Operation(summary = "获取所有节点列表", description = "获取当前配置的所有 ZLM 节点信息")
    @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(schema = @Schema(implementation = List.class)))
    public List<ZlmNode> getAllNodes() {
        return nodeSupplier.getNodes();
    }

    // ==================== 异常处理 ====================

    /**
     * 处理节点不存在异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ServerResponse<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数错误: {}", e.getMessage());
        return new ServerResponse<>(0, null, e.getMessage(), null);
    }
}
