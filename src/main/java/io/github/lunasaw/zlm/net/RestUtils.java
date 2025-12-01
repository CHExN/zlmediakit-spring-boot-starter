package io.github.lunasaw.zlm.net;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luna
 */
public class RestUtils {

    public static String doGet(String host, String path, Map<String, String> headers,
                               Map<String, String> queries, boolean isEnsure) {
        return HttpUtils.doGetHandler(host, path, headers, queries);
    }

    public static String doGet(String host, String path, Map<String, String> headers,
                               Map<String, String> queries) {
        return HttpUtils.doGetHandler(host, path, headers, queries);
    }

    public static String doDelete(String host, String path, Map<String, String> headers,
                                  Map<String, String> queries, String body) {
        return HttpUtils.doDeleteHandler(host, path, headers, queries, body);
    }

    public static String doPut(String host, String path, Map<String, String> headers,
                               Map<String, String> queries, String body) {
        return HttpUtils.doPutHandler(host, path, headers, queries, body);
    }

    public static String doPost(String host, String path, Map<String, String> headers,
                                Map<String, String> queries, String body) {
        if (headers == null || headers.isEmpty()) {
            headers = new HashMap<>();
        }
        headers.put(HttpContentTypeEnum.CONTENT_TYPE_JSON.getKey(), HttpContentTypeEnum.CONTENT_TYPE_JSON.getValue());
        return HttpUtils.doPostHandler(host, path, headers, queries, body);
    }

    public static String doPost(String host, String path, Map<String, String> headers,
                                Map<String, String> queries, String body, boolean isEnsure) {
        if (headers == null || headers.isEmpty()) {
            headers = new HashMap<>();
        }
        headers.put(HttpContentTypeEnum.CONTENT_TYPE_JSON.getKey(), HttpContentTypeEnum.CONTENT_TYPE_JSON.getValue());
        return HttpUtils.doPostHandler(host, path, headers, queries, body);
    }
}
