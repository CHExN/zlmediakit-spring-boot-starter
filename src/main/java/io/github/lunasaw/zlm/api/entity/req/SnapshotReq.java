package io.github.lunasaw.zlm.api.entity.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * 截图任务参数。
 *
 * @param url        需要截图的 URL
 * @param timeoutSec 截图失败超时时间（秒）
 * @param expireSec  截图缓存过期时间（秒）
 * @param savePath   本地保存路径（仅本地使用，不参与序列化）
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SnapshotReq(
        @JsonProperty("url") String url,
        @JsonProperty("timeout_sec") Integer timeoutSec,
        @JsonProperty("expire_sec") Integer expireSec,
        @JsonIgnore String savePath
) {

    public SnapshotReq {
        timeoutSec = timeoutSec == null ? 30 : timeoutSec;
        expireSec = expireSec == null ? 5 : expireSec;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        map.put("timeout_sec", String.valueOf(timeoutSec));
        map.put("expire_sec", String.valueOf(expireSec));
        return map;
    }
}
