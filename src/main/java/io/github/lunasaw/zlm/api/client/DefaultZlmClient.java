package io.github.lunasaw.zlm.api.client;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.lunasaw.zlm.api.client.exception.ZlmClientException;
import io.github.lunasaw.zlm.api.client.exception.ZlmHttpException;
import io.github.lunasaw.zlm.api.client.exception.ZlmNetworkException;
import io.github.lunasaw.zlm.api.client.exception.ZlmProtocolException;
import io.github.lunasaw.zlm.api.client.exception.ZlmServerException;
import io.github.lunasaw.zlm.api.client.http.HttpClient5Executor;
import io.github.lunasaw.zlm.api.client.http.HttpExecutor;
import io.github.lunasaw.zlm.api.client.http.HttpStatusException;
import io.github.lunasaw.zlm.api.client.result.ApiResponse;
import io.github.lunasaw.zlm.api.client.result.ApiResponseForCloseRtpServer;
import io.github.lunasaw.zlm.api.client.result.ApiResponseForCloseStreams;
import io.github.lunasaw.zlm.api.client.result.ApiResponseForGeneral;
import io.github.lunasaw.zlm.api.client.result.ApiResponseForGetRtpInfo;
import io.github.lunasaw.zlm.api.client.result.ApiResponseForOpenRtpServer;
import io.github.lunasaw.zlm.api.client.result.ApiResponseForSendRtp;
import io.github.lunasaw.zlm.api.client.result.ApiResponseForSetServerConfig;
import io.github.lunasaw.zlm.api.entity.CloseSendRtpItem;
import io.github.lunasaw.zlm.api.entity.CloseStreams;
import io.github.lunasaw.zlm.api.entity.ConnectRtpServerItem;
import io.github.lunasaw.zlm.api.entity.DeleteFlag;
import io.github.lunasaw.zlm.api.entity.MediaInfo;
import io.github.lunasaw.zlm.api.entity.MediaPlayerSession;
import io.github.lunasaw.zlm.api.entity.Mp4LoadInfo;
import io.github.lunasaw.zlm.api.entity.Mp4RecordFile;
import io.github.lunasaw.zlm.api.entity.ProxyItemInfo;
import io.github.lunasaw.zlm.api.entity.FFmpegSourceInfo;
import io.github.lunasaw.zlm.api.entity.RtpServerBind;
import io.github.lunasaw.zlm.api.entity.RtpServerMultiplexItem;
import io.github.lunasaw.zlm.api.entity.RtpServerSsrcItem;
import io.github.lunasaw.zlm.api.entity.SendRtpItem;
import io.github.lunasaw.zlm.api.entity.SendRtpPassiveItem;
import io.github.lunasaw.zlm.api.entity.SendRtpTalkItem;
import io.github.lunasaw.zlm.api.entity.SessionFilter;
import io.github.lunasaw.zlm.api.entity.SessionInfo;
import io.github.lunasaw.zlm.api.entity.SnapshotItem;
import io.github.lunasaw.zlm.api.entity.SocketAddressPair;
import io.github.lunasaw.zlm.api.entity.StackStartItem;
import io.github.lunasaw.zlm.api.entity.StartRecordTaskResult;
import io.github.lunasaw.zlm.api.entity.StreamFFmpegItem;
import io.github.lunasaw.zlm.api.entity.StreamFilter;
import io.github.lunasaw.zlm.api.entity.StreamKey;
import io.github.lunasaw.zlm.api.entity.StreamProxyItem;
import io.github.lunasaw.zlm.api.entity.StreamPusherItem;
import io.github.lunasaw.zlm.api.entity.StatisticData;
import io.github.lunasaw.zlm.api.entity.ThreadLoad;
import io.github.lunasaw.zlm.api.entity.Version;
import io.github.lunasaw.zlm.api.entity.WebrtcRoomKeeperItem;
import io.github.lunasaw.zlm.util.JsonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 默认的 ZLM 客户端实现，基于 HttpClient5，持有 host/secret 等状态。
 */
public class DefaultZlmClient implements ZlmClient {

    public static final class Paths {
        public static final String API_INDEX = "/index/api";

        public static final String GET_API_LIST = API_INDEX + "/getApiList";
        public static final String GET_THREADS_LOAD = API_INDEX + "/getThreadsLoad";
        public static final String GET_STATISTIC = API_INDEX + "/getStatistic";
        public static final String GET_WORK_THREADS_LOAD = API_INDEX + "/getWorkThreadsLoad";
        public static final String GET_SERVER_CONFIG = API_INDEX + "/getServerConfig";
        public static final String SET_SERVER_CONFIG = API_INDEX + "/setServerConfig";
        public static final String RESTART_SERVER = API_INDEX + "/restartServer";
        public static final String GET_MEDIA_LIST = API_INDEX + "/getMediaList";
        public static final String DELETE_SNAP_DIRECTORY = API_INDEX + "/deleteSnapDirectory";
        public static final String CLOSE_STREAM = API_INDEX + "/close_stream";
        public static final String CLOSE_STREAMS = API_INDEX + "/close_streams";
        public static final String GET_ALL_SESSION = API_INDEX + "/getAllSession";
        public static final String KICK_SESSION = API_INDEX + "/kick_session";
        public static final String KICK_SESSIONS = API_INDEX + "/kick_sessions";
        public static final String ADD_STREAM_PROXY = API_INDEX + "/addStreamProxy";
        public static final String DEL_STREAM_PROXY = API_INDEX + "/delStreamProxy";
        public static final String LIST_STREAM_PROXY = API_INDEX + "/listStreamProxy";
        public static final String LIST_STREAM_PUSHER_PROXY = API_INDEX + "/listStreamPusherProxy";
        public static final String ADD_STREAM_PUSHER_PROXY = API_INDEX + "/addStreamPusherProxy";
        public static final String DEL_STREAM_PUSHER_PROXY = API_INDEX + "/delStreamPusherProxy";
        public static final String LIST_FFMPEG_SOURCE = API_INDEX + "/listFFmpegSource";
        public static final String ADD_FFMPEG_SOURCE = API_INDEX + "/addFFmpegSource";
        public static final String DEL_FFMPEG_SOURCE = API_INDEX + "/delFFmpegSource";
        public static final String IS_MEDIA_ONLINE = API_INDEX + "/isMediaOnline";
        public static final String GET_MEDIA_PLAYER_LIST = API_INDEX + "/getMediaPlayerList";
        public static final String BROADCAST_MESSAGE = API_INDEX + "/broadcastMessage";
        public static final String GET_MEDIA_INFO = API_INDEX + "/getMediaInfo";
        public static final String GET_MP4_RECORD_FILE = API_INDEX + "/getMP4RecordFile";
        public static final String DELETE_RECORD_DIRECTORY = API_INDEX + "/deleteRecordDirectory";
        public static final String START_RECORD = API_INDEX + "/startRecord";
        public static final String START_RECORD_TASK = API_INDEX + "/startRecordTask";
        public static final String SET_RECORD_SPEED = API_INDEX + "/setRecordSpeed";
        public static final String SEEK_RECORD_STAMP = API_INDEX + "/seekRecordStamp";
        public static final String STOP_RECORD = API_INDEX + "/stopRecord";
        public static final String IS_RECORDING = API_INDEX + "/isRecording";
        public static final String GET_SNAP = API_INDEX + "/getSnap";
        public static final String GET_RTP_INFO = API_INDEX + "/getRtpInfo";
        public static final String OPEN_RTP_SERVER = API_INDEX + "/openRtpServer";
        public static final String OPEN_RTP_SERVER_MULTIPLEX = API_INDEX + "/openRtpServerMultiplex";
        public static final String CONNECT_RTP_SERVER = API_INDEX + "/connectRtpServer";
        public static final String CLOSE_RTP_SERVER = API_INDEX + "/closeRtpServer";
        public static final String UPDATE_RTP_SERVER_SSRC = API_INDEX + "/updateRtpServerSSRC";
        public static final String PAUSE_RTP_CHECK = API_INDEX + "/pauseRtpCheck";
        public static final String RESUME_RTP_CHECK = API_INDEX + "/resumeRtpCheck";
        public static final String LIST_RTP_SERVER = API_INDEX + "/listRtpServer";
        public static final String START_SEND_RTP = API_INDEX + "/startSendRtp";
        public static final String START_SEND_RTP_PASSIVE = API_INDEX + "/startSendRtpPassive";
        public static final String START_SEND_RTP_TALK = API_INDEX + "/startSendRtpTalk";
        public static final String STOP_SEND_RTP = API_INDEX + "/stopSendRtp";
        public static final String LIST_RTP_SENDER = API_INDEX + "/listRtpSender";
        public static final String VERSION = API_INDEX + "/version";
        public static final String GET_PROXY_INFO = API_INDEX + "/getProxyInfo";
        public static final String GET_PROXY_PUSHER_INFO = API_INDEX + "/getProxyPusherInfo";
        public static final String LOAD_MP4_FILE = API_INDEX + "/loadMP4File";
        public static final String DOWNLOAD_FILE = API_INDEX + "/downloadFile";
        public static final String ADD_WEBRTC_ROOM_KEEPER = API_INDEX + "/addWebrtcRoomKeeper";
        public static final String DEL_WEBRTC_ROOM_KEEPER = API_INDEX + "/delWebrtcRoomKeeper";
        public static final String LIST_WEBRTC_ROOM_KEEPERS = API_INDEX + "/listWebrtcRoomKeepers";
        public static final String LIST_WEBRTC_ROOMS = API_INDEX + "/listWebrtcRooms";
        public static final String GET_WEBRTC_PROXY_PLAYER_INFO = API_INDEX + "/getWebrtcProxyPlayerInfo";
        public static final String STACK_START = API_INDEX + "/stack/start";
        public static final String STACK_STOP = API_INDEX + "/stack/stop";
    }

    private final String host;
    private final String secret;
    private final HttpExecutor httpExecutor;
    private final ObjectMapper objectMapper;
    @Deprecated
    private final JavaType mapStringObjectType;
    @Deprecated
    private final JavaType listMapStringObjectType;

    public DefaultZlmClient(String host, String secret) {
        this(host, secret, HttpClient5Executor.builder().build(), JsonUtils.mapper());
    }

    public DefaultZlmClient(String host, String secret, HttpExecutor httpExecutor, ObjectMapper objectMapper) {
        this.host = Objects.requireNonNull(host, "host must not be null");
        this.secret = Objects.requireNonNull(secret, "secret must not be null");
        this.httpExecutor = Objects.requireNonNull(httpExecutor, "httpExecutor must not be null");
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper must not be null");
        this.mapStringObjectType = mapOf(String.class, Object.class);
        this.listMapStringObjectType = listOf(mapStringObjectType);
    }

    @Override
    public List<String> getApiList() {
        return call(Paths.GET_API_LIST, Map.of(), listOf(String.class));
    }

    @Override
    public List<ThreadLoad> getThreadsLoad() {
        return call(Paths.GET_THREADS_LOAD, Map.of(), listOf(ThreadLoad.class));
    }

    @Override
    public List<ThreadLoad> getWorkThreadsLoad() {
        return call(Paths.GET_WORK_THREADS_LOAD, Map.of(), listOf(ThreadLoad.class));
    }

    @Override
    public StatisticData getStatistic() {
        return call(Paths.GET_STATISTIC, Map.of(), typeOf(StatisticData.class));
    }

    @Override
    public Map<String, String> getServerConfig() {
        List<Map<String, String>> config = call(Paths.GET_SERVER_CONFIG, Map.of(), listOf(mapOf(String.class, String.class)));
        Map<String, String> merged = new LinkedHashMap<>();
        if (config != null) {
            config.stream()
                    .filter(Objects::nonNull)
                    .forEach(merged::putAll);
        }
        return merged;
    }

    @Override
    public int setServerConfig(Map<String, String> changeConfigs) {
        ApiResponseForSetServerConfig result = invokeRaw(Paths.SET_SERVER_CONFIG, changeConfigs, typeOf(ApiResponseForSetServerConfig.class));
        ensureSuccess(result);
        return result.changed();
    }

    @Override
    public void restartServer() {
        call(Paths.RESTART_SERVER, Map.of());
    }

    @Override
    public List<MediaInfo> getMediaList(StreamFilter filter) {
        return call(Paths.GET_MEDIA_LIST, JsonUtils.toStringMap(filter), listOf(MediaInfo.class));
    }

    @Override
    public void closeStream(StreamFilter filter) {
        call(Paths.CLOSE_STREAM, JsonUtils.toStringMap(filter));
    }

    @Override
    public CloseStreams closeStreams(StreamFilter filter, Boolean force) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        if (force != null) {
            params.put("force", String.valueOf(force));
        }
        ApiResponseForCloseStreams result = invokeRaw(Paths.CLOSE_STREAMS, params, typeOf(ApiResponseForCloseStreams.class));
        ensureSuccess(result);
        return result;
    }

    @Override
    public void deleteSnapDirectory(StreamFilter filter, String file) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        if (file != null) {
            params.put("file", file);
        }
        call(Paths.DELETE_SNAP_DIRECTORY, params);
    }

    @Override
    public List<SessionInfo> getAllSession(SessionFilter filter) {
        return call(Paths.GET_ALL_SESSION, JsonUtils.toStringMap(filter), listOf(SessionInfo.class));
    }

    @Override
    public void kickSession(String sessionId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", sessionId);
        call(Paths.KICK_SESSION, params);
    }

    @Override
    public void kickSessions(SessionFilter filter) {
        call(Paths.KICK_SESSIONS, JsonUtils.toStringMap(filter));
    }

    @Override
    public String addStreamProxy(StreamProxyItem proxyItem) {
        StreamKey streamKey = call(Paths.ADD_STREAM_PROXY, JsonUtils.toStringMap(proxyItem), typeOf(StreamKey.class));
        return streamKey.key();
    }

    @Override
    public Boolean delStreamProxy(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        DeleteFlag deleteFlag = call(Paths.DEL_STREAM_PROXY, params, typeOf(DeleteFlag.class));
        return deleteFlag.flag();
    }

    @Override
    public List<ProxyItemInfo> listStreamProxy() {
        return call(Paths.LIST_STREAM_PROXY, Map.of(), listOf(ProxyItemInfo.class));
    }

    @Override
    public ProxyItemInfo getProxyInfo(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        return call(Paths.GET_PROXY_INFO, params, typeOf(ProxyItemInfo.class));
    }

    @Override
    public String addStreamPusherProxy(StreamPusherItem item) {
        StreamKey streamKey = call(Paths.ADD_STREAM_PUSHER_PROXY, JsonUtils.toStringMap(item), typeOf(StreamKey.class));
        return streamKey.key();
    }

    @Override
    public Boolean delStreamPusherProxy(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        DeleteFlag deleteFlag = call(Paths.DEL_STREAM_PUSHER_PROXY, params, typeOf(DeleteFlag.class));
        return deleteFlag.flag();
    }

    @Override
    public List<ProxyItemInfo> listStreamPusherProxy() {
        return call(Paths.LIST_STREAM_PUSHER_PROXY, Map.of(), listOf(ProxyItemInfo.class));
    }

    @Override
    public ProxyItemInfo getProxyPusherInfo(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        return call(Paths.GET_PROXY_PUSHER_INFO, params, typeOf(ProxyItemInfo.class));
    }

    @Override
    public String addFFmpegSource(StreamFFmpegItem ffmpegItem) {
        StreamKey streamKey = call(Paths.ADD_FFMPEG_SOURCE, JsonUtils.toStringMap(ffmpegItem), typeOf(StreamKey.class));
        return streamKey.key();
    }

    @Override
    public Boolean delFFmpegSource(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        DeleteFlag deleteFlag = call(Paths.DEL_FFMPEG_SOURCE, params, typeOf(DeleteFlag.class));
        return deleteFlag.flag();
    }

    @Override
    public List<FFmpegSourceInfo> listFFmpegSource() {
        return call(Paths.LIST_FFMPEG_SOURCE, Map.of(), listOf(FFmpegSourceInfo.class));
    }

    @Override
    public boolean isMediaOnline(StreamFilter filter) {
        ApiResponseForGeneral<Void> resp = invoke(Paths.IS_MEDIA_ONLINE, JsonUtils.toStringMap(filter), typeOf(Void.class));
        ensureSuccess(resp);
        return Boolean.TRUE.equals(resp.online());
    }

    @Override
    public List<MediaPlayerSession> getMediaPlayerList(StreamFilter filter) {
        return call(Paths.GET_MEDIA_PLAYER_LIST, JsonUtils.toStringMap(filter), listOf(MediaPlayerSession.class));
    }

    @Override
    public void broadcastMessage(StreamFilter filter, String msg) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        params.put("msg", msg);
        call(Paths.BROADCAST_MESSAGE, params);
    }

    @Override
    public MediaInfo getMediaInfo(StreamFilter filter) {
        return call(Paths.GET_MEDIA_INFO, JsonUtils.toStringMap(filter), typeOf(MediaInfo.class));
    }

    @Override
    public Mp4RecordFile getMp4RecordFile(StreamFilter filter, String customizedPath, String period) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        if (customizedPath != null) {
            params.put("customized_path", customizedPath);
        }
        if (period != null) {
            params.put("period", period);
        }
        return call(Paths.GET_MP4_RECORD_FILE, params, typeOf(Mp4RecordFile.class));
    }

    @Override
    public void deleteRecordDirectory(StreamFilter filter, String period) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        if (period != null) {
            params.put("period", period);
        }
        call(Paths.DELETE_RECORD_DIRECTORY, params);
    }

    @Override
    public boolean startRecord(StreamFilter filter, Integer type, String customizedPath, Integer maxSecond) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        if (type != null) {
            params.put("type", String.valueOf(type));
        }
        if (customizedPath != null) {
            params.put("customized_path", customizedPath);
        }
        if (maxSecond != null) {
            params.put("max_second", String.valueOf(maxSecond));
        }
        ApiResponseForGeneral<Void> resp = invoke(Paths.START_RECORD, params, typeOf(Void.class));
        ensureSuccess(resp);
        return Boolean.TRUE.equals(resp.result());
    }

    @Override
    public StartRecordTaskResult startRecordTask(StreamFilter filter, String path, Integer backMs, Integer forwardMs) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        params.put("path", path);
        if (backMs != null) {
            params.put("back_ms", String.valueOf(backMs));
        }
        if (forwardMs != null) {
            params.put("forward_ms", String.valueOf(forwardMs));
        }
        return call(Paths.START_RECORD_TASK, params, typeOf(StartRecordTaskResult.class));
    }

    @Override
    public void setRecordSpeed(StreamFilter filter, Double speed) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        if (speed != null) {
            params.put("speed", String.valueOf(speed));
        }
        call(Paths.SET_RECORD_SPEED, params);
    }

    @Override
    public void seekRecordStamp(StreamFilter filter, Long stamp) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        if (stamp != null) {
            params.put("stamp", String.valueOf(stamp));
        }
        call(Paths.SEEK_RECORD_STAMP, params);
    }

    @Override
    public void stopRecord(StreamFilter filter, Integer type) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        if (type != null) {
            params.put("type", String.valueOf(type));
        }
        call(Paths.STOP_RECORD, params);
    }

    @Override
    public boolean isRecording(StreamFilter filter, Integer type) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        if (type != null) {
            params.put("type", String.valueOf(type));
        }
        ApiResponseForGeneral<Void> resp = invoke(Paths.IS_RECORDING, params, typeOf(Void.class));
        ensureSuccess(resp);
        return Boolean.TRUE.equals(resp.status());
    }

    @Override
    public byte[] getSnap(SnapshotItem snapItem) {
        return postBytes(Paths.GET_SNAP, JsonUtils.toStringMap(snapItem), true);
    }

    @Override
    public SocketAddressPair getRtpInfo(String streamId) {
        Map<String, String> params = new HashMap<>();
        params.put("stream_id", streamId);
        ApiResponseForGetRtpInfo result = invokeRaw(Paths.GET_RTP_INFO, params, typeOf(ApiResponseForGetRtpInfo.class));
        ensureSuccess(result);
        if (!Boolean.TRUE.equals(result.exist())) {
            return null;
        }
        return result;
    }

    @Override
    public Integer openRtpServer(Integer tcpMode, RtpServerBind rtpServerBind) {
        Map<String, String> params = JsonUtils.toStringMap(rtpServerBind);
        if (tcpMode != null) {
            params.put("tcp_mode", String.valueOf(tcpMode));
        }
        ApiResponseForOpenRtpServer result = invokeRaw(Paths.OPEN_RTP_SERVER, params, typeOf(ApiResponseForOpenRtpServer.class));
        ensureSuccess(result);
        return result.port();
    }

    @Override
    public Integer openRtpServerMultiplex(RtpServerMultiplexItem item) {
        ApiResponseForOpenRtpServer result = invokeRaw(Paths.OPEN_RTP_SERVER_MULTIPLEX, JsonUtils.toStringMap(item), typeOf(ApiResponseForOpenRtpServer.class));
        ensureSuccess(result);
        return result.port();
    }

    @Override
    public void connectRtpServer(ConnectRtpServerItem item) {
        call(Paths.CONNECT_RTP_SERVER, JsonUtils.toStringMap(item));
    }

    @Override
    public Integer closeRtpServer(String streamId) {
        Map<String, String> params = new HashMap<>();
        params.put("stream_id", streamId);
        ApiResponseForCloseRtpServer result = invokeRaw(Paths.CLOSE_RTP_SERVER, params, typeOf(ApiResponseForCloseRtpServer.class));
        ensureSuccess(result);
        return result.hit();
    }

    @Override
    public void updateRtpServerSSRC(RtpServerSsrcItem item) {
        call(Paths.UPDATE_RTP_SERVER_SSRC, JsonUtils.toStringMap(item));
    }

    @Override
    public void pauseRtpCheck(String streamId) {
        Map<String, String> params = new HashMap<>();
        params.put("stream_id", streamId);
        call(Paths.PAUSE_RTP_CHECK, params);
    }

    @Override
    public void resumeRtpCheck(String streamId) {
        Map<String, String> params = new HashMap<>();
        params.put("stream_id", streamId);
        call(Paths.RESUME_RTP_CHECK, params);
    }

    @Override
    public List<RtpServerBind> listRtpServer() {
        return call(Paths.LIST_RTP_SERVER, Map.of(), listOf(RtpServerBind.class));
    }

    @Override
    public Integer startSendRtp(SendRtpItem sendRtpItem) {
        ApiResponseForSendRtp result = invokeRaw(Paths.START_SEND_RTP, JsonUtils.toStringMap(sendRtpItem), typeOf(ApiResponseForSendRtp.class));
        ensureSuccess(result);
        return result.localPort();
    }

    @Override
    public Integer startSendRtpPassive(SendRtpPassiveItem sendRtpPassiveItem) {
        ApiResponseForSendRtp result = invokeRaw(Paths.START_SEND_RTP_PASSIVE, JsonUtils.toStringMap(sendRtpPassiveItem), typeOf(ApiResponseForSendRtp.class));
        ensureSuccess(result);
        return result.localPort();
    }

    @Override
    public Integer startSendRtpTalk(SendRtpTalkItem sendRtpTalkItem) {
        ApiResponseForSendRtp result = invokeRaw(Paths.START_SEND_RTP_TALK, JsonUtils.toStringMap(sendRtpTalkItem), typeOf(ApiResponseForSendRtp.class));
        ensureSuccess(result);
        return result.localPort();
    }

    @Override
    public void stopSendRtp(CloseSendRtpItem closeSendRtpItem) {
        ApiResponseForGeneral<Void> result = invoke(Paths.STOP_SEND_RTP, JsonUtils.toStringMap(closeSendRtpItem), typeOf(Void.class));
        ensureSuccess(result);
    }

    @Override
    public List<Map<String, Object>> listRtpSender(StreamFilter filter) {
        return call(Paths.LIST_RTP_SENDER, JsonUtils.toStringMap(filter), listMapStringObjectType);
    }

    @Override
    public Mp4LoadInfo loadMp4File(StreamFilter filter, String filePath, Map<String, String> options) {
        Map<String, String> params = JsonUtils.toStringMap(filter);
        params.put("file_path", filePath);
        if (options != null) {
            options.forEach((k, v) -> {
                if (v != null) {
                    params.put(k, v);
                }
            });
        }
        return call(Paths.LOAD_MP4_FILE, params, typeOf(Mp4LoadInfo.class));
    }

    @Override
    public byte[] downloadFile(String filePath, String saveName) {
        Map<String, String> params = new HashMap<>();
        params.put("file_path", filePath);
        if (saveName != null) {
            params.put("save_name", saveName);
        }
        return postBytes(Paths.DOWNLOAD_FILE, params, false);
    }

    @Override
    public void addWebrtcRoomKeeper(WebrtcRoomKeeperItem item) {
        call(Paths.ADD_WEBRTC_ROOM_KEEPER, JsonUtils.toStringMap(item));
    }

    @Override
    public void delWebrtcRoomKeeper(WebrtcRoomKeeperItem item) {
        call(Paths.DEL_WEBRTC_ROOM_KEEPER, JsonUtils.toStringMap(item));
    }

    @Override
    public List<Map<String, Object>> listWebrtcRoomKeepers() {
        return call(Paths.LIST_WEBRTC_ROOM_KEEPERS, Map.of(), listMapStringObjectType);
    }

    @Override
    public List<Map<String, Object>> listWebrtcRooms() {
        return call(Paths.LIST_WEBRTC_ROOMS, Map.of(), listMapStringObjectType);
    }

    @Override
    public Map<String, Object> getWebrtcProxyPlayerInfo(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        return call(Paths.GET_WEBRTC_PROXY_PLAYER_INFO, params, mapStringObjectType);
    }

    @Override
    public Map<String, Object> stackStart(StackStartItem item) {
        ApiResponseForGeneral<Map<String, Object>> result = invokeJson(Paths.STACK_START, item, mapStringObjectType);
        return unwrap(result);
    }

    @Override
    public void stackStop(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        call(Paths.STACK_STOP, params);
    }

    @Override
    public Version version() {
        return call(Paths.VERSION, Map.of(), typeOf(Version.class));
    }

    @Override
    public void close() throws Exception {
        httpExecutor.close();
    }

    /**
     * 调用无需返回数据的接口，自动附带 secret。
     *
     * @param apiPath API 路径
     * @param params  表单参数
     */
    protected void call(String apiPath, Map<String, String> params) {
        ApiResponseForGeneral<Void> response = invoke(apiPath, params, typeOf(Void.class));
        unwrap(response);
    }

    /**
     * 调用返回数据的接口，自动附带 secret。
     *
     * @param apiPath  API 路径
     * @param params   表单参数
     * @param dataType 数据类型
     */
    protected <T> T call(String apiPath, Map<String, String> params, JavaType dataType) {
        ApiResponseForGeneral<T> response = invoke(apiPath, params, dataType);
        return unwrap(response);
    }

    /**
     * 表单方式调用并按泛型解析响应。
     *
     * @param apiPath  API 路径
     * @param params   表单参数
     * @param dataType data 字段类型
     */
    protected <T> ApiResponseForGeneral<T> invoke(String apiPath, Map<String, String> params, JavaType dataType) {
        String json = post(apiPath, params, true);
        JavaType responseType = objectMapper.getTypeFactory()
                .constructParametricType(ApiResponseForGeneral.class, dataType);
        try {
            return objectMapper.readValue(json, responseType);
        } catch (IOException e) {
            throw new ZlmProtocolException("Failed to parse response for " + apiPath, e);
        }
    }

    /**
     * 表单方式调用并解析为任意类型（不要求实现 ApiResponse）。
     */
    protected <T> T invokeRaw(String apiPath, Map<String, String> params, JavaType dataType) {
        String json = post(apiPath, params, true);
        try {
            return objectMapper.readValue(json, dataType);
        } catch (IOException e) {
            throw new ZlmProtocolException("Failed to parse raw response for " + apiPath, e);
        }
    }

    /**
     * JSON 方式调用并按泛型解析响应，secret 通过 query 透传。
     *
     * @param apiPath  API 路径
     * @param payload  请求体对象
     * @param dataType data 字段类型
     */
    protected <T> ApiResponseForGeneral<T> invokeJson(String apiPath, Object payload, JavaType dataType) {
        String body;
        try {
            body = objectMapper.writeValueAsString(payload);
        } catch (IOException e) {
            throw new ZlmProtocolException("Failed to serialize payload for " + apiPath, e);
        }
        String json = postJson(apiPath, body);
        JavaType responseType = objectMapper.getTypeFactory()
                .constructParametricType(ApiResponseForGeneral.class, dataType);
        try {
            return objectMapper.readValue(json, responseType);
        } catch (IOException e) {
            throw new ZlmProtocolException("Failed to parse response for " + apiPath, e);
        }
    }

    protected byte[] postBytes(String apiPath, Map<String, String> params, boolean includeSecret) {
        Map<String, String> allParams = includeSecret ? withSecret(params) : params;
        try {
            return httpExecutor.postFormForBytes(apiUrl(apiPath), allParams);
        } catch (HttpStatusException e) {
            throw new ZlmHttpException(e.getStatusCode(), "HTTP error when calling " + apiPath, e);
        } catch (IOException e) {
            throw new ZlmNetworkException("Network error when calling " + apiPath, e);
        }
    }

    protected String postJson(String apiPath, String json) {
        try {
            return httpExecutor.postJson(apiUrlWithSecret(apiPath), json);
        } catch (HttpStatusException e) {
            throw new ZlmHttpException(e.getStatusCode(), "HTTP error when calling " + apiPath, e);
        } catch (IOException e) {
            throw new ZlmNetworkException("Network error when calling " + apiPath, e);
        }
    }

    protected String post(String apiPath, Map<String, String> params, boolean includeSecret) {
        Map<String, String> allParams = includeSecret ? withSecret(params) : params;
        try {
            return httpExecutor.postForm(apiUrl(apiPath), allParams);
        } catch (HttpStatusException e) {
            throw new ZlmHttpException(e.getStatusCode(), "HTTP error when calling " + apiPath, e);
        } catch (IOException e) {
            throw new ZlmNetworkException("Network error when calling " + apiPath, e);
        }
    }


    /// ====== 响应辅助方法 ======

    /**
     * 确保响应表示成功，否则抛出异常
     *
     * @param resp ZLM API 返回响应
     * @throws ZlmClientException 如果响应为空或缺少 code 字段
     * @throws ZlmServerException 如果响应 code 不为 0
     */
    protected void ensureSuccess(ApiResponse resp) {
        if (resp == null) {
            throw new ZlmClientException("Empty response from ZLMediaKit");
        }
        if (resp.code() == null) {
            throw new ZlmClientException("Missing code in response");
        }
        if (resp.code() != 0) {
            throw new ZlmServerException(resp.code(), resp.msg());
        }
    }

    /**
     * 解包响应数据，确保响应表示成功
     *
     * @param resp ZLM API 返回响应
     * @param <T>  响应数据类型
     * @return 解包后的响应数据
     * @throws ZlmClientException 如果响应为空或缺少 code 字段
     * @throws ZlmServerException 如果响应 code 不为 0
     */
    protected <T> T unwrap(ApiResponseForGeneral<T> resp) {
        ensureSuccess(resp);
        return resp.data();
    }


    /// ====== URL 和参数辅助方法 ======

    protected String apiUrl(String apiPath) {
        if (apiPath.startsWith("/")) {
            return host + apiPath;
        }
        return host + "/" + apiPath;
    }

    protected String apiUrlWithSecret(String apiPath) {
        String base = apiUrl(apiPath);
        return base.contains("?") ? base + "&secret=" + secret : base + "?secret=" + secret;
    }

    protected Map<String, String> withSecret(Map<String, String> params) {
        Map<String, String> merged = new HashMap<>();
        if (params != null) {
            merged.putAll(params);
        }
        if (!merged.containsKey("secret") || merged.get("secret") == null) {
            merged.put("secret", secret);
        }
        return merged;
    }


    /// ====== 类型辅助方法 ======

    protected JavaType typeOf(Class<?> clazz) {
        return objectMapper.getTypeFactory().constructType(clazz);
    }

    protected JavaType listOf(Class<?> elementClass) {
        return objectMapper.getTypeFactory().constructCollectionType(List.class, elementClass);
    }

    protected JavaType listOf(JavaType elementType) {
        return objectMapper.getTypeFactory().constructCollectionType(List.class, elementType);
    }

    protected JavaType mapOf(Class<?> keyClass, Class<?> valueClass) {
        return objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
    }

}
