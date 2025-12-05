package io.github.lunasaw.zlm.api.client;

import io.github.lunasaw.zlm.api.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 面向业务侧的 ZLMediaKit 客户端接口，内部会自动携带 host/secret。
 */
public interface ZlmClient extends AutoCloseable {

    /**
     * 获取当前节点支持的 API 列表
     *
     * @return API 名称集合
     */
    List<String> getApiList();

    /**
     * 查询网络线程负载
     *
     * @return 每个线程的延迟/负载信息
     */
    List<ThreadLoad> getThreadsLoad();

    /**
     * 查询后台工作线程的负载情况
     *
     * @return epoll/select 线程的负载明细
     */
    List<ThreadLoad> getWorkThreadsLoad();

    /**
     * 获取主要对象统计，主要用于分析内存性能
     *
     * @return 统计字段
     */
    StatisticData getStatistic();

    /**
     * 获取服务器配置，返回扁平化后的 key/value
     *
     * @return 配置键值对
     */
    Map<String, String> getServerConfig();

    /**
     * 设置服务器配置
     *
     * @param changeConfigs 需要修改的配置项键值对
     * @return 配置项变更个数
     */
    int setServerConfig(Map<String, String> changeConfigs);

    /**
     * 重启服务器
     *
     * @apiNote 只有 Daemon 方式才能重启，否则是直接关闭
     */
    void restartServer();

    /**
     * 获取流列表，可选筛选参数
     *
     * @param filter 筛选条件，允许为空
     * @return 满足条件的流列表
     */
    List<MediaInfo> getMediaList(StreamFilter filter);

    /**
     * 关闭单个流（兼容 close_stream）
     *
     * @param filter 筛选条件
     */
    void closeStream(StreamFilter filter);

    /**
     * 关闭流，可选筛选参数
     *
     * @param filter 筛选条件，允许为空
     * @param force  是否强制关闭
     * @return 关闭结果统计
     * @apiNote 目前所有类型的流都支持关闭
     */
    CloseStreams closeStreams(StreamFilter filter, Boolean force);

    /**
     * 删除截图目录或文件
     *
     * @param filter 流过滤条件
     * @param file   文件名，可为空表示整目录
     */
    void deleteSnapDirectory(StreamFilter filter, String file);

    /**
     * 获取 TCP Session 列表
     *
     * @param filter 筛选条件
     * @return 会话列表
     */
    List<SessionInfo> getAllSession(SessionFilter filter);

    /**
     * 断开指定 TCP 连接
     *
     * @param sessionId 会话 ID
     */
    void kickSession(String sessionId);

    /**
     * 批量断开 TCP 连接
     *
     * @param filter 筛选条件
     */
    void kickSessions(SessionFilter filter);

    /**
     * 动态添加 rtsp/rtmp/hls/http-ts/http-flv 拉流代理
     *
     * @param proxyItem 代理参数
     * @return 流的唯一标识
     * @apiNote 只支持 H264/H265/aac/G711/opus 负载
     */
    String addStreamProxy(StreamProxyItem proxyItem);

    /**
     * 关闭拉流代理
     *
     * @param key {@link #addStreamProxy} 接口返回的 key
     * @return 是否关闭成功
     */
    Boolean delStreamProxy(String key);

    /**
     * 获取拉流代理列表
     *
     * @return 代理列表
     */
    List<ProxyItemInfo> listStreamProxy();

    /**
     * 获取拉流代理信息
     *
     * @param key 代理 key
     * @return 代理详情
     */
    ProxyItemInfo getProxyInfo(String key);

    /**
     * 添加推流代理
     *
     * @param item 推流配置
     * @return 流的唯一标识
     */
    String addStreamPusherProxy(StreamPusherItem item);

    /**
     * 关闭推流代理
     *
     * @param key 推流代理 key
     * @return 是否关闭成功
     */
    Boolean delStreamPusherProxy(String key);

    /**
     * 获取推流代理列表
     *
     * @return 推流代理集合
     */
    List<ProxyItemInfo> listStreamPusherProxy();

    /**
     * 获取推流代理信息
     *
     * @param key 推流代理 key
     * @return 推流代理详情
     */
    ProxyItemInfo getProxyPusherInfo(String key);

    /**
     * 通过 fork FFmpeg 进程的方式拉流代理
     *
     * @param ffmpegItem FFmpeg 代理参数
     * @return 流的唯一标识
     * @apiNote 支持任意协议
     */
    String addFFmpegSource(StreamFFmpegItem ffmpegItem);

    /**
     * 关闭 ffmpeg 拉流代理
     *
     * @param key {@link #addFFmpegSource} 接口返回的 key
     * @return 是否关闭成功
     */
    Boolean delFFmpegSource(String key);

    /**
     * 获取 FFmpeg 拉流代理列表
     *
     * @return 代理列表
     */
    List<FFmpegSourceInfo> listFFmpegSource();

    /**
     * 流是否在线
     *
     * @param filter 流过滤
     * @return 是否在线
     */
    boolean isMediaOnline(StreamFilter filter);

    /**
     * 获取媒体流播放器列表
     *
     * @param filter 流过滤
     * @return 播放器列表
     */
    List<MediaPlayerSession> getMediaPlayerList(StreamFilter filter);

    /**
     * 广播 WebRTC datachannel 消息
     *
     * @param filter 流过滤
     * @param msg    要发送的消息
     */
    void broadcastMessage(StreamFilter filter, String msg);

    /**
     * 获取流信息
     *
     * @param filter 流过滤
     * @return 媒体信息
     */
    MediaInfo getMediaInfo(StreamFilter filter);

    /**
     * 查询录像文件列表
     *
     * @param filter         流过滤
     * @param customizedPath 自定义根目录
     * @param period         日期或路径
     * @return 列表数据
     */
    Mp4RecordFile getMp4RecordFile(StreamFilter filter, String customizedPath, String period);

    /**
     * 删除录像文件夹
     *
     * @param filter 流过滤
     * @param period 日期或路径
     */
    void deleteRecordDirectory(StreamFilter filter, String period);

    /**
     * 开始录制
     *
     * @param filter         流过滤
     * @param type           0 hls，1 mp4
     * @param customizedPath 自定义根目录
     * @param maxSecond      mp4 切片时长
     * @return 是否启动成功
     */
    boolean startRecord(StreamFilter filter, Integer type, String customizedPath, Integer maxSecond);

    /**
     * 开始事件录像
     *
     * @param filter    流过滤
     * @param path      录像相对路径（含文件名）
     * @param backMs    回溯录制时长
     * @param forwardMs 后续录制时长
     * @return 录制任务信息
     */
    StartRecordTaskResult startRecordTask(StreamFilter filter, String path, Integer backMs, Integer forwardMs);

    /**
     * 设置录像速度
     *
     * @param filter 流过滤
     * @param speed  倍速
     */
    void setRecordSpeed(StreamFilter filter, Double speed);

    /**
     * 设置录像播放位置
     *
     * @param filter 流过滤
     * @param stamp  播放位置，毫秒
     */
    void seekRecordStamp(StreamFilter filter, Long stamp);

    /**
     * 停止录制
     *
     * @param filter 流过滤
     * @param type   0 hls，1 mp4
     */
    void stopRecord(StreamFilter filter, Integer type);

    /**
     * 查询是否正在录制
     *
     * @param filter 流过滤
     * @param type   0 hls，1 mp4
     * @return 是否在录制
     */
    boolean isRecording(StreamFilter filter, Integer type);

    /**
     * 获取网络截图
     *
     * @param snapItem 截图参数
     * @return 图片二进制数据
     */
    byte[] getSnap(SnapshotItem snapItem);

    /**
     * 查询 RTP 代理端口的上下游地址
     *
     * @param streamId RTP 流 ID
     * @return 上下游地址信息，不存在时返回 null
     */
    SocketAddressPair getRtpInfo(String streamId);

    /**
     * 开启 GB28181 RTP 接收端口
     *
     * @param tcpMode       0 udp 模式，1 tcp 被动模式, 2 tcp 主动模式 (兼容 enable_tcp 为 0/1)
     * @param rtpServerBind 绑定的端口和流 ID
     * @return 实际绑定端口
     */
    Integer openRtpServer(Integer tcpMode, RtpServerBind rtpServerBind);

    /**
     * 开启多路复用 GB28181 RTP 接收端口
     *
     * @param item 绑定参数
     * @return 实际绑定端口
     */
    Integer openRtpServerMultiplex(RtpServerMultiplexItem item);

    /**
     * 主动连接 RTP 服务器
     *
     * @param item 连接参数
     */
    void connectRtpServer(ConnectRtpServerItem item);

    /**
     * 关闭 GB28181 RTP 接收端口
     *
     * @param streamId RTP 流 ID
     * @return 命中的记录数量
     */
    Integer closeRtpServer(String streamId);

    /**
     * 更新 RTP 服务器的 SSRC 过滤
     *
     * @param item SSRC 更新参数
     */
    void updateRtpServerSSRC(RtpServerSsrcItem item);

    /**
     * 暂停 RTP 超时检查
     *
     * @param streamId RTP 流 ID
     */
    void pauseRtpCheck(String streamId);

    /**
     * 恢复 RTP 超时检查
     *
     * @param streamId RTP 流 ID
     */
    void resumeRtpCheck(String streamId);

    /**
     * 获取已开启的 RTP 接收端口列表
     *
     * @return 端口与流绑定列表
     */
    List<RtpServerBind> listRtpServer();

    /**
     * 启动主动模式 RTP 推流
     *
     * @param sendRtpItem 推流参数
     * @return 本地端口
     */
    Integer startSendRtp(SendRtpItem sendRtpItem);

    /**
     * 启动被动模式 RTP 推流
     *
     * @param sendRtpPassiveItem 推流参数
     * @return 本地端口
     */
    Integer startSendRtpPassive(SendRtpPassiveItem sendRtpPassiveItem);

    /**
     * 启动双向对讲 RTP 推流
     *
     * @param sendRtpTalkItem 推流参数
     * @return 本地端口
     */
    Integer startSendRtpTalk(SendRtpTalkItem sendRtpTalkItem);

    /**
     * 停止 RTP 推流
     *
     * @param closeSendRtpItem 推流标识
     */
    void stopSendRtp(CloseSendRtpItem closeSendRtpItem);

    /**
     * 获取 RTP 推流列表
     *
     * @param filter 可选过滤
     * @return 推流列表
     */
    List<Map<String, Object>> listRtpSender(StreamFilter filter);

    /**
     * 点播 MP4 文件
     *
     * @param filter   流定位
     * @param filePath mp4 绝对路径
     * @param options  额外选项，键同官方参数
     * @return 点播结果
     */
    Mp4LoadInfo loadMp4File(StreamFilter filter, String filePath, Map<String, String> options);

    /**
     * 下载文件
     *
     * @param filePath 文件绝对路径
     * @param saveName 下载文件名，可为空
     * @return 文件二进制
     */
    byte[] downloadFile(String filePath, String saveName);

    /**
     * 注册到 WebRTC 信令服务器
     *
     * @param item 注册参数
     */
    void addWebrtcRoomKeeper(WebrtcRoomKeeperItem item);

    /**
     * 从 WebRTC 信令服务器注销
     *
     * @param item 注册参数
     */
    void delWebrtcRoomKeeper(WebrtcRoomKeeperItem item);

    /**
     * Peer 端查看注册信息
     *
     * @return 注册信息列表
     */
    List<Map<String, Object>> listWebrtcRoomKeepers();

    /**
     * 信令服务器查看注册信息
     *
     * @return 房间信息列表
     */
    List<Map<String, Object>> listWebrtcRooms();

    /**
     * 查看 WebRTCProxyPlayer 连接信息
     *
     * @param localPort 监听端口
     * @return 连接详情
     */
    Map<String, Object> getWebrtcProxyPlayerInfo(String localPort);

    /**
     * 开始多屏拼接
     *
     * @param item 拼接参数
     * @return 任务信息
     */
    Map<String, Object> stackStart(StackStartItem item);

    /**
     * 停止多屏拼接
     *
     * @param id 任务 ID
     */
    void stackStop(String id);

    /**
     * 获取版本信息
     *
     * @return 版本号与提交信息
     */
    Version version();

}
