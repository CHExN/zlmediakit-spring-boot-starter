package io.github.lunasaw.zlm.api.entity;

public class ServerConfig {

    public interface Keys {

        interface API {
            String PREFIX = "api";
            String API_DEBUG = "api.apiDebug";
            String SECRET = "api.secret";
            String SNAP_ROOT = "api.snapRoot";
            String DEFAULT_SNAP = "api.defaultSnap";
            String DOWNLOAD_ROOT = "api.downloadRoot";
        }

        interface FFMPEG {
            String PREFIX = "ffmpeg";
            String BIN = "ffmpeg.bin";
            String CMD = "ffmpeg.cmd";
            String SNAP = "ffmpeg.snap";
            String LOG = "ffmpeg.log";
            String RESTART_SEC = "ffmpeg.restart_sec";
        }

        interface PROTOCOL {
            String PREFIX = "protocol";
            String MODIFY_STAMP = "protocol.modify_stamp";
            String ENABLE_AUDIO = "protocol.enable_audio";
            String ADD_MUTE_AUDIO = "protocol.add_mute_audio";
            String AUTO_CLOSE = "protocol.auto_close";
            String CONTINUE_PUSH_MS = "protocol.continue_push_ms";
            String PACED_SENDER_MS = "protocol.paced_sender_ms";
            String ENABLE_HLS = "protocol.enable_hls";
            String ENABLE_HLS_FMP4 = "protocol.enable_hls_fmp4";
            String ENABLE_MP4 = "protocol.enable_mp4";
            String ENABLE_RTSP = "protocol.enable_rtsp";
            String ENABLE_RTMP = "protocol.enable_rtmp";
            String ENABLE_TS = "protocol.enable_ts";
            String ENABLE_FMP4 = "protocol.enable_fmp4";
            String MP4_AS_PLAYER = "protocol.mp4_as_player";
            String MP4_MAX_SECOND = "protocol.mp4_max_second";
            String MP4_SAVE_PATH = "protocol.mp4_save_path";
            String HLS_SAVE_PATH = "protocol.hls_save_path";
            String HLS_DEMAND = "protocol.hls_demand";
            String RTSP_DEMAND = "protocol.rtsp_demand";
            String RTMP_DEMAND = "protocol.rtmp_demand";
            String TS_DEMAND = "protocol.ts_demand";
            String FMP4_DEMAND = "protocol.fmp4_demand";
        }

        interface GENERAL {
            String PREFIX = "general";
            String ENABLE_VHOST = "general.enableVhost";
            String FLOW_THRESHOLD = "general.flowThreshold";
            String MAX_STREAM_WAIT_MS = "general.maxStreamWaitMS";
            String STREAM_NONE_READER_DELAY_MS = "general.streamNoneReaderDelayMS";
            String RESET_WHEN_RE_PLAY = "general.resetWhenRePlay";
            String MERGE_WRITE_MS = "general.mergeWriteMS";
            String MEDIA_SERVER_ID = "general.mediaServerId";
            String WAIT_TRACK_READY_MS = "general.wait_track_ready_ms";
            String WAIT_AUDIO_TRACK_DATA_MS = "general.wait_audio_track_data_ms";
            String WAIT_ADD_TRACK_MS = "general.wait_add_track_ms";
            String UNREADY_FRAME_CACHE = "general.unready_frame_cache";
            String BROADCAST_PLAYER_COUNT_CHANGED = "general.broadcast_player_count_changed";
            String LISTEN_IP = "general.listen_ip";
        }

        interface HLS {
            String PREFIX = "hls";
            String FILE_BUF_SIZE = "hls.fileBufSize";
            String SEG_DUR = "hls.segDur";
            String SEG_NUM = "hls.segNum";
            String SEG_DELAY = "hls.segDelay";
            String SEG_RETAIN = "hls.segRetain";
            String BROADCAST_RECORD_TS = "hls.broadcastRecordTs";
            String DELETE_DELAY_SEC = "hls.deleteDelaySec";
            String SEG_KEEP = "hls.segKeep";
            String FAST_REGISTER = "hls.fastRegister";
        }

        interface HOOK {
            String PREFIX = "hook";
            String ENABLE = "hook.enable";
            String ON_FLOW_REPORT = "hook.on_flow_report";
            String ON_HTTP_ACCESS = "hook.on_http_access";
            String ON_PLAY = "hook.on_play";
            String ON_PUBLISH = "hook.on_publish";
            String ON_RECORD_MP4 = "hook.on_record_mp4";
            String ON_RECORD_TS = "hook.on_record_ts";
            String ON_RTSP_AUTH = "hook.on_rtsp_auth";
            String ON_RTSP_REALM = "hook.on_rtsp_realm";
            String ON_SHELL_LOGIN = "hook.on_shell_login";
            String ON_STREAM_CHANGED = "hook.on_stream_changed";
            String STREAM_CHANGED_SCHEMAS = "hook.stream_changed_schemas";
            String ON_STREAM_NONE_READER = "hook.on_stream_none_reader";
            String ON_STREAM_NOT_FOUND = "hook.on_stream_not_found";
            String ON_SERVER_STARTED = "hook.on_server_started";
            String ON_SERVER_EXITED = "hook.on_server_exited";
            String ON_SERVER_KEEPALIVE = "hook.on_server_keepalive";
            String ON_SEND_RTP_STOPPED = "hook.on_send_rtp_stopped";
            String ON_RTP_SERVER_TIMEOUT = "hook.on_rtp_server_timeout";
            String TIMEOUT_SEC = "hook.timeoutSec";
            String ALIVE_INTERVAL = "hook.alive_interval";
            String RETRY = "hook.retry";
            String RETRY_DELAY = "hook.retry_delay";
        }

        interface CLUSTER {
            String PREFIX = "cluster";
            String ORIGIN_URL = "cluster.origin_url";
            String TIMEOUT_SEC = "cluster.timeout_sec";
            String RETRY_COUNT = "cluster.retry_count";
        }

        interface HTTP {
            String PREFIX = "http";
            String CHAR_SET = "http.charSet";
            String KEEP_ALIVE_SECOND = "http.keepAliveSecond";
            String MAX_REQ_SIZE = "http.maxReqSize";
            String PORT = "http.port";
            String ROOT_PATH = "http.rootPath";
            String SEND_BUF_SIZE = "http.sendBufSize";
            String SSL_PORT = "http.sslport";
            String DIR_MENU = "http.dirMenu";
            String VIRTUAL_PATH = "http.virtualPath";
            String FORBID_CACHE_SUFFIX = "http.forbidCacheSuffix";
            String FORWARDED_IP_HEADER = "http.forwarded_ip_header";
            String ALLOW_CROSS_DOMAINS = "http.allow_cross_domains";
            String ALLOW_IP_RANGE = "http.allow_ip_range";
        }

        interface MULTICAST {
            String PREFIX = "multicast";
            String ADDR_MAX = "multicast.addrMax";
            String ADDR_MIN = "multicast.addrMin";
            String UDP_TTL = "multicast.udpTTL";
        }

        interface RECORD {
            String PREFIX = "record";
            String APP_NAME = "record.appName";
            String FILE_BUF_SIZE = "record.fileBufSize";
            String SAMPLE_MS = "record.sampleMS";
            String FAST_START = "record.fastStart";
            String FILE_REPEAT = "record.fileRepeat";
            String ENABLE_FMP4 = "record.enableFmp4";
        }

        interface RTMP {
            String PREFIX = "rtmp";
            String HANDSHAKE_SECOND = "rtmp.handshakeSecond";
            String KEEP_ALIVE_SECOND = "rtmp.keepAliveSecond";
            String PORT = "rtmp.port";
            String SSL_PORT = "rtmp.sslport";
            String DIRECT_PROXY = "rtmp.directProxy";
            String ENHANCED = "rtmp.enhanced";
        }

        interface RTP {
            String PREFIX = "rtp";
            String AUDIO_MTU_SIZE = "rtp.audioMtuSize";
            String VIDEO_MTU_SIZE = "rtp.videoMtuSize";
            String RTP_MAX_SIZE = "rtp.rtpMaxSize";
            String LOW_LATENCY = "rtp.lowLatency";
            String H264_STAP_A = "rtp.h264_stap_a";
        }

        interface RTP_PROXY {
            String PREFIX = "rtp_proxy";
            String DUMP_DIR = "rtp_proxy.dumpDir";
            String PORT = "rtp_proxy.port";
            String TIMEOUT_SEC = "rtp_proxy.timeoutSec";
            String PORT_RANGE = "rtp_proxy.port_range";
            String H264_PT = "rtp_proxy.h264_pt";
            String H265_PT = "rtp_proxy.h265_pt";
            String PS_PT = "rtp_proxy.ps_pt";
            String OPUS_PT = "rtp_proxy.opus_pt";
            String GOP_CACHE = "rtp_proxy.gop_cache";
            String RTP_G711_DUR_MS = "rtp_proxy.rtp_g711_dur_ms";
            String UDP_RECV_SOCKET_BUFFER = "rtp_proxy.udp_recv_socket_buffer";
            String MERGE_FRAME = "rtp_proxy.merge_frame";
        }

        interface RTC {
            String PREFIX = "rtc";
            String SIGNALING_PORT = "rtc.signalingPort";
            String SIGNALING_SSL_PORT = "rtc.signalingSslPort";
            String ICE_PORT = "rtc.icePort";
            String ICE_TCP_PORT = "rtc.iceTcpPort";
            String ENABLE_TURN = "rtc.enableTurn";
            String ICE_TRANSPORT_POLICY = "rtc.iceTransportPolicy";
            String ICE_UFRAG = "rtc.iceUfrag";
            String ICE_PWD = "rtc.icePwd";
            String DATACHANNEL_ECHO = "rtc.datachannel_echo";
            String MAX_STUN_RETRY = "rtc.max_stun_retry";
            String PORT_RANGE = "rtc.port_range";
            String TIMEOUT_SEC = "rtc.timeoutSec";
            String EXTERN_IP = "rtc.externIP";
            String INTERFACES = "rtc.interfaces";
            String PORT = "rtc.port";
            String TCP_PORT = "rtc.tcpPort";
            String REMB_BIT_RATE = "rtc.rembBitRate";
            String PREFERRED_CODEC_A = "rtc.preferredCodecA";
            String PREFERRED_CODEC_V = "rtc.preferredCodecV";
            String START_BITRATE = "rtc.start_bitrate";
            String MAX_BITRATE = "rtc.max_bitrate";
            String MIN_BITRATE = "rtc.min_bitrate";
            String MAX_RTP_CACHE_MS = "rtc.maxRtpCacheMS";
            String MAX_RTP_CACHE_SIZE = "rtc.maxRtpCacheSize";
            String NACK_MAX_SIZE = "rtc.nackMaxSize";
            String NACK_MAX_MS = "rtc.nackMaxMS";
            String NACK_MAX_COUNT = "rtc.nackMaxCount";
            String NACK_INTERVAL_RATIO = "rtc.nackIntervalRatio";
            String NACK_RTP_SIZE = "rtc.nackRtpSize";
            String BFILTER = "rtc.bfilter";
        }

        interface SRT {
            String PREFIX = "srt";
            String TIMEOUT_SEC = "srt.timeoutSec";
            String PORT = "srt.port";
            String LATENCY_MUL = "srt.latencyMul";
            String PKT_BUF_SIZE = "srt.pktBufSize";
            String PASS_PHRASE = "srt.passPhrase";
        }

        interface RTSP {
            String PREFIX = "rtsp";
            String AUTH_BASIC = "rtsp.authBasic";
            String DIRECT_PROXY = "rtsp.directProxy";
            String HANDSHAKE_SECOND = "rtsp.handshakeSecond";
            String KEEP_ALIVE_SECOND = "rtsp.keepAliveSecond";
            String PORT = "rtsp.port";
            String SSL_PORT = "rtsp.sslport";
            String LOW_LATENCY = "rtsp.lowLatency";
            String RTP_TRANSPORT_TYPE = "rtsp.rtpTransportType";
        }

        interface SHELL {
            String PREFIX = "shell";
            String MAX_REQ_SIZE = "shell.maxReqSize";
            String PORT = "shell.port";
        }

        interface ONVIF {
            String PREFIX = "onvif";
            String PORT = "onvif.port";
        }
    }
}
